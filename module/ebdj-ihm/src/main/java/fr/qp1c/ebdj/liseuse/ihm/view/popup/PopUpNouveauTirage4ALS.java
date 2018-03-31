package fr.qp1c.ebdj.liseuse.ihm.view.popup;

import java.util.Optional;

import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class PopUpNouveauTirage4ALS {

	private PopUpNouveauTirage4ALS() {
	}

	public static boolean afficherPopUp() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(Libelle.TITRE);
		// Ne pas remplir l'entete
		alert.setHeaderText(null);
		alert.setContentText("Voulez-vous réellement 4 nouveaux thèmes de 4ALS ?");
		alert.initOwner(Launcher.getStage());

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {
			return result.get() == ButtonType.OK;
		}
		return false;
	}
}
