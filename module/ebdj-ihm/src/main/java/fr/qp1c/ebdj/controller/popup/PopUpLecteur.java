package fr.qp1c.ebdj.controller.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurLecteurDaoImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.Libelle;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;

public class PopUpLecteur {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PopUpLecteur.class);

	public PopUpLecteur() {
	}

	public static Optional<String> afficherPopUp() {

		DBConnecteurLecteurDao dbConnecteurLecteurDao = new DBConnecteurLecteurDaoImpl();
		List<Lecteur> lecteurs = dbConnecteurLecteurDao.listerLecteur();

		List<String> libelleLecteurs = new ArrayList<>();

		libelleLecteurs.add("Par défaut");
		for (Lecteur lecteur : lecteurs) {
			libelleLecteurs.add(lecteur.formatterNomUtilisateur());
		}

		ChoiceDialog<String> popupLecteur = new ChoiceDialog<>("Par défaut", libelleLecteurs);
		popupLecteur.setTitle(Libelle.TITRE);
		popupLecteur.setHeaderText("Sélectionner l'utilisateur...");
		popupLecteur.setContentText("Utilisateur:");

		// TODO : Afficher logo la selection du lecteur
		popupLecteur.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_POPUP));

		DialogPane dialogPane = popupLecteur.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		// Traditional way to get the response value.
		Optional<String> result = popupLecteur.showAndWait();
		if (result.isPresent()) {
			LOGGER.info("Utilisateur séléctionné : " + result.get());
			return Optional.ofNullable(result.get());
		}

		return Optional.ofNullable(null);
	}
}
