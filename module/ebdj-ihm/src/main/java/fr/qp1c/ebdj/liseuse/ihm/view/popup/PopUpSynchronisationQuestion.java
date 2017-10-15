package fr.qp1c.ebdj.liseuse.ihm.view.popup;

import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.Libelle;
import fr.qp1c.ebdj.liseuse.synchronisation.service.Synchronisation4ALSService;
import fr.qp1c.ebdj.liseuse.synchronisation.service.Synchronisation9PGService;
import fr.qp1c.ebdj.liseuse.synchronisation.service.SynchronisationFAFService;
import fr.qp1c.ebdj.liseuse.synchronisation.service.SynchronisationJDService;
import fr.qp1c.ebdj.liseuse.synchronisation.service.impl.Synchronisation4ALSServiceImpl;
import fr.qp1c.ebdj.liseuse.synchronisation.service.impl.Synchronisation9PGServiceImpl;
import fr.qp1c.ebdj.liseuse.synchronisation.service.impl.SynchronisationFAFServiceImpl;
import fr.qp1c.ebdj.liseuse.synchronisation.service.impl.SynchronisationJDServiceImpl;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Pair;

public class PopUpSynchronisationQuestion {

	private static Label libelleBarreProgressionPhase;

	private static Label libelleBarreProgressionSousPhase;

	private static ProgressBar barreProgressionPhase;

	private static ProgressBar barreProgressionSousPhase;

	private static boolean status = true;

	public PopUpSynchronisationQuestion() {

	}

	public static boolean afficherPopUp() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle(Libelle.TITRE);
		dialog.setHeaderText("Synchronisation de la boite de jeu");
		dialog.initOwner(Launcher.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setHeight(200);
		dialog.setWidth(500);

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add("/css/styles.css");
		dialogPane.getStyleClass().add("popUp");

		libelleBarreProgressionPhase = new Label();

		libelleBarreProgressionSousPhase = new Label();

		barreProgressionPhase = new ProgressBar();

		barreProgressionSousPhase = new ProgressBar();

		// Set the button types.

		ButtonType btnCloseType = new ButtonType("Fermer", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(btnCloseType);
		dialog.getDialogPane().lookupButton(btnCloseType).setStyle("boutonFermer");

		libelleBarreProgressionPhase.setText("Synchronisation non démarrée.");
		libelleBarreProgressionSousPhase.setText("");

		barreProgressionPhase.setProgress(0);
		barreProgressionSousPhase.setProgress(0);

		VBox box = new VBox();
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(libelleBarreProgressionPhase);
		box.getChildren().add(barreProgressionPhase);
		box.getChildren().add(libelleBarreProgressionSousPhase);
		box.getChildren().add(barreProgressionSousPhase);

		dialog.getDialogPane().setContent(box);
		dialog.show();

		new Thread() {
			@Override
			public void run() {

				dialog.getDialogPane().lookupButton(btnCloseType).disableProperty().setValue(true);

				// Synchronisation des 9PGS
				try {
					updateLibelleBarreProgressionPhase("Synchronisation des questions de 9PG");
					updateLibelleBarreProgressionSousPhase("Transfert des anomalies.");

					barreProgressionPhase.setProgress(0.0);
					barreProgressionSousPhase.setProgress(0.0);

					Synchronisation9PGService synchronisation9pgService = new Synchronisation9PGServiceImpl();
					synchronisation9pgService.synchroniserAnomalies9PG();
					barreProgressionSousPhase.setProgress(0.1);
					updateLibelleBarreProgressionSousPhase("Transfert des lectures.");

					synchronisation9pgService.synchroniserLectures9PG();
					barreProgressionSousPhase.setProgress(0.4);
					updateLibelleBarreProgressionSousPhase("Téléchargement des questions.");

					synchronisation9pgService.synchroniserQuestions9PG();
					barreProgressionSousPhase.setProgress(0.9);
					updateLibelleBarreProgressionSousPhase("Téléchargement des corrections.");

					synchronisation9pgService.synchroniserCorrections9PG();
					barreProgressionSousPhase.setProgress(1.0);
					updateLibelleBarreProgressionPhase("Synchronisation de la phase de 9PG terminée.");
					updateLibelleBarreProgressionSousPhase("");

					// Synchronisation des 4ALS

					barreProgressionPhase.setProgress(0.25);
					barreProgressionSousPhase.setProgress(0.0);

					Synchronisation4ALSService synchronisation4alsService = new Synchronisation4ALSServiceImpl();
					synchronisation4alsService.synchroniserAnomalies4ALS();
					barreProgressionSousPhase.setProgress(0.1);
					updateLibelleBarreProgressionSousPhase("Transfert des lectures.");

					synchronisation4alsService.synchroniserLectures4ALS();
					barreProgressionSousPhase.setProgress(0.4);
					updateLibelleBarreProgressionSousPhase("Téléchargement des questions.");

					synchronisation4alsService.synchroniserThemes4ALS();
					barreProgressionSousPhase.setProgress(0.9);
					updateLibelleBarreProgressionSousPhase("Téléchargement des corrections.");

					synchronisation4alsService.synchroniserCorrections4ALS();
					barreProgressionSousPhase.setProgress(1.0);

					// Synchronisation des JDS

					barreProgressionPhase.setProgress(0.50);
					barreProgressionSousPhase.setProgress(0.0);

					SynchronisationJDService synchronisationJdService = new SynchronisationJDServiceImpl();
					synchronisationJdService.synchroniserAnomaliesJD();
					barreProgressionSousPhase.setProgress(0.1);
					updateLibelleBarreProgressionSousPhase("Transfert des lectures.");

					synchronisationJdService.synchroniserLecturesJD();
					barreProgressionSousPhase.setProgress(0.4);
					updateLibelleBarreProgressionSousPhase("Téléchargement des questions.");

					synchronisationJdService.synchroniserQuestionsJD();
					barreProgressionSousPhase.setProgress(0.9);
					updateLibelleBarreProgressionSousPhase("Téléchargement des corrections.");

					synchronisationJdService.synchroniserCorrectionsJD();
					barreProgressionSousPhase.setProgress(1.0);

					// Synchronisation des FAFS

					barreProgressionPhase.setProgress(0.75);
					barreProgressionSousPhase.setProgress(0.0);

					SynchronisationFAFService synchronisationFafService = new SynchronisationFAFServiceImpl();
					synchronisationFafService.synchroniserAnomaliesFAF();
					barreProgressionSousPhase.setProgress(0.1);
					updateLibelleBarreProgressionSousPhase("Transfert des lectures.");

					synchronisationFafService.synchroniserLecturesFAF();
					barreProgressionSousPhase.setProgress(0.4);
					updateLibelleBarreProgressionSousPhase("Téléchargement des questions.");

					synchronisationFafService.synchroniserQuestionsFAF();
					barreProgressionSousPhase.setProgress(0.9);
					updateLibelleBarreProgressionSousPhase("Téléchargement des corrections.");

					synchronisationFafService.synchroniserCorrectionsFAF();
					barreProgressionSousPhase.setProgress(1.0);

					updateLibelleBarreProgressionPhase("Synchronisation terminée.");
					updateLibelleBarreProgressionSousPhase("");
					barreProgressionSousPhase.visibleProperty().set(false);

					barreProgressionPhase.setProgress(1.0);

					dialog.getDialogPane().lookupButton(btnCloseType).disableProperty().setValue(false);
				} catch (BdjException e) {
					Runnable command = new Runnable() {
						@Override
						public void run() {
							PopUpErreur.afficherPopUp(e);
							status = false;

							updateLibelleBarreProgressionPhase("Echec de la synchronisation.");
							updateLibelleBarreProgressionSousPhase("");
							barreProgressionSousPhase.visibleProperty().set(false);

							barreProgressionPhase.setProgress(1.0);

							dialog.getDialogPane().lookupButton(btnCloseType).disableProperty().setValue(false);
						}
					};
					if (Platform.isFxApplicationThread()) {
						// Nous sommes déjà dans le thread graphique
						command.run();
					} else {
						// Nous ne sommes pas dans le thread graphique
						// on utilise runLater.
						Platform.runLater(command);
					}
				}
			}
		}.start();

		return status;
	}

	protected static void updateLibelleBarreProgressionSousPhase(String message) {
		Runnable command = new Runnable() {
			@Override
			public void run() {
				// Le label étant le label JavaFX dont tu
				// souhaites modifier le texte.
				libelleBarreProgressionSousPhase.setText(message);
			}
		};
		if (Platform.isFxApplicationThread()) {
			// Nous sommes déjà dans le thread graphique
			command.run();
		} else {
			// Nous ne sommes pas dans le thread graphique
			// on utilise runLater.
			Platform.runLater(command);
		}

	}

	protected static void updateProgress(String message) {
		Runnable command = new Runnable() {
			@Override
			public void run() {
				// Le label étant le label JavaFX dont tu
				// souhaites modifier le texte.
				// libelleBarreProgressionPhase.setText(message);
			}
		};
		if (Platform.isFxApplicationThread()) {
			// Nous sommes déjà dans le thread graphique
			command.run();
		} else {
			// Nous ne sommes pas dans le thread graphique
			// on utilise runLater.
			Platform.runLater(command);
		}

	}

	protected static void updateLibelleBarreProgressionPhase(String message) {
		Runnable command = new Runnable() {
			@Override
			public void run() {
				// Le label étant le label JavaFX dont tu
				// souhaites modifier le texte.
				libelleBarreProgressionPhase.setText(message);
			}
		};
		if (Platform.isFxApplicationThread()) {
			// Nous sommes déjà dans le thread graphique
			command.run();
		} else {
			// Nous ne sommes pas dans le thread graphique
			// on utilise runLater.
			Platform.runLater(command);
		}
	}

}
