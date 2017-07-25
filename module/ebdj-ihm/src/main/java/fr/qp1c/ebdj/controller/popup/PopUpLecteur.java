package fr.qp1c.ebdj.controller.popup;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurLecteurDaoImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.Libelle;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.util.Callback;

public class PopUpLecteur {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpLecteur.class);

	public PopUpLecteur() {
	}

	private static List<Lecteur> listerLecteurs() {
		DBConnecteurLecteurDao dbConnecteurLecteurDao = new DBConnecteurLecteurDaoImpl();
		List<Lecteur> lecteurs = dbConnecteurLecteurDao.listerLecteur();

		Lecteur lecteurParDefaut = new Lecteur();
		lecteurParDefaut.setId(Long.valueOf(-1));
		lecteurParDefaut.setPrenom("Lecteur par défaut");

		lecteurs.add(0, lecteurParDefaut);

		return lecteurs;
	}

	public static Optional<Lecteur> afficherPopUp() {

		Dialog<Lecteur> popupLecteur = new Dialog<>();
		popupLecteur.initOwner(Launcher.getStage());
		popupLecteur.initModality(Modality.APPLICATION_MODAL);
		popupLecteur.setTitle(Libelle.TITRE);
		popupLecteur.setHeaderText("Sélection du lecteur");

		// TODO : Afficher logo la selection du lecteur
		popupLecteur.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_POPUP));

		DialogPane dialogPane = popupLecteur.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		ButtonType btnSelectionType = new ButtonType("Selectionner", ButtonData.OK_DONE);
		dialogPane.getButtonTypes().addAll(btnSelectionType);
		dialogPane.lookupButton(btnSelectionType).setStyle("boutonFermer");

		HBox box = new HBox();
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(new Label("Lecteur : "));

		ComboBox<Lecteur> cmb = new ComboBox<>();
		cmb.getItems().addAll(listerLecteurs());
		cmb.getSelectionModel().select(0);

		cmb.setCellFactory(new Callback<ListView<Lecteur>, ListCell<Lecteur>>() {
			@Override
			public ListCell<Lecteur> call(ListView<Lecteur> p) {
				return new ListCell<Lecteur>() {

					@Override
					protected void updateItem(Lecteur item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.formatterNomUtilisateur());
						}
					}
				};
			}
		});

		box.getChildren().add(cmb);

		popupLecteur.getDialogPane().setContent(box);
		popupLecteur.showAndWait();

		// Traditional way to get the response value.
		Lecteur result = cmb.getValue();
		if (result != null && result.getId() > 0) {
			LOGGER.info("Lecteur séléctionné : " + result.formatterNomUtilisateur());
			return Optional.ofNullable(result);
		}

		return Optional.ofNullable(null);
	}
}
