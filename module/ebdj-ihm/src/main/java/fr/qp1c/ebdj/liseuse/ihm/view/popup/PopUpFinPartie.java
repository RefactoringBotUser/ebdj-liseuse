package fr.qp1c.ebdj.liseuse.ihm.view.popup;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class PopUpFinPartie {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpFinPartie.class);

	public PopUpFinPartie() {
	}

	public static boolean afficherPopUp() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(Libelle.TITRE);
		// Ne pas remplir l'entete
		alert.setHeaderText(null);
		alert.setContentText("Voulez-vous quitter la partie ?");
		alert.initOwner(Launcher.getStage());

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			// ... user chose CANCEL or closed the dialog
			return false;
		}
	}
}
