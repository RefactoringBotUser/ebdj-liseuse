package fr.qp1c.ebdj.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageUtils {

	// public static void iconiserImage(ImageView imageView) {
	// reduireImageCustom(imageView, 12);
	// }
	//
	// public static void reduireImage(ImageView imageView) {
	// reduireImageCustom(imageView, 40);
	// }

	public static ImageView iconiserImage(Image image) {
		return reduireImage(image, 12);
	}

	public static ImageView reduireImage(Image image) {
		return reduireImage(image, 40);
	}

	public static ImageView reduireImage(Image image, int size) {
		ImageView imageView = new ImageView(image);
		reduireImageCustom(imageView, size);
		return imageView;
	}

	public static void reduireImageCustom(ImageView imageView, int size) {
		imageView.setFitHeight(size);
		imageView.setFitWidth(size);
	}
}
