package fr.qp1c.ebdj.utils.config;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public final class ImageConstants {

	// URL Images

	public static final String LOGO_QP1C = "/images/commun/QP1C.JPG";

	public static final String ICONE_POPUP = "/images/commun/dialog.png";

	public static final String ICONE_UTILISATEUR = "/images/commun/utilisateur.png";

	public static final String ICONE_PARAMETRAGE = "/images/commun/parametrage.png";

	public static final String ICONE_QUITTER_PARTIE = "/images/commun/quitter_partie.png";

	// Images pour le jeu

	public static final String ETOILE = "/images/jeu/etoile.png";

	public static final String ICONE_1 = "/images/jeu/1.png";

	public static final String ICONE_2 = "/images/jeu/2.png";

	public static final String ICONE_3 = "/images/jeu/3.png";

	public static final String ICONE_4 = "/images/jeu/4.png";

	public static final String ICONE_5 = "/images/jeu/5.png";

	// Images pour la partie home

	public static final String ICONE_HOME = "/images/home/home.png";

	public static final String ICONE_HOME_QUESTION = "/images/home/question.png";

	public static final String ICONE_HOME_QUESTION_SELECTED = "/images/home/question_selected.png";

	public static final String ICONE_HOME_STATS = "/images/home/stats.png";

	public static final String ICONE_HOME_PARAMETRAGE = "/images/home/parametrage.png";

	// Images

	// public static final Image IMAGE_QUESTION;

	// public static final Image IMAGE_REMPLACEMENT_QUESTION;

	public static final Image IMAGE_POPUP;

	public static final Image IMAGE_ETOILE;

	public static final Image IMAGE_UTILISATEUR;

	public static final Image IMAGE_PARAMETRAGE;

	public static final Image IMAGE_HOME;

	public static final Image IMAGE_QUITTER_PARTIE;

	public static final Image IMAGE_HOME_QUESTION;

	public static final Image IMAGE_HOME_QUESTION_SELECTED;

	public static final Image IMAGE_HOME_STATS;

	public static final Image IMAGE_HOME_PARAMETRAGE;

	public static final Image IMAGE_1;

	public static final Image IMAGE_2;

	public static final Image IMAGE_3;

	public static final Image IMAGE_4;

	public static final Image IMAGE_5;

	public static final WritableImage IMAGE_VIDE;

	public static final WritableImage IMAGE_DEMI_VIDE;

	static {

		// Chargement des icones
		// IMAGE_QUESTION = new Image("/images/question1.png");

		// IMAGE_REMPLACEMENT_QUESTION = new Image("/images/remplacement2.png");

		IMAGE_VIDE = new WritableImage(12, 12);

		IMAGE_DEMI_VIDE = new WritableImage(4, 12);

		IMAGE_POPUP = new Image(ImageConstants.ICONE_POPUP);

		IMAGE_ETOILE = new Image(ImageConstants.ETOILE);

		IMAGE_UTILISATEUR = new Image(ImageConstants.ICONE_UTILISATEUR);

		IMAGE_PARAMETRAGE = new Image(ImageConstants.ICONE_PARAMETRAGE);

		IMAGE_HOME = new Image(ImageConstants.ICONE_HOME);

		IMAGE_HOME_QUESTION = new Image(ImageConstants.ICONE_HOME_QUESTION);

		IMAGE_HOME_QUESTION_SELECTED = new Image(ImageConstants.ICONE_HOME_QUESTION_SELECTED);

		IMAGE_QUITTER_PARTIE = new Image(ImageConstants.ICONE_QUITTER_PARTIE);

		IMAGE_HOME_STATS = new Image(ImageConstants.ICONE_HOME_STATS);

		IMAGE_HOME_PARAMETRAGE = new Image(ImageConstants.ICONE_HOME_PARAMETRAGE);

		IMAGE_1 = new Image(ImageConstants.ICONE_1);

		IMAGE_2 = new Image(ImageConstants.ICONE_2);

		IMAGE_3 = new Image(ImageConstants.ICONE_3);

		IMAGE_4 = new Image(ImageConstants.ICONE_4);

		IMAGE_5 = new Image(ImageConstants.ICONE_5);
	}
}
