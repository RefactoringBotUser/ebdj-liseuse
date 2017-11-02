package fr.qp1c.ebdj.liseuse.moteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurQALSDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;

public class MoteurQALS implements Moteur {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MoteurQALS.class);

    private Map<String, Boolean> themes4ALSLecture;

    // Contraintes

    private NiveauPartie niveauPartie;

    // Tracker

    private Lecteur lecteur;

    private DBConnecteurQALSDao dbConnecteurQALSDao;

    // Constructeur

    public MoteurQALS() {
        // Chargement des questions.
        this.themes4ALSLecture = new HashMap<>();

        this.niveauPartie = NiveauPartie.MOYEN;

        this.dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
    }

    @Override
    public void definirLecteur(Lecteur lecteur) {
        this.lecteur = lecteur;
    }

    @Override
    public void definirNiveauPartie(NiveauPartie niveauPartie) {
        this.niveauPartie = niveauPartie;
    }

    public Map<String, Theme4ALS> tirerThemes() {
        themes4ALSLecture = new HashMap<>();

        Map<String, Theme4ALS> themes4ALS = listerThemesJouables(niveauPartie);

        // TODO mettre le questionnaire non-prioritaire avec un système de
        // pondération

        for (Theme4ALS theme4ALS : themes4ALS.values()) {
            themes4ALSLecture.put(theme4ALS.getReference(), Boolean.FALSE);

            marquerThemePresente(theme4ALS.getReference());
        }

        return themes4ALS;
    }

    /**
     * {@inheritDoc} - V1
     * 
     */
    public Map<String, Theme4ALS> listerThemesJouables(NiveauPartie niveauPartie) throws DBManagerException {

        Map<String, Theme4ALS> themes4ALS = new HashMap<>();
        DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();

        // Lister les niveaux possibles en fonction du niveau
        int niveau = donnerNiveauJouable(niveauPartie);

        LOGGER.debug("Niveau du questionnaire : {}", niveau);

        // TODO gérer les pénuries

        // TODO gérer si il manque un questionnaire dans une catégorie pour un
        // niveau
        // donné
        // TODO prendre en compte les thèmes présentés
        // TODO jouer en priorité dans des catégories manquantes

        for (int groupeCategorie = 1; groupeCategorie <= 4; groupeCategorie++) {

            Theme4ALS theme4ALS = dbConnecteurQALSDao.donnerTheme(groupeCategorie, niveau);
            if (themes4ALS == null) {
                LOGGER.debug("Attention le theme de 4ALS pour le groupe {} est introuvable (=null).", groupeCategorie);
            }

            themes4ALS.put(String.valueOf(groupeCategorie), theme4ALS);
        }

        return themes4ALS;
    }

    private int donnerNiveauJouable(NiveauPartie niveauPartie) {
        List<Integer> niveauJouable = new ArrayList<>();

        if (NiveauPartie.FACILE.equals(niveauPartie)) {
            niveauJouable.add(2);
            niveauJouable.add(3);
            niveauJouable.add(4);
        } else if (NiveauPartie.MOYEN.equals(niveauPartie)) {
            niveauJouable.add(1);
            niveauJouable.add(2);
            niveauJouable.add(3);
            niveauJouable.add(4);
        } else if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
            niveauJouable.add(1);
            niveauJouable.add(2);
            niveauJouable.add(3);
        }

        int indexRandom = new Random().nextInt(niveauJouable.size());

        LOGGER.debug("Niveau à jouer : {}", niveauJouable.get(indexRandom));

        return niveauJouable.get(indexRandom);
    }

    public void jouerTheme(String referenceTheme) {
        dbConnecteurQALSDao.marquerThemeJoue(referenceTheme, lecteur.formatterNomUtilisateur());
    }

    public void annulerTheme(String referenceTheme) {
        dbConnecteurQALSDao.annulerMarquerThemeJoue(referenceTheme, lecteur.formatterNomUtilisateur());
    }

    public void marquerThemePresente(String referenceTheme) {
        dbConnecteurQALSDao.marquerThemePresente(referenceTheme, lecteur.formatterNomUtilisateur());
    }

    public void signalerAnomalie(SignalementAnomalie signalementAnomalie, Theme4ALS theme4ALS) {
        dbConnecteurQALSDao.signalerAnomalie(theme4ALS.getReference(), theme4ALS.getVersion(), signalementAnomalie,
                lecteur.formatterNomUtilisateur());
    }

    @Override
    public void signalerAnomalie(SignalementAnomalie signalementAnomalie) {
        // TODO Auto-generated method stub

    }

}
