package fr.qp1c.ebdj.view.component;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoutonSwitch4ALS extends HBox {

	private final Label label = new Label();
	private final Button button = new Button();

	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);

	private TitledPane4ALS titledPane4ALS;

	public BoutonSwitch4ALS() {
		init();
		switchedOn.addListener((a, b, c) -> {
			if (c) {
				selectionner4ALS();
			} else {
				deselectionner4ALS();
			}
		});
	}

	public void selectionner4ALS() {
		label.setText("JOUE");
		setStyle("-fx-background-color: green;");
		label.toFront();

		if (titledPane4ALS != null) {
			titledPane4ALS.setExpanded(true);
		}
	}

	public void deselectionner4ALS() {
		label.setText("NON JOUE");
		setStyle("-fx-background-color: grey;");
		button.toFront();

		if (titledPane4ALS != null) {
			titledPane4ALS.setExpanded(false);
		}
	}

	public void setTitledPane(TitledPane4ALS titledPane4ALS) {
		this.titledPane4ALS = titledPane4ALS;
	}

	public SimpleBooleanProperty switchOnProperty() {
		return switchedOn;
	}

	private void init() {

		label.setText("NON JOUE");

		getChildren().addAll(label, button);
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
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
