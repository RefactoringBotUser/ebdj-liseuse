package fr.qp1c.ebdj.view.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestion9PG;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(Historique9PGListCell.class);

	private EventHandler<MouseEvent> clickHandler;

	public Historique9PGListCell(EventHandler<MouseEvent> clickHandler) {
		this.clickHandler = clickHandler;
	}

	@Override
	public void updateItem(HistoriqueQuestion9PG item, boolean empty) {
		LOGGER.debug("[DEBUT] Maj de l'entrée de l'historique : {} ", item);
		super.updateItem(item, empty);

		// if null, display nothing
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);

			String nbQuestionString = String.valueOf(item.getNbQuestion()) + " -";
			if (item.getNbQuestion() < 10) {
				nbQuestionString = " " + nbQuestionString;
			}

			Label nbQuestion = new Label(nbQuestionString);

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

			ImageView imageEtoile1 = new ImageView(ImageConstants.IMAGE_ETOILE);
			ImageView imageEtoile2 = new ImageView(ImageConstants.IMAGE_ETOILE);
			ImageView imageEtoile3 = new ImageView(ImageConstants.IMAGE_ETOILE);
			ImageView imageVide = new ImageView(ImageConstants.IMAGE_VIDE);
			ImageView imageDemiVide = new ImageView(ImageConstants.IMAGE_DEMI_VIDE);
			imageDemiVide.setFitHeight(12);
			imageDemiVide.setFitWidth(4);

			ImageUtils.iconiserImage(imageEtoile1);
			ImageUtils.iconiserImage(imageEtoile2);
			ImageUtils.iconiserImage(imageEtoile3);

			iconLabel1.setGraphic(imageEtoile1);
			iconLabel2.setGraphic(imageEtoile2);
			iconLabel3.setGraphic(imageEtoile3);

			iconLabelVide1.setGraphic(imageVide);
			iconLabelVide2.setGraphic(imageVide);
			iconLabelVideDemi1.setGraphic(imageDemiVide);
			iconLabelVideDemi2.setGraphic(imageDemiVide);

			if (item.isNonComptabilise()) {
				nbQuestion.setStyle("-fx-text-fill: red;");
				reponse.setStyle("-fx-text-fill: red;");
			}

			if (item.getNiveau() == 1) {
				hbox.getChildren().addAll(nbQuestion, iconLabelVide1, iconLabel1, iconLabelVide2, reponse);
			} else if (item.getNiveau() == 2) {
				hbox.getChildren().addAll(nbQuestion, iconLabelVideDemi1, iconLabel1, iconLabel2, iconLabelVideDemi2,
						reponse);
			} else if (item.getNiveau() == 3) {
				hbox.getChildren().addAll(nbQuestion, iconLabel1, iconLabel2, iconLabel3, reponse);
			}
			hbox.setOnMouseClicked(clickHandler);
			hbox.setUserData(item);
			setGraphic(hbox);
		}

		LOGGER.debug("[FIN] Maj de l'entrée de l'historique.");
	}
}
