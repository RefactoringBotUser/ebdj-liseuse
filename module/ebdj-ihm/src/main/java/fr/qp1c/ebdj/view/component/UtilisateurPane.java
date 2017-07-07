package fr.qp1c.ebdj.view.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UtilisateurPane extends HBox {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurPane.class);

	@FXML
	private ToggleButton btnSelectionnerUilisateur;

	@FXML
	private Label libelleUtilisateur;

	@FXML
	private HBox zoneUtilisateur;

	@FXML
	private VBox carton9PG;

	public UtilisateurPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UtilisateurView.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		ImageView imageUtilisateur = new ImageView(ImageConstants.IMAGE_UTILISATEUR);
		ImageUtils.reduireImageCustom(imageUtilisateur, 25);

		btnSelectionnerUilisateur.setGraphic(imageUtilisateur);

	}

	@FXML
	public void selectionnerUtilisateur() {

		LOGGER.info("### --> Clic sur \"Selectionner un utilisateur\".");

		List<String> listeUtilisateur = new ArrayList<>();
		listeUtilisateur.add("Par défaut");
		listeUtilisateur.add("Colette");
		listeUtilisateur.add("Jean-Marc");
		listeUtilisateur.add("Jocelyne");
		listeUtilisateur.add("Laurent");

		ChoiceDialog<String> popupErreur = new ChoiceDialog<>("Par défaut", listeUtilisateur);
		popupErreur.setTitle("QP1C - E-Boite de jeu");
		popupErreur.setHeaderText("Sélectionner l'utilisateur...");
		popupErreur.setContentText("Utilisateur:");

		ImageView imagePopup = new ImageView(ImageConstants.IMAGE_POPUP);
		ImageUtils.reduireImage(imagePopup);
		popupErreur.setGraphic(imagePopup);

		// Traditional way to get the response value.
		Optional<String> result = popupErreur.showAndWait();
		if (result.isPresent()) {
			LOGGER.info("Utilisateur séléctionné : " + result.get());

			if (!"Par défaut".equals(result.get())) {
				libelleUtilisateur.setText(result.get());
				libelleUtilisateur.getStyleClass().add("utilisateur");

				btnSelectionnerUilisateur.setSelected(true);
				//
				// ImageView imageUtilisateur = new
				// ImageView(ImageConstants.IMAGE_UTILISATEUR_BLANK);
				// ImageUtils.reduireImageCustom(imageUtilisateur, 25);
				// btnSelectionnerUilisateur.setGraphic(imageUtilisateur);

			} else {
				libelleUtilisateur.setText("");
				libelleUtilisateur.getStyleClass().clear();
				btnSelectionnerUilisateur.setSelected(false);
			}
		}
	}

}
