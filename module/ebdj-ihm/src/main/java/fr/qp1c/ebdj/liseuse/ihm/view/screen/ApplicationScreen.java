package fr.qp1c.ebdj.liseuse.ihm.view.screen;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.TypePartie;
import fr.qp1c.ebdj.liseuse.ihm.controller.home.HomeController;
import fr.qp1c.ebdj.liseuse.ihm.controller.jeu.TypePartieController;
import fr.qp1c.ebdj.liseuse.ihm.controller.parametrage.ParametrageController;
import fr.qp1c.ebdj.liseuse.ihm.controller.stats.StatistiquesController;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpErreur;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpNiveauPartie;
import fr.qp1c.ebdj.liseuse.ihm.view.scene.SceneBdj;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.MyClassLoader;
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

public class ApplicationScreen extends Application {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationScreen.class);

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

	public static ClassLoader cachingClassLoader = new MyClassLoader(FXMLLoader.getDefaultClassLoader());

	// Image
	public static Image icon;

	@Override
	public void init() throws Exception {
		super.init();

		Font.loadFont(getClass().getResourceAsStream("./src/main/resources/fonts/Venacti Bold.ttf"), 14);
		icon = new Image(this.getClass().getResource(ImageConstants.LOGO_QP1C).toString());
		try {
			initialiserEcrans();
		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
			PopUpErreur.afficherPopUp(e);
		}
	}

	@Override
	public void start(Stage primaryStage) {
		LOGGER.info("[DEBUT] Start");

		stage = primaryStage;

		primaryStage.setTitle(Libelle.TITRE);

		ecranHome.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				LOGGER.debug("Width: {}", newSceneWidth);
			}
		});
		ecranHome.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				LOGGER.debug("Height: {}", newSceneHeight);
			}
		});

		// Initialisation de la stage principale
		primaryStage.setScene(ecranHome);
		primaryStage.getIcons().add(icon);
		primaryStage.show();

		LOGGER.info("[FIN] Start");
	}

	private void initialiserEcrans() throws IOException {
		LOGGER.info("[DEBUT] initialiserEcrans");

		// Création de la scène principale (=home)
		this.initialiserEcranHome();

		// Création de la scène (=questions).
		this.initialiserEcranQuestions();

		// Création de la scène (=stats).
		this.initialiserEcranStats();

		// Création de la scène (=parametrage).
		this.initialiserEcranParametrage();

		LOGGER.info("[FIN] initialiserEcrans");
	}

	private void initialiserEcranHome() throws IOException {
		LOGGER.info("[DEBUT] initialiserEcranHome");

		FXMLLoader loader = new FXMLLoader(ApplicationScreen.class.getResource("/view/home/HomeView.fxml"));
		loader.setClassLoader(cachingClassLoader);
		BorderPane page = (BorderPane) loader.load();

		homeControler = (HomeController) loader.getController();
		homeControler.setLauncher(this);

		ecranHome = new SceneBdj(page, Screen.getPrimary().getVisualBounds());

		LOGGER.info("[FIN] initialiserEcranHome");
	}

	private void initialiserEcranStats() throws IOException {
		LOGGER.info("[DEBUT] initialiserEcranStats");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stats/StatsView.fxml"));
		loader.setClassLoader(cachingClassLoader);
		VBox page = (VBox) loader.load();

		statistiquesController = (StatistiquesController) loader.getController();
		statistiquesController.setLauncher(this);

		ecranStats = new SceneBdj(page, Screen.getPrimary().getVisualBounds());

		LOGGER.info("[FIN] initialiserEcranStats");
	}

	private void initialiserEcranParametrage() throws IOException {
		LOGGER.info("[DEBUT] initialiserEcranParametrage");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/parametrage/ParametrageView.fxml"));
		loader.setClassLoader(cachingClassLoader);
		BorderPane page = (BorderPane) loader.load();
		((ParametrageController) loader.getController()).setLauncher(this);

		ecranParametrage = new SceneBdj(page, Screen.getPrimary().getVisualBounds());

		LOGGER.info("[FIN] initialiserEcranParametrage");
	}

	private void initialiserEcranQuestions() throws IOException {
		LOGGER.info("[DEBUT] initialiserEcranQuestions");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/jeu/TypePartieView.fxml"));
		loader.setClassLoader(cachingClassLoader);
		VBox page = (VBox) loader.load();

		typePartieController = (TypePartieController) loader.getController();
		typePartieController.setLauncher(this);

		ecranQuestions = new SceneBdj(page, Screen.getPrimary().getVisualBounds());

		LOGGER.info("[FIN] initialiserEcranQuestions");
	}

	public void afficherEcranQuestions(TypePartie typePartie) {
		LOGGER.info("[DEBUT] Affichage de l'écran partie.");

		NiveauPartie niveauPartie = PopUpNiveauPartie.afficherPopUp();

		if (niveauPartie == null) {
			// Sortir si abandon.
			return;
		}

		// Réinitialisation de l'écran
		typePartieController.reinitialiser(typePartie, niveauPartie);

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

		homeControler.masquerSousMenuTypeQuestionnaire();

		afficherEcran(ecranHome);

		LOGGER.info("[FIN] Affichage de l'écran accueil.");
	}

	private void afficherEcran(Scene scene) {

		int addHeight = 0;
		int addWidth = 0;

		// Fixbug : rétrecissement de la fenetre

		if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac os")) {
			LOGGER.debug("Affichage optimisé pour MAC OS.");

			addHeight = 22;
		} else if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
			LOGGER.debug("Affichage optimisé pour WINDOWS.");

			addHeight = 38;
			addWidth = 16;
		}

		Scene oldScene = stage.getScene();

		LOGGER.debug("Taille de l'écran original : largeurEcran={}, hauteurEcran={}", oldScene.getWidth(),
				oldScene.getHeight());

		double oldWidth = oldScene.getWidth() + addWidth;
		double oldHeight = oldScene.getHeight() + addHeight;

		stage.setScene(scene);
		stage.setWidth(oldWidth);
		stage.setHeight(oldHeight);
		stage.show();
	}

	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ApplicationScreen.class);
		logger.info("[DEBUT] Démarrage de l'application E-BDJ.");

		System.setProperty("prism.forceGPU", "true");

		/**
		 * try {
		 * 
		 * URL iconURL = new
		 * URL(Launcher.class.getResource(ImageConstants.LOGO_QP1C).toString());
		 * java.awt.Image image = new ImageIcon(iconURL).getImage();
		 * com.apple.eawt.Application.getApplication().setDockIconImage(image); } catch
		 * (Exception e) { // Won't work on Windows or Linux. }
		 */

		launch(args);

		logger.info("[FIN] Arrêt de l'application E-BDJ.");
	}

}
