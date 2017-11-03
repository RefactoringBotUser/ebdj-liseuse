package fr.qp1c.ebdj.liseuse.ihm.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.partie.TypePartie;
import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.BoutonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

public class HomeController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	// Composant(s) JavaFX

	@FXML
	private ToggleButton btnQuestion;

	@FXML
	private Button btnStats;

	@FXML
	private Button btnParametrage;

	@FXML
	private Button btn9PG;

	@FXML
	private Button btn4ALS;

	@FXML
	private Button btnJD;

	@FXML
	private Button btnFAF;

	@FXML
	private ToggleButton btnPhase;

	@FXML
	private ToggleButton btnPartie;

	@FXML
	private Label labelOu;

	@FXML
	private VBox btnPanneauTypePartie;

	// Autres attributs

	private Launcher launcher;

	@FXML
	private void initialize() {

		LOGGER.info("[DEBUT] Initialisation du panneau home.");

		// Création du bouton "Question"
		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION);

		// Création du bouton "Stats"
		BoutonUtils.customiserBouton150(btnStats, ImageConstants.IMAGE_HOME_STATS);

		// Création du bouton "Paramétrage"
		BoutonUtils.customiserBouton150(btnParametrage, ImageConstants.IMAGE_HOME_PARAMETRAGE);

		masquerSousMenuTypeQuestionnaire();

		LOGGER.info("[FIN] Initialisation du panneau home.");
	}

	@FXML
	public void afficherEcranStats() {
		LOGGER.info("### --> Clic sur \"Stats\"");

		launcher.afficherEcranStats();

		masquerSousMenuTypeQuestionnaire();
	}

	@FXML
	public void afficherEcranParametrage() {
		LOGGER.info("### --> Clic sur \"Paramétrage\"");

		launcher.afficherEcranParametrage();

		masquerSousMenuTypeQuestionnaire();
	}

	@FXML
	public void afficherEcranPartieComplete() {
		LOGGER.info("### --> Clic sur \"Une partie complète\"");

		afficherSousMenuTypeQuestionnaire();
	}

	@FXML
	public void selectionnerPhase() {
		LOGGER.info("### --> Clic sur \"Une phase de jeu uniquement\"");

		btnPhase.setSelected(true);

		afficherSousMenuPhase(true);
	}

	@FXML
	public void afficherEcran9PG() {
		LOGGER.info("### --> Clic sur \"9PG\"");

		launcher.afficherEcranQuestions(TypePartie.NPG);
	}

	@FXML
	public void afficherEcran4ALS() {
		LOGGER.info("### --> Clic sur \"4ALS\"");

		launcher.afficherEcranQuestions(TypePartie.QALS);
	}

	@FXML
	public void afficherEcranJD() {
		LOGGER.info("### --> Clic sur \"JD\"");

		launcher.afficherEcranQuestions(TypePartie.JD);
	}

	@FXML
	public void afficherEcranFAF() {
		LOGGER.info("### --> Clic sur \"FAF\"");

		launcher.afficherEcranQuestions(TypePartie.FAF);
	}

	@FXML
	public void afficherEcranPartie() {
		LOGGER.info("### --> Clic sur \"Partie\"");

		launcher.afficherEcranQuestions(TypePartie.PARTIE);
	}

	/**
	 * Afficher le sous-menu permettant de selectionner le type de
	 * questionnaire.
	 * 
	 */
	private void afficherSousMenuTypeQuestionnaire() {
		btnPartie.setVisible(true);
		labelOu.setVisible(true);
		btnPhase.setVisible(true);

		afficherSousMenuPhase(false);

		btnQuestion.setSelected(true);
		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION_SELECTED);

		btnPanneauTypePartie.setVisible(true);
	}

	/**
	 * Masquer le sous-menu permettant de sélectionner le type de questionnaire.
	 * 
	 */
	public void masquerSousMenuTypeQuestionnaire() {
		btnPartie.setVisible(false);
		labelOu.setVisible(false);
		btnPhase.setVisible(false);

		afficherSousMenuPhase(false);

		btnQuestion.setSelected(false);

		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION);

		btnPhase.setSelected(false);

		btnPanneauTypePartie.setVisible(false);
	}

	/**
	 * Afficher/masquer le sous-menu phase de jeu.
	 * 
	 * @param etat
	 *            etat d'affichage à positionner
	 */
	private void afficherSousMenuPhase(boolean etat) {
		btn9PG.setVisible(etat);
		btn4ALS.setVisible(etat);
		btnJD.setVisible(etat);
		btnFAF.setVisible(etat);
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
