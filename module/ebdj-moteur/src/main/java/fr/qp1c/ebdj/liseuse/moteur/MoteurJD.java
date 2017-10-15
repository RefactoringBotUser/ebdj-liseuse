package fr.qp1c.ebdj.liseuse.moteur;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.moteur.loader.LoaderQuestionJD;

public class MoteurJD {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurJD.class);

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private List<QuestionJD> questionsJD = new ArrayList<>();

	private QuestionJD derniereQuestionJD;

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	public MoteurJD() {

		// Chargement des questions.
		questionsJD = LoaderQuestionJD.chargerQuestions();

		// Données 9PG.

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;
	}

	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	private QuestionJD donnerNouvelleQuestion() {
		LOGGER.info("[DEBUT] Donner une nouvelle question.");

		QuestionJD question = questionsJD.get(nbQuestReel);

		// TODO : gérer la récupération du lecteur
		DBConnecteurJDDao dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
		dbConnecteurJDDao.jouerQuestion(question.getReference(), lecteur.formatterNomUtilisateur());

		LOGGER.info("[FIN] Donner une nouvelle question.");

		return question;
	}

	public QuestionJD changerQuestion(boolean questionACompter) {
		LOGGER.info("[DEBUT] Changer de question.");

		QuestionJD nouvelleQuestion = donnerNouvelleQuestion();

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();

		derniereQuestionJD = nouvelleQuestion;

		LOGGER.info("[FIN] Changer de question.");

		return nouvelleQuestion;
	}

	public void signalerAnomalie(SignalementAnomalie signalementAnomalie) {
		LOGGER.info("[DEBUT] Signaler anomalie.");

		DBConnecteurJDDao dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
		dbConnecteurJDDao.signalerAnomalie(derniereQuestionJD.getReference(), derniereQuestionJD.getVersion(),
				signalementAnomalie, lecteur.formatterNomUtilisateur());

		LOGGER.info("[FIN] Signaler anomalie.");
	}

	public void calculerNbQuestion() {
		nbQuest++;
	}

	public void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	// Getters - setters

	public int getNbQuest() {
		return nbQuest;
	}

	public int getNbQuestReel() {
		return nbQuestReel;
	}

	public List<QuestionJD> getQuestionsJD() {
		return questionsJD;
	}

	public QuestionJD getDerniereQuestionJD() {
		return derniereQuestionJD;
	}

}
