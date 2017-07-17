package fr.qp1c.ebdj.view.component;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.controller.jeu.TypePartieController;
import fr.qp1c.ebdj.controller.popup.PopUpErreur;
import fr.qp1c.ebdj.controller.popup.PopUpLecteur;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
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

	private TypePartieController typePartieController;

	public PanneauLecteur() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/composant/LecteurView.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			PopUpErreur.afficherPopUp(exception);
			throw new RuntimeException(exception);
		}

		btnSelectionnerLecteur.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_UTILISATEUR, 25));
	}

	public void setTypePartieController(TypePartieController typePartieController) {
		this.typePartieController = typePartieController;
	}

	@FXML
	public void selectionnerLecteur() {

		LOGGER.info("### --> Clic sur \"Selectionner un utilisateur\".");

		Optional<String> result = PopUpLecteur.afficherPopUp();
		if (result.isPresent()) {
			LOGGER.info("Utilisateur séléctionné : " + result.get());

			if (!"Par défaut".equals(result.get())) {
				libelleLecteur.setText(result.get());
				libelleLecteur.getStyleClass().add("panneauLecteur");

				btnSelectionnerLecteur.setSelected(true);
			} else {
				libelleLecteur.setText("");
				libelleLecteur.getStyleClass().clear();
				btnSelectionnerLecteur.setSelected(false);
			}

			// TODO : Récupérer le bon lecteur

			Lecteur lecteur = new Lecteur();
			lecteur.setNom("GENDRON");
			lecteur.setPrenom("Nicolas");

			typePartieController.definirLecteur(lecteur);
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
