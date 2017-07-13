package fr.qp1c.ebdj.controller.jeu;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.controller.jeu.phase.FAFController;
import fr.qp1c.ebdj.controller.jeu.phase.JDController;
import fr.qp1c.ebdj.controller.jeu.phase.NPGController;
import fr.qp1c.ebdj.controller.jeu.phase.QALSController;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.TypePartie;
import fr.qp1c.ebdj.view.component.Chronometre;
import fr.qp1c.ebdj.view.component.UtilisateurPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TypePartieController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypePartieController.class);

	// Composants graphiques

	@FXML
	private BorderPane content;

	@FXML
	private UtilisateurPane utilisateurPane;

	@FXML
	private Chronometre chronometre;

	@FXML
	private Button btnHome;

	@FXML
	private ToggleButton btnParametrer;

	@FXML
	private ToggleButton btn9PG;

	@FXML
	private ToggleButton btn4ALS;

	@FXML
	private ToggleButton btnJD;

	@FXML
	private ToggleButton btnFAF;

	// Autres composants

	private BorderPane panneauNPG;

	private BorderPane panneau4ALS;

	private BorderPane panneauJD;

	private BorderPane panneauFAF;

	// Controllers

	private NPGController controllerNPG;

	private QALSController controller4ALS;

	private JDController controllerJD;

	private FAFController controllerFAF;

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau phase de jeu.");

		initialiser();

		// selectionnerVuePhase9PG();

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

		LOGGER.debug("[FIN] Initialisation du panneau phase de jeu.");
	}

	private void initialiser() {
		initialiser9PG();
		initialiser4ALS();
		initialiserJD();
		initialiserFAF();
	}

	private void initialiser9PG() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NPGView.fxml"));
			panneauNPG = (BorderPane) loader.load();
			controllerNPG = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}
	}

	private void initialiser4ALS() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/4ALSView.fxml"));
			panneau4ALS = (BorderPane) loader.load();
			controller4ALS = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}
	}

	private void initialiserJD() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/JDView.fxml"));
			panneauJD = (BorderPane) loader.load();
			controllerJD = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}
	}

	private void initialiserFAF() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FAFView.fxml"));
			panneauFAF = (BorderPane) loader.load();
			controllerFAF = loader.getController();

		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}
	}

	public void reinitialiser(TypePartie typePartie) {
		LOGGER.debug("[DEBUT] Réinitialisation de l'écran.");

		if (controllerNPG != null && (TypePartie.NPG.equals(typePartie) || TypePartie.PARTIE.equals(typePartie))) {
			LOGGER.debug("Réinitialisation du 9PG.");
			controllerNPG.reinitialiser();
		}
		if (controller4ALS != null && (TypePartie.QALS.equals(typePartie) || TypePartie.PARTIE.equals(typePartie))) {
			LOGGER.debug("Réinitialisation du 4ALS.");
			controller4ALS.reinitialiser();
		}
		if (controllerJD != null && (TypePartie.JD.equals(typePartie) || TypePartie.PARTIE.equals(typePartie))) {
			LOGGER.debug("Réinitialisation du JD.");
			controllerJD.reinitialiser();
		}
		if (controllerFAF != null && (TypePartie.FAF.equals(typePartie) || TypePartie.PARTIE.equals(typePartie))) {
			LOGGER.debug("Réinitialisation du FAF.");
			controllerFAF.reinitialiser();
		}
		if (chronometre != null) {
			chronometre.restart();
		}

		LOGGER.debug("[FIN] Réinitialisation de l'écran.");
	}

	// Gestion des évenements

	@FXML
	public void selectionnerVuePhase9PG() {
		LOGGER.info("### --> Clic sur \"Affichage vue 9PG\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);

		selectionnerVue(panneauNPG);
	}

	@FXML
	public void selectionnerVuePartie() {
		LOGGER.info("### --> Clic sur \"Affichage vue Partie\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn4ALS.setVisible(true);
		btnJD.setVisible(true);
		btnFAF.setVisible(true);

		selectionnerVue(panneauNPG);
	}

	@FXML
	public void selectionnerVuePhase4ALS() {
		LOGGER.info("### --> Clic sur \"Affichage vue 4ALS\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(true);
		btn4ALS.setSelected(true);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);

		selectionnerVue(panneau4ALS);
	}

	@FXML
	public void selectionnerVuePhaseJD() {
		LOGGER.info("### --> Clic sur \"Affichage vue JD\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(true);
		btnJD.setSelected(true);
		btnFAF.setVisible(false);

		selectionnerVue(panneauJD);
	}

	@FXML
	public void selectionnerVuePhaseFAF() {
		LOGGER.info("### --> Clic sur \"Affichage vue FAF\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(true);
		btnFAF.setSelected(true);

		selectionnerVue(panneauFAF);
	}

	private void selectionnerVue(BorderPane panneau) {
		content.getChildren().clear();
		content.setCenter(panneau);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("QP1C - E-BDJ");
		// Ne pas remplir l'entete
		alert.setHeaderText(null);
		alert.setContentText("Voulez-vous quitter la partie ?");
		alert.initOwner(Launcher.getStage());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			launcher.afficherEcranHome();
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
