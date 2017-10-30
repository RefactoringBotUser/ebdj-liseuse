package fr.qp1c.ebdj.liseuse.moteur;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.Mode9PG;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;
import fr.qp1c.ebdj.liseuse.moteur.loader.LoaderQuestion9PG;

public class MoteurNPG implements Moteur{

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
	
	private DBConnecteurNPGDao dbConnecteurNPGDao;

	public MoteurNPG() {
		dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();
		
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

		niveauPartie = NiveauPartie.MOYEN;

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
		LOGGER.info("[DEBUT] Donner une nouvelle question.");

		QuestionNPG question = null;

		if ((niveau == 1 && Mode9PG.MODE_123.equals(mode9PG)) || (niveau == 2 && Mode9PG.MODE_23.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_3.equals(mode9PG))) {
			LOGGER.info("Question à 1 étoile.");

			if (NiveauPartie.DIFFICILE.equals(niveauPartie)) {
				question = donnerQuestionA2Points();
			} else {
				question = donnerQuestionA1Point();
			}

		} else if ((niveau == 2 && Mode9PG.MODE_123.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_23.equals(mode9PG))) {
			LOGGER.info("Question à 2 étoiles.");

			question = donnerQuestionA2Points();

		} else {
			LOGGER.info("Question à 3 étoiles.");

			if (NiveauPartie.FACILE.equals(niveauPartie)
					|| (NiveauPartie.MOYEN.equals(niveauPartie) && Mode9PG.MODE_123.equals(mode9PG) && nbQuest > 12)) {
				question = donnerQuestionA2Points();
			} else {
				question = donnerQuestionA3Points();
			}
		}
		questions9PGJouee.add(question);


		dbConnecteurNPGDao.jouerQuestion(question.getReference(), lecteur.formatterNomUtilisateur());

		LOGGER.info("[FIN] Donner une nouvelle question.");

		return question;

	}

	private QuestionNPG donnerQuestionA1Point() {

		// Charger de nouvelles questions si il en manque
		if (questions9PG_1.size() <= cpt_1) {

			// TODO : vérifier que le chargement a été efficace
			questions9PG_1.addAll(LoaderQuestion9PG.chargerQuestions1Etoile());
		}

		// Récupérer la question à jouer
		QuestionNPG question = questions9PG_1.get(cpt_1);
		cpt_1++;

		return question;
	}

	private QuestionNPG donnerQuestionA2Points() {

		// Charger de nouvelles questions si il en manque
		if (questions9PG_2.size() <= cpt_2) {

			// TODO : vérifier que le chargement a été efficace
			questions9PG_2.addAll(LoaderQuestion9PG.chargerQuestions2Etoiles());
		}

		// Récupérer la question à jouer
		QuestionNPG question = questions9PG_2.get(cpt_2);
		cpt_2++;

		return question;
	}

	private QuestionNPG donnerQuestionA3Points() {

		// Charger de nouvelles questions si il en manque
		if (questions9PG_3.size() <= cpt_3) {

			// TODO : vérifier que le chargement a été efficace
			questions9PG_3.addAll(LoaderQuestion9PG.chargerQuestions3Etoiles());
		}

		// Récupérer la question à jouer
		QuestionNPG question = questions9PG_3.get(cpt_3);
		cpt_3++;

		return question;
	}

	public void signalerAnomalie(SignalementAnomalie signalementAnomalie) {
		dbConnecteurNPGDao.signalerAnomalie(derniereQuestion9PG.getReference(), derniereQuestion9PG.getVersion(),
				signalementAnomalie, lecteur.formatterNomUtilisateur());
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
		LOGGER.info("[DEBUT] Changer de question avec niveau.");

		// Calcul du niveau
		calculerNiveauQuestion();

		QuestionNPG nouvelleQuestion = changerQuestion(questionACompter);

		LOGGER.info("[FIN] Changer de question avec niveau.");

		return nouvelleQuestion;
	}

	public QuestionNPG changerQuestion(boolean questionACompter) {
		LOGGER.info("[DEBUT] Changer de question.");

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();

		derniereQuestion9PG = nouvelleQuestion;

		LOGGER.info("[FIN] Changer de question.");

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
