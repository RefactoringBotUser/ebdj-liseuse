package fr.qp1c.ebdj.moteur.moteur;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurQALSDaoImpl;

public class MoteurQALS {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurQALS.class);

	private Map<String, Boolean> themes4ALS;

	// Contraintes

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	// Constructeur

	public MoteurQALS() {

		// Chargement des questions.
		themes4ALS = new HashMap<>();

		this.niveauPartie = NiveauPartie.MOYEN;
	}

	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	private Map<Integer, Theme4ALS> tirerThemes() {

		Map<Integer, Theme4ALS> themes4ALS = new HashMap<>();

		if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
			// TODO : tirer au sort
		} else if (NiveauPartie.FACILE.equals(niveauPartie)) {
			// TODO : tirer au sort
		} else if (NiveauPartie.MOYEN.equals(niveauPartie)) {
			// TODO : tirer au sort
		}

		// TODO mettre le questionnaire non-prioritaire avec un système de
		// pondération

		return themes4ALS;
	}

	public void jouerTheme() {
		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();

	}

	public void annulerTheme() {
		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
	}

	public void signalerAnomalie(SignalementAnomalie signalementAnomalie, Theme4ALS theme4ALS) {

		DBConnecteurQALSDao dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
		dbConnecteurQALSDao.signalerAnomalie(theme4ALS.getReference(), theme4ALS.getVersion(), signalementAnomalie,
				lecteur.formatterNomUtilisateur());
	}

	// Getters - setters

}
