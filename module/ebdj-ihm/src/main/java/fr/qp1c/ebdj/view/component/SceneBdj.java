package fr.qp1c.ebdj.view.component;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBdj extends Scene {

	public SceneBdj(Parent root, Rectangle2D screenSize) {
		super(root, screenSize.getWidth(), screenSize.getHeight());
		getStylesheets().add("/css/styles.css");
	}

}
