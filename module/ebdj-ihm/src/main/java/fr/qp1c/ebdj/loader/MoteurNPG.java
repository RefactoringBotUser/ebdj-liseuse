package fr.qp1c.ebdj.loader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.model.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.Mode9PG;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;

public class MoteurNPG {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoteurNPG.class);

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private int niveau = 0;

	private int cpt_1 = 0;

	private int cpt_2 = 0;

	private int cpt_3 = 0;

	private Mode9PG mode9PG;

	private List<QuestionNPG> questions9PGJouee = new ArrayList<>();

	private List<QuestionNPG> questions9PG_1 = new ArrayList<>();

	private List<QuestionNPG> questions9PG_2 = new ArrayList<>();

	private List<QuestionNPG> questions9PG_3 = new ArrayList<>();

	private QuestionNPG derniereQuestion9PG;

	// Contraintes

	private NiveauPartie niveauPartie;

	// Tracker

	private Lecteur lecteur;

	public MoteurNPG() {
		questions9PG_1 = LoaderQuestion9PG.chargerQuestions1Etoile();
		questions9PG_2 = LoaderQuestion9PG.chargerQuestions2Etoiles();
		questions9PG_3 = LoaderQuestion9PG.chargerQuestions3Etoiles();

		questions9PGJouee = new ArrayList<>();

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

		niveau = 0;

		cpt_1 = 0;

		cpt_2 = 0;

		cpt_3 = 0;

		// Lancer en mode 1,2,3
		changerNiveau123();
	}

	public void changerNiveau123() {
		mode9PG = Mode9PG.MODE_123;
	}

	public void changerNiveau23() {
		// Selection du mode de jeu
		mode9PG = Mode9PG.MODE_23;

		niveau = 2;
	}

	public void changerNiveau3() {

		// Selection du mode de jeu
		mode9PG = Mode9PG.MODE_3;

		niveau = 3;
	}

	public void definirLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		this.niveauPartie = niveauPartie;
	}

	private QuestionNPG donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionNPG question = null;

		if ((niveau == 1 && Mode9PG.MODE_123.equals(mode9PG)) || (niveau == 2 && Mode9PG.MODE_23.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_3.equals(mode9PG))) {
			LOGGER.info("Question à 1 étoile.");

			question = questions9PG_1.get(cpt_1);
			cpt_1++;

			if (question != null) {
				questions9PG_1.addAll(LoaderQuestion9PG.chargerQuestions1Etoile());
				question = questions9PG_1.get(cpt_1);
				cpt_1++;
			}
		} else if ((niveau == 2 && Mode9PG.MODE_123.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_23.equals(mode9PG))) {
			LOGGER.info("Question à 2 étoiles.");

			question = questions9PG_2.get(cpt_2);
			cpt_2++;

			if (question != null) {
				questions9PG_2.addAll(LoaderQuestion9PG.chargerQuestions2Etoiles());
				question = questions9PG_2.get(cpt_2);
				cpt_2++;
			}
		} else {
			LOGGER.info("Question à 3 étoiles.");

			question = questions9PG_3.get(cpt_3);
			cpt_3++;

			if (question != null) {
				questions9PG_3.addAll(LoaderQuestion9PG.chargerQuestions3Etoiles());
				question = questions9PG_3.get(cpt_3);
				cpt_3++;
			}
		}
		questions9PGJouee.add(question);

		DBConnecteurNPGDao dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();
		dbConnecteurNPGDao.jouerQuestion(question.getId(), question.getReference(), lecteur.formatterNomUtilisateur());

		LOGGER.debug("[FIN] Donner une nouvelle question.");

		return question;

	}

	private void calculerNbQuestion() {
		nbQuest++;
	}

	private void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	private void calculerNiveauQuestion() {
		if (Mode9PG.MODE_123.equals(mode9PG)) {
			niveau = ((niveau++) % 3) + 1;
		} else if (Mode9PG.MODE_23.equals(mode9PG)) {
			if (niveau == 2) {
				niveau = 3;
			} else {
				niveau = 2;
			}
		} else if (Mode9PG.MODE_3.equals(mode9PG)) {
			niveau = 3;
		}
	}

	public QuestionNPG changerQuestionAvecNiveau(boolean questionACompter) {
		LOGGER.debug("[DEBUT] Changer de question avec niveau.");

		// Calcul du niveau
		calculerNiveauQuestion();

		QuestionNPG nouvelleQuestion = changerQuestion(questionACompter);

		LOGGER.debug("[FIN] Changer de question avec niveau.");

		return nouvelleQuestion;
	}

	public QuestionNPG changerQuestion(boolean questionACompter) {
		LOGGER.debug("[DEBUT] Changer de question.");

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();

		derniereQuestion9PG = nouvelleQuestion;

		LOGGER.debug("[FIN] Changer de question.");

		return nouvelleQuestion;
	}

	// Getters - setters

	public int getNbQuest() {
		return nbQuest;
	}

	public int getNbQuestReel() {
		return nbQuestReel;
	}

	public int getNiveau() {
		return niveau;
	}

	public Mode9PG getMode9PG() {
		return mode9PG;
	}

	public QuestionNPG getDerniereQuestion9PG() {
		return derniereQuestion9PG;
	}

}