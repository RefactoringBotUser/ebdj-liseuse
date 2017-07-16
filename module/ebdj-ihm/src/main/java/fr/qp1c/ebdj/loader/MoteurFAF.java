package fr.qp1c.ebdj.loader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.loader.LoaderQuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurFAFDaoImpl;

public class MoteurFAF {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurFAF.class);

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	// private int niveau = 0;

	private List<QuestionFAF> questionsFAF = new ArrayList<>();

	private QuestionFAF derniereQuestionFAF;

	public MoteurFAF() {

		// Chargement des questions.
		questionsFAF = LoaderQuestionFAF.chargerQuestions();

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

	}

	private QuestionFAF donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionFAF question = questionsFAF.get(nbQuestReel);

		// TODO : gérer la récupération du lecteur
		DBConnecteurFAFDao dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
		dbConnecteurFAFDao.jouerQuestion(question.getId(), question.getReference(), "lecteur");

		LOGGER.debug("[FIN] Donner une nouvelle question.");

		return question;
	}

	public QuestionFAF changerQuestion(boolean questionACompter) {
		LOGGER.debug("[DEBUT] Changer de question.");

		QuestionFAF nouvelleQuestion = donnerNouvelleQuestion();

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();

		derniereQuestionFAF = nouvelleQuestion;

		LOGGER.debug("[FIN] Changer de question.");

		return nouvelleQuestion;
	}

	private void calculerNbQuestion() {
		nbQuest++;
	}

	private void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	// Getters

	public int getNbQuest() {
		return nbQuest;
	}

	public int getNbQuestReel() {
		return nbQuestReel;
	}

	public List<QuestionFAF> getQuestionsFAF() {
		return questionsFAF;
	}

	public QuestionFAF getDerniereQuestionFAF() {
		return derniereQuestionFAF;
	}
}
