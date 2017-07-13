package fr.qp1c.ebdj.controller.jeu.phase;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.loader.LoaderQuestion4ALS;
import fr.qp1c.ebdj.moteur.bean.question.Question;
import fr.qp1c.ebdj.moteur.bean.question.Question4ALS;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class QALSController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QALSController.class);

	@FXML
	private TitledPane theme4ALS1;

	@FXML
	private TitledPane theme4ALS2;

	@FXML
	private TitledPane theme4ALS3;

	@FXML
	private TitledPane theme4ALS4;

	@FXML
	private TitledPane theme4ALS5;

	@FXML
	private TextFlow questionTheme4ALS1;

	@FXML
	private TextFlow questionTheme4ALS2;

	@FXML
	private TextFlow questionTheme4ALS3;

	@FXML
	private TextFlow questionTheme4ALS4;

	@FXML
	private ScrollPane panneauQuestionTheme4ALS1;

	@FXML
	private ScrollPane panneauQuestionTheme4ALS2;

	@FXML
	private ScrollPane panneauQuestionTheme4ALS3;

	@FXML
	private ScrollPane panneauQuestionTheme4ALS4;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau 4ALS.");

		theme4ALS5.setVisible(false);

		Map<String, Question4ALS> themes4ALS = LoaderQuestion4ALS.chargerQuestions();

		Question4ALS theme4ALS_1 = themes4ALS.get("1");
		Question4ALS theme4ALS_2 = themes4ALS.get("2");
		Question4ALS theme4ALS_3 = themes4ALS.get("3");
		Question4ALS theme4ALS_4 = themes4ALS.get("4");

		theme4ALS1.setGraphic(create(ImageConstants.IMAGE_1));
		theme4ALS1.setText(theme4ALS_1.getTheme());

		theme4ALS2.setGraphic(create(ImageConstants.IMAGE_2));
		theme4ALS2.setText(theme4ALS_2.getTheme());

		theme4ALS3.setGraphic(create(ImageConstants.IMAGE_3));
		theme4ALS3.setText(theme4ALS_3.getTheme());

		theme4ALS4.setGraphic(create(ImageConstants.IMAGE_4));
		theme4ALS4.setText(theme4ALS_4.getTheme());

		// Setting the line spacing between the text objects
		questionTheme4ALS1.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS1.setPrefSize(800, 800);
		questionTheme4ALS1.setLineSpacing(2.0);

		questionTheme4ALS2.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS2.setPrefSize(800, 800);
		questionTheme4ALS2.setLineSpacing(2.0);

		questionTheme4ALS3.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS3.setPrefSize(800, 800);
		questionTheme4ALS3.setLineSpacing(2.0);

		questionTheme4ALS4.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS4.setPrefSize(800, 800);
		questionTheme4ALS4.setLineSpacing(2.0);

		// Retrieving the observable list of the TextFlow Pane
		ObservableList list1 = questionTheme4ALS1.getChildren();
		afficher4ALS(list1, theme4ALS_1);

		ObservableList list2 = questionTheme4ALS2.getChildren();
		afficher4ALS(list2, theme4ALS_2);

		ObservableList list3 = questionTheme4ALS3.getChildren();
		afficher4ALS(list3, theme4ALS_3);

		ObservableList list4 = questionTheme4ALS4.getChildren();
		afficher4ALS(list4, theme4ALS_4);

		panneauQuestionTheme4ALS1.setFitToHeight(true);
		panneauQuestionTheme4ALS1.setFitToWidth(true);

		panneauQuestionTheme4ALS2.setFitToHeight(true);
		panneauQuestionTheme4ALS2.setFitToWidth(true);

		panneauQuestionTheme4ALS3.setFitToHeight(true);
		panneauQuestionTheme4ALS3.setFitToWidth(true);

		panneauQuestionTheme4ALS4.setFitToHeight(true);
		panneauQuestionTheme4ALS4.setFitToWidth(true);

		LOGGER.debug("[FIN] Initialisation du panneau 4ALS.");
	}

	public void reinitialiser() {

	}

	public void afficher4ALS(ObservableList list, Question4ALS theme4ALS) {

		Font fontR = Font.font("Venacti", FontWeight.BOLD, 18);
		Font fontQ = new Font("Arial", 18);
		Font fontLV = new Font("Venacti", 4);

		for (int i = 1; i <= 12; i++) {

			Question question = theme4ALS.getQuestions().get(i + "");

			Text textQ = new Text(i + "/ " + question.getQuestion() + "\n");
			textQ.setFont(fontQ);

			Text textR = new Text(question.getReponse().toUpperCase(Locale.FRANCE) + "\n");
			textR.setFont(fontR);

			Text textLV = new Text("\n");
			textLV.setFont(fontLV);

			// Adding cylinder to the pane
			list.addAll(textQ, textR, textLV);
		}
	}

	private HBox create(Image image) {

		final HBox hbox = new HBox();
		hbox.setSpacing(15);

		ImageView logo = new ImageView(image);
		ImageUtils.reduireImageCustom(logo, 34);

		ImageView demiVide = new ImageView(ImageConstants.IMAGE_DEMI_VIDE);
		ImageUtils.iconiserImage(demiVide);

		hbox.getChildren().addAll(logo, /** new Button("Joué"), */
				demiVide);

		return hbox;
	}

}
