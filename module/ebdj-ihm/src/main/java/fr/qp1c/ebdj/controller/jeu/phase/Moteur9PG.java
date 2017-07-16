package fr.qp1c.ebdj.controller.jeu.phase;

public class Moteur9PG {
	/*
		*//**
			 * Default logger.
			 *//*
			 * private static final Logger LOGGER =
			 * LoggerFactory.getLogger(Moteur9PG.class);
			 * 
			 * // Nombre de questions officiellement joué. private int nbQuest =
			 * 0;
			 * 
			 * // Nombre de questions réel (inclus erreur et remplacement).
			 * private int nbQuestReel = 0;
			 * 
			 * private int niveau = 0;
			 * 
			 * private int cpt_1 = 0;
			 * 
			 * private int cpt_2 = 0;
			 * 
			 * private int cpt_3 = 0;
			 * 
			 * private Mode9PG mode9PG;
			 * 
			 * private List<QuestionNPG> questions9PGJouee = new ArrayList<>();
			 * 
			 * private List<QuestionNPG> questions9PG_1 = new ArrayList<>();
			 * 
			 * private List<QuestionNPG> questions9PG_2 = new ArrayList<>();
			 * 
			 * private List<QuestionNPG> questions9PG_3 = new ArrayList<>();
			 * 
			 * private QuestionNPG derniereQuestion9PG;
			 * 
			 * private boolean affichageHistoriqueEnCours = false;
			 * 
			 * private int numQuestionAffiche = 0;
			 * 
			 * private Lecteur lecteur;
			 * 
			 * private void charger() { // Chargement des questions.
			 * questions9PG_1 = LoaderQuestion9PG.chargerQuestions1Etoile();
			 * questions9PG_2 = LoaderQuestion9PG.chargerQuestions2Etoiles();
			 * questions9PG_3 = LoaderQuestion9PG.chargerQuestions3Etoiles();
			 * 
			 * questions9PGJouee = new ArrayList<>();
			 * 
			 * numQuestionAffiche = 0;
			 * 
			 * // Nombre de questions officiellement joué. nbQuest = 0;
			 * 
			 * // Nombre de questions réel (inclus erreur et remplacement).
			 * nbQuestReel = 0;
			 * 
			 * niveau = 0;
			 * 
			 * cpt_1 = 0;
			 * 
			 * cpt_2 = 0;
			 * 
			 * cpt_3 = 0;
			 * 
			 * // Lancer en mode 1,2,3 changerNiveau123(); }
			 * 
			 * // Méthodes d'affichage
			 * 
			 * public void definirLecteur(Lecteur lecteur) { this.lecteur =
			 * lecteur; }
			 * 
			 * public void afficherNouvelleQuestion() {
			 * 
			 * // Calcul du niveau calculerNiveauQuestion();
			 * 
			 * QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();
			 * 
			 * changerQuestion(nouvelleQuestion, true); }
			 * 
			 * private QuestionNPG donnerNouvelleQuestion() {
			 * LOGGER.debug("[DEBUT] Donner une nouvelle question.");
			 * 
			 * QuestionNPG question = null;
			 * 
			 * if ((niveau == 1 && Mode9PG.MODE_123.equals(mode9PG)) || (niveau
			 * == 2 && Mode9PG.MODE_23.equals(mode9PG)) || (niveau == 3 &&
			 * Mode9PG.MODE_3.equals(mode9PG))) {
			 * LOGGER.info("Question à 1 étoile.");
			 * 
			 * question = questions9PG_1.get(cpt_1); cpt_1++;
			 * 
			 * } else if ((niveau == 2 && Mode9PG.MODE_123.equals(mode9PG)) ||
			 * (niveau == 3 && Mode9PG.MODE_23.equals(mode9PG))) {
			 * LOGGER.info("Question à 2 étoiles.");
			 * 
			 * question = questions9PG_2.get(cpt_2); cpt_2++; } else {
			 * LOGGER.info("Question à 3 étoiles.");
			 * 
			 * question = questions9PG_3.get(cpt_3); cpt_3++; }
			 * questions9PGJouee.add(question);
			 * 
			 * // TODO : gérer la récupération du lecteur DBConnecteurNPGDao
			 * dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();
			 * dbConnecteurNPGDao.jouerQuestion(question.getId(),
			 * question.getReference(), lecteur.formatterNomUtilisateur());
			 * 
			 * LOGGER.debug("[FIN] Donner une nouvelle question.");
			 * 
			 * return question;
			 * 
			 * }
			 * 
			 * private void changerQuestion(QuestionNPG nouvelleQuestion,
			 * boolean questionACompter) {
			 * LOGGER.debug("[DEBUT] Changer de question.");
			 * 
			 * // Calcul du nombre de question joué if (questionACompter) {
			 * calculerNbQuestion();
			 * 
			 * } calculerNbQuestionReel(); numQuestionAffiche = nbQuestReel;
			 * 
			 * // Historiser la nouvelle question
			 * historiserQuestion9PG(nouvelleQuestion);
			 * 
			 * derniereQuestion9PG = nouvelleQuestion;
			 * 
			 * LOGGER.debug("[FIN] Changer de question."); }
			 * 
			 * private void historiserQuestion9PG(QuestionNPG question9PG) {
			 * LOGGER.debug("[DEBUT] Historisation de la question 9PG.");
			 * 
			 * if (question9PG != null) {
			 * 
			 * HistoriqueQuestion9PG histo = new HistoriqueQuestion9PG();
			 * histo.setNbQuestion(nbQuest);
			 * histo.setNbQuestionReel(nbQuestReel); histo.setNiveau(niveau);
			 * histo.setQuestion(question9PG);
			 * 
			 * listeHistorique9PG.add(0, histo); }
			 * 
			 * LOGGER.debug("[FIN] Historisation de la question 9PG."); }
			 * 
			 * private void calculerNbQuestion() { nbQuest++; }
			 * 
			 * private void calculerNbQuestionReel() { nbQuestReel++; }
			 * 
			 * private void calculerNiveauQuestion() { if
			 * (Mode9PG.MODE_123.equals(mode9PG)) { niveau = ((niveau++) % 3) +
			 * 1; } else if (Mode9PG.MODE_23.equals(mode9PG)) { if (niveau == 2)
			 * { niveau = 3; } else { niveau = 2; } } else if
			 * (Mode9PG.MODE_3.equals(mode9PG)) { niveau = 3; } }
			 */
}
