package fr.qp1c.ebdj.utils;

import com.sun.javafx.stage.StageHelper;

import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public final class DialogUtils {

	public static Stage getStage() {
		return StageHelper.getStages().get(0);
	}

	public static void centrer(Dialog dialog) {
		Stage scene = DialogUtils.getStage();
		double x = scene.getX() + scene.getWidth() / 2 - dialog.getWidth() / 2;
		double y = scene.getY() + scene.getHeight() / 2 - dialog.getHeight() / 2;

		System.out.println(scene.getWidth() + " " + scene.getHeight());
		System.out.println(x + " " + y);

		dialog.setX(x);
		dialog.setY(y);
	}
}