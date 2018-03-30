package fr.qp1c.ebdj.liseuse.commun.configuration;

import java.io.BufferedInputStream;
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

		// load a properties file/
		try {
			prop.load(new FileInputStream(getConfigurationFileName()));
		} catch (IOException e) {
			LOGGER.error("L'initialisation a échouée : ", e);
		}

		database = prop.getProperty("db-nom");

		if (database.contains("TEST")) {
			test = true;
			url = "jdbc:h2:mem:test";
		} else {
			url = "jdbc:sqlite:" + getPwd() + "/db/" + database + ".sqlite";
		}

		user = prop.getProperty("db-user");
		password = prop.getProperty("db-password");

		cockpitUrl = prop.getProperty("cockpit-url");
		cockpitBdjNom = prop.getProperty("cockpit-bdj-nom");
		cockpitBdjCle = prop.getProperty("cockpit-bdj-cle");

		LOGGER.debug("Url DB : {}", url);

		LOGGER.debug("Fin de l'initialisation de la configuration.");
	}

	private String getConfigurationFileName() {
		File f = new File(".");
		try {
			String fichierConfig = f.getCanonicalPath().toString() + "/config/config.properties";
			LOGGER.debug("URL du fichier de configuration={}", fichierConfig);

			return fichierConfig;
		} catch (IOException e) {
			LOGGER.error("L'initialisation a échouée : ", e);
		}

		return null;
	}

	private String getPwd() {
		return new File(".").getAbsolutePath();
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

	public String afficherFichierParametrage() {
		BufferedInputStream bis = null;
		String strFileContents = "";

		try {
			bis = new BufferedInputStream(new FileInputStream(getConfigurationFileName()));

			byte[] contents = new byte[1024];

			int bytesRead = 0;
			while ((bytesRead = bis.read(contents)) != -1) {
				strFileContents += new String(contents, 0, bytesRead);
			}
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					LOGGER.error("An error has occured :", e);
				}
			}
		}
		return strFileContents;
	}

}
