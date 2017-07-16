package fr.qp1c.ebdj.controller.popup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.Libelle;
import fr.qp1c.ebdj.view.Niveau;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class PopUpNiveauPartie {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpNiveauPartie.class);

	public PopUpNiveauPartie() {
	}

	public static Niveau afficherPopUp() {

		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle(Libelle.TITRE);
		dialog.setHeaderText("Niveau des questions");
		dialog.initOwner(Launcher.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setHeight(200);
		dialog.setWidth(500);

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		dialog.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_POPUP));

		ButtonType btnGoType = new ButtonType("Jouer !", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btnGoType);
		dialog.getDialogPane().lookupButton(btnGoType).setStyle("boutonFermer");

		final ToggleGroup group = new ToggleGroup();

		ToggleButton btnFacile = new ToggleButton("Facile");
		ToggleButton btnMoyen = new ToggleButton("Moyen");
		btnMoyen.setSelected(true);
		ToggleButton btnDifficile = new ToggleButton("Difficile");

		btnFacile.setToggleGroup(group);
		btnMoyen.setToggleGroup(group);
		btnDifficile.setToggleGroup(group);

		HBox box = new HBox();
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(btnFacile);
		box.getChildren().add(ImageUtils.iconiserImage(ImageConstants.IMAGE_DEMI_VIDE));
		box.getChildren().add(btnMoyen);
		box.getChildren().add(ImageUtils.iconiserImage(ImageConstants.IMAGE_DEMI_VIDE));
		box.getChildren().add(btnDifficile);

		dialog.getDialogPane().setContent(box);
		dialog.showAndWait();

		if (btnFacile.isSelected()) {
			return Niveau.FACILE;
		} else if (btnDifficile.isSelected()) {
			return Niveau.DIFFICILE;
		}

		return Niveau.MOYEN;
	}

}
