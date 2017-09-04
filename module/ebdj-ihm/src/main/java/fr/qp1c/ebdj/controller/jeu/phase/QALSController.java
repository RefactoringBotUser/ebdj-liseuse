package fr.qp1c.ebdj.controller.jeu.phase;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.controller.jeu.phase.preferences.PreferencesLecteur;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.question.Question;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.loader.LoaderTheme4ALS;
import fr.qp1c.ebdj.moteur.moteur.MoteurQALS;
import fr.qp1c.ebdj.utils.config.ImageConstants;
import fr.qp1c.ebdj.view.TaillePolice;
import fr.qp1c.ebdj.view.panneau.PanneauTheme4ALS;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class QALSController implements PreferencesLecteur {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QALSController.class);

	@FXML
	private PanneauTheme4ALS theme4ALS1;

	@FXML
	private PanneauTheme4ALS theme4ALS2;

	@FXML
	private PanneauTheme4ALS theme4ALS3;

	@FXML
	private PanneauTheme4ALS theme4ALS4;

	@FXML
	private PanneauTheme4ALS theme4ALS5;

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

	// Autres attributs

	private MoteurQALS moteurQALS;

	@FXML
	private void initialize() {
		LOGGER.info("[DEBUT] Initialisation du panneau 4ALS.");

		moteurQALS = new MoteurQALS();

		theme4ALS5.setVisible(false);

		Map<String, Theme4ALS> themes4ALS = LoaderTheme4ALS.chargerQuestions();

		Theme4ALS theme4ALS_1 = themes4ALS.get("1");
		Theme4ALS theme4ALS_2 = themes4ALS.get("2");
		Theme4ALS theme4ALS_3 = themes4ALS.get("3");
		Theme4ALS theme4ALS_4 = themes4ALS.get("4");

		theme4ALS1.setGraphic(ImageConstants.IMAGE_1);
		theme4ALS1.setText(theme4ALS_1.getTheme());
		theme4ALS1.setCollapsible(true);
		theme4ALS1.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 1\".");

				theme4ALS1.afficherTheme();
			}
		});

		theme4ALS2.setGraphic(ImageConstants.IMAGE_2);
		theme4ALS2.setText(theme4ALS_2.getTheme());
		theme4ALS2.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 2\".");

				theme4ALS2.afficherTheme();
			}
		});

		theme4ALS3.setGraphic(ImageConstants.IMAGE_3);
		theme4ALS3.setText(theme4ALS_3.getTheme());
		theme4ALS3.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 3\".");

				theme4ALS3.afficherTheme();
			}
		});

		theme4ALS4.setGraphic(ImageConstants.IMAGE_4);
		theme4ALS4.setText(theme4ALS_4.getTheme());
		theme4ALS4.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 4\".");

				theme4ALS4.afficherTheme();
			}
		});

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
		ObservableList<Node> list1 = questionTheme4ALS1.getChildren();
		formaterContenuTheme4ALS(list1, theme4ALS_1);

		ObservableList<Node> list2 = questionTheme4ALS2.getChildren();
		formaterContenuTheme4ALS(list2, theme4ALS_2);

		ObservableList<Node> list3 = questionTheme4ALS3.getChildren();
		formaterContenuTheme4ALS(list3, theme4ALS_3);

		ObservableList<Node> list4 = questionTheme4ALS4.getChildren();
		formaterContenuTheme4ALS(list4, theme4ALS_4);

		panneauQuestionTheme4ALS1.setFitToHeight(true);
		panneauQuestionTheme4ALS1.setFitToWidth(true);

		panneauQuestionTheme4ALS2.setFitToHeight(true);
		panneauQuestionTheme4ALS2.setFitToWidth(true);

		panneauQuestionTheme4ALS3.setFitToHeight(true);
		panneauQuestionTheme4ALS3.setFitToWidth(true);

		panneauQuestionTheme4ALS4.setFitToHeight(true);
		panneauQuestionTheme4ALS4.setFitToWidth(true);

		LOGGER.info("[FIN] Initialisation du panneau 4ALS.");
	}

	public void reinitialiser() {

	}

	public void formaterContenuTheme4ALS(ObservableList<Node> list, Theme4ALS theme4ALS) {
		LOGGER.info("[DEBUT] Afficher theme 4ALS.");

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

		LOGGER.info("[FIN] Afficher theme 4ALS.");
	}

	@Override
	public void definirLecteur(Lecteur lecteur) {
		LOGGER.info("[DEBUT] Définir lecteur.");

		moteurQALS.definirLecteur(lecteur);

		LOGGER.info("[FIN] Définir lecteur.");
	}

	@Override
	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		LOGGER.info("[DEBUT] Définir niveau partie.");

		moteurQALS.definirNiveauPartie(niveauPartie);

		LOGGER.info("[FIN] Définir niveau partie.");
	}

	@Override
	public void modifierTaille(TaillePolice taille) {
		// TODO a implementer

	}

}
