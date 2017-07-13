package fr.qp1c.ebdj.controller.jeu.phase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.loader.LoaderQuestionJD;
import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestionJD;
import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.TaillePolice;
import fr.qp1c.ebdj.view.component.HistoriqueJDListCell;
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

public class JDController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JDController.class);

	// Labels.

	@FXML
	private Label nbQuestion;

	@FXML
	private Label themeJD;

	@FXML
	private Label libelleQuestionJD;

	@FXML
	private Label reponseJD;

	@FXML
	private Label questionJDInfos;

	// Liste.

	@FXML
	private ListView<HistoriqueQuestionJD> histoQuestion;

	public static ObservableList<HistoriqueQuestionJD> listeHistoriqueJD = FXCollections.observableArrayList();

	// Boutons.

	@FXML
	private Button btnNouvelleQuestionJD;

	@FXML
	private Button btnReprendreJD;

	@FXML
	private Button btnSignalerErreurQuestionJD;

	@FXML
	private Button btnRemplacerQuestionJD;

	// Panneau.

	@FXML
	private BorderPane panneau;

	@FXML
	private VBox cartonJD;

	// Données 9PG.

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private List<QuestionJD> questionsJD = new ArrayList<>();

	private QuestionJD derniereQuestionJD;

	private boolean affichageHistoriqueEnCours = false;

	private int numQuestionAffiche = 0;

	@FXML
	private void initialize() {
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.debug("[DEBUT] Initialisation du panneau JD.");

		// Chargement des questions.
		questionsJD = LoaderQuestionJD.chargerQuestions();

		listeHistoriqueJD.clear();

		// Données 9PG.

		// Nombre de questions officiellement joué.
		nbQuest = 0;

		// Nombre de questions réel (inclus erreur et remplacement).
		nbQuestReel = 0;

		numQuestionAffiche = 0;

		// Création de l'historique des questions du 9PG
		histoQuestion.setEditable(false);
		histoQuestion.setItems(listeHistoriqueJD);
		histoQuestion.setCellFactory(new Callback<ListView<HistoriqueQuestionJD>, ListCell<HistoriqueQuestionJD>>() {

			// only one global event handler
			private EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Parent p = (Parent) event.getSource();

					LOGGER.info("Clic sur historique JD : {}", p.getUserData());

					afficherQuestionHistorique((HistoriqueQuestionJD) p.getUserData());

					btnRemplacerQuestionJD.setDisable(true);

					affichageHistoriqueEnCours = true;
				}
			};

			@Override
			public ListCell<HistoriqueQuestionJD> call(ListView<HistoriqueQuestionJD> list) {
				return new HistoriqueJDListCell(eventHandler);
			}
		});

		// Création des boutons

		btnReprendreJD.setVisible(false);
		btnReprendreJD.setDisable(true);

		modifierTaille(TaillePolice.GRAND);

		jouerNouvelleQuestionJD();

		LOGGER.debug("[FIN] Initialisation du panneau JD.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestionJD() {
		LOGGER.info("### --> Clic sur \"Nouvelle question JD\".");

		afficherNouvelleQuestion();
	}

	@FXML
	public void reprendreJD() {
		LOGGER.info("### --> Clic sur \"Reprendre JD\".");

		if (affichageHistoriqueEnCours) {

			btnReprendreJD.setDisable(true);
			btnReprendreJD.setVisible(false);

			btnNouvelleQuestionJD.setDisable(false);
			btnNouvelleQuestionJD.setVisible(true);

			btnRemplacerQuestionJD.setDisable(false);

			cartonJD.setStyle("-fx-background-color: #ffe808;");

			afficherCartonJD(derniereQuestionJD);

			numQuestionAffiche = nbQuestReel;
		}
	}

	@FXML
	public void signalerErreurQuestionJD() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de JD\".");

		List<String> typesErreur = new ArrayList<>();
		typesErreur.add("Question périmée");
		typesErreur.add("Question mal rédigée");
		typesErreur.add("Réponse incomplète");
		typesErreur.add("Réponse fausse");
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

			listeHistoriqueJD.get(listeHistoriqueJD.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();

			// TODO mettre en anomalie la question
		}
	}

	@FXML
	public void remplacerQuestionJD() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de JD\".");

		QuestionJD nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, false);

		listeHistoriqueJD.get((nbQuestReel - listeHistoriqueJD.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void afficherNouvelleQuestion() {

		QuestionJD nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, true);
	}

	private QuestionJD donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionJD question = questionsJD.get(nbQuestReel);

		// TODO : gérer la récupération du lecteur
		DBConnecteurJDDao dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
		dbConnecteurJDDao.jouerQuestion(question.getId(), question.getReference(), "lecteur");

		LOGGER.debug("[FIN] Donner une nouvelle question.");

		return question;
	}

	private void changerQuestion(QuestionJD nouvelleQuestion, boolean questionACompter) {

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();
		numQuestionAffiche = nbQuestReel;

		// Historiser la nouvelle question
		historiserQuestionJD(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCartonJD(nouvelleQuestion);
		afficherNbQuestion();

		derniereQuestionJD = nouvelleQuestion;
	}

	private void afficherQuestionHistorique(HistoriqueQuestionJD question) {
		LOGGER.debug("[DEBUT] Affichage question depuis l'historique.");

		// Mise à jour de l'affichage
		afficherCartonJD(question.getQuestion());

		// Désactivation du bouton "Nouvelle question"
		btnNouvelleQuestionJD.setVisible(false);
		btnNouvelleQuestionJD.setDisable(true);

		// Activation du bouton "Reprendre le JD"
		btnReprendreJD.setVisible(true);
		btnReprendreJD.setDisable(false);

		// Modifcation de la couleur de fond du carton du JD.
		cartonJD.setStyle("-fx-background-color: #D8D8D8;");

		numQuestionAffiche = question.getNbQuestionReel();

		LOGGER.debug("[FIN] Affichage question depuis l'historique.");
	}

	private void afficherNbQuestion() {
		LOGGER.debug("[DEBUT] Affichage du nombre de question.");

		if (nbQuest == 1) {
			nbQuestion.setText(nbQuest + " question jouée");
		} else {
			if (nbQuest >= 25) {
				nbQuestion.setStyle("-fx-background-color: #FE2E64;");
			}
			nbQuestion.setText(nbQuest + " questions jouées");
		}
		LOGGER.debug("[FIN] Affichage du nombre de question.");
	}

	private void afficherCartonJD(QuestionJD questionJD) {
		LOGGER.debug("[DEBUT] Affichage carton JD.");

		themeJD.setText(questionJD.getTheme());
		libelleQuestionJD.setText(questionJD.getQuestion());
		reponseJD.setText(questionJD.getReponse().toUpperCase());
		reponseJD.setTextAlignment(TextAlignment.CENTER);
		questionJDInfos.setText(
				Utils.formaterReference(questionJD.getReference()) + " - " + questionJD.getSource().toString());

		LOGGER.debug("[FIN] Affichage carton JD.");
	}

	// Méthodes métier

	private void historiserQuestionJD(QuestionJD questionJD) {
		LOGGER.debug("[DEBUT] Historisation de la question JD.");

		if (questionJD != null) {

			HistoriqueQuestionJD histo = new HistoriqueQuestionJD();
			histo.setNbQuestion(nbQuest);
			histo.setNbQuestionReel(nbQuestReel);
			histo.setQuestion(questionJD);

			listeHistoriqueJD.add(0, histo);
		}

		LOGGER.debug("[FIN] Historisation de la question JD.");
	}

	private void calculerNbQuestion() {
		nbQuest++;
	}

	private void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	public void modifierTaille(TaillePolice taille) {
		switch (taille) {
		case PETIT:
			definirTailleCartonJD(14);
			break;
		case MOYEN:
			definirTailleCartonJD(18);
			break;
		case GRAND:
			definirTailleCartonJD(22);
			break;

		}
	}

	private void definirTailleCartonJD(int taille) {
		themeJD.setStyle("-fx-font-size:" + taille + "px");
		libelleQuestionJD.setStyle("-fx-font-size:" + taille + "px");
		reponseJD.setStyle("-fx-font-size:" + taille + "px");
		questionJDInfos.setStyle("-fx-font-size:" + (taille - 4) + "px");
	}
}
