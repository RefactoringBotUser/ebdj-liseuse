package fr.qp1c.ebdj.controller.parametrage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.config.ImageConstants;
import fr.qp1c.ebdj.view.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ParametrageController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParametrageController.class);

	// Composant(s) JavaFX

	@FXML
	private Button btnHome;

	// Autres attributs

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau paramétrage.");

		btnHome.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_HOME, 25));

		// TODO : régler : nom de la base de données, clé de la boite de jeu,
		// url BDJ distante, liste lecteur + préférences taille...)

		// TODO : Afficher le numéro de version de l'application

		LOGGER.debug("[FIN] Initialisation du panneau paramétrage.");
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		launcher.afficherEcranHome();
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
