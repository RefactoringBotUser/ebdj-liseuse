package fr.qp1c.ebdj.liseuse.ihm.view.screen;

import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {
	private static Stage stage;

	private Scene createPreloaderScene() {
		VBox preloaderPane = new VBox();
		preloaderPane.setAlignment(Pos.TOP_CENTER);
		preloaderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(30), Insets.EMPTY)));

		Region region = new Region();
		region.setMinSize(580, 30);
		region.setStyle("-fx-border-radius:30 30 0 0;-fx-background-radius: 30 30 0 0;-fx-background-color: #02298E;");
		preloaderPane.getChildren().add(region);

		ImageView imageView = new ImageView(
				new Image(this.getClass().getResourceAsStream("/images/commun/QP1C-min.png")));
		preloaderPane.getChildren().add(imageView);

		Font.loadFont(getClass().getResourceAsStream("./src/main/resources/fonts/Venacti Bold.ttf"), 14);
		Label loadingLabel = new Label("Démarrage en cours...");
		loadingLabel.setStyle("-fx-font-family: \"Venacti\"; -fx-font-size: 16px;-fx-font-weight: bold;");
		preloaderPane.getChildren().add(loadingLabel);

		Scene scene = new Scene(preloaderPane, 580, 480, Color.TRANSPARENT);
		return scene;
	}

	@Override
	public void start(Stage stage) throws Exception {
		SplashScreen.stage = new Stage();
		SplashScreen.stage.setTitle(Libelle.TITRE);
		SplashScreen.stage.setScene(createPreloaderScene());
		SplashScreen.stage.initStyle(StageStyle.TRANSPARENT);
		SplashScreen.stage.show();
	}

	public static void hide() {
		if (SplashScreen.stage != null) {
			SplashScreen.stage.close();
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification scn) {
		if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
			stage.hide();
		}

	}

	@Override
	public void handleProgressNotification(ProgressNotification pn) {
		// DO NOTHING
	}
}
