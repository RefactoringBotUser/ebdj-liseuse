package fr.qp1c.ebdj.liseuse.ihm.view.listcell;

import fr.qp1c.ebdj.liseuse.commun.bean.historique.HistoriqueQuestion9PG;
import fr.qp1c.ebdj.liseuse.commun.utils.StringUtilities;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.Style;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.ImageUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Historique9PGListCell extends ListCell<HistoriqueQuestion9PG> {

	/**
	 * Default logger.
	 */
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(Historique9PGListCell.class);

	private EventHandler<MouseEvent> clickHandler;

	// Constructeur

	public Historique9PGListCell(EventHandler<MouseEvent> clickHandler) {
		this.clickHandler = clickHandler;
	}

	// Méthodes

	@Override
	public void updateItem(HistoriqueQuestion9PG item, boolean empty) {
		// LOGGER.debug("[DEBUT] Maj de l'entrée de l'historique : {} ", item);

		if (item != null) {
			super.updateItem(item, empty);

			// if null, display nothing
			if (empty || item == null) {
				setText(null);
				setGraphic(null);
			} else {
				setText(null);

				Label nbQuestion = new Label(StringUtilities.formaterNumeroQuestion(item.getNbQuestion()));

				Label reponse = new Label(" - " + item.getQuestion().getReponse().toUpperCase());

				final HBox hbox = new HBox();
				hbox.setSpacing(5);

				Label iconLabel1 = new Label();
				Label iconLabel2 = new Label();
				Label iconLabel3 = new Label();

				Label iconLabelVide1 = new Label();
				Label iconLabelVide2 = new Label();
				Label iconLabelVideDemi1 = new Label();
				Label iconLabelVideDemi2 = new Label();

				ImageView imageVide = new ImageView(ImageConstants.IMAGE_VIDE);
				ImageView imageDemiVide = new ImageView(ImageConstants.IMAGE_DEMI_VIDE);
				imageDemiVide.setFitHeight(12);
				imageDemiVide.setFitWidth(4);

				iconLabel1.setGraphic(ImageUtils.iconiserImage(ImageConstants.IMAGE_ETOILE));
				iconLabel2.setGraphic(ImageUtils.iconiserImage(ImageConstants.IMAGE_ETOILE));
				iconLabel3.setGraphic(ImageUtils.iconiserImage(ImageConstants.IMAGE_ETOILE));
				iconLabelVide1.setGraphic(imageVide);
				iconLabelVide2.setGraphic(imageVide);
				iconLabelVideDemi1.setGraphic(imageDemiVide);
				iconLabelVideDemi2.setGraphic(imageDemiVide);

				if (item.isNonComptabilise()) {
					nbQuestion.setStyle(Style.ANOMALIE_HISTORIQUE);
					reponse.setStyle(Style.ANOMALIE_HISTORIQUE);
				}

				if (item.getNiveau() == 1) {
					hbox.getChildren().addAll(nbQuestion, iconLabelVide1, iconLabel1, iconLabelVide2, reponse);
				} else if (item.getNiveau() == 2) {
					hbox.getChildren().addAll(nbQuestion, iconLabelVideDemi1, iconLabel1, iconLabel2,
							iconLabelVideDemi2, reponse);
				} else if (item.getNiveau() == 3) {
					hbox.getChildren().addAll(nbQuestion, iconLabel1, iconLabel2, iconLabel3, reponse);
				}
				hbox.setOnMouseClicked(clickHandler);
				hbox.setUserData(item);
				setGraphic(hbox);
			}
		}
		// LOGGER.debug("[FIN] Maj de l'entrée de l'historique.");
	}
}
