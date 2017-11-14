package fr.qp1c.ebdj.liseuse.commun.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static Configuration configuration;

    private String url;

    private String user;

    private String password;

    private String database;

    private boolean test = false;

    private String cockpitUrl;

    private String cockpitBdjNom;

    private String cockpitBdjCle;

    private Configuration() {
        LOGGER.debug("Démarrage de l'initialisation de la configuration.");

        Properties prop = new Properties();

        File f = new File(".");

        // load a properties file/
        try {

            System.out.println("Répertoire : " + f.getCanonicalPath().toString());
            // System.out.println("Ressource : " +
            // getClass().getClassLoader().getResource("config.properties").getPath());

            String fichierConfig = f.getCanonicalPath().toString() + "/config/config.properties";
            LOGGER.debug("URL du fichier de configuration={}", fichierConfig);

            // prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            prop.load(new FileInputStream(fichierConfig));
        } catch (IOException e) {
            LOGGER.error("L'initialisation a échouée : ", e);
        }

        database = prop.getProperty("db-nom");

        if (database.contains("TEST")) {
            test = true;
            url = "jdbc:h2:mem:test";
        } else {
            url = "jdbc:sqlite:" + f.getAbsolutePath() + "/db/" + database + ".sqlite";
        }

        user = prop.getProperty("db-user");
        password = prop.getProperty("db-password");

        cockpitUrl = prop.getProperty("cockpit-url");
        cockpitBdjNom = prop.getProperty("cockpit-bdj-nom");
        cockpitBdjCle = prop.getProperty("cockpit-bdj-cle");

        LOGGER.debug(url);

        LOGGER.debug("Fin de l'initialisation de la configuration.");
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public boolean isTest() {
        return test;
    }

    public String getCockpitUrl() {
        return cockpitUrl;
    }

    public String getCockpitBdjNom() {
        return cockpitBdjNom;
    }

    public String getCockpitBdjCle() {
        return cockpitBdjCle;
    }

}
