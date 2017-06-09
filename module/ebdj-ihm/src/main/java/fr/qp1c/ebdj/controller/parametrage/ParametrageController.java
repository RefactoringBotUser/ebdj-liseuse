package fr.qp1c.ebdj.controller.parametrage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ParametrageController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParametrageController.class);

	@FXML
	private Button btnHome;

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau paramétrage.");

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

		LOGGER.debug("[FIN] Initialisation du panneau paramétrage.");
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
