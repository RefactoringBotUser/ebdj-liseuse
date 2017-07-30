package fr.qp1c.ebdj.loader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.model.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
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

	private List<QuestionFAF> questionsFAF = new ArrayList<>();

	private QuestionFAF derniereQuestionFAF;

	private List<Long> categoriesJouees;

	// Contraintes

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	// Constructeur

	public MoteurFAF() {

		// Chargement des questions.
		questionsFAF = new ArrayList<>();

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

		categoriesJouees = new ArrayList<>();

		this.niveauPartie = NiveauPartie.MOYEN;
	}

	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	private QuestionFAF donnerNouvelleQuestionInedite() {

		QuestionFAF questionFAF = null;

		if (nbQuest % 2 == 0) {

			Long niveauMin = Long.valueOf(1);
			Long niveauMax = Long.valueOf(4);

			if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
				niveauMax = Long.valueOf(3);
			} else if (NiveauPartie.FACILE.equals(niveauPartie)) {
				niveauMin = Long.valueOf(2);
			}
			questionFAF = LoaderQuestionFAF.chargerQuestions(categoriesJouees, niveauMin, niveauMax);
		} else {
			questionFAF = LoaderQuestionFAF.chargerQuestions(categoriesJouees, derniereQuestionFAF.getDifficulte());
		}
		questionsFAF.add(questionFAF);

		return questionFAF;
	}

	private QuestionFAF donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionFAF question = donnerNouvelleQuestionInedite();

		categoriesJouees.add(question.getCategorieRef());

		DBConnecteurFAFDao dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
		dbConnecteurFAFDao.jouerQuestion(question.getId(), question.getReference(), lecteur.formatterNomUtilisateur());

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

	public void signalerAnomalie(SignalementAnomalie signalementAnomalie) {

		DBConnecteurFAFDao dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
		dbConnecteurFAFDao.signalerAnomalie(derniereQuestionFAF.getReference(), derniereQuestionFAF.getVersion(),
				signalementAnomalie, lecteur.formatterNomUtilisateur());
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
