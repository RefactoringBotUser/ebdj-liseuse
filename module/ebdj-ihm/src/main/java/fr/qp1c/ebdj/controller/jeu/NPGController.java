package fr.qp1c.ebdj.controller.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.loader.LoaderQuestion9PG;
import fr.qp1c.ebdj.moteur.bean.Mode9PG;
import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestion9PG;
import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import fr.qp1c.ebdj.view.TaillePolice;
import fr.qp1c.ebdj.view.component.Historique9PGListCell;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class NPGController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NPGController.class);

	// Labels.

	@FXML
	private Label nbQuestion;

	@FXML
	private Label question9PG;

	@FXML
	private Label reponse9PG;

	@FXML
	private Label question9PGInfos;

	// Liste.

	@FXML
	private ListView<HistoriqueQuestion9PG> histoQuestion;

	public static ObservableList<HistoriqueQuestion9PG> listeHistorique9PG = FXCollections.observableArrayList();

	// Images.

	@FXML
	private ImageView niveau1;

	@FXML
	private ImageView niveau2;

	@FXML
	private ImageView niveau3;

	// Boutons.

	@FXML
	private Button btnNouvelleQuestion9PG;

	@FXML
	private Button btnReprendre9PG;

	@FXML
	private Button btnSignalerErreurQuestion9PG;

	@FXML
	private Button btnRemplacerQuestion9PG;

	@FXML
	private ToggleButton btn123;

	@FXML
	private ToggleButton btn23;

	@FXML
	private ToggleButton btn3;

	// Panneau.

	@FXML
	private BorderPane panneau;

	@FXML
	private VBox carton9PG;

	// Données 9PG.

	// Nombre de questions officiellement joué.
	private int nbQuest = 0;

	// Nombre de questions réel (inclus erreur et remplacement).
	private int nbQuestReel = 0;

	private int niveau = 0;

	private int cpt_1 = 0;

	private int cpt_2 = 0;

	private int cpt_3 = 0;

	private Mode9PG mode9PG;

	private List<QuestionNPG> questions9PGJouee = new ArrayList<>();

	private List<QuestionNPG> questions9PG_1 = new ArrayList<>();

	private List<QuestionNPG> questions9PG_2 = new ArrayList<>();

	private List<QuestionNPG> questions9PG_3 = new ArrayList<>();

	private QuestionNPG derniereQuestion9PG;

	private boolean affichageHistoriqueEnCours = false;

	private int numQuestionAffiche = 0;

	@FXML
	private void initialize() {
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.debug("[DEBUT] Initialisation du panneau 9PG.");

		// Chargement des questions.
		questions9PG_1 = LoaderQuestion9PG.chargerQuestions1Etoile();
		questions9PG_2 = LoaderQuestion9PG.chargerQuestions2Etoiles();
		questions9PG_3 = LoaderQuestion9PG.chargerQuestions3Etoiles();

		// Création de l'historique des questions du 9PG
		histoQuestion.setEditable(false);
		histoQuestion.setItems(listeHistorique9PG);
		histoQuestion.setCellFactory(new Callback<ListView<HistoriqueQuestion9PG>, ListCell<HistoriqueQuestion9PG>>() {

			// only one global event handler
			private EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Parent p = (Parent) event.getSource();

					LOGGER.info("Clic sur historique 9PG : {}", p.getUserData());

					afficherQuestionHistorique((HistoriqueQuestion9PG) p.getUserData());

					btnRemplacerQuestion9PG.setDisable(true);

					affichageHistoriqueEnCours = true;
				}
			};

			@Override
			public ListCell<HistoriqueQuestion9PG> call(ListView<HistoriqueQuestion9PG> list) {
				return new Historique9PGListCell(eventHandler);
			}
		});

		// Création des boutons

		btnReprendre9PG.setVisible(false);
		btnReprendre9PG.setDisable(true);

		// btnNouvelleQuestion9PG.setContentDisplay(ContentDisplay.LEFT);
		// btnNouvelleQuestion9PG.setGraphicTextGap(10);
		// btnNouvelleQuestion9PG.setGraphic(reprendre);
		//
		// btnRemplacerQuestion9PG.setContentDisplay(ContentDisplay.RIGHT);
		// btnRemplacerQuestion9PG.setGraphicTextGap(10);
		// btnRemplacerQuestion9PG.setGraphic(iconeRemplacement);

		// Lancer en mode 1,2,3
		changerNiveau123();

		modifierTaille(TaillePolice.GRAND);

		LOGGER.debug("[FIN] Initialisation du panneau 9PG.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Nouvelle question 9PG\".");

		afficherNouvelleQuestion();
	}

	@FXML
	public void reprendre9PG() {
		LOGGER.info("### --> Clic sur \"Reprendre 9PG\".");

		if (affichageHistoriqueEnCours) {

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle("-fx-background-color: #ffe808;");

			afficherCarton9PG(derniereQuestion9PG, niveau);

			numQuestionAffiche = nbQuestReel;
		}
	}

	@FXML
	public void changerNiveau123() {
		LOGGER.info("### --> Clic sur \"Niveau 1-2-3\".");

		mode9PG = Mode9PG.MODE_123;

		btn123.setSelected(true);

		afficherNouvelleQuestion();

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle("-fx-background-color: #ffe808;");
		}

		// modifierTaille(TaillePolice.MOYEN);
	}

	@FXML
	public void changerNiveau23() {
		LOGGER.info("### --> Clic sur \"Niveau 2-3\".");

		// Selection du mode de jeu
		mode9PG = Mode9PG.MODE_23;

		btn23.setSelected(true);
		btn123.setDisable(true);

		niveau = 2;

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();
		changerQuestion(nouvelleQuestion, true);

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle("-fx-background-color: #ffe808;");
		}

		// modifierTaille(TaillePolice.GRAND);
	}

	@FXML
	public void changerNiveau3() {
		LOGGER.info("### --> Clic sur \"Niveau 3\".");

		// Selection du mode de jeu
		mode9PG = Mode9PG.MODE_3;

		btn3.setSelected(true);

		btn23.setDisable(true);

		niveau = 3;

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();
		changerQuestion(nouvelleQuestion, true);

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle("-fx-background-color: #ffe808;");
		}

		// modifierTaille(TaillePolice.PETIT);
	}

	@FXML
	public void signalerErreurQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de 9PG\".");

		List<String> typesErreur = new ArrayList<>();
		typesErreur.add("Question périmée");
		typesErreur.add("Question mal rédigée");
		typesErreur.add("Réponse incomplète");
		typesErreur.add("Réponse fausse");
		typesErreur.add("Autre problème");

		ChoiceDialog<String> popupErreur = new ChoiceDialog<>("Question périmée", typesErreur);
		popupErreur.setTitle("QP1C - E-Boite de jeu");
		popupErreur.initOwner(Launcher.getStage());
		popupErreur.setHeaderText("Des commes çà on en veut plus...");
		popupErreur.setContentText("Motif:");

		ImageView imagePopup = new ImageView(ImageConstants.IMAGE_POPUP);
		ImageUtils.reduireImage(imagePopup);
		popupErreur.setGraphic(imagePopup);

		// Traditional way to get the response value.
		Optional<String> result = popupErreur.showAndWait();
		if (result.isPresent()) {
			LOGGER.info("Type d'anomalie sur la question : " + result.get());
			LOGGER.info("Num question affiche            : " + numQuestionAffiche);

			listeHistorique9PG.get(listeHistorique9PG.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();

			// TODO mettre en anomalie la question
		}
	}

	@FXML
	public void remplacerQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de 9PG\".");

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, false);

		listeHistorique9PG.get((nbQuestReel - listeHistorique9PG.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void afficherNouvelleQuestion() {

		// Calcul du niveau
		calculerNiveauQuestion();

		QuestionNPG nouvelleQuestion = donnerNouvelleQuestion();

		changerQuestion(nouvelleQuestion, true);
	}

	private QuestionNPG donnerNouvelleQuestion() {
		LOGGER.debug("[DEBUT] Donner une nouvelle question.");

		QuestionNPG question = null;

		if ((niveau == 1 && Mode9PG.MODE_123.equals(mode9PG)) || (niveau == 2 && Mode9PG.MODE_23.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_3.equals(mode9PG))) {
			LOGGER.info("Question à 1 étoile.");

			question = questions9PG_1.get(cpt_1);
			cpt_1++;

		} else if ((niveau == 2 && Mode9PG.MODE_123.equals(mode9PG))
				|| (niveau == 3 && Mode9PG.MODE_23.equals(mode9PG))) {
			LOGGER.info("Question à 2 étoiles.");

			question = questions9PG_2.get(cpt_2);
			cpt_2++;
		} else {
			LOGGER.info("Question à 3 étoiles.");

			question = questions9PG_3.get(cpt_3);
			cpt_3++;
		}
		questions9PGJouee.add(question);

		// TODO : gérer la récupération du lecteur
		DBConnecteurNPGDao dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();
		dbConnecteurNPGDao.jouerQuestion(question.getId(), question.getReference(), "lecteur");

		LOGGER.debug("[FIN] Donner une nouvelle question.");

		return question;

	}

	private void changerQuestion(QuestionNPG nouvelleQuestion, boolean questionACompter) {

		// Calcul du nombre de question joué
		if (questionACompter) {
			calculerNbQuestion();

		}
		calculerNbQuestionReel();
		numQuestionAffiche = nbQuestReel;

		// Historiser la nouvelle question
		historiserQuestion9PG(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCarton9PG(nouvelleQuestion, niveau);
		afficherNbQuestion();

		derniereQuestion9PG = nouvelleQuestion;
	}

	private void afficherQuestionHistorique(HistoriqueQuestion9PG question) {
		LOGGER.debug("[DEBUT] Affichage question depuis l'historique.");

		// Mise à jour de l'affichage
		afficherCarton9PG(question.getQuestion(), question.getNiveau());

		// Désactivation du bouton "Nouvelle question"
		btnNouvelleQuestion9PG.setVisible(false);
		btnNouvelleQuestion9PG.setDisable(true);

		// Activation du bouton "Reprendre le 9PG"
		btnReprendre9PG.setVisible(true);
		btnReprendre9PG.setDisable(false);

		// Modifcation de la couleur de fond du carton du 9PG.
		carton9PG.setStyle("-fx-background-color: #D8D8D8;");

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

	private void afficherCarton9PG(QuestionNPG questionNPG, int niveau) {
		LOGGER.debug("[DEBUT] Affichage carton 9PG.");

		afficherNiveauQuestion(niveau);

		question9PG.setText(questionNPG.getQuestion());
		reponse9PG.setText(questionNPG.getReponse().toUpperCase());
		reponse9PG.setTextAlignment(TextAlignment.CENTER);
		question9PGInfos.setText(questionNPG.getSource().toString());

		LOGGER.debug("[FIN] Affichage carton 9PG.");
	}

	private void afficherNiveauQuestion(int niveau) {
		LOGGER.debug("[DEBUT] Affichage niveau question : {}", niveau);

		if (niveau == 1) {
			niveau1.setVisible(false);
			niveau2.setVisible(true);
			niveau3.setVisible(false);
		} else if (niveau == 2) {
			niveau1.setVisible(true);
			niveau2.setVisible(true);
			niveau3.setVisible(false);
		} else if (niveau == 3) {
			niveau1.setVisible(true);
			niveau2.setVisible(true);
			niveau3.setVisible(true);
		}

		LOGGER.debug("[FIN] Affichage niveau question.");
	}

	// Méthodes métier

	private void historiserQuestion9PG(QuestionNPG question9PG) {
		LOGGER.debug("[DEBUT] Historisation de la question 9PG.");

		if (question9PG != null) {

			HistoriqueQuestion9PG histo = new HistoriqueQuestion9PG();
			histo.setNbQuestion(nbQuest);
			histo.setNbQuestionReel(nbQuestReel);
			histo.setNiveau(niveau);
			histo.setQuestion(question9PG);

			listeHistorique9PG.add(0, histo);
		}

		LOGGER.debug("[FIN] Historisation de la question 9PG.");
	}

	private void calculerNbQuestion() {
		nbQuest++;
	}

	private void calculerNbQuestionReel() {
		nbQuestReel++;
	}

	private void calculerNiveauQuestion() {
		if (Mode9PG.MODE_123.equals(mode9PG)) {
			niveau = ((niveau++) % 3) + 1;
		} else if (Mode9PG.MODE_23.equals(mode9PG)) {
			if (niveau == 2) {
				niveau = 3;
			} else {
				niveau = 2;
			}
		} else if (Mode9PG.MODE_3.equals(mode9PG)) {
			niveau = 3;
		}
	}

	/**
	 * Modifier la taille du texte sur le carton.
	 * 
	 * @param taille
	 *            liste de taille pré-établi (PETIT, MOYEN ou GRAND)
	 */
	public void modifierTaille(TaillePolice taille) {
		switch (taille) {
		case PETIT:
			definirTailleCarton9PG(14);
			break;
		case MOYEN:
			definirTailleCarton9PG(18);
			break;
		case GRAND:
			definirTailleCarton9PG(22);
			break;
		}
	}

	/**
	 * Modifier la taille du texte sur le carton.
	 * 
	 * @param taille
	 *            la taille à définir
	 */
	private void definirTailleCarton9PG(int taille) {
		question9PG.setStyle("-fx-font-size:" + taille + "px");
		reponse9PG.setStyle("-fx-font-size:" + taille + "px");
		question9PGInfos.setStyle("-fx-font-size:" + (taille - 4) + "px");
	}
}
