package fr.qp1c.ebdj.liseuse.ihm.controller.parametrage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;
import fr.qp1c.ebdj.liseuse.bdd.service.impl.ParametrageServiceImpl;
import fr.qp1c.ebdj.liseuse.ihm.Launcher;
import fr.qp1c.ebdj.liseuse.ihm.utils.config.ImageConstants;
import fr.qp1c.ebdj.liseuse.ihm.view.utils.ImageUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ParametrageController {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrageController.class);

    // Composant(s) JavaFX

    @FXML
    private Button btnHome;

    @FXML
    private TextFlow configuration;

    @FXML
    private ScrollPane panneauConfiguration;

    private ParametrageService parametrageService;

    // Autres attributs

    private Launcher launcher;

    @FXML
    private void initialize() {
        LOGGER.info("[DEBUT] Initialisation du panneau paramétrage.");

        btnHome.setGraphic(ImageUtils.reduireImage(ImageConstants.IMAGE_HOME, 25));

        this.parametrageService = new ParametrageServiceImpl();

        System.out.println(parametrageService.afficherFichierParametrage());
        System.out.println(parametrageService.afficherVersionApplication());

        // TODO : Afficher le numéro de version de l'application

        ObservableList<Node> conf = configuration.getChildren();
        conf.add(new Text(parametrageService.afficherFichierParametrage()));
        conf.add(new Text("\n\nVersion : "+ parametrageService.afficherVersionApplication()));

        panneauConfiguration.setFitToHeight(true);
        panneauConfiguration.setFitToWidth(true);

        LOGGER.info("[FIN] Initialisation du panneau paramétrage.");
    }

    @FXML
    public void retournerEcranHome() {
        LOGGER.info("### --> Clic sur \"Ecran HOME\".");

        launcher.afficherEcranHome();
    }

    // Getters - setters

    public Launcher getLauncher() {
        return launcher;
    }

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }
}
