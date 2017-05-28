package fr.qp1c.ebdj.utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public final class ImageConstants {

	public static final String LOGO_QP1C = "/images/QP1C.JPG";

	public static final String ETOILE = "/images/etoile.png";

	public static final String ICONE_POPUP = "/images/dialog.png";

	public static final String ICONE_UTILISATEUR = "/images/utilisateur.png";

	public static final String ICONE_UTILISATEUR_BLANK = "/images/utilisateur_blank.png";

	public static final String ICONE_1 = "/images/1.png";

	public static final String ICONE_2 = "/images/2.png";

	public static final String ICONE_3 = "/images/3.png";

	public static final String ICONE_4 = "/images/4.png";

	public static final String ICONE_5 = "/images/5.png";

	public static final Image IMAGE_QUESTION;

	public static final Image IMAGE_REMPLACEMENT_QUESTION;

	public static final Image IMAGE_POPUP;

	public static final Image IMAGE_ETOILE;

	public static final Image IMAGE_UTILISATEUR;

	public static final Image IMAGE_1;

	public static final Image IMAGE_2;

	public static final Image IMAGE_3;

	public static final Image IMAGE_4;

	public static final Image IMAGE_5;

	public static final WritableImage IMAGE_VIDE;

	public static final WritableImage IMAGE_DEMI_VIDE;

	static {

		// Chargement des icones
		IMAGE_QUESTION = new Image("images/question1.png");

		IMAGE_REMPLACEMENT_QUESTION = new Image("images/remplacement2.png");

		IMAGE_VIDE = new WritableImage(12, 12);

		IMAGE_DEMI_VIDE = new WritableImage(4, 12);

		IMAGE_POPUP = new Image(ImageConstants.ICONE_POPUP);

		IMAGE_ETOILE = new Image(ImageConstants.ETOILE);

		IMAGE_UTILISATEUR = new Image(ImageConstants.ICONE_UTILISATEUR);

		IMAGE_1 = new Image(ImageConstants.ICONE_1);

		IMAGE_2 = new Image(ImageConstants.ICONE_2);

		IMAGE_3 = new Image(ImageConstants.ICONE_3);

		IMAGE_4 = new Image(ImageConstants.ICONE_4);

		IMAGE_5 = new Image(ImageConstants.ICONE_5);
	}
}
