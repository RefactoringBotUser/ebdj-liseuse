package fr.qp1c.ebdj.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.view.TypePartie;
import fr.qp1c.ebdj.view.component.BoutonBdj;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;

public class HomeController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@FXML
	private Button btnQuestion;

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
	private Button btnPartie;

	@FXML
	private Separator separateur;

	private Launcher launcher;

	@FXML
	private void initialize() {

		LOGGER.debug("[DEBUT] Initialisation du panneau home.");

		// Création du bouton "Question"
		BoutonBdj.customiserBouton150(btnQuestion, ImageConstants.IMAGE_HOME_QUESTION);

		// Création du bouton "Stats"
		BoutonBdj.customiserBouton150(btnStats, ImageConstants.IMAGE_HOME_STATS);

		// Création du bouton "Paramétrage"
		BoutonBdj.customiserBouton150(btnParametrage, ImageConstants.IMAGE_HOME_PARAMETRAGE);

		// TODO : Archiver les questions par partie

		separateur.setVisible(false);

		masquerSousMenuQuestion();

		LOGGER.debug("[FIN] Initialisation du panneau home.");
	}

	@FXML
	public void afficherEcranPartieComplete() {
		LOGGER.info("### --> Clic sur \"Partie complète\"");

		afficherSousMenuQuestion();
	}

	@FXML
	public void afficherEcranStats() {
		LOGGER.info("### --> Clic sur \"Stats\"");

		launcher.afficherEcranStats();

		masquerSousMenuQuestion();
	}

	@FXML
	public void afficherEcranParametrage() {
		LOGGER.info("### --> Clic sur \"Paramétrage\"");

		launcher.afficherEcranParametrage();

		masquerSousMenuQuestion();
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
	private void afficherSousMenuQuestion() {

		btn9PG.setVisible(true);
		btn4ALS.setVisible(true);
		btnJD.setVisible(true);
		btnFAF.setVisible(true);
		btnPartie.setVisible(true);

		btnQuestion.setDisable(true);
	}

	/**
	 * Masquer le sous-menu permettant de sélectionner le type de questionnaire.
	 * 
	 */
	public void masquerSousMenuQuestion() {

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);
		btnPartie.setVisible(false);

		btnQuestion.setDisable(false);
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
