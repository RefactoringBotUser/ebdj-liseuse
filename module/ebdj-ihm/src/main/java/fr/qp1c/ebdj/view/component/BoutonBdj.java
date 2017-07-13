package fr.qp1c.ebdj.view.component;

import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class BoutonBdj {

	public static void customiserBouton150(Button btn, Image imageBouton) {
		ImageView image = new ImageView(imageBouton);
		ImageUtils.reduireImageCustom(image, 150);

		btn.setGraphic(image);
		btn.setTextAlignment(TextAlignment.CENTER);
		btn.setContentDisplay(ContentDisplay.TOP);
	}
}
