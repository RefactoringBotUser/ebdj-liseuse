package fr.qp1c.ebdj.view.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestionFAF;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class HistoriqueFAFListCell extends ListCell<HistoriqueQuestionFAF> {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoriqueFAFListCell.class);

	private EventHandler<MouseEvent> clickHandler;

	public HistoriqueFAFListCell(EventHandler<MouseEvent> clickHandler) {
		this.clickHandler = clickHandler;
	}

	@Override
	public void updateItem(HistoriqueQuestionFAF item, boolean empty) {
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

			Label reponse = new Label(item.getQuestion().getReponse().toUpperCase());

			final HBox hbox = new HBox();
			hbox.setSpacing(5);

			if (item.isNonComptabilise()) {
				nbQuestion.setStyle("-fx-text-fill: red;");
				reponse.setStyle("-fx-text-fill: red;");
			}

			hbox.getChildren().addAll(nbQuestion, reponse);
			hbox.setOnMouseClicked(clickHandler);
			hbox.setUserData(item);
			setGraphic(hbox);
		}

		LOGGER.debug("[FIN] Maj de l'entrée de l'historique.");
	}
}
