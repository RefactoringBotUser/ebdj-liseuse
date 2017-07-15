package fr.qp1c.ebdj;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.controller.home.HomeController;
import fr.qp1c.ebdj.controller.jeu.TypePartieController;
import fr.qp1c.ebdj.controller.parametrage.ParametrageController;
import fr.qp1c.ebdj.controller.popup.PopUpErreur;
import fr.qp1c.ebdj.controller.stats.StatistiquesController;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.view.Libelle;
import fr.qp1c.ebdj.view.TypePartie;
import fr.qp1c.ebdj.view.component.SceneBdj;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Launcher extends Application {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

	// Scene principale

	private static Stage stage;

	// Ecrans

	private Scene ecranHome;

	private Scene ecranQuestions;

	private Scene ecranStats;

	private Scene ecranParametrage;

	// Controllers

	private TypePartieController typePartieController;

	private StatistiquesController statistiquesController;

	private HomeController homeControler;

	@Override
	public void start(Stage primaryStage) {
		LOGGER.info("[DEBUT] Start");

		stage = primaryStage;

		primaryStage.setTitle(Libelle.TITRE);

		try {
			Font.loadFont(getClass().getResourceAsStream("./src/main/resources/fonts/Venacti Bold.ttf"), 14);

			initialiserEcrans();

			ecranHome.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
						Number newSceneWidth) {
					LOGGER.debug("Width: " + newSceneWidth);
				}
			});
			ecranHome.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
						Number newSceneHeight) {
					LOGGER.debug("Height: " + newSceneHeight);
				}
			});

			// Initialisation de la stage principale
			primaryStage.setScene(ecranHome);
			primaryStage.getIcons().add(new Image(this.getClass().getResource(ImageConstants.LOGO_QP1C).toString()));
			primaryStage.show();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}

		LOGGER.info("[FIN] Start");
	}

	private void initialiserEcrans() throws IOException {

		// Création de la scène principale (=home)
		this.initialiserEcranHome();

		// Création de la scène (=questions).
		this.initialiserEcranQuestions();

		// Création de la scène (=stats).
		this.initialiserEcranStats();

		// Création de la scène (=parametrage).
		this.initialiserEcranParametrage();
	}

	private void initialiserEcranHome() throws IOException {
		FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("/view/home/HomeView.fxml"));
		BorderPane page = (BorderPane) loader.load();

		homeControler = (HomeController) loader.getController();
		homeControler.setLauncher(this);

		ecranHome = new SceneBdj(page, Screen.getPrimary().getVisualBounds());
	}

	private void initialiserEcranStats() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stats/StatsView.fxml"));
		VBox page = (VBox) loader.load();

		statistiquesController = (StatistiquesController) loader.getController();
		statistiquesController.setLauncher(this);

		ecranStats = new SceneBdj(page, Screen.getPrimary().getVisualBounds());
	}

	private void initialiserEcranParametrage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/parametrage/ParametrageView.fxml"));
		BorderPane page = (BorderPane) loader.load();
		((ParametrageController) loader.getController()).setLauncher(this);

		ecranParametrage = new SceneBdj(page, Screen.getPrimary().getVisualBounds());
	}

	private void initialiserEcranQuestions() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/TypePartieView.fxml"));
		BorderPane page = (BorderPane) loader.load();

		typePartieController = (TypePartieController) loader.getController();
		typePartieController.setLauncher(this);

		ecranQuestions = new SceneBdj(page, Screen.getPrimary().getVisualBounds());
	}

	public void afficherEcranQuestions(TypePartie typePartie) {
		LOGGER.info("[DEBUT] Affichage de l'écran partie.");

		// Réinitialisation de l'écran
		typePartieController.reinitialiser(typePartie);

		switch (typePartie) {
		case NPG:
			typePartieController.selectionnerVuePhase9PG();
			break;
		case QALS:
			typePartieController.selectionnerVuePhase4ALS();
			break;
		case JD:
			typePartieController.selectionnerVuePhaseJD();
			break;
		case FAF:
			typePartieController.selectionnerVuePhaseFAF();
			break;
		case PARTIE:
			typePartieController.selectionnerVuePartie();
			break;
		}

		afficherEcran(ecranQuestions);

		LOGGER.info("[FIN] Affichage de l'écran partie.");
	}

	public void afficherEcranStats() {
		LOGGER.info("[DEBUT] Affichage de l'écran de stats");

		statistiquesController.actualiserContenuTableaux();

		afficherEcran(ecranStats);

		LOGGER.info("[FIN] Affichage de l'écran de stats.");
	}

	public void afficherEcranParametrage() {
		LOGGER.info("[DEBUT] Affichage de l'écran de paramétrage");

		afficherEcran(ecranParametrage);

		LOGGER.info("[FIN] Affichage de l'écran de paramétrage.");
	}

	public void afficherEcranHome() {
		LOGGER.info("[DEBUT] Affichage de l'écran accueil");

		homeControler.masquerSousMenuQuestion();

		afficherEcran(ecranHome);

		LOGGER.info("[FIN] Affichage de l'écran accueil.");
	}

	private void afficherEcran(Scene scene) {
		Scene oldScene = stage.getScene();
		double oldWidth = oldScene.getWidth();
		double oldHeight = oldScene.getHeight() + 22;

		stage.setScene(scene);
		stage.setWidth(oldWidth);
		stage.setHeight(oldHeight);
		stage.show();
	}

	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		LOGGER.info("[DEBUT] Démarrage de l'application E-BDJ.");

		/**
		 * try {
		 * 
		 * URL iconURL = new
		 * URL(Launcher.class.getResource(ImageConstants.LOGO_QP1C).toString());
		 * java.awt.Image image = new ImageIcon(iconURL).getImage();
		 * com.apple.eawt.Application.getApplication().setDockIconImage(image);
		 * } catch (Exception e) { // Won't work on Windows or Linux. }
		 */

		launch(args);

		LOGGER.info("[FIN] Arrêt de l'application E-BDJ.");
	}

}
