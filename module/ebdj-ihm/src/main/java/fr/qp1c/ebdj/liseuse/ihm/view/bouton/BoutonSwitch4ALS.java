package fr.qp1c.ebdj.liseuse.ihm.view.bouton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.ihm.view.panneau.PanneauTheme4ALS;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoutonSwitch4ALS extends HBox {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BoutonSwitch4ALS.class);

	private final Label label = new Label();
	private final Button button = new Button();

	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);

	private PanneauTheme4ALS panneauTheme4ALS;

	public BoutonSwitch4ALS() {
		init();
		switchedOn.addListener((a, b, c) -> {
			if (c) {
				LOGGER.info("### --> Clic sur \"Joué theme 4ALS\".");

				selectionner4ALS();
			} else {
				LOGGER.info("### --> Clic sur \"Non joué theme 4ALS\".");

				deselectionner4ALS();
			}
		});
	}

	public void selectionner4ALS() {
		label.setText("JOUE");
		setStyle("-fx-background-color: green;");
		label.toFront();

		if (panneauTheme4ALS != null) {
			panneauTheme4ALS.setExpanded(true);

			// TODO : appeler le moteur
		}
	}

	public void deselectionner4ALS() {
		label.setText("NON JOUE");
		setStyle("-fx-background-color: grey;");
		button.toFront();

		if (panneauTheme4ALS != null) {
			panneauTheme4ALS.setExpanded(false);

			// TODO : appeler le moteur
		}
	}

	public void setTitledPane(PanneauTheme4ALS panneauTheme4ALS) {
		this.panneauTheme4ALS = panneauTheme4ALS;
	}

	public SimpleBooleanProperty switchOnProperty() {
		return switchedOn;
	}

	private void init() {
		label.setText("NON JOUE");

		getChildren().addAll(label, button);
		button.setOnAction(e -> {

			LOGGER.info("### --> Clic sur \"Joué theme 4ALS\".");

			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked(e -> {

			LOGGER.info("### --> Clic sur \"Non-joué theme 4ALS\".");

			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}

	private void setStyle() {
		// Default Width
		setWidth(200);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
		setAlignment(Pos.CENTER_LEFT);
	}

	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}
}
