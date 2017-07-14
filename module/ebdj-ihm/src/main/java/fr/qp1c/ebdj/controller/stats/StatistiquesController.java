package fr.qp1c.ebdj.controller.stats;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.controller.popup.PopUpSynchronisationQuestion;
import fr.qp1c.ebdj.model.stats.StockCategorieFAF;
import fr.qp1c.ebdj.model.stats.StockQuantite;
import fr.qp1c.ebdj.moteur.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.moteur.bean.stats.StatsCategorieFAF;
import fr.qp1c.ebdj.moteur.service.StatistiqueService;
import fr.qp1c.ebdj.moteur.service.impl.StatistiqueServiceImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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

	@FXML
	public void synchroniser() {
		LOGGER.info("### --> Clic sur \"Synchroniser\".");

		PopUpSynchronisationQuestion.afficherPopUp();
	}

	public Launcher getLauncher() {
		return launcher;
	}

	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
