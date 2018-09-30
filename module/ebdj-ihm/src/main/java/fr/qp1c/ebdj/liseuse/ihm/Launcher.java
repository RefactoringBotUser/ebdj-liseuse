package fr.qp1c.ebdj.liseuse.ihm;

import java.net.URL;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.javafx.application.LauncherImpl;

import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.screen.ApplicationScreen;
import fr.qp1c.ebdj.liseuse.ihm.view.screen.SplashScreen;

public class Launcher {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ApplicationScreen.class);
		logger.info("[DEBUT] Démarrage de l'application E-BDJ.");

		if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac os")) {
			try {
				URL iconURL = new URL(ApplicationScreen.class.getResource(ImageConstants.LOGO_QP1C).toString());
				java.awt.Image image = new ImageIcon(iconURL).getImage();
				com.apple.eawt.Application.getApplication().setDockIconImage(image);
			} catch (Exception e) { // Won't work on Windows or Linux.
				// DO NOTHING
			}
		}

		System.setProperty("prism.forceGPU", "true");

		LauncherImpl.launchApplication(ApplicationScreen.class, SplashScreen.class, args);

		logger.info("[FIN] Arrêt de l'application E-BDJ.");
	}
}
