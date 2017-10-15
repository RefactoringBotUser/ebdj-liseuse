package fr.qp1c.ebdj.liseuse.ihm.view.popup;

import java.io.PrintWriter;
import java.io.StringWriter;

import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class PopUpErreur {

	public PopUpErreur() {

	}

	public static void afficherPopUp(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(Libelle.TITRE);
		alert.setHeaderText("Une erreur est survenue.");
		alert.setContentText("Cause de l'anomalie : " + ex.getMessage());

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUpErreur");

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Voici la pile d'exécution :");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
