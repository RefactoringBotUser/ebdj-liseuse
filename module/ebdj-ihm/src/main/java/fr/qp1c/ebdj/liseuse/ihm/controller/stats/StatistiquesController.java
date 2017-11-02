package fr.qp1c.ebdj.liseuse.ihm.controller.stats;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsCategorieFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsGroupeCategorieQALS;
import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.model.stats.StockCategorieFAF;
import fr.qp1c.ebdj.liseuse.ihm.model.stats.StockGroupeCategorieQALS;
import fr.qp1c.ebdj.liseuse.ihm.model.stats.StockQuantite;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.popup.PopUpSynchronisationQuestion;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.ImageUtils;
import fr.qp1c.ebdj.liseuse.stats.service.StatistiqueService;
import fr.qp1c.ebdj.liseuse.stats.service.impl.StatistiqueServiceImpl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class StatistiquesController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesController.class);

	// Composant(s) JavaFX

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
	private TitledPane stockQALS;

	@FXML
	private TableView<StockQuantite> tableauStockSynthese;

	@FXML
	private TableView<StockCategorieFAF> tableauStockFAF;

	@FXML
	private TableView<StockGroupeCategorieQALS> tableauStockQALS;

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

	// Colonne tabeau FAF

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

	// Colonne tableau QALS

	@FXML
	private TableColumn<StockGroupeCategorieQALS, String> tabSQColGroupeCategorie;

	@FXML
	private TableColumn<StockGroupeCategorieQALS, String> tabSQColQuantiteNiveau1;

	@FXML
	private TableColumn<StockGroupeCategorieQALS, String> tabSQColQuantiteNiveau2;

	@FXML
	private TableColumn<StockGroupeCategorieQALS, String> tabSQColQuantiteNiveau3;

	@FXML
	private TableColumn<StockGroupeCategorieQALS, String> tabSQColQuantiteNiveau4;

	@FXML
	private Button btnSynchroniser;

	// Autres attributs

	private Launcher launcher;

	private ObservableList<StockQuantite> donneesTableauSG = FXCollections.observableArrayList();

	private ObservableList<StockCategorieFAF> donneesTableauSF = FXCollections.observableArrayList();

	private ObservableList<StockGroupeCategorieQALS> donneesTableauSQ = FXCollections.observableArrayList();

	private final Comparator<StockCategorieFAF> CATEGORIE_FAF_COMPARATOR = (o1, o2) -> o1.getCategorie()
			.compareTo(o2.getCategorie());

	private final Comparator<StockGroupeCategorieQALS> GROUPE_CATEGORIE_QALS_COMPARATOR = (o1, o2) -> o1
			.getGroupeCategorie().compareTo(o2.getGroupeCategorie());

	private final ObjectProperty<Comparator<? super StockCategorieFAF>> CATEGORIE_FAF_COMPARATOR_WRAPPER = new SimpleObjectProperty<>(
			CATEGORIE_FAF_COMPARATOR);

	private final ObjectProperty<Comparator<? super StockGroupeCategorieQALS>> GROUPE_CATEGORIE_QALS_COMPARATOR_WRAPPER = new SimpleObjectProperty<>(
			GROUPE_CATEGORIE_QALS_COMPARATOR);

	@FXML
	private void initialize() {
		LOGGER.info("[DEBUT] Initialisation du panneau stats.");

		btnHome.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_HOME, 25));

		// Sélection de l'onglet Stock synthèse

		stockAccordion.setExpandedPane(stockSynthese);

		// Panneau synthese

		stockSynthese.setExpanded(true);
		stockSynthese.setPrefHeight(200);

		tabSGColType.setCellValueFactory(new PropertyValueFactory<StockQuantite, String>("type"));
		tabSGColQuantiteTotale.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteTotale"));
		tabSGColQuantiteDisponible
				.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteDisponible"));
		tabSGColQuantiteJouee.setCellValueFactory(new PropertyValueFactory<StockQuantite, Long>("quantiteJouee"));

		tableauStockSynthese.setEditable(false);

		// Panneau 4ALS

		stockQALS.setExpanded(false);

		tabSQColGroupeCategorie
				.setCellValueFactory(new PropertyValueFactory<StockGroupeCategorieQALS, String>("groupeCategorie"));
		tabSQColQuantiteNiveau4
				.setCellValueFactory(new PropertyValueFactory<StockGroupeCategorieQALS, String>("niveau4"));
		tabSQColQuantiteNiveau3
				.setCellValueFactory(new PropertyValueFactory<StockGroupeCategorieQALS, String>("niveau3"));
		tabSQColQuantiteNiveau2
				.setCellValueFactory(new PropertyValueFactory<StockGroupeCategorieQALS, String>("niveau2"));
		tabSQColQuantiteNiveau1
				.setCellValueFactory(new PropertyValueFactory<StockGroupeCategorieQALS, String>("niveau1"));

		tableauStockQALS.setEditable(false);

		// Panneau FAF

		stockFAF.setExpanded(false);

		tabSFColCategorie.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, String>("categorie"));
		tabSFColQuantiteTotale.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteTotale"));
		tabSFColQuantiteDisponible
				.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteDisponible"));
		tabSFColQuantiteJouee.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteJouee"));

		tableauStockFAF.setEditable(false);

		actualiserContenuTableaux();

		// TODO : Archiver les questions par partie

		LOGGER.info("[FIN] Initialisation du panneau stats.");
	}

	public void actualiserContenuTableaux() {
		LOGGER.info("[DEBUT] Réactualisation des données.");

		// Refresh des tableaux
		actualiserContenuTableauStockSynthese();
		actualiserContenuTableauStockQALS();
		actualiserContenuTableauStockFAF();

		LOGGER.info("[FIN] Réactualisation des données.");
	}

	private void actualiserContenuTableauStockSynthese() {
		LOGGER.info("[DEBUT] Réactualisation du contenu du tableau stock synthese.");

		StatistiqueService statistiqueService = new StatistiqueServiceImpl();

		// Réactualisation tableau stock

		StatsBDJ statsBDJ = statistiqueService.calculerStatistique();

		donneesTableauSG.clear();

		donneesTableauSG.add(new StockQuantite("9PG - 1", statsBDJ.getStats9PG1()));
		donneesTableauSG.add(new StockQuantite("9PG - 2", statsBDJ.getStats9PG2()));
		donneesTableauSG.add(new StockQuantite("9PG - 3", statsBDJ.getStats9PG3()));
		donneesTableauSG.add(new StockQuantite("4ALS", statsBDJ.getStats4ALS()));
		donneesTableauSG.add(new StockQuantite("JD", statsBDJ.getStatsJD()));
		donneesTableauSG.add(new StockQuantite("FAF", statsBDJ.getStatsFAF()));

		tableauStockSynthese.setItems(donneesTableauSG);

		tableauStockSynthese.refresh();

		LOGGER.info("[FIN] Réactualisation du contenu du tableau stock synthese.");
	}

	private void actualiserContenuTableauStockFAF() {
		LOGGER.info("[DEBUT] Réactualisation du contenu du tableau stock des FAF.");

		StatistiqueService statistiqueService = new StatistiqueServiceImpl();

		// Réactualisation tableau catégorie FAF
		List<StatsCategorieFAF> statsCategorieFAF = statistiqueService.calculerStatsCategorieFAF();

		donneesTableauSF.clear();

		for (StatsCategorieFAF statCategorieFAF : statsCategorieFAF) {
			donneesTableauSF.add(new StockCategorieFAF(statCategorieFAF));
		}

		SortedList<StockCategorieFAF> sortedItems = new SortedList<>(donneesTableauSF);

		sortedItems.comparatorProperty().bind(CATEGORIE_FAF_COMPARATOR_WRAPPER);
		tableauStockFAF.setItems(sortedItems);

		tableauStockFAF.refresh();

		LOGGER.info("[FIN] Réactualisation du contenu du tableau stock des FAF.");
	}

	private void actualiserContenuTableauStockQALS() {
		LOGGER.info("[DEBUT] Réactualisation du contenu du tableau stock des QALS.");

		StatistiqueService statistiqueService = new StatistiqueServiceImpl();

		// Réactualisation tableau groupe catégorie QALS
		List<StatsGroupeCategorieQALS> statsGroupeCategorieQALS = statistiqueService.calculerStatsGroupeCategorieQALS();

		donneesTableauSQ.clear();

		for (StatsGroupeCategorieQALS statGroupeCategorieQALS : statsGroupeCategorieQALS) {
			donneesTableauSQ.add(new StockGroupeCategorieQALS(statGroupeCategorieQALS));
		}

		SortedList<StockGroupeCategorieQALS> sortedItems = new SortedList<>(donneesTableauSQ);

		sortedItems.comparatorProperty().bind(GROUPE_CATEGORIE_QALS_COMPARATOR_WRAPPER);
		tableauStockQALS.setItems(sortedItems);

		tableauStockQALS.refresh();

		LOGGER.info("[FIN] Réactualisation du contenu du tableau stock des QALS.");
	}

	@FXML
	public void retournerEcranHome() {
		LOGGER.info("### --> Clic sur \"Ecran HOME\".");

		launcher.afficherEcranHome();
	}

	@FXML
	public void synchroniser() {
		LOGGER.info("### --> Clic sur \"Synchroniser\".");

		PopUpSynchronisationQuestion.afficherPopUp();

		actualiserContenuTableaux();
	}

	// Getters - setters

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
