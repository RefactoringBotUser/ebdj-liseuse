package fr.qp1c.ebdj.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.model.TypePartie;
import fr.qp1c.ebdj.utils.BoutonUtils;
import fr.qp1c.ebdj.utils.ImageConstants;
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

	private Launcher launcher;

	@FXML
	private void initialize() {

		LOGGER.debug("[DEBUT] Initialisation du panneau home.");

		// Création du bouton "Question"
		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION);

		// Création du bouton "Stats"
		BoutonUtils.customiserBouton150(btnStats, ImageConstants.IMAGE_HOME_STATS);

		// Création du bouton "Paramétrage"
		BoutonUtils.customiserBouton150(btnParametrage, ImageConstants.IMAGE_HOME_PARAMETRAGE);

		// TODO : Archiver les questions par partie

		masquerSousMenuTypeQuestionnaire();

		LOGGER.debug("[FIN] Initialisation du panneau home.");
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

		afficherSousMenuPhase();
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

		masquerSousMenuPhase();

		btnQuestion.setSelected(true);
		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION_SELECTED);

		btnPanneauTypePartie.setVisible(true);
	}

	private void afficherSousMenuPhase() {
		btn9PG.setVisible(true);
		btn4ALS.setVisible(true);
		btnJD.setVisible(true);
		btnFAF.setVisible(true);
	}

	/**
	 * Masquer le sous-menu permettant de sélectionner le type de questionnaire.
	 * 
	 */
	public void masquerSousMenuTypeQuestionnaire() {
		btnPartie.setVisible(false);
		labelOu.setVisible(false);
		btnPhase.setVisible(false);

		masquerSousMenuPhase();

		btnQuestion.setSelected(false);

		BoutonUtils.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION);

		btnPhase.setSelected(false);

		btnPanneauTypePartie.setVisible(false);
	}

	private void masquerSousMenuPhase() {
		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
