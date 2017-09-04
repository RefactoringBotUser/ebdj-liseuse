package fr.qp1c.ebdj.view.panneau;

import fr.qp1c.ebdj.utils.config.ImageConstants;
import fr.qp1c.ebdj.view.bouton.BoutonSwitch4ALS;
import fr.qp1c.ebdj.view.utils.ImageUtils;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PanneauTheme4ALS extends TitledPane {

	public BoutonSwitch4ALS boutonSwith4ALS;

	public BoutonSwitch4ALS getBoutonSwith4ALS() {
		return boutonSwith4ALS;
	}

	public void setBoutonSwith4ALS(BoutonSwitch4ALS boutonSwith4ALS) {
		this.boutonSwith4ALS = boutonSwith4ALS;
	}

	public void setGraphic(Image image) {
		setGraphic(create(image));
	}

	private HBox create(Image image) {

		final HBox hbox = new HBox();
		hbox.setSpacing(15);

		ImageView logo = ImageUtils.reduireImage(image, 34);

		ImageView demiVide = ImageUtils.iconiserImage(ImageConstants.IMAGE_DEMI_VIDE);

		this.boutonSwith4ALS = new BoutonSwitch4ALS();
		this.boutonSwith4ALS.setTitledPane(this);

		hbox.getChildren().addAll(logo, this.boutonSwith4ALS, demiVide);

		return hbox;
	}

	public void afficherTheme() {
		this.boutonSwith4ALS.selectionner4ALS();
	}
}
