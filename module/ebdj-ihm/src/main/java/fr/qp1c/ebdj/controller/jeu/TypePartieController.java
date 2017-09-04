package fr.qp1c.ebdj.controller.jeu;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.controller.jeu.phase.FAFController;
import fr.qp1c.ebdj.controller.jeu.phase.JDController;
import fr.qp1c.ebdj.controller.jeu.phase.NPGController;
import fr.qp1c.ebdj.controller.jeu.phase.QALSController;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.partie.TypePartie;
import fr.qp1c.ebdj.utils.config.ImageConstants;
import fr.qp1c.ebdj.view.panneau.PanneauChronometre;
import fr.qp1c.ebdj.view.panneau.PanneauLecteur;
import fr.qp1c.ebdj.view.popup.PopUpErreur;
import fr.qp1c.ebdj.view.popup.PopUpFinPartie;
import fr.qp1c.ebdj.view.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class TypePartieController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TypePartieController.class);

	// Composant(s) JavaFX

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
	private HBox hboxPhase;

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

	// Autres attributs

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.info("[DEBUT] Initialisation du panneau quitter la partie.");

		initialiser();

		btnHome.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_QUITTER_PARTIE, 20));
		btnHome.setText("Quitter la partie");
		btnHome.setTextAlignment(TextAlignment.CENTER);

		LOGGER.info("[FIN] Initialisation du panneau quitter la partie.");
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
		LOGGER.info("[DEBUT] Réinitialisation de l'écran.");

		Lecteur lecteur = panneauLecteur.getLecteur();
		panneauLecteur.selectionnerLecteurParDefaut();

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
			// TODO implementer la gestion des 4ALS
		} else if (TypePartie.JD.equals(typePartie)) {
			controllerJD.jouerNouvelleQuestionJD();
		} else if (TypePartie.FAF.equals(typePartie)) {
			controllerFAF.jouerNouvelleQuestionFAF();
		}

		LOGGER.info("[FIN] Réinitialisation de l'écran.");
	}

	// Gestion des évenements

	public void selectionnerVuePhase9PG() {
		LOGGER.info("### --> Clic sur \"Affichage vue 9PG\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn9PG.setMaxWidth(100.0);
		btn9PG.setText("9PG");
		btn4ALS.setVisible(false);
		btn4ALS.setMaxWidth(0.0);
		btn4ALS.setText("");
		btnJD.setVisible(false);
		btnJD.setMaxWidth(0.0);
		btnJD.setText("");
		btnFAF.setVisible(false);
		btnFAF.setMaxWidth(0.0);
		btnFAF.setText("");

		hboxPhase.setMargin(btn4ALS, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnJD, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnFAF, new Insets(0.0, 0.0, 0.0, 0.0));

		selectionnerVue(panneauNPG);
	}

	public void selectionnerVuePhase4ALS() {
		LOGGER.info("### --> Clic sur \"Affichage vue 4ALS\".");

		btn9PG.setVisible(false);
		btn9PG.setMaxWidth(0.0);
		btn9PG.setText("");
		btn4ALS.setVisible(true);
		btn4ALS.setSelected(true);
		btn4ALS.setMaxWidth(100.0);
		btn4ALS.setText("4ALS");
		btnJD.setVisible(false);
		btnJD.setMaxWidth(0.0);
		btnJD.setText("");
		btnFAF.setVisible(false);
		btnFAF.setMaxWidth(0.0);
		btnFAF.setText("");

		hboxPhase.setMargin(btn9PG, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnJD, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnFAF, new Insets(0.0, 0.0, 0.0, 0.0));

		selectionnerVue(panneau4ALS);
	}

	public void selectionnerVuePhaseJD() {
		LOGGER.info("### --> Clic sur \"Affichage vue JD\".");

		btn9PG.setVisible(false);
		btn9PG.setMaxWidth(0.0);
		btn9PG.setText("");
		btn4ALS.setVisible(false);
		btn4ALS.setMaxWidth(0.0);
		btn4ALS.setText("");
		btnJD.setVisible(true);
		btnJD.setSelected(true);
		btnJD.setMaxWidth(100.0);
		btnJD.setText("JD");
		btnFAF.setVisible(false);
		btnFAF.setMaxWidth(0.0);
		btnFAF.setText("");

		hboxPhase.setMargin(btn9PG, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btn4ALS, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnFAF, new Insets(0.0, 0.0, 0.0, 0.0));

		selectionnerVue(panneauJD);
	}

	public void selectionnerVuePhaseFAF() {
		LOGGER.info("### --> Clic sur \"Affichage vue FAF\".");

		btn9PG.setVisible(false);
		btn9PG.setMaxWidth(0.0);
		btn9PG.setText("");
		btn4ALS.setVisible(false);
		btn4ALS.setMaxWidth(0.0);
		btn4ALS.setText("");
		btnJD.setVisible(false);
		btnJD.setMaxWidth(0.0);
		btnJD.setText("");
		btnFAF.setVisible(true);
		btnFAF.setSelected(true);
		btnFAF.setMaxWidth(100.0);
		btnFAF.setText("FAF");
		hboxPhase.setMargin(btn9PG, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btn4ALS, new Insets(0.0, 0.0, 0.0, 0.0));
		hboxPhase.setMargin(btnJD, new Insets(0.0, 0.0, 0.0, 0.0));

		selectionnerVue(panneauFAF);
	}

	public void selectionnerVuePartie() {
		LOGGER.info("### --> Clic sur \"Affichage vue Partie\".");

		btn9PG.setVisible(true);
		btn9PG.setSelected(true);
		btn9PG.setMaxWidth(100.0);
		btn9PG.setText("9PG");
		btn4ALS.setVisible(true);
		btn4ALS.setMaxWidth(100.0);
		btn4ALS.setText("4ALS");
		btnJD.setVisible(true);
		btnJD.setMaxWidth(100.0);
		btnJD.setText("JD");
		btnFAF.setVisible(true);
		btnFAF.setMaxWidth(100.0);
		btnFAF.setText("FAF");
		hboxPhase.setMargin(btn9PG, new Insets(10.0, 10.0, 10.0, 10.0));
		hboxPhase.setMargin(btn4ALS, new Insets(10.0, 10.0, 10.0, 10.0));
		hboxPhase.setMargin(btnJD, new Insets(10.0, 10.0, 10.0, 10.0));
		hboxPhase.setMargin(btnFAF, new Insets(10.0, 10.0, 10.0, 10.0));

		selectionnerVue(panneauNPG);
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

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		if (PopUpFinPartie.afficherPopUp()) {
			launcher.afficherEcranHome();
		}
	}

	private void selectionnerVue(BorderPane panneau) {
		content.getChildren().clear();
		content.setCenter(panneau);
		BorderPane.setAlignment(content, Pos.TOP_CENTER);
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}

}
