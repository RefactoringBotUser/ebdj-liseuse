package fr.qp1c.ebdj.controller.stats;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.Launcher;
import fr.qp1c.ebdj.moteur.service.Synchronisation4ALSService;
import fr.qp1c.ebdj.moteur.service.Synchronisation9PGService;
import fr.qp1c.ebdj.moteur.service.SynchronisationFAFService;
import fr.qp1c.ebdj.moteur.service.SynchronisationJDService;
import fr.qp1c.ebdj.moteur.service.impl.Synchronisation4ALSServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.Synchronisation9PGServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationFAFServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationJDServiceImpl;
import fr.qp1c.ebdj.utils.ImageConstants;
import fr.qp1c.ebdj.utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class StatistiquesController {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatistiquesController.class);

	@FXML
	private Button btnHome;

	@FXML
	private Button btnSynchroniser;

	@FXML
	private Label libelleBarreProgressionPhase;

	@FXML
	private Label libelleBarreProgressionSousPhase;

	@FXML
	private ProgressBar barreProgressionPhase;

	@FXML
	private ProgressBar barreProgressionSousPhase;

	private Launcher launcher;

	@FXML
	private void initialize() {
		LOGGER.debug("[DEBUT] Initialisation du panneau stats.");

		ImageView imageHome = new ImageView(ImageConstants.IMAGE_HOME);
		ImageUtils.reduireImageCustom(imageHome, 25);

		btnHome.setGraphic(imageHome);

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

		new Thread() {
			@Override
			public void run() {

				// Synchronisation des 9PGS

				libelleBarreProgressionPhase.setText("Synchronisation des questions de 9PG");
				libelleBarreProgressionSousPhase.setText("Transfert des anomalies.");

				barreProgressionPhase.setProgress(0.0);
				barreProgressionSousPhase.setProgress(0.0);

				Synchronisation9PGService synchronisation9pgService = new Synchronisation9PGServiceImpl();
				synchronisation9pgService.synchroniserAnomalies9PG();
				barreProgressionSousPhase.setProgress(0.1);
				libelleBarreProgressionSousPhase.setText("Transfert des lectures.");

				synchronisation9pgService.synchroniserLectures9PG();
				barreProgressionSousPhase.setProgress(0.4);
				libelleBarreProgressionSousPhase.setText("Téléchargement des questions.");

				synchronisation9pgService.synchroniserQuestions9PG();
				barreProgressionSousPhase.setProgress(0.9);
				libelleBarreProgressionSousPhase.setText("Téléchargement des corrections.");

				synchronisation9pgService.synchroniserCorrections9PG();
				barreProgressionSousPhase.setProgress(1.0);
				libelleBarreProgressionPhase.setText("Synchronisation de la phase de 9PG terminée.");
				libelleBarreProgressionSousPhase.setText("");

				// Synchronisation des 4ALS

				barreProgressionPhase.setProgress(0.25);
				barreProgressionSousPhase.setProgress(0.0);

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Synchronisation4ALSService synchronisation4alsService = new Synchronisation4ALSServiceImpl();
				synchronisation4alsService.synchroniserAnomalies4ALS();
				barreProgressionSousPhase.setProgress(0.1);
				libelleBarreProgressionSousPhase.setText("Transfert des lectures.");

				synchronisation4alsService.synchroniserLectures4ALS();
				barreProgressionSousPhase.setProgress(0.4);
				libelleBarreProgressionSousPhase.setText("Téléchargement des questions.");

				synchronisation4alsService.synchroniserQuestions4ALS();
				barreProgressionSousPhase.setProgress(0.9);
				libelleBarreProgressionSousPhase.setText("Téléchargement des corrections.");

				synchronisation4alsService.synchroniserCorrections4ALS();
				barreProgressionSousPhase.setProgress(1.0);

				// Synchronisation des JDS

				barreProgressionPhase.setProgress(0.50);
				barreProgressionSousPhase.setProgress(0.0);

				SynchronisationJDService synchronisationJdService = new SynchronisationJDServiceImpl();
				synchronisationJdService.synchroniserAnomaliesJD();
				barreProgressionSousPhase.setProgress(0.1);
				libelleBarreProgressionSousPhase.setText("Transfert des lectures.");

				synchronisationJdService.synchroniserLecturesJD();
				barreProgressionSousPhase.setProgress(0.4);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				libelleBarreProgressionSousPhase.setText("Téléchargement des questions.");

				synchronisationJdService.synchroniserQuestionsJD();
				barreProgressionSousPhase.setProgress(0.9);
				libelleBarreProgressionSousPhase.textProperty().set("Téléchargement des corrections.");

				synchronisationJdService.synchroniserCorrectionsJD();
				barreProgressionSousPhase.setProgress(1.0);

				// Synchronisation des FAFS

				barreProgressionPhase.setProgress(0.75);
				barreProgressionSousPhase.setProgress(0.0);

				SynchronisationFAFService synchronisationFafService = new SynchronisationFAFServiceImpl();
				synchronisationFafService.synchroniserAnomaliesFAF();
				barreProgressionSousPhase.setProgress(0.1);
				libelleBarreProgressionSousPhase.setText("Transfert des lectures.");

				synchronisationFafService.synchroniserLecturesFAF();
				barreProgressionSousPhase.setProgress(0.4);
				libelleBarreProgressionSousPhase.setText("Téléchargement des questions.");

				synchronisationFafService.synchroniserQuestionsFAF();
				barreProgressionSousPhase.setProgress(0.9);
				libelleBarreProgressionSousPhase.setText("Téléchargement des corrections.");

				synchronisationFafService.synchroniserCorrectionsFAF();
				barreProgressionSousPhase.setProgress(1.0);

				libelleBarreProgressionPhase.setText("Synchronisation terminée.");
				libelleBarreProgressionSousPhase.setText("");

				barreProgressionPhase.setProgress(1.0);
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
