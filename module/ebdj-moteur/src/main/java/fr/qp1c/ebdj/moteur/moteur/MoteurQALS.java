package fr.qp1c.ebdj.moteur.moteur;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurQALSDaoImpl;

public class MoteurQALS {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurQALS.class);

	private Map<String, Boolean> themes4ALSLecture;

	// Contraintes

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	// Constructeur

	public MoteurQALS() {

		// Chargement des questions.
		themes4ALSLecture = new HashMap<>();

		this.niveauPartie = NiveauPartie.MOYEN;
	}

	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	public Map<Integer, Theme4ALS> tirerThemes() {

		Map<Integer, Theme4ALS> themes4ALS = new HashMap<>();

		Theme4ALS theme_4ALS = new Theme4ALS();
		theme_4ALS.setTheme("Theme");
		theme_4ALS.setVersion(Long.valueOf(1));
		theme_4ALS.setSource(new Source("LILLE"));
		theme_4ALS.setQuestions(new HashMap<>());

		themes4ALS.put(1, theme_4ALS);
		themes4ALS.put(2, theme_4ALS);
		themes4ALS.put(3, theme_4ALS);
		themes4ALS.put(4, theme_4ALS);

		if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
			// TODO : tirer au sort
		} else if (NiveauPartie.FACILE.equals(niveauPartie)) {
			// TODO : tirer au sort
		} else if (NiveauPartie.MOYEN.equals(niveauPartie)) {
			// TODO : tirer au sort
		}

		// TODO mettre le questionnaire non-prioritaire avec un système de
		// pondération

		for (Theme4ALS theme4ALS : themes4ALS.values()) {
			themes4ALSLecture.put(theme4ALS.getReference(), Boolean.FALSE);

			// marquerThemePropose(theme4ALS.getReference());
		}

		return themes4ALS;
	}

	public void jouerTheme(String referenceTheme) {
		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
		dbConnecteurQALSDao.marquerThemeJoue(referenceTheme, lecteur.formatterNomUtilisateur());

	}

	public void annulerTheme(String referenceTheme) {
		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
		dbConnecteurQALSDao.annulerMarquerThemeJoue(referenceTheme, lecteur.formatterNomUtilisateur());
	}

	public void marquerThemePropose(String referenceTheme) {
		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
		dbConnecteurQALSDao.marquerThemePropose(referenceTheme, lecteur.formatterNomUtilisateur());
	}

	public void signalerAnomalie(SignalementAnomalie signalementAnomalie, Theme4ALS theme4ALS) {

		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
		dbConnecteurQALSDao.signalerAnomalie(theme4ALS.getReference(), theme4ALS.getVersion(), signalementAnomalie,
				lecteur.formatterNomUtilisateur());
	}

	// Getters - setters

}
