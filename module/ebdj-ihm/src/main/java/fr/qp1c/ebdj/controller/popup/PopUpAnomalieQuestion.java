package fr.qp1c.ebdj.controller.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.model.TypePartie;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.TypeAnomalie;
import fr.qp1c.ebdj.utils.DialogUtils;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.Libelle;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class PopUpAnomalieQuestion {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpAnomalieQuestion.class);

	public PopUpAnomalieQuestion() {
	}

	private static List<TypeAnomalie> listerTypeAnomalie() {
		List<TypeAnomalie> typesErreur = new ArrayList<>();
		typesErreur.add(TypeAnomalie.QUESTION_MAL_REDIGEE);
		typesErreur.add(TypeAnomalie.QUESTION_PERIMEE);
		typesErreur.add(TypeAnomalie.QUESTION_FAUSSE);
		typesErreur.add(TypeAnomalie.REPONSE_INEXACTE);
		typesErreur.add(TypeAnomalie.REPONSE_INCOMPLETE);
		typesErreur.add(TypeAnomalie.REPONSE_FAUSSE);
		typesErreur.add(TypeAnomalie.MULTI_ANOMALIE);

		return typesErreur;
	}

	public static SignalementAnomalie afficherPopUp(TypePartie typePartie) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle(Libelle.TITRE);
		dialog.initOwner(Launcher.getStage());
		dialog.setHeaderText("Description de l'anomalie");

		// TODO remplacer le logo
		dialog.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_POPUP));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");
		dialogPane.setMinHeight(250);
		dialogPane.setMinWidth(500);

		ButtonType btnSignalerType = new ButtonType("Signaler", ButtonData.OK_DONE);
		ButtonType btnAnnulerType = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		dialogPane.getButtonTypes().addAll(btnAnnulerType, btnSignalerType);
		dialogPane.lookupButton(btnAnnulerType).setStyle("boutonFermer");
		dialogPane.lookupButton(btnSignalerType).setStyle("boutonFermer");

		Label labelTypeAnomalie = new Label("Type d'anomalie:");
		labelTypeAnomalie.setPrefHeight(40);
		labelTypeAnomalie.setPrefWidth(100);

		Label labelDescription = new Label("Description:");
		labelDescription.setPrefHeight(40);
		labelDescription.setPrefWidth(100);

		TextField description = new TextField();
		description.setPromptText("");
		description.setPrefHeight(40);
		description.setPrefWidth(400);

		ComboBox<TypeAnomalie> listeTypeAnomalie = new ComboBox<>();
		listeTypeAnomalie.getItems().addAll(listerTypeAnomalie());
		listeTypeAnomalie.getSelectionModel().select(0);
		listeTypeAnomalie.setPrefHeight(40);
		listeTypeAnomalie.setPrefWidth(400);
		listeTypeAnomalie.setCellFactory(new Callback<ListView<TypeAnomalie>, ListCell<TypeAnomalie>>() {
			@Override
			public ListCell<TypeAnomalie> call(ListView<TypeAnomalie> p) {
				return new ListCell<TypeAnomalie>() {

					@Override
					protected void updateItem(TypeAnomalie item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					}
				};
			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.add(labelTypeAnomalie, 0, 0);
		grid.add(listeTypeAnomalie, 1, 0);
		grid.add(labelDescription, 0, 1);
		grid.add(description, 1, 1);

		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> {
			DialogUtils.centrer(dialog);
		});

		// Traditional way to get the response value.
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent()) {

			if ("Signaler".equalsIgnoreCase(result.get().getText())) {
				SignalementAnomalie signalementAnomalie = new SignalementAnomalie();
				signalementAnomalie.setDescription(description.getText());
				signalementAnomalie.setTypeAnomalie(listeTypeAnomalie.getValue());
				return signalementAnomalie;
			}
		}

		return null;
	}

}
