package fr.qp1c.ebdj.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class HomeController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@FXML
	private MenuButton btnQuestion;

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

		ImageView imageHomeQuestion = new ImageView(ImageConstants.IMAGE_HOME_QUESTION);
		ImageUtils.reduireImageCustom(imageHomeQuestion, 150);

		btnQuestion.setGraphic(imageHomeQuestion);
		btnQuestion.setTextAlignment(TextAlignment.CENTER);
		btnQuestion.setContentDisplay(ContentDisplay.TOP);

		// MenuItem menu9PG = new MenuItem("9PG uniquement");
		// MenuItem menu4ALS = new MenuItem("4ALS uniquement");
		// MenuItem menuJD = new MenuItem("JD uniquement");
		// MenuItem menuFAF = new MenuItem("FAF uniquement");
		// MenuItem menuPartieComplete = new MenuItem("PARTIE COMPLETE");

		// btnQuestion.getItems().add(menuPartieComplete);
		// btnQuestion.getItems().add(menu9PG);
		// btnQuestion.getItems().add(menu4ALS);
		// btnQuestion.getItems().add(menuJD);
		// btnQuestion.getItems().add(menuFAF);

		ImageView imageHomeStats = new ImageView(ImageConstants.IMAGE_HOME_STATS);
		ImageUtils.reduireImageCustom(imageHomeStats, 150);

		btnStats.setGraphic(imageHomeStats);
		btnStats.setTextAlignment(TextAlignment.CENTER);
		btnStats.setContentDisplay(ContentDisplay.TOP);

		ImageView imageHomeParametrage = new ImageView(ImageConstants.IMAGE_HOME_PARAMETRAGE);
		ImageUtils.reduireImageCustom(imageHomeParametrage, 150);

		btnParametrage.setGraphic(imageHomeParametrage);
		btnParametrage.setTextAlignment(TextAlignment.CENTER);
		btnParametrage.setContentDisplay(ContentDisplay.TOP);

		separateur.setVisible(false);

		// btn9PG.setVisible(false);
		// btn4ALS.setVisible(false);
		// btnJD.setVisible(false);
		// btnFAF.setVisible(false);
		// btnPartie.setVisible(false);

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

		LOGGER.error("Non implémenté !");
	}

	@FXML
	public void afficherEcranParametrage() {
		LOGGER.info("### --> Clic sur \"Paramétrage\"");

		launcher.afficherEcranParametrage();

		masquerSousMenuQuestion();

		LOGGER.error("Non implémenté !");
	}

	private void afficherSousMenuQuestion() {

		btn9PG.setVisible(true);
		btn4ALS.setVisible(true);
		btnJD.setVisible(true);
		btnFAF.setVisible(true);
		btnPartie.setVisible(true);

		btnQuestion.setDisable(true);
	}

	private void masquerSousMenuQuestion() {

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);
		btnPartie.setVisible(false);

		btnQuestion.setDisable(false);
	}

	@FXML
	public void afficherEcran9PG() {
		LOGGER.info("### --> Clic sur \"9PG\"");

		masquerSousMenuQuestion();

		LOGGER.error("Non implémenté !");
	}

	@FXML
	public void afficherEcran4ALS() {
		LOGGER.info("### --> Clic sur \"4ALS\"");

		masquerSousMenuQuestion();

		LOGGER.error("Non implémenté !");
	}

	@FXML
	public void afficherEcranJD() {
		LOGGER.info("### --> Clic sur \"JD\"");

		masquerSousMenuQuestion();

		LOGGER.error("Non implémenté !");
	}

	@FXML
	public void afficherEcranFAF() {
		LOGGER.info("### --> Clic sur \"FAF\"");

		masquerSousMenuQuestion();

		LOGGER.error("Non implémenté !");
	}

	@FXML
	public void afficherEcranPartie() {
		LOGGER.info("### --> Clic sur \"Partie\"");

		launcher.afficherEcranPartieComplete();

		// masquerSousMenuQuestion();
	}

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
