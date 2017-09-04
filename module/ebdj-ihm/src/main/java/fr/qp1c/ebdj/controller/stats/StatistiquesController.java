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
import fr.qp1c.ebdj.moteur.service.impl.StatistiqueServiceImpl;
import fr.qp1c.ebdj.utils.config.ImageConstants;
import fr.qp1c.ebdj.view.popup.PopUpSynchronisationQuestion;
import fr.qp1c.ebdj.view.utils.ImageUtils;
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

	@FXML
	private Button btnSynchroniser;

	// Autres attributs

	private Launcher launcher;

	private ObservableList<StockQuantite> donneesTableauSG = FXCollections.observableArrayList();

	private ObservableList<StockCategorieFAF> donneesTableauSF = FXCollections.observableArrayList();

	private final Comparator<StockCategorieFAF> CATEGORIE_FAF_COMPARATOR = (o1, o2) -> o1.getCategorie()
			.compareTo(o2.getCategorie());

	private final ObjectProperty<Comparator<? super StockCategorieFAF>> CATEGORIE_FAF_COMPARATOR_WRAPPER = new SimpleObjectProperty<>(
			CATEGORIE_FAF_COMPARATOR);

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

		// TODO : Implementer la gestion du stock des 4ALS.
		stock4ALS.setVisible(false);

		// TODO : Archiver les questions par partie

		// Panneau FAF

		stockFAF.setExpanded(false);

		tabSFColCategorie.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, String>("categorie"));
		tabSFColQuantiteTotale.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteTotale"));
		tabSFColQuantiteDisponible
				.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteDisponible"));
		tabSFColQuantiteJouee.setCellValueFactory(new PropertyValueFactory<StockCategorieFAF, Long>("quantiteJouee"));

		tableauStockFAF.setEditable(false);

		actualiserContenuTableaux();

		LOGGER.info("[FIN] Initialisation du panneau stats.");
	}

	public void actualiserContenuTableaux() {
		LOGGER.info("[DEBUT] Réactualisation des données.");

		// Refresh des tableaux
		actualiserContenuTableauStockSynthese();
		actualiserContenuTableauStockFAF();

		LOGGER.info("[FIN] Réactualisation des données.");
	}

	private void actualiserContenuTableauStockSynthese() {
		LOGGER.info("[DEBUT] Réactualisation du contenu du tableau stock synthese.");

		StatistiqueService statistiqueService = new StatistiqueServiceImpl();

		// Réactualisation tableau stock

		StatsBDJ statsBDJ = statistiqueService.calculerStatistique();

		donneesTableauSG.clear();

		donneesTableauSG.add(new StockQuantite("9PG - 1", statsBDJ.getStats9PG_1()));
		donneesTableauSG.add(new StockQuantite("9PG - 2", statsBDJ.getStats9PG_2()));
		donneesTableauSG.add(new StockQuantite("9PG - 3", statsBDJ.getStats9PG_3()));
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
