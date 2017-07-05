package fr.qp1c.ebdj;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.controller.home.HomeController;
import fr.qp1c.ebdj.controller.jeu.PartieCompleteController;
import fr.qp1c.ebdj.controller.parametrage.ParametrageController;
import fr.qp1c.ebdj.controller.stats.StatistiquesController;
import fr.qp1c.ebdj.utils.ImageConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Launcher extends Application {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

	private Stage stage;

	private Scene ecranHome;

	private Scene ecranStats;

	private Scene ecranParametrage;

	private Scene ecranPartieComplete;

	@Override
	public void start(Stage primaryStage) {
		LOGGER.info("[DEBUT] Start");

		stage = primaryStage;

		primaryStage.setTitle("QP1C - E-Boite de jeu");

		try {

			Font.loadFont(getClass().getResourceAsStream("./src/main/resources/fonts/Venacti Bold.ttf"), 14);

			// Création de la scène principale (=home)
			this.initialiserEcranHome();

			// Création de la scène (=partie complète).
			this.initialiserEcranPartieComplete();

			// Création de la scène (=stats).
			this.initialiserEcranStats();

			// Création de la scène (=parametrage).
			this.initialiserEcranParametrage();

			// scene.widthProperty().addListener(new ChangeListener<Number>() {
			// @Override public void changed(ObservableValue<? extends Number>
			// observableValue, Number oldSceneWidth, Number newSceneWidth) {
			// System.out.println("Width: " + newSceneWidth);
			// }
			// });
			// scene.heightProperty().addListener(new ChangeListener<Number>() {
			// @Override public void changed(ObservableValue<? extends Number>
			// observableValue, Number oldSceneHeight, Number newSceneHeight) {
			// System.out.println("Height: " + newSceneHeight);
			// }
			// });

			// Initialisation de la stage principale
			primaryStage.setScene(ecranHome);
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(800);
			primaryStage.getIcons().add(new Image(this.getClass().getResource(ImageConstants.LOGO_QP1C).toString()));
			primaryStage.show();

		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}

		LOGGER.info("[FIN] Start");
	}

	public void initialiserEcranHome() throws IOException {
		FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("./view/HomeView.fxml"));
		BorderPane page = (BorderPane) loader.load();
		((HomeController) loader.getController()).setLauncher(this);
		ecranHome = new Scene(page, 1024, 800);
		ecranHome.getStylesheets().add("./css/styles.css");
	}

	public void initialiserEcranStats() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/stats/StatsView.fxml"));
		BorderPane page = (BorderPane) loader.load();
		((StatistiquesController) loader.getController()).setLauncher(this);
		ecranStats = new Scene(page, 1024, 800);
		ecranStats.getStylesheets().add("./css/styles.css");
	}

	public void initialiserEcranParametrage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/parametrage/ParametrageView.fxml"));
		BorderPane page = (BorderPane) loader.load();
		((ParametrageController) loader.getController()).setLauncher(this);
		ecranParametrage = new Scene(page, 1024, 800);
		ecranParametrage.getStylesheets().add("./css/styles.css");
	}

	public void initialiserEcranPartieComplete() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/jeu/PartieCompleteView.fxml"));
		BorderPane page = (BorderPane) loader.load();
		((PartieCompleteController) loader.getController()).setLauncher(this);
		ecranPartieComplete = new Scene(page, 1024, 800);
		ecranPartieComplete.getStylesheets().add("./css/styles.css");
	}

	public void afficherEcranPartieComplete() {
		LOGGER.info("[DEBUT] Affichage de l'écran partie complète.");

		stage.setScene(ecranPartieComplete);
		stage.show();

		LOGGER.info("[FIN] Affichage de l'écran partie complète.");
	}

	public void afficherEcranStats() {
		LOGGER.info("[DEBUT] Affichage de l'écran de stats");

		stage.setScene(ecranStats);
		stage.show();

		LOGGER.info("[FIN] Affichage de l'écran de stats.");
	}

	public void afficherEcranParametrage() {
		LOGGER.info("[DEBUT] Affichage de l'écran de paramétrage");

		stage.setScene(ecranParametrage);
		stage.show();

		LOGGER.info("[FIN] Affichage de l'écran de paramétrage.");
	}

	public void afficherEcranHome() {
		LOGGER.info("[DEBUT] Affichage de l'écran accueil");
		stage.setScene(ecranHome);
		stage.show();

		LOGGER.info("[FIN] Affichage de l'écran accueil.");
	}

	public static void main(String[] args) {
		LOGGER.info("[DEBUT] Démarrage de l'application E-BDJ.");

		launch(args);

		LOGGER.info("[FIN] Arrêt de l'application E-BDJ.");
	}

}
