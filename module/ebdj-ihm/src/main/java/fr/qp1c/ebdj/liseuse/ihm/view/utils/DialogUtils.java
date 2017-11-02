package fr.qp1c.ebdj.liseuse.ihm.view.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.javafx.stage.StageHelper;

import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public final class DialogUtils {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DialogUtils.class);

    private DialogUtils() {

    }

    public static Stage getStage() {
        return StageHelper.getStages().get(0);
    }

    public static void centrer(Dialog dialog) {
        Stage scene = DialogUtils.getStage();
        double x = scene.getX() + scene.getWidth() / 2 - dialog.getWidth() / 2;
        double y = scene.getY() + scene.getHeight() / 2 - dialog.getHeight() / 2;

        LOGGER.debug("Calcul pour centrer l'écran : largeurEcran={}, hauteurEcran={}, positionX={}, positionY={}",
                scene.getWidth(), scene.getHeight(), x, y);

        dialog.setX(x);
        dialog.setY(y);
    }
}