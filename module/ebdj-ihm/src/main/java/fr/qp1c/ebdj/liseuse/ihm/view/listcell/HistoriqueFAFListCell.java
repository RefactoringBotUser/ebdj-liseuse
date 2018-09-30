package fr.qp1c.ebdj.liseuse.ihm.view.listcell;

import fr.qp1c.ebdj.liseuse.commun.bean.historique.HistoriqueQuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.utils.StringUtilities;
import fr.qp1c.ebdj.liseuse.ihm.view.Style;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class HistoriqueFAFListCell extends ListCell<HistoriqueQuestionFAF> {

	/**
	 * Default logger.
	 */
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(HistoriqueFAFListCell.class);

	private EventHandler<MouseEvent> clickHandler;

	public HistoriqueFAFListCell(EventHandler<MouseEvent> clickHandler) {
		this.clickHandler = clickHandler;
	}

	@Override
	public void updateItem(HistoriqueQuestionFAF item, boolean empty) {
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
				Label reponse = new Label(item.getQuestion().getReponse().toUpperCase());

				final HBox hbox = new HBox();
				hbox.setSpacing(5);

				if (item.isNonComptabilise()) {
					nbQuestion.setStyle(Style.ANOMALIE_HISTORIQUE);
					reponse.setStyle(Style.ANOMALIE_HISTORIQUE);
				}

				hbox.getChildren().addAll(nbQuestion, reponse);
				hbox.setOnMouseClicked(clickHandler);
				hbox.setUserData(item);
				setGraphic(hbox);
			}
		}
		// LOGGER.debug("[FIN] Maj de l'entrée de l'historique.");
	}
}
