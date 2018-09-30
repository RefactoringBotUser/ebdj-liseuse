package fr.qp1c.ebdj.liseuse.ihm.view.popup;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import fr.qp1c.ebdj.liseuse.ihm.view.screen.ApplicationScreen;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.DialogUtils;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.ImageUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class PopUpNiveauPartie {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpNiveauPartie.class);

	private PopUpNiveauPartie() {
	}

	public static NiveauPartie afficherPopUp() {

		Dialog<String> dialog = new Dialog<>();
		dialog.initOwner(ApplicationScreen.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle(Libelle.TITRE);
		dialog.setHeaderText("Niveau du questionnaire");
		dialog.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_POPUP));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");
		dialogPane.setMinHeight(500);
		dialogPane.setMinWidth(900);

		Button btnFacile = new Button("FACILE", ImageUtils.creerImage("/images/niveau/niveau_facile.png"));
		btnFacile.setContentDisplay(ContentDisplay.TOP);
		btnFacile.setGraphicTextGap(10);
		btnFacile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				LOGGER.info("### --> Clic sur \"Niveau FACILE\".");

				dialog.setResult("FACILE");
			}
		});

		Button btnMoyen = new Button("MOYEN", ImageUtils.creerImage("/images/niveau/niveau_moyen.png"));
		btnMoyen.setContentDisplay(ContentDisplay.TOP);
		btnMoyen.setGraphicTextGap(10);
		btnMoyen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				LOGGER.info("### --> Clic sur \"Niveau MOYEN\".");

				dialog.setResult("MOYEN");
			}
		});

		Button btnDifficile = new Button("DIFFICILE", ImageUtils.creerImage("/images/niveau/niveau_difficile.png"));
		btnDifficile.setContentDisplay(ContentDisplay.TOP);
		btnDifficile.setGraphicTextGap(10);
		btnDifficile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				LOGGER.info("### --> Clic sur \"Niveau DIFFICILE\".");

				dialog.setResult("DIFFICILE");
			}
		});

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(btnFacile);
		box.getChildren().add(ImageUtils.iconiserImage(ImageConstants.IMAGE_VIDE));
		box.getChildren().add(btnMoyen);
		box.getChildren().add(ImageUtils.iconiserImage(ImageConstants.IMAGE_VIDE));
		box.getChildren().add(btnDifficile);

		dialog.getDialogPane().setContent(box);

		Platform.runLater(() -> DialogUtils.centrer(dialog));

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			if ("FACILE".equals(result.get())) {
				return NiveauPartie.FACILE;
			} else if ("MOYEN".equals(result.get())) {
				return NiveauPartie.MOYEN;
			} else if ("DIFFICILE".equals(result.get())) {
				return NiveauPartie.DIFFICILE;
			}
		}

		return null;
	}

}
