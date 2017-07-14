package fr.qp1c.ebdj.controller.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class PopUpAnomalieQuestion {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpAnomalieQuestion.class);

	public PopUpAnomalieQuestion() {
	}

	public static Pair<String, String> afficherPopUp() {

		List<String> typesErreur = new ArrayList<>();
		typesErreur.add("Question périmée");
		typesErreur.add("Question mal rédigée");
		typesErreur.add("Réponse incomplète");
		typesErreur.add("Réponse fausse");
		typesErreur.add("Autre problème");

		ChoiceDialog<String> popupErreur = new ChoiceDialog<>("Question périmée", typesErreur);
		popupErreur.setTitle("QP1C - E-Boite de jeu");
		popupErreur.initOwner(Launcher.getStage());
		popupErreur.setHeaderText("Des commes çà on en veut plus...");
		popupErreur.setContentText("Motif:");

		DialogPane dialogPane = popupErreur.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		ImageView imagePopup = new ImageView(ImageConstants.IMAGE_POPUP);
		ImageUtils.reduireImage(imagePopup);
		popupErreur.setGraphic(imagePopup);

		// Traditional way to get the response value.
		Optional<String> result = popupErreur.showAndWait();
		if (result.isPresent()) {
			LOGGER.info("Type d'anomalie sur la question : " + result.get());
		}

		return null;
	}

}
