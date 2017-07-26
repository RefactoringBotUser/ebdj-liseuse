package fr.qp1c.ebdj.controller.jeu.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.controller.popup.PopUpAnomalieQuestion;
import fr.qp1c.ebdj.loader.MoteurNPG;
import fr.qp1c.ebdj.model.NiveauPartie;
import fr.qp1c.ebdj.model.TypePartie;
import fr.qp1c.ebdj.moteur.bean.historique.HistoriqueQuestion9PG;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.TypePhase;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.view.Seuil;
import fr.qp1c.ebdj.view.Style;
import fr.qp1c.ebdj.view.TaillePolice;
import fr.qp1c.ebdj.view.component.Historique9PGListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

	private MoteurNPG moteur9PG;

	private boolean affichageHistoriqueEnCours = false;

	private int numQuestionAffiche = 0;

	@FXML
	private void initialize() {
		reinitialiser();
	}

	public void reinitialiser() {
		LOGGER.debug("[DEBUT] Initialisation du panneau 9PG.");

		moteur9PG = new MoteurNPG();

		listeHistorique9PG.clear();

		numQuestionAffiche = 0;

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

		modifierTaille(TaillePolice.GRAND);

		nbQuestion.setStyle(Style.FOND_NORMAL);

		LOGGER.debug("[FIN] Initialisation du panneau 9PG.");
	}

	// Gestion des évenements

	@FXML
	public void jouerNouvelleQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Nouvelle question 9PG\".");

		changerQuestion(true, true);
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

			carton9PG.setStyle(Style.FOND_CARTON);

			afficherCarton9PG(moteur9PG.getDerniereQuestion9PG(), moteur9PG.getNiveau());

			numQuestionAffiche = moteur9PG.getNbQuestReel();
		}
	}

	@FXML
	public void changerNiveau123() {
		LOGGER.info("### --> Clic sur \"Niveau 1-2-3\".");

		btn123.setSelected(true);
		btn123.setDisable(false);
		btn23.setDisable(false);
		btn3.setDisable(false);

		moteur9PG.changerNiveau123();

		changerQuestion(true, true);

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle(Style.FOND_CARTON);
		}

		// modifierTaille(TaillePolice.MOYEN);
	}

	@FXML
	public void changerNiveau23() {
		LOGGER.info("### --> Clic sur \"Niveau 2-3\".");

		btn23.setSelected(true);
		btn123.setDisable(true);

		moteur9PG.changerNiveau23();
		changerQuestion(true, false);

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle(Style.FOND_CARTON);
		}

		// modifierTaille(TaillePolice.GRAND);
	}

	@FXML
	public void changerNiveau3() {
		LOGGER.info("### --> Clic sur \"Niveau 3\".");

		btn3.setSelected(true);
		btn23.setDisable(true);

		moteur9PG.changerNiveau3();
		changerQuestion(true, false);

		if (affichageHistoriqueEnCours) {

			affichageHistoriqueEnCours = false;

			btnReprendre9PG.setDisable(true);
			btnReprendre9PG.setVisible(false);

			btnNouvelleQuestion9PG.setDisable(false);
			btnNouvelleQuestion9PG.setVisible(true);

			btnRemplacerQuestion9PG.setDisable(false);

			carton9PG.setStyle(Style.FOND_CARTON);
		}

		// modifierTaille(TaillePolice.PETIT);
	}

	@FXML
	public void signalerErreurQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Signaler une erreur sur la question de 9PG\".");

		SignalementAnomalie signalementAnomalie = PopUpAnomalieQuestion.afficherPopUp(TypePartie.NPG);

		if (signalementAnomalie != null) {

			moteur9PG.signalerAnomalie(signalementAnomalie);

			LOGGER.debug("Num question affiche            : " + numQuestionAffiche);

			listeHistorique9PG.get(listeHistorique9PG.size() - numQuestionAffiche).setNonComptabilise(true);
			histoQuestion.refresh();
		}

	}

	@FXML
	public void remplacerQuestion9PG() {
		LOGGER.info("### --> Clic sur \"Remplacer la question de 9PG\".");

		changerQuestion(false, false);

		listeHistorique9PG.get((moteur9PG.getNbQuestReel() - listeHistorique9PG.size()) + 1).setNonComptabilise(true);
	}

	// Méthodes d'affichage

	private void changerQuestion(boolean questionACompter, boolean calculerNiveau) {
		LOGGER.debug("[DEBUT] Changer de question.");

		QuestionNPG nouvelleQuestion = null;

		if (calculerNiveau) {
			nouvelleQuestion = moteur9PG.changerQuestionAvecNiveau(questionACompter);
		} else {
			nouvelleQuestion = moteur9PG.changerQuestion(questionACompter);
		}

		numQuestionAffiche = moteur9PG.getNbQuestReel();

		// Historiser la nouvelle question
		historiserQuestion9PG(nouvelleQuestion);

		// Mise à jour de l'affichage
		afficherCarton9PG(nouvelleQuestion, moteur9PG.getNiveau());
		afficherNbQuestion();

		LOGGER.debug("[FIN] Changer de question.");
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

		// Modification de la couleur de fond du carton du 9PG.
		carton9PG.setStyle(Style.FOND_CARTON_HISTORIQUE);

		numQuestionAffiche = question.getNbQuestionReel();

		LOGGER.debug("[FIN] Affichage question depuis l'historique.");
	}

	private void afficherNbQuestion() {
		LOGGER.debug("[DEBUT] Affichage du nombre de question.");

		if (moteur9PG.getNbQuest() == 1) {
			nbQuestion.setText(moteur9PG.getNbQuest() + " question jouée");
		} else {
			if (moteur9PG.getNbQuest() >= Seuil.SEUIL_WARNING_9PG) {
				nbQuestion.setStyle(Style.FOND_WARNING);
			}
			nbQuestion.setText(moteur9PG.getNbQuest() + " questions jouées");
		}
		LOGGER.debug("[FIN] Affichage du nombre de question.");
	}

	private void afficherCarton9PG(QuestionNPG questionNPG, int niveau) {
		LOGGER.debug("[DEBUT] Affichage carton 9PG.");

		afficherNiveauQuestion(niveau);

		question9PG.setText(questionNPG.getQuestion());
		reponse9PG.setText(questionNPG.getReponse().toUpperCase());
		reponse9PG.setTextAlignment(TextAlignment.CENTER);
		// TODO formatter la référence de la question
		question9PGInfos.setText(
				Utils.formaterReference(questionNPG.getReference(), TypePhase.NPG) + " - " + questionNPG.getSource());

		System.out.println("--> " + questionNPG.getSource());

		LOGGER.debug("[FIN] Affichage carton 9PG.");
	}

	private void afficherNiveauQuestion(int niveau) {
		LOGGER.debug("[DEBUT] Affichage niveau question : {}", niveau);

		if (niveau == 1) {
			niveau1.setVisible(false);
			// niveau2.setFitWidth(0.0);
			niveau2.setVisible(true);
			niveau3.setVisible(false);
			niveau3.setFitWidth(0.0);
		} else if (niveau == 2) {
			niveau1.setVisible(true);
			niveau2.setVisible(true);
			niveau2.setFitWidth(40.0);
			niveau3.setVisible(false);
			niveau3.setFitWidth(0.0);
		} else if (niveau == 3) {
			niveau1.setVisible(true);
			niveau2.setVisible(true);
			niveau2.setFitWidth(40.0);
			niveau3.setVisible(true);
			niveau3.setFitWidth(40.0);
		}

		LOGGER.debug("[FIN] Affichage niveau question.");
	}

	// Méthodes métier

	private void historiserQuestion9PG(QuestionNPG question9PG) {
		LOGGER.debug("[DEBUT] Historisation de la question 9PG.");

		if (question9PG != null) {

			HistoriqueQuestion9PG histo = new HistoriqueQuestion9PG();
			histo.setNbQuestion(moteur9PG.getNbQuest());
			histo.setNbQuestionReel(moteur9PG.getNbQuestReel());
			histo.setNiveau(moteur9PG.getNiveau());
			histo.setQuestion(question9PG);

			listeHistorique9PG.add(0, histo);
		}

		LOGGER.debug("[FIN] Historisation de la question 9PG.");
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

	public void definirNiveauPartie(NiveauPartie niveauPartie) {
		moteur9PG.definirNiveauPartie(niveauPartie);
	}

	public void definirLecteur(Lecteur lecteur) {
		moteur9PG.definirLecteur(lecteur);
	}
}
