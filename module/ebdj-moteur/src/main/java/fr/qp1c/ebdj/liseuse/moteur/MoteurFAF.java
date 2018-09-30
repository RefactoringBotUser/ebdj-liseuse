package fr.qp1c.ebdj.liseuse.moteur;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.moteur.loader.LoaderQuestionFAF;

public class MoteurFAF implements Moteur {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurFAF.class);

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private int nbQuestDifficileMax = 0;

	private List<QuestionFAF> questionsFAF = new ArrayList<>();

	private QuestionFAF derniereQuestionFAF;

	private List<Long> categoriesJouees;

	// Contraintes

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	private DBConnecteurFAFDao dbConnecteurFAFDao;

	// Constructeur

	public MoteurFAF(NiveauPartie niveauPartie) {

		// Chargement des questions.
		questionsFAF = new ArrayList<>();

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

		categoriesJouees = new ArrayList<>();

		if (niveauPartie == null) {
			this.niveauPartie = NiveauPartie.MOYEN;
		} else {
			this.niveauPartie = niveauPartie;
		}

		if (NiveauPartie.DIFFICILE.equals(this.niveauPartie)) {
			nbQuestDifficileMax = 4;
		} else if (NiveauPartie.MOYEN.equals(this.niveauPartie)) {
			nbQuestDifficileMax = 2;
		}

		this.dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
	}

	@Override
	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	@Override
	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	private QuestionFAF donnerNouvelleQuestionInedite() {

		QuestionFAF questionFAF;

		if (nbQuest % 2 == 0) {
			Long niveauMin = Long.valueOf(1);
			Long niveauMax = Long.valueOf(4);

			if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
				niveauMax = Long.valueOf(3);
			} else if (NiveauPartie.FACILE.equals(niveauPartie)) {
				niveauMin = Long.valueOf(2);
			}

			if (nbQuestDifficileMax == 0) {
				niveauMin = Long.valueOf(2);
				LOGGER.info("Limite du nombre de FAF difficile atteinte.");
			}

			questionFAF = LoaderQuestionFAF.chargerQuestions(categoriesJouees, niveauMin, niveauMax);
		} else {
			questionFAF = LoaderQuestionFAF.chargerQuestions(categoriesJouees, derniereQuestionFAF.getDifficulte());
		}

		if (questionFAF.getDifficulte().intValue() == 1) {
			nbQuestDifficileMax -= 1;
		}

		questionsFAF.add(questionFAF);

		return questionFAF;
	}

	private QuestionFAF donnerNouvelleQuestion() {
		LOGGER.info("[DEBUT] Donner une nouvelle question.");

		QuestionFAF question = donnerNouvelleQuestionInedite();

		categoriesJouees.add(question.getCategorieRef());

		dbConnecteurFAFDao.jouerQuestion(question.getReference(), lecteur.formatterNomUtilisateur());

		LOGGER.info("[FIN] Donner une nouvelle question.");

		return question;
	}

	public QuestionFAF changerQuestion(boolean questionACompter) {
		LOGGER.info("[DEBUT] Changer de question.");

		QuestionFAF nouvelleQuestion = donnerNouvelleQuestion();

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();

		derniereQuestionFAF = nouvelleQuestion;

		LOGGER.info("[FIN] Changer de question.");

		return nouvelleQuestion;
	}

	@Override
	public void signalerAnomalie(SignalementAnomalie signalementAnomalie) {
		LOGGER.info("[DEBUT] Signaler anomalie.");

		dbConnecteurFAFDao.signalerAnomalie(derniereQuestionFAF.getReference(), derniereQuestionFAF.getVersion(),
				signalementAnomalie, lecteur.formatterNomUtilisateur());

		LOGGER.info("[FIN] Signaler anomalie.");
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
