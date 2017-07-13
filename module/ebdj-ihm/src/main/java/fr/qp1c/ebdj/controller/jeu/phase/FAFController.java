package fr.qp1c.ebdj.controller.jeu.phase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.loader.LoaderQuestionFAF;
import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.Style;
import fr.qp1c.ebdj.view.TaillePolice;
import fr.qp1c.ebdj.view.component.HistoriqueFAFListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 * 
 * 
 * @author NICO
 *
 */
public class FAFController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FAFController.class);

	// Labels.

	@FXML
	private Label nbQuestion;

	@FXML
	private Label themeFAF;

	@FXML
	private Label libelleQuestionFAF;

	@FXML
	private Label reponseFAF;

	@FXML
	private Label questionFAFInfos;

	// Liste.

	@FXML
	private ListView<HistoriqueQuestionFAF> histoQuestion;

	public static ObservableList<HistoriqueQuestionFAF> listeHistoriqueFAF = FXCollections.observableArrayList();

	// Boutons.

	@FXML
	private Button btnNouvelleQuestionFAF;

	@FXML
	private Button btnReprendreFAF;

	@FXML
	private Button btnSignalerErreurQuestionFAF;

	@FXML
	private Button btnRemplacerQuestionFAF;

	// Panneau.

	@FXML
	private BorderPane panneau;

	@FXML
	private VBox cartonFAF;

	// Données FAF.

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private int numQuestionAffiche = 0;

	// private int niveau = 0;

	private List<QuestionFAF> questionsFAF = new ArrayList<>();

	private QuestionFAF derniereQuestionFAF;

	private boolean affichageHistoriqueEnCours = false;

	@FXML
	private void initialize() {
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.debug("[DEBUT] Initialisation du panneau FAF.");

		// Chargement des questions.
		questionsFAF = LoaderQuestionFAF.chargerQuestions();

		listeHistoriqueFAF.clear();

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

		numQuestionAffiche = 0;

		// Création de l'historique des questions du 9PG
		histoQuestion.setEditable(false);
		histoQuestion.setItems(listeHistoriqueFAF);
		histoQuestion.setCellFactory(new Callback<ListView<HistoriqueQuestionFAF>, ListCell<HistoriqueQuestionFAF>>() {

			// only one global event handler
			private EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Parent p = (Parent) event.getSource();

					LOGGER.info("Clic sur historique FAF : {}", p.getUserData());

					afficherQuestionHistorique((HistoriqueQuestionFAF) p.getUserData());

					btnRemplacerQuestionFAF.setDisable(true);

					affichageHistoriqueEnCours = true;
				}
			};

			@Override
			public ListCell<HistoriqueQuestionFAF> call(ListView<HistoriqueQuestionFAF> list) {
				return new HistoriqueFAFListCell(eventHandler);
			}
		});

		// Création des boutons

		btnReprendreFAF.setVisible(false);
		btnReprendreFAF.setDisable(true);

		modifierTaille(TaillePolice.GRAND);

		jouerNouvelleQuestionFAF();

		LOGGER.debug("[FIN] Initialisation du panneau FAF.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Nouvelle question FAF\".");

		afficherNouvelleQuestion();
	}

	@FXML
	public void reprendreFAF() {
		LOGGER.info("### --> Clic sur \"Reprendre FAF\".");

		if (affichageHistoriqueEnCours) {

			btnReprendreFAF.setDisable(true);
			btnReprendreFAF.setVisible(false);

			btnNouvelleQuestionFAF.setDisable(false);
			btnNouvelleQuestionFAF.setVisible(true);

			btnRemplacerQuestionFAF.setDisable(false);

			cartonFAF.setStyle(Style.FOND_CARTON);

			afficherCartonFAF(derniereQuestionFAF);

			numQuestionAffiche = nbQuestReel;
		}
	}

	@FXML
	public void signalerErreurQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de FAF\".");

		List<String> typesErreur = new ArrayList<>();
		typesErreur.add("Question périmée");
		typesErreur.add("Question mal rédigée");
		typesErreur.add("Réponse incomplète");
		typesErreur.add("Réponse fausse");
		typesErreur.add("Thème incorrect");
		typesErreur.add("Autre problème");

		ChoiceDialog<String> popupErreur = new ChoiceDialog<>("Question périmée", typesErreur);
		popupErreur.setTitle("QP1C - E-Boite de jeu");
		popupErreur.setHeaderText("Plus jamais comme celà...");
		popupErreur.setContentText("Motif:");

		ImageView imagePopup = new ImageView(ImageConstants.IMAGE_POPUP);
		ImageUtils.reduireImage(imagePopup);
		popupErreur.setGraphic(imagePopup);

		// Traditional way to get the response value.
		Optional<String> result = popupErreur.showAndWait();
		if (result.isPresent()) {
			LOGGER.info("Type d'anomalie sur la question : " + result.get());
			LOGGER.info(" Num question affiche           : " + numQuestionAffiche);

			listeHistoriqueFAF.get(listeHistoriqueFAF.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();

			// TODO mettre en anomalie la question
		}
	}

	@FXML
	public void remplacerQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de FAF\".");

		QuestionFAF nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, false);

		listeHistoriqueFAF.get((nbQuestReel - listeHistoriqueFAF.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void afficherNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Afficher la nouvelle question.");

		QuestionFAF nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, true);

		LOGGER.debug("[FIN] Afficher la nouvelle question.");
	}

	private QuestionFAF donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionFAF question = questionsFAF.get(nbQuestReel);

		// TODO : gérer la récupération du lecteur
		DBConnecteurFAFDao dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
		dbConnecteurFAFDao.jouerQuestion(question.getId(), question.getReference(), "lecteur");

		LOGGER.debug("[FIN] Donner une nouvelle question.");

		return question;
	}

	private void changerQuestion(QuestionFAF nouvelleQuestion, boolean questionACompter) {
		LOGGER.debug("[DEBUT] Changer de question.");

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();
		numQuestionAffiche = nbQuestReel;

		// Historiser la nouvelle question
		historiserQuestionFAF(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCartonFAF(nouvelleQuestion);
		afficherNbQuestion();

		derniereQuestionFAF = nouvelleQuestion;

		LOGGER.debug("[FIN] Changer de question.");
	}

	private void afficherQuestionHistorique(HistoriqueQuestionFAF question) {
		LOGGER.debug("[DEBUT] Affichage question depuis l'historique.");

		// Mise à jour de l'affichage
		afficherCartonFAF(question.getQuestion());

		// Désactivation du bouton "Nouvelle question"
		btnNouvelleQuestionFAF.setVisible(false);
		btnNouvelleQuestionFAF.setDisable(true);

		// Activation du bouton "Reprendre le FAF"
		btnReprendreFAF.setVisible(true);
		btnReprendreFAF.setDisable(false);

		// Modifcation de la couleur de fond du carton du FAF.
		cartonFAF.setStyle(Style.FOND_CARTON_HISTORIQUE);

		numQuestionAffiche = question.getNbQuestionReel();

		LOGGER.debug("[FIN] Affichage question depuis l'historique.");
	}

	private void afficherNbQuestion() {
		LOGGER.debug("[DEBUT] Affichage du nombre de question.");

		if (nbQuest == 1) {
			nbQuestion.setText(nbQuest + " question jouée");
		} else {
			if (nbQuest >= 8) {
				nbQuestion.setStyle("-fx-background-color: #FE2E64;");
			}
			nbQuestion.setText(nbQuest + " questions jouées");
		}
		LOGGER.debug("[FIN] Affichage du nombre de question.");
	}

	private int compterNombreDeMots(QuestionFAF questionFAF) {
		return questionFAF.getQuestion().split(" ").length;
	}

	private void afficherCartonFAF(QuestionFAF questionFAF) {
		LOGGER.debug("[DEBUT] Affichage carton FAF.");

		String nbMots = " (" + compterNombreDeMots(questionFAF) + " mots)";

		themeFAF.setText(questionFAF.getTheme().toUpperCase() + nbMots);
		libelleQuestionFAF.setText(questionFAF.getQuestion().replaceAll("  ", " "));
		reponseFAF.setText(questionFAF.getReponse().toUpperCase());
		reponseFAF.setTextAlignment(TextAlignment.CENTER);
		questionFAFInfos.setText(
				Utils.formaterReference(questionFAF.getReference()) + " - " + questionFAF.getSource().toString());

		LOGGER.debug("[FIN] Affichage carton FAF.");
	}

	// Méthodes métier

	private void historiserQuestionFAF(QuestionFAF questionFAF) {
		LOGGER.debug("[DEBUT] Historisation de la question FAF.");

		if (questionFAF != null) {

			HistoriqueQuestionFAF histo = new HistoriqueQuestionFAF();
			histo.setNbQuestion(nbQuest);
			histo.setNbQuestionReel(nbQuestReel);
			histo.setQuestion(questionFAF);

			listeHistoriqueFAF.add(0, histo);
		}

		LOGGER.debug("[FIN] Historisation de la question FAF.");
	}

	private void calculerNbQuestion() {
		nbQuest++;
	}

	private void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	public void modifierTaille(TaillePolice taille) {
		LOGGER.debug("[DEBUT] Modifier la taille.");
		switch (taille) {
		case PETIT:
			definirTailleCartonFAF(14);
			break;
		case MOYEN:
			definirTailleCartonFAF(18);
			break;
		case GRAND:
			definirTailleCartonFAF(22);
			break;

		}

		LOGGER.debug("[FIN] Modifier la taille.");
	}

	private void definirTailleCartonFAF(int taille) {
		themeFAF.setStyle("-fx-font-size:" + taille + "px");
		libelleQuestionFAF.setStyle("-fx-font-size:" + taille + "px");
		reponseFAF.setStyle("-fx-font-size:" + taille + "px");
		questionFAFInfos.setStyle("-fx-font-size:" + (taille - 4) + "px");
	}
}
