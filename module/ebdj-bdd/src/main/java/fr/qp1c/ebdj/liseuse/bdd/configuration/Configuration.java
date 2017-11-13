package fr.qp1c.ebdj.liseuse.bdd.configuration;

import java.io.File;
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

    private String urlCockpit;

    private String nomCockpit;

    private String cleCockpit;

    private Configuration() {
        LOGGER.debug("Init config.");

        File f = new File(".");

        Properties prop = new Properties();

        // load a properties file
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            LOGGER.error("L'initialisation a échouée : ", e);
        }

        database = prop.getProperty("database");

        if (database.contains("TEST")) {
            test = true;
            url = "jdbc:h2:mem:test";
        } else {
            url = "jdbc:sqlite:" + f.getAbsolutePath() + "/db/" + database;
        }

        user = prop.getProperty("dbuser");
        password = prop.getProperty("dbpassword");
        // urlCockpit = prop

        LOGGER.debug(url);
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

    public String getUrlCockpit() {
        return urlCockpit;
    }

    public String getNomCockpit() {
        return nomCockpit;
    }

    public String getCleCockpit() {
        return cleCockpit;
    }

}
