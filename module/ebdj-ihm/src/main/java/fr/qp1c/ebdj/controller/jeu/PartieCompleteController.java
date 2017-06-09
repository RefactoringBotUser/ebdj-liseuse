package fr.qp1c.ebdj.controller.jeu;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.component.UtilisateurPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PartieCompleteController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PartieCompleteController.class);

	@FXML
	private BorderPane content;

	private BorderPane panneauNPG;

	private BorderPane panneau4ALS;

	private BorderPane panneauJD;

	private BorderPane panneauFAF;

	@FXML
	private UtilisateurPane utilisateurPane;

	@FXML
	private Button btnHome;

	@FXML
	private ToggleButton btnParametrer;

	private Launcher launcher;

	@FXML
	private void initialize() {

		LOGGER.debug("[DEBUT] Initialisation du panneau phase de jeu.");

		FXMLLoader loader = new FXMLLoader();

		try {
			panneauNPG = (BorderPane) loader.load(getClass().getResource("../../view/jeu/NPGView.fxml"));
			panneau4ALS = (BorderPane) loader.load(getClass().getResource("../../view/jeu/4ALSView.fxml"));
			panneauJD = (BorderPane) loader.load(getClass().getResource("../../view/jeu/JDView.fxml"));
			panneauFAF = (BorderPane) loader.load(getClass().getResource("../../view/jeu/FAFView.fxml"));

		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}
		content.getChildren().clear();
		content.setCenter(panneauNPG);

		BorderPane.setAlignment(content, Pos.TOP_CENTER);

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

		LOGGER.debug("[FIN] Initialisation du panneau phase de jeu.");
	}

	// Gestion des évenements

	@FXML
	public void selectionnerVuePhase9PG() {
		LOGGER.info("### --> Clic sur \"Affichage vue 9PG\".");

		content.getChildren().clear();
		content.setCenter(panneauNPG);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void selectionnerVuePhase4ALS() {
		LOGGER.info("### --> Clic sur \"Affichage vue 4ALS\".");

		content.getChildren().clear();
		content.setCenter(panneau4ALS);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void selectionnerVuePhaseJD() {
		LOGGER.info("### --> Clic sur \"Affichage vue JD\".");

		content.getChildren().clear();
		content.setCenter(panneauJD);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void selectionnerVuePhaseFAF() {
		LOGGER.info("### --> Clic sur \"Affichage vue FAF\".");

		content.getChildren().clear();
		content.setCenter(panneauFAF);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		launcher.afficherEcranHome();
	}

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
