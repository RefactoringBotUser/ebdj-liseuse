package fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.historique.HistoriqueQuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.TypePartie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.question.TypePhase;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;
import fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase.preferences.PreferencesLecteur;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Seuil;
import fr.qp1c.ebdj.liseuse.ihm.view.Style;
import fr.qp1c.ebdj.liseuse.ihm.view.TaillePolice;
import fr.qp1c.ebdj.liseuse.ihm.view.listcell.HistoriqueJDListCell;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpAnomalieQuestion;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.FontConstants;
import fr.qp1c.ebdj.liseuse.moteur.MoteurJD;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
import javafx.util.Duration;

public class JDController implements PreferencesLecteur {

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

	public static final ObservableList<HistoriqueQuestionJD> listeHistoriqueJD = FXCollections.observableArrayList();

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

	// Données JD.

	private MoteurJD moteurJD;

	private boolean affichageHistoriqueEnCours = false;

	private int numQuestionAffiche = 0;

	@FXML
	private void initialize() {
		LOGGER.info("[DEBUT] Initialisation du panneau JD.");

		reinitialiser();

		LOGGER.info("[FIN] Initialisation du panneau JD.");
	}

	public void reinitialiser() {
		LOGGER.info("[DEBUT] Reinitialisation du panneau JD.");

		moteurJD = new MoteurJD();

		listeHistoriqueJD.clear();

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
					LOGGER.info("### --> Clic sur \"Historique JD\" : {}.", p.getUserData());

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

		// Fix anomalie JD en mode historique par
		btnNouvelleQuestionJD.setVisible(true);
		btnNouvelleQuestionJD.setDisable(false);

		btnRemplacerQuestionJD.setDisable(false);

		cartonJD.setStyle(Style.FOND_CARTON);

		nbQuestion.setStyle(Style.FOND_NORMAL);

		modifierTaille(TaillePolice.GRAND);

		btnNouvelleQuestionJD.setCursor(Cursor.HAND);
		btnRemplacerQuestionJD.setCursor(Cursor.HAND);
		btnReprendreJD.setCursor(Cursor.HAND);
		btnSignalerErreurQuestionJD.setCursor(Cursor.HAND);

		LOGGER.info("[FIN] Reinitialisation du panneau JD.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestionJD() {
		LOGGER.info("### --> Clic sur \"Nouvelle question JD\".");

		changerQuestion(true);

		final KeyFrame kf1 = new KeyFrame(Duration.millis(0), e -> btnNouvelleQuestionJD.setDisable(true));
		final KeyFrame kf2 = new KeyFrame(Duration.millis(300), e -> btnNouvelleQuestionJD.setDisable(false));
		final Timeline timeline = new Timeline(kf1, kf2);
		Platform.runLater(timeline::play);
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

			cartonJD.setStyle(Style.FOND_CARTON);

			afficherCartonJD(moteurJD.getDerniereQuestionJD());

			numQuestionAffiche = moteurJD.getNbQuestReel();
		}
	}

	@FXML
	public void signalerErreurQuestionJD() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de JD\".");

		SignalementAnomalie signalementAnomalie = PopUpAnomalieQuestion.afficherPopUp(TypePartie.JD);

		if (signalementAnomalie != null) {

			moteurJD.signalerAnomalie(signalementAnomalie);

			listeHistoriqueJD.get(listeHistoriqueJD.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();
		}
	}

	@FXML
	public void remplacerQuestionJD() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de JD\".");

		changerQuestion(false);

		listeHistoriqueJD.get((moteurJD.getNbQuestReel() - listeHistoriqueJD.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void changerQuestion(boolean questionACompter) {
		LOGGER.info("[DEBUT] Changer de question.");

		QuestionJD nouvelleQuestion = moteurJD.changerQuestion(questionACompter);

		// Historiser la nouvelle question
		historiserQuestionJD(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCartonJD(nouvelleQuestion);
		afficherNbQuestion();

		LOGGER.info("[FIN] Changer de question.");
	}

	private void afficherQuestionHistorique(HistoriqueQuestionJD question) {
		LOGGER.info("[DEBUT] Affichage question depuis l'historique.");

		// Mise à jour de l'affichage
		afficherCartonJD(question.getQuestion());

		// Désactivation du bouton "Nouvelle question"
		btnNouvelleQuestionJD.setVisible(false);
		btnNouvelleQuestionJD.setDisable(true);

		// Activation du bouton "Reprendre le JD"
		btnReprendreJD.setVisible(true);
		btnReprendreJD.setDisable(false);

		// Modifcation de la couleur de fond du carton du JD.
		cartonJD.setStyle(Style.FOND_CARTON);

		numQuestionAffiche = question.getNbQuestionReel();

		LOGGER.info("[FIN] Affichage question depuis l'historique.");
	}

	private void afficherNbQuestion() {
		LOGGER.info("[DEBUT] Affichage du nombre de question.");

		if (moteurJD.getNbQuest() == 1) {
			nbQuestion.setText(moteurJD.getNbQuest() + " question jouée");
		} else {
			if (moteurJD.getNbQuest() >= Seuil.SEUIL_WARNING_JD) {
				nbQuestion.setStyle(Style.FOND_WARNING);
			}
			nbQuestion.setText(moteurJD.getNbQuest() + " questions jouées");
		}
		LOGGER.info("[FIN] Affichage du nombre de question.");
	}

	private void afficherCartonJD(QuestionJD questionJD) {
		LOGGER.info("[DEBUT] Affichage carton JD.");

		themeJD.setText(questionJD.getTheme());
		libelleQuestionJD.setText(questionJD.getQuestion());
		reponseJD.setText(questionJD.getReponse().toUpperCase());
		reponseJD.setTextAlignment(TextAlignment.CENTER);
		questionJDInfos.setText(formaterQuestionJDInfos(questionJD));

		LOGGER.info("=> Question JD - {} - Difficulté : {}", formaterQuestionJDInfos(questionJD));

		LOGGER.info("[FIN] Affichage carton JD.");
	}

	private String formaterQuestionJDInfos(QuestionJD questionJD) {
		return Utils.formaterReference(questionJD.getReference(), TypePhase.JD) + " - " + questionJD.getSource();
	}

	// Méthodes métier

	private void historiserQuestionJD(QuestionJD questionJD) {
		LOGGER.info("[DEBUT] Historisation de la question JD.");

		if (questionJD != null) {

			HistoriqueQuestionJD histo = new HistoriqueQuestionJD();
			histo.setNbQuestion(moteurJD.getNbQuest());
			histo.setNbQuestionReel(moteurJD.getNbQuestReel());
			histo.setQuestion(questionJD);

			listeHistoriqueJD.add(0, histo);
		}

		LOGGER.info("[FIN] Historisation de la question JD.");
	}

	@Override
	public void modifierTaille(TaillePolice taille) {
		LOGGER.info("[DEBUT] Modifier la taille.");

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

		LOGGER.info("[FIN] Modifier la taille.");
	}

	private void definirTailleCartonJD(int taille) {
		LOGGER.info("[DEBUT] Définir taille carton.");

		themeJD.setStyle(FontConstants.FONT_SIZE + ":" + taille + "px");
		libelleQuestionJD.setStyle(FontConstants.FONT_SIZE + ":" + taille + "px");
		reponseJD.setStyle(FontConstants.FONT_SIZE + ":" + taille + "px");
		questionJDInfos.setStyle(FontConstants.FONT_SIZE + ":" + (taille - 4) + "px");

		LOGGER.info("[FIN] Définir taille carton.");
	}

	@Override
	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		LOGGER.info("[DEBUT] Définir niveau partie.");

		moteurJD.definirNiveauPartie(niveauPartie);

		LOGGER.info("[FIN] Définir niveau partie.");
	}

	@Override
	public void definirLecteur(Lecteur lecteur) {
		LOGGER.info("[DEBUT] Définir lecteur.");

		moteurJD.definirLecteur(lecteur);

		LOGGER.info("[FIN] Définir lecteur.");
	}
}
