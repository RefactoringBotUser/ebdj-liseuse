package fr.qp1c.ebdj.controller.jeu;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.controller.jeu.phase.FAFController;
import fr.qp1c.ebdj.controller.jeu.phase.JDController;
import fr.qp1c.ebdj.controller.jeu.phase.NPGController;
import fr.qp1c.ebdj.controller.jeu.phase.QALSController;
import fr.qp1c.ebdj.controller.popup.PopUpErreur;
import fr.qp1c.ebdj.controller.popup.PopUpFinPartie;
import fr.qp1c.ebdj.model.NiveauPartie;
import fr.qp1c.ebdj.model.TypePartie;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.component.PanneauChronometre;
import fr.qp1c.ebdj.view.component.PanneauLecteur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

public class TypePartieController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypePartieController.class);

	// Composants graphiques

	@FXML
	private BorderPane content;

	@FXML
	private PanneauLecteur panneauLecteur;

	@FXML
	private PanneauChronometre panneauChronometre;

	@FXML
	private Button btnHome;

	@FXML
	private ToggleButton btnParametrer;

	@FXML
	private ToggleButton btn9PG;

	@FXML
	private ToggleButton btn4ALS;

	@FXML
	private ToggleButton btnJD;

	@FXML
	private ToggleButton btnFAF;

	// Autres composants

	private BorderPane panneauNPG;

	private BorderPane panneau4ALS;

	private BorderPane panneauJD;

	private BorderPane panneauFAF;

	// Controllers

	private NPGController controllerNPG;

	private QALSController controller4ALS;

	private JDController controllerJD;

	private FAFController controllerFAF;

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau phase de jeu.");

		initialiser();

		btnHome.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_HOME, 25));

		LOGGER.debug("[FIN] Initialisation du panneau phase de jeu.");
	}

	private void initialiser() {
		initialiserPanneauLecteur();
		initialiser9PG(panneauLecteur.getLecteur());
		initialiser4ALS(panneauLecteur.getLecteur());
		initialiserJD(panneauLecteur.getLecteur());
		initialiserFAF(panneauLecteur.getLecteur());

	}

	public void definirLecteur(Lecteur lecteur) {
		controllerNPG.definirLecteur(lecteur);
		controller4ALS.definirLecteur(lecteur);
		controllerJD.definirLecteur(lecteur);
		controllerFAF.definirLecteur(lecteur);
	}

	private void initialiserPanneauLecteur() {
		panneauLecteur.setTypePartieController(this);
		panneauLecteur.selectionnerLecteurParDefaut();
	}

	private void initialiser9PG(Lecteur lecteur) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/phase/NPGView.fxml"));
			panneauNPG = (BorderPane) loader.load();
			controllerNPG = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}
	}

	private void initialiser4ALS(Lecteur lecteur) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/phase/4ALSView.fxml"));
			panneau4ALS = (BorderPane) loader.load();
			controller4ALS = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}
	}

	private void initialiserJD(Lecteur lecteur) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/phase/JDView.fxml"));
			panneauJD = (BorderPane) loader.load();
			controllerJD = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}
	}

	private void initialiserFAF(Lecteur lecteur) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/phase/FAFView.fxml"));
			panneauFAF = (BorderPane) loader.load();
			controllerFAF = loader.getController();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}
	}

	public void reinitialiser(TypePartie typePartie, NiveauPartie niveauPartie) {
		LOGGER.debug("[DEBUT] Réinitialisation de l'écran.");

		Lecteur lecteur = panneauLecteur.getLecteur();

		if (controllerNPG != null) {
			LOGGER.debug("Réinitialisation du 9PG.");
			controllerNPG.reinitialiser();
			controllerNPG.definirNiveauPartie(niveauPartie);
			controllerNPG.definirLecteur(lecteur);
		}
		if (controller4ALS != null) {
			LOGGER.debug("Réinitialisation du 4ALS.");
			controller4ALS.reinitialiser();
			controller4ALS.definirNiveauPartie(niveauPartie);
			controller4ALS.definirLecteur(lecteur);
		}
		if (controllerJD != null) {
			LOGGER.debug("Réinitialisation du JD.");
			controllerJD.reinitialiser();
			controllerJD.definirNiveauPartie(niveauPartie);
			controllerJD.definirLecteur(lecteur);
		}
		if (controllerFAF != null) {
			LOGGER.debug("Réinitialisation du FAF.");
			controllerFAF.reinitialiser();
			controllerFAF.definirNiveauPartie(niveauPartie);
			controllerFAF.definirLecteur(lecteur);
		}
		if (panneauChronometre != null) {
			panneauChronometre.restart();
		}

		if (TypePartie.NPG.equals(typePartie) || TypePartie.PARTIE.equals(typePartie)) {
			// Lancer en mode 1,2,3
			controllerNPG.changerNiveau123();

		} else if (TypePartie.QALS.equals(typePartie)) {

		} else if (TypePartie.JD.equals(typePartie)) {
			controllerJD.jouerNouvelleQuestionJD();
		} else if (TypePartie.FAF.equals(typePartie)) {
			controllerFAF.jouerNouvelleQuestionFAF();
		}

		LOGGER.debug("[FIN] Réinitialisation de l'écran.");
	}

	// Gestion des évenements

	public void selectionnerVuePhase9PG() {
		LOGGER.info("### --> Clic sur \"Affichage vue 9PG\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);

		selectionnerVue(panneauNPG);
	}

	public void selectionnerVuePartie() {
		LOGGER.info("### --> Clic sur \"Affichage vue Partie\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn4ALS.setVisible(true);
		btnJD.setVisible(true);
		btnFAF.setVisible(true);

		selectionnerVue(panneauNPG);
	}

	public void selectionnerVuePhase4ALS() {
		LOGGER.info("### --> Clic sur \"Affichage vue 4ALS\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(true);
		btn4ALS.setSelected(true);
		btnJD.setVisible(false);
		btnFAF.setVisible(false);

		selectionnerVue(panneau4ALS);
	}

	public void selectionnerVuePhaseJD() {
		LOGGER.info("### --> Clic sur \"Affichage vue JD\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(true);
		btnJD.setSelected(true);
		btnFAF.setVisible(false);

		selectionnerVue(panneauJD);
	}

	public void selectionnerVuePhaseFAF() {
		LOGGER.info("### --> Clic sur \"Affichage vue FAF\".");

		btn9PG.setVisible(false);
		btn4ALS.setVisible(false);
		btnJD.setVisible(false);
		btnFAF.setVisible(true);
		btnFAF.setSelected(true);

		selectionnerVue(panneauFAF);
	}

	@FXML
	public void afficherVuePhase9PG() {
		LOGGER.info("### --> Clic sur \"Affichage vue 9PG\".");

		btn9PG.setSelected(true);

		selectionnerVue(panneauNPG);
	}

	@FXML
	public void afficherVuePhase4ALS() {
		LOGGER.info("### --> Clic sur \"Affichage vue 4ALS\".");

		btn4ALS.setSelected(true);

		selectionnerVue(panneau4ALS);
	}

	@FXML
	public void afficherVuePhaseJD() {
		LOGGER.info("### --> Clic sur \"Affichage vue JD\".");

		btnJD.setSelected(true);

		controllerJD.jouerNouvelleQuestionJD();

		selectionnerVue(panneauJD);
	}

	@FXML
	public void afficherVuePhaseFAF() {
		LOGGER.info("### --> Clic sur \"Affichage vue FAF\".");

		btnFAF.setSelected(true);

		controllerFAF.jouerNouvelleQuestionFAF();

		selectionnerVue(panneauFAF);
	}

	private void selectionnerVue(BorderPane panneau) {
		content.getChildren().clear();
		content.setCenter(panneau);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		if (PopUpFinPartie.afficherPopUp()) {
			launcher.afficherEcranHome();
		}
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
