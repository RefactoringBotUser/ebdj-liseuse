package fr.qp1c.ebdj.controller.stats;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class StatistiquesController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesController.class);

	@FXML
	private Button btnHome;

	@FXML
	private Button btnSynchroniser;

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau stats.");

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

		LOGGER.debug("[FIN] Initialisation du panneau stats.");
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		launcher.afficherEcranHome();
	}

	// protected void updateLibelleBarreProgressionPhase(String message) {
	// Runnable command = new Runnable() {
	// @Override
	// public void run() {
	// // Le label étant le label JavaFX dont tu
	// // souhaites modifier le texte.
	// libelleBarreProgressionPhase.setText(message);
	// }
	// };
	// if (Platform.isFxApplicationThread()) {
	// // Nous sommes déjà dans le thread graphique
	// command.run();
	// } else {
	// // Nous ne sommes pas dans le thread graphique
	// // on utilise runLater.
	// Platform.runLater(command);
	// }
	//
	// }
	//
	// protected void updateLibelleBarreProgressionSousPhase(String message) {
	// Runnable command = new Runnable() {
	// @Override
	// public void run() {
	// // Le label étant le label JavaFX dont tu
	// // souhaites modifier le texte.
	// libelleBarreProgressionSousPhase.setText(message);
	// }
	// };
	// if (Platform.isFxApplicationThread()) {
	// // Nous sommes déjà dans le thread graphique
	// command.run();
	// } else {
	// // Nous ne sommes pas dans le thread graphique
	// // on utilise runLater.
	// Platform.runLater(command);
	// }
	//
	// }
	//
	// protected void updateProgress(String message) {
	// Runnable command = new Runnable() {
	// @Override
	// public void run() {
	// // Le label étant le label JavaFX dont tu
	// // souhaites modifier le texte.
	// libelleBarreProgressionPhase.setText(message);
	// }
	// };
	// if (Platform.isFxApplicationThread()) {
	// // Nous sommes déjà dans le thread graphique
	// command.run();
	// } else {
	// // Nous ne sommes pas dans le thread graphique
	// // on utilise runLater.
	// Platform.runLater(command);
	// }
	//
	// }

	@FXML
	public void synchroniser() {
		LOGGER.info("### --> Clic sur \"Synchroniser\".");

		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("QP1C - E-BDJ");
		dialog.setHeaderText("Synchronisation de la boite de jeu");
		dialog.initOwner(Launcher.getStage());

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new
		// ImageView(this.getClass().getResource("login.png").toString()));

		Label libelleBarreProgressionPhase = new Label();

		Label libelleBarreProgressionSousPhase = new Label();

		ProgressBar barreProgressionPhase = new ProgressBar();

		ProgressBar barreProgressionSousPhase = new ProgressBar();

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Synchroniser", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		final Button btOk = (Button) dialog.getDialogPane().lookupButton(loginButtonType);

		btOk.addEventFilter(ActionEvent.ACTION, (event) -> {

			System.out.println("Hello world !");
		});

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		grid.add(libelleBarreProgressionPhase, 0, 0);
		grid.add(barreProgressionPhase, 0, 1);
		grid.add(libelleBarreProgressionSousPhase, 0, 2);
		grid.add(barreProgressionSousPhase, 0, 2);

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>("", "");
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});

		// new Thread() {
		// @Override
		// public void run() {
		//
		// // Synchronisation des 9PGS
		//
		// updateLibelleBarreProgressionPhase("Synchronisation des questions de
		// 9PG");
		// updateLibelleBarreProgressionSousPhase("Transfert des anomalies.");
		//
		// barreProgressionPhase.setProgress(0.0);
		// barreProgressionSousPhase.setProgress(0.0);
		//
		// Synchronisation9PGService synchronisation9pgService = new
		// Synchronisation9PGServiceImpl();
		// synchronisation9pgService.synchroniserAnomalies9PG();
		// barreProgressionSousPhase.setProgress(0.1);
		// updateLibelleBarreProgressionSousPhase("Transfert des lectures.");
		//
		// synchronisation9pgService.synchroniserLectures9PG();
		// barreProgressionSousPhase.setProgress(0.4);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// questions.");
		//
		// synchronisation9pgService.synchroniserQuestions9PG();
		// barreProgressionSousPhase.setProgress(0.9);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// corrections.");
		//
		// synchronisation9pgService.synchroniserCorrections9PG();
		// barreProgressionSousPhase.setProgress(1.0);
		// updateLibelleBarreProgressionPhase("Synchronisation de la phase de
		// 9PG terminée.");
		// updateLibelleBarreProgressionSousPhase("");
		//
		// // Synchronisation des 4ALS
		//
		// barreProgressionPhase.setProgress(0.25);
		// barreProgressionSousPhase.setProgress(0.0);
		//
		// try {
		// TimeUnit.SECONDS.sleep(1);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Synchronisation4ALSService synchronisation4alsService = new
		// Synchronisation4ALSServiceImpl();
		// synchronisation4alsService.synchroniserAnomalies4ALS();
		// barreProgressionSousPhase.setProgress(0.1);
		// updateLibelleBarreProgressionSousPhase("Transfert des lectures.");
		//
		// synchronisation4alsService.synchroniserLectures4ALS();
		// barreProgressionSousPhase.setProgress(0.4);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// questions.");
		//
		// synchronisation4alsService.synchroniserQuestions4ALS();
		// barreProgressionSousPhase.setProgress(0.9);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// corrections.");
		//
		// synchronisation4alsService.synchroniserCorrections4ALS();
		// barreProgressionSousPhase.setProgress(1.0);
		//
		// // Synchronisation des JDS
		//
		// barreProgressionPhase.setProgress(0.50);
		// barreProgressionSousPhase.setProgress(0.0);
		//
		// SynchronisationJDService synchronisationJdService = new
		// SynchronisationJDServiceImpl();
		// synchronisationJdService.synchroniserAnomaliesJD();
		// barreProgressionSousPhase.setProgress(0.1);
		// updateLibelleBarreProgressionSousPhase("Transfert des lectures.");
		//
		// synchronisationJdService.synchroniserLecturesJD();
		// barreProgressionSousPhase.setProgress(0.4);
		// try {
		// TimeUnit.SECONDS.sleep(1);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// questions.");
		//
		// synchronisationJdService.synchroniserQuestionsJD();
		// barreProgressionSousPhase.setProgress(0.9);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// corrections.");
		//
		// synchronisationJdService.synchroniserCorrectionsJD();
		// barreProgressionSousPhase.setProgress(1.0);
		//
		// // Synchronisation des FAFS
		//
		// barreProgressionPhase.setProgress(0.75);
		// barreProgressionSousPhase.setProgress(0.0);
		//
		// SynchronisationFAFService synchronisationFafService = new
		// SynchronisationFAFServiceImpl();
		// synchronisationFafService.synchroniserAnomaliesFAF();
		// barreProgressionSousPhase.setProgress(0.1);
		// updateLibelleBarreProgressionSousPhase("Transfert des lectures.");
		//
		// synchronisationFafService.synchroniserLecturesFAF();
		// barreProgressionSousPhase.setProgress(0.4);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// questions.");
		//
		// synchronisationFafService.synchroniserQuestionsFAF();
		// barreProgressionSousPhase.setProgress(0.9);
		// updateLibelleBarreProgressionSousPhase("Téléchargement des
		// corrections.");
		//
		// synchronisationFafService.synchroniserCorrectionsFAF();
		// barreProgressionSousPhase.setProgress(1.0);
		//
		// updateLibelleBarreProgressionPhase("Synchronisation terminée.");
		// updateLibelleBarreProgressionSousPhase("");
		//
		// barreProgressionPhase.setProgress(1.0);
		// }
		// }.start();
	}

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
