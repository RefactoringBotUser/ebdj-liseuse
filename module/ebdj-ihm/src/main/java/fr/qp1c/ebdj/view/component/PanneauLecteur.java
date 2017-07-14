package fr.qp1c.ebdj.view.component;

import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PanneauLecteur extends HBox {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PanneauLecteur.class);

	@FXML
	private ToggleButton btnSelectionnerLecteur;

	@FXML
	private Label libelleLecteur;

	@FXML
	private HBox zoneLecteur;

	@FXML
	private VBox carton9PG;

	private Lecteur lecteur;

	public PanneauLecteur() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/composant/LecteurView.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		ImageView imageUtilisateur = new ImageView(ImageConstants.IMAGE_UTILISATEUR);
		ImageUtils.reduireImageCustom(imageUtilisateur, 25);

		btnSelectionnerLecteur.setGraphic(imageUtilisateur);

	}

	@FXML
	public void selectionnerLecteur() {

		LOGGER.info("### --> Clic sur \"Selectionner un utilisateur\".");

		DBConnecteurLecteurDao dbConnecteurLecteurDao = new DBConnecteurLecteurDaoImpl();
		List<Lecteur> lecteurs = dbConnecteurLecteurDao.listerLecteur();

		List<String> libelleLecteurs = new ArrayList<>();

		libelleLecteurs.add("Par défaut");
		for (Lecteur lecteur : lecteurs) {
			libelleLecteurs.add(lecteur.formatterNomUtilisateur());
		}

		ChoiceDialog<String> popupErreur = new ChoiceDialog<>("Par défaut", libelleLecteurs);
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
				libelleLecteur.setText(result.get());
				libelleLecteur.getStyleClass().add("utilisateur");

				btnSelectionnerLecteur.setSelected(true);
			} else {
				libelleLecteur.setText("");
				libelleLecteur.getStyleClass().clear();
				btnSelectionnerLecteur.setSelected(false);

				lecteur = lecteurs.get(0);
			}
		}
	}

	// Getters - setters

	public Lecteur getLecteur() {
		return lecteur;
	}

	public void setLecteur(Lecteur lecteur) {
		this.lecteur = lecteur;
	}

	public boolean isLecteurRenseigne() {
		if (lecteur != null) {
			return true;
		}
		return false;
	}

}
