package fr.qp1c.ebdj.liseuse.ihm.view.utils;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class BoutonUtils {

	/**
	 * Ajouter une image carrée de 150 pixels sur un bouton.
	 * 
	 * @param btn
	 *            le bouton à modifier
	 * @param imageBouton
	 */
	public static void customiserBouton150(ButtonBase btn, Image imageBouton) {
		ImageView image = new ImageView(imageBouton);
		ImageUtils.reduireImageCustom(image, 150);

		btn.setGraphic(image);
		btn.setTextAlignment(TextAlignment.CENTER);
		btn.setContentDisplay(ContentDisplay.TOP);
	}

}
