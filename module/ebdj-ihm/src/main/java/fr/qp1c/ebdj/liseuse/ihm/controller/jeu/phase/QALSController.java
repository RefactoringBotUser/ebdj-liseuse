package fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QR;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase.preferences.PreferencesLecteur;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.TaillePolice;
import fr.qp1c.ebdj.liseuse.ihm.view.panneau.PanneauTheme4ALS;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpPenurieTheme4ALS;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.FontConstants;
import fr.qp1c.ebdj.liseuse.moteur.MoteurQALS;
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
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.info("[DEBUT] Reinitialisation du panneau 4ALS.");

		moteurQALS = new MoteurQALS();

		theme4ALS5.setVisible(false);

		theme4ALS1.setGraphic(ImageConstants.IMAGE_1);
		theme4ALS1.setText("");
		theme4ALS1.setCollapsible(true);
		theme4ALS1.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 1\".");

				theme4ALS1.afficherTheme();
			}
		});

		theme4ALS2.setGraphic(ImageConstants.IMAGE_2);
		theme4ALS2.setText("");
		theme4ALS2.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 2\".");

				theme4ALS2.afficherTheme();
			}
		});

		theme4ALS3.setGraphic(ImageConstants.IMAGE_3);
		theme4ALS3.setText("");
		theme4ALS3.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 3\".");

				theme4ALS3.afficherTheme();
			}
		});

		theme4ALS4.setGraphic(ImageConstants.IMAGE_4);
		theme4ALS4.setText("");
		theme4ALS4.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (isNowExpanded) {
				LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 4\".");

				theme4ALS4.afficherTheme();
			}
		});

		// Setting the line spacing between the text objects
		questionTheme4ALS1.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS1.setPrefSize(800, 600);
		questionTheme4ALS1.setLineSpacing(2.0);

		questionTheme4ALS2.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS2.setPrefSize(800, 600);
		questionTheme4ALS2.setLineSpacing(2.0);

		questionTheme4ALS3.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS3.setPrefSize(800, 600);
		questionTheme4ALS3.setLineSpacing(2.0);

		questionTheme4ALS4.setTextAlignment(TextAlignment.JUSTIFY);
		questionTheme4ALS4.setPrefSize(800, 600);
		questionTheme4ALS4.setLineSpacing(2.0);

		// Retrieving the observable list of the TextFlow Pane
		panneauQuestionTheme4ALS1.setFitToHeight(true);
		panneauQuestionTheme4ALS1.setFitToWidth(true);

		panneauQuestionTheme4ALS2.setFitToHeight(true);
		panneauQuestionTheme4ALS2.setFitToWidth(true);

		panneauQuestionTheme4ALS3.setFitToHeight(true);
		panneauQuestionTheme4ALS3.setFitToWidth(true);

		panneauQuestionTheme4ALS4.setFitToHeight(true);
		panneauQuestionTheme4ALS4.setFitToWidth(true);

		LOGGER.info("[FIN] Reinitialisation du panneau 4ALS.");
	}

	public void jouerNouveau4ALS() {

		Map<String, Theme4ALS> themes4ALS = moteurQALS.tirerThemes();

		if (themes4ALS.size() == 4) {

			Theme4ALS themeQALS1 = themes4ALS.get("1");
			Theme4ALS themeQALS2 = themes4ALS.get("2");
			Theme4ALS themeQALS3 = themes4ALS.get("3");
			Theme4ALS themeQALS4 = themes4ALS.get("4");

			theme4ALS1.setGraphic(ImageConstants.IMAGE_1);
			theme4ALS1.setText(themeQALS1.getTheme());
			theme4ALS1.setCollapsible(true);
			theme4ALS1.setExpanded(false);
			theme4ALS1.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
				if (isNowExpanded) {
					LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 1\".");

					theme4ALS1.afficherTheme();
					moteurQALS.jouerTheme(themeQALS1.getReference());
				}
			});

			theme4ALS2.setGraphic(ImageConstants.IMAGE_2);
			theme4ALS2.setText(themeQALS2.getTheme());
			theme4ALS2.setCollapsible(true);
			theme4ALS2.setExpanded(false);
			theme4ALS2.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
				if (isNowExpanded) {
					LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 2\".");

					theme4ALS2.afficherTheme();
					moteurQALS.jouerTheme(themeQALS2.getReference());
				}
			});

			theme4ALS3.setGraphic(ImageConstants.IMAGE_3);
			theme4ALS3.setText(themeQALS3.getTheme());
			theme4ALS3.setCollapsible(true);
			theme4ALS3.setExpanded(false);
			theme4ALS3.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
				if (isNowExpanded) {
					LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 3\".");

					theme4ALS3.afficherTheme();
					moteurQALS.jouerTheme(themeQALS3.getReference());
				}
			});

			theme4ALS4.setGraphic(ImageConstants.IMAGE_4);
			theme4ALS4.setText(themeQALS4.getTheme());
			theme4ALS4.setCollapsible(true);
			theme4ALS4.setExpanded(false);
			theme4ALS4.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
				if (isNowExpanded) {
					LOGGER.info("### --> Clic sur l'onglet \"Quatre à la suite 4\".");

					theme4ALS4.afficherTheme();
					moteurQALS.jouerTheme(themeQALS4.getReference());
				}
			});

			// Retrieving the observable list of the TextFlow Pane
			formaterContenuTheme4ALS(questionTheme4ALS1.getChildren(), themeQALS1);
			formaterContenuTheme4ALS(questionTheme4ALS2.getChildren(), themeQALS2);
			formaterContenuTheme4ALS(questionTheme4ALS3.getChildren(), themeQALS3);
			formaterContenuTheme4ALS(questionTheme4ALS4.getChildren(), themeQALS4);
		} else {
			PopUpPenurieTheme4ALS.afficherPopUp();
		}
	}

	public void formaterContenuTheme4ALS(ObservableList<Node> list, Theme4ALS theme4ALS) {
		LOGGER.info("[DEBUT] Afficher theme 4ALS.");

		if (list != null) {
			list.clear();
		}

		Font fontR = Font.font(FontConstants.VENACTI, FontWeight.BOLD, 18);
		Font fontQ = new Font(FontConstants.ARIAL, 18);
		Font fontLV = new Font(FontConstants.VENACTI, 4);
		Font fontS = Font.font(FontConstants.VENACTI, FontWeight.BOLD, 18);

		for (int i = 1; i <= theme4ALS.getQuestions().size(); i++) {

			QR question = theme4ALS.getQuestions().get(String.valueOf(i));

			Text textQ = new Text(i + "/ " + question.getQuestion() + "\n");
			textQ.setFont(fontQ);

			Text textR = new Text(question.getReponse().toUpperCase(Locale.FRANCE) + "\n");
			textR.setFont(fontR);

			Text textLV = new Text("\n");
			textLV.setFont(fontLV);

			if (list != null) {
				// Adding cylinder to the pane
				list.addAll(textQ, textR, textLV);
			}
		}

		Text textS = new Text("\n(" + theme4ALS.getSource() + ")\n\n\n" + "");
		textS.setFont(fontS);

		if (list != null) {
			list.addAll(textS);
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
