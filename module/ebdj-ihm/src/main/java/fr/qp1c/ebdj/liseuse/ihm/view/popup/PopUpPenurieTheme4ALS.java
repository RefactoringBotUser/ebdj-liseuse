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

public class PopUpPenurieTheme4ALS {

    private PopUpPenurieTheme4ALS() {

    }

    public static void afficherPopUp() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Libelle.TITRE);
        alert.setHeaderText("Pénurie de thèmes de 4ALS.");
        alert.setContentText("Recycler les thèmes de 4ALS non joués.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("/css/styles.css");
        dialogPane.getStyleClass().add("popUpErreur");

        alert.showAndWait();
    }
}
