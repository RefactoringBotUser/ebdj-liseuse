package fr.qp1c.ebdj;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@Override
	public void start(Stage primaryStage) {
		LOGGER.info("[DEBUT] Start");

		primaryStage.setTitle("QP1C - E-Boite de jeu");

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/MainView.fxml"));
			BorderPane page = (BorderPane) loader.load();

			Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Venacti Bold.ttf"), 14);

			// Création de la scène principale
			Scene scene = new Scene(page, 1024, 800);
			scene.getStylesheets().add("css/styles.css");
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
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(800);
			primaryStage.getIcons().add(new Image(this.getClass().getResource(ImageConstants.LOGO_QP1C).toString()));
			primaryStage.show();

		} catch (IOException e) {
			LOGGER.error("Une erreur s'est produite :", e);
		}

		LOGGER.info("[FIN] Start");
	}

	public static void main(String[] args) {
		LOGGER.info("[DEBUT] Démarrage de l'application E-BDJ.");

		launch(args);

		LOGGER.info("[FIN] Arrêt de l'application E-BDJ.");
	}

}
