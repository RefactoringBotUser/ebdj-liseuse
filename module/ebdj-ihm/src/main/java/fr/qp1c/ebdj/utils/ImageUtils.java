package fr.qp1c.ebdj.utils;

import javafx.scene.image.ImageView;

public class ImageUtils {

	public static void iconiserImage(ImageView imageView) {
		imageView.setFitHeight(12);
		imageView.setFitWidth(12);
	}

	public static void reduireImage(ImageView imageView) {
		imageView.setFitHeight(40);
		imageView.setFitWidth(40);
	}

	public static void reduireImageCustom(ImageView imageView, int size) {
		imageView.setFitHeight(size);
		imageView.setFitWidth(size);
	}
}
