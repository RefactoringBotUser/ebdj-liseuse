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

	private String urlDb;

	private String dbUser;

	private String dbPwd;
	
	private boolean test = false;

	private Configuration() {
		LOGGER.debug("Init config.");
		
		File f = new File(".");

		Properties prop = new Properties();
	
		// load a properties file
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			LOGGER.error("L'initialisation a échouée : ",e);
		}
		
		String database=prop.getProperty("database");

		if(database.contains("TEST")) {
			test=true;
			urlDb = "jdbc:h2:mem:test";
		} else {
			urlDb = "jdbc:sqlite:" + f.getAbsolutePath() + "/db/" + database;
		}
		
		dbUser = prop.getProperty("dbuser");
		dbPwd = prop.getProperty("dbpassword");

		LOGGER.debug(urlDb);
	}

	public static Configuration getInstance() {
		if(configuration==null) {
			configuration= new Configuration();
		}
		return configuration;
	}

	public String getUrlDb() {
		return urlDb;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPwd;
	}

	public boolean isTest() {
		return test;
	}
}
