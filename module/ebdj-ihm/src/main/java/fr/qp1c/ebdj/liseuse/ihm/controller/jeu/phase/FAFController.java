package fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.historique.HistoriqueQuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.TypePartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.question.TypePhase;
import fr.qp1c.ebdj.liseuse.commun.utils.StringUtilities;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;
import fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase.preferences.PreferencesLecteur;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Seuil;
import fr.qp1c.ebdj.liseuse.ihm.view.Style;
import fr.qp1c.ebdj.liseuse.ihm.view.TaillePolice;
import fr.qp1c.ebdj.liseuse.ihm.view.listcell.HistoriqueFAFListCell;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpAnomalieQuestion;
import fr.qp1c.ebdj.liseuse.moteur.MoteurFAF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
public class FAFController implements PreferencesLecteur {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FAFController.class);

	// Composant(s) JavaFX

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

	public static ObservableList<HistoriqueQuestionFAF> listeHistoriqueFAF = FXCollections.observableArrayList();

	private MoteurFAF moteurFAF;

	private int numQuestionAffiche = 0;

	private boolean affichageHistoriqueEnCours = false;

	@FXML
	private void initialize() {
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.info("[DEBUT] Initialisation du panneau FAF.");

		moteurFAF = new MoteurFAF();

		listeHistoriqueFAF.clear();

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

					LOGGER.info("### --> Clic sur \"Historique FAF\" : {}.", p.getUserData());

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

		nbQuestion.setStyle(Style.FOND_NORMAL);

		modifierTaille(TaillePolice.GRAND);

		LOGGER.info("[FIN] Initialisation du panneau FAF.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Nouvelle question FAF\".");

		changerQuestion(true);
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

			afficherCartonFAF(moteurFAF.getDerniereQuestionFAF());

			numQuestionAffiche = moteurFAF.getNbQuestReel();
		}
	}

	@FXML
	public void signalerErreurQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de FAF\".");

		SignalementAnomalie signalementAnomalie = PopUpAnomalieQuestion.afficherPopUp(TypePartie.FAF);

		if (signalementAnomalie != null) {
			moteurFAF.signalerAnomalie(signalementAnomalie);

			// Mise à jour de l'historique des questions
			listeHistoriqueFAF.get(listeHistoriqueFAF.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();
		}

	}

	@FXML
	public void remplacerQuestionFAF() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de FAF\".");

		changerQuestion(false);

		// Mise à jour de l'historique des questions
		listeHistoriqueFAF.get((moteurFAF.getNbQuestReel() - listeHistoriqueFAF.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void changerQuestion(boolean questionACompter) {
		LOGGER.info("[DEBUT] Changer de question.");

		QuestionFAF nouvelleQuestion = moteurFAF.changerQuestion(questionACompter);

		// Historiser la nouvelle question
		historiserQuestionFAF(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCartonFAF(nouvelleQuestion);
		afficherNbQuestion();

		LOGGER.info("[FIN] Changer de question.");
	}

	private void afficherQuestionHistorique(HistoriqueQuestionFAF question) {
		LOGGER.info("[DEBUT] Affichage question depuis l'historique.");

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

		LOGGER.info("[FIN] Affichage question depuis l'historique.");
	}

	private void afficherNbQuestion() {
		LOGGER.info("[DEBUT] Affichage du nombre de question.");

		if (moteurFAF.getNbQuest() == 1) {
			nbQuestion.setText(moteurFAF.getNbQuest() + " question jouée");
		} else {
			if (moteurFAF.getNbQuest() >= Seuil.SEUIL_WARNING_FAF) {
				nbQuestion.setStyle(Style.FOND_WARNING);
			}
			nbQuestion.setText(moteurFAF.getNbQuest() + " questions jouées");
		}
		LOGGER.info("[FIN] Affichage du nombre de question.");
	}

	private void afficherCartonFAF(QuestionFAF questionFAF) {
		LOGGER.info("[DEBUT] Affichage carton FAF.");

		String nbMots = " (" + StringUtilities.compterNombreDeMots(questionFAF.getQuestion()) + " mots)";

		themeFAF.setText(questionFAF.getTheme().toUpperCase() + nbMots);
		libelleQuestionFAF.setText(questionFAF.getQuestion());
		reponseFAF.setText(questionFAF.getReponse().toUpperCase());
		reponseFAF.setTextAlignment(TextAlignment.CENTER);
		questionFAFInfos.setText(Utils.formaterReference(questionFAF.getReference(), TypePhase.FAF) + " - "
				+ questionFAF.getSource().toString());

		LOGGER.info("[FIN] Affichage carton FAF.");
	}

	// Méthodes métier

	private void historiserQuestionFAF(QuestionFAF questionFAF) {
		LOGGER.info("[DEBUT] Historisation de la question FAF.");

		if (questionFAF != null) {
			HistoriqueQuestionFAF histo = new HistoriqueQuestionFAF();
			histo.setNbQuestion(moteurFAF.getNbQuest());
			histo.setNbQuestionReel(moteurFAF.getNbQuestReel());
			histo.setQuestion(questionFAF);

			listeHistoriqueFAF.add(0, histo);
		}

		LOGGER.info("[FIN] Historisation de la question FAF.");
	}

	@Override
	public void modifierTaille(TaillePolice taille) {
		LOGGER.info("[DEBUT] Modifier la taille.");

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

		LOGGER.info("[FIN] Modifier la taille.");
	}

	private void definirTailleCartonFAF(int taille) {
		LOGGER.info("[DEBUT] Définir taille carton.");

		themeFAF.setStyle("-fx-font-size:" + taille + "px");
		libelleQuestionFAF.setStyle("-fx-font-size:" + taille + "px");
		reponseFAF.setStyle("-fx-font-size:" + taille + "px");
		questionFAFInfos.setStyle("-fx-font-size:" + (taille - 4) + "px");

		LOGGER.info("[FIN] Définir taille carton.");
	}

	@Override
	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		LOGGER.info("[DEBUT] Définir niveau partie.");

		moteurFAF.definirNiveauPartie(niveauPartie);

		LOGGER.info("[FIN] Définir niveau partie.");
	}

	@Override
	public void definirLecteur(Lecteur lecteur) {
		LOGGER.info("[DEBUT] Définir lecteur.");

		moteurFAF.definirLecteur(lecteur);

		LOGGER.info("[FIN] Définir lecteur.");
	}
}
