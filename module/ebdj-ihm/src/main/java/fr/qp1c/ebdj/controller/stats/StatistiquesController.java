package fr.qp1c.ebdj.controller.stats;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.model.stats.StockCategorieFAF;
import fr.qp1c.ebdj.model.stats.StockQuantite;
import fr.qp1c.ebdj.moteur.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.moteur.bean.stats.StatsCategorieFAF;
import fr.qp1c.ebdj.moteur.service.StatistiqueService;
import fr.qp1c.ebdj.moteur.service.Synchronisation4ALSService;
import fr.qp1c.ebdj.moteur.service.Synchronisation9PGService;
import fr.qp1c.ebdj.moteur.service.SynchronisationFAFService;
import fr.qp1c.ebdj.moteur.service.SynchronisationJDService;
import fr.qp1c.ebdj.moteur.service.impl.StatistiqueServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.Synchronisation4ALSServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.Synchronisation9PGServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationFAFServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationJDServiceImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Pair;

public class StatistiquesController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesController.class);

	@FXML
	private Button btnHome;

	@FXML
	private Accordion stockAccordion;

	@FXML
	private VBox panneauStats;

	@FXML
	private TitledPane stockSynthese;

	@FXML
	private TitledPane stockFAF;

	@FXML
	private TitledPane stock4ALS;

	@FXML
	private TableView<StockQuantite> tableauStockSynthese;

	@FXML
	private TableView<StockCategorieFAF> tableauStockFAF;

	@FXML
	private TableColumn<StockQuantite, String> tabSGColType;

	@FXML
	private TableColumn<StockQuantite, String> tabSGColDateSynchro;

	@FXML
	private TableColumn<StockQuantite, Long> tabSGColQuantiteTotale;

	@FXML
	private TableColumn<StockQuantite, Long> tabSGColQuantiteDisponible;

	@FXML
	private TableColumn<StockQuantite, Long> tabSGColQuantiteJouee;

	@FXML
	private TableColumn<StockCategorieFAF, String> tabSFColCategorie;

	@FXML
	private TableColumn<StockCategorieFAF, String> tabSFColDateSynchro;

	@FXML
	private TableColumn<StockCategorieFAF, Long> tabSFColQuantiteTotale;

	@FXML
	private TableColumn<StockCategorieFAF, Long> tabSFColQuantiteDisponible;

	@FXML
	private TableColumn<StockCategorieFAF, Long> tabSFColQuantiteJouee;

	private ObservableList<StockQuantite> donneesTableauSG = FXCollections.observableArrayList();

	private ObservableList<StockCategorieFAF> donneesTableauSF = FXCollections.observableArrayList();

	@FXML
	private Button btnSynchroniser;

	private Launcher launcher;

	private Label libelleBarreProgressionPhase;

	private Label libelleBarreProgressionSousPhase;

	private ProgressBar barreProgressionPhase;

	private ProgressBar barreProgressionSousPhase;

	private final Comparator<StockCategorieFAF> CATEGORIE_FAF_COMPARATOR = (o1, o2) -> o1.getCategorie()
			.compareTo(o2.getCategorie());
	private final ObjectProperty<Comparator<? super StockCategorieFAF>> CATEGORIE_FAF_COMPARATOR_WRAPPER = new SimpleObjectProperty<>(
			CATEGORIE_FAF_COMPARATOR);

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau stats.");

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

		// Sélection de l'onglet Stock synthèse

		stockAccordion.setExpandedPane(stockSynthese);

		// Panneau synthese

		stockSynthese.setExpanded(true);
		stockSynthese.setPrefHeight(200);

		StatistiqueService statistiqueService = new StatistiqueServiceImpl();
		StatsBDJ statsBDJ = statistiqueService.calculerStatistique();

		donneesTableauSG.add(new StockQuantite("9PG - 1", statsBDJ.getStats9PG_1()));
		donneesTableauSG.add(new StockQuantite("9PG - 2", statsBDJ.getStats9PG_2()));
		donneesTableauSG.add(new StockQuantite("9PG - 3", statsBDJ.getStats9PG_3()));
		donneesTableauSG.add(new StockQuantite("4ALS", statsBDJ.getStats4ALS()));
		donneesTableauSG.add(new StockQuantite("JD", statsBDJ.getStatsJD()));
		donneesTableauSG.add(new StockQuantite("FAF", statsBDJ.getStatsFAF()));

		tabSGColType.setCellValueFactory(new PropertyValueFactory<StockQuantite, String>("type"));
		tabSGColQuantiteTotale.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteTotale"));
		tabSGColQuantiteDisponible
				.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteDisponible"));
		tabSGColQuantiteJouee.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteJouee"));

		tableauStockSynthese.setEditable(false);
		tableauStockSynthese.setItems(donneesTableauSG);

		// Panneau FAF

		stockFAF.setExpanded(false);

		List<StatsCategorieFAF> statsCategorieFAF = statistiqueService.calculerStatsCategorieFAF();

		for (StatsCategorieFAF statCategorieFAF : statsCategorieFAF) {
			donneesTableauSF.add(new StockCategorieFAF(statCategorieFAF));
		}

		tabSFColCategorie.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, String>("categorie"));
		tabSFColQuantiteTotale.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteTotale"));
		tabSFColQuantiteDisponible
				.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteDisponible"));
		tabSFColQuantiteJouee.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteJouee"));

		tableauStockFAF.setEditable(false);

		SortedList<StockCategorieFAF> sortedItems = new SortedList<>(donneesTableauSF);

		sortedItems.comparatorProperty().bind(CATEGORIE_FAF_COMPARATOR_WRAPPER);
		tableauStockFAF.setItems(sortedItems);

		LOGGER.debug("[FIN] Initialisation du panneau stats.");
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		launcher.afficherEcranHome();
	}

	protected void updateLibelleBarreProgressionPhase(String message) {
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

	protected void updateLibelleBarreProgressionSousPhase(String message) {
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

	protected void updateProgress(String message) {
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

	@FXML
	public void synchroniser() {
		LOGGER.info("### --> Clic sur \"Synchroniser\".");

		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("QP1C - E-Boite de jeu");
		dialog.setHeaderText("Synchronisation de la boite de jeu");
		dialog.initOwner(Launcher.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setHeight(200);
		dialog.setWidth(500);

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new
		// ImageView(this.getClass().getResource("login.png").toString()));

		libelleBarreProgressionPhase = new Label();

		libelleBarreProgressionSousPhase = new Label();

		barreProgressionPhase = new ProgressBar();

		barreProgressionSousPhase = new ProgressBar();

		// Set the button types.

		ButtonType btnCloseType = new ButtonType("Fermer", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(btnCloseType);
		dialog.getDialogPane().lookupButton(btnCloseType).setStyle("boutonFermer");

		System.out.println("Synchroniser !");

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

				synchronisation4alsService.synchroniserQuestions4ALS();
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
			}
		}.start();

	}

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
