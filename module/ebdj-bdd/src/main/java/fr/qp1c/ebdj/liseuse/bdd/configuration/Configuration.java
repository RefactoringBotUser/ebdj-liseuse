package fr.qp1c.ebdj.liseuse.bdd.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Configuration CONFIG = new Configuration();

	private String urlDb;

	private String dbUser = "";

	private String dbPassword = "";
	
	private boolean test = false;

	private Configuration() {
		System.out.println("Init config.");
		File f = new File(".");

		Properties prop = new Properties();
	
		// load a properties file
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String database=prop.getProperty("database");

		if(database.contains("TEST")) {
			test=true;
			urlDb = "jdbc:h2:mem:test";
		} else {
			urlDb = "jdbc:sqlite:" + f.getAbsolutePath() + "/db/" + database;
		}
		
		dbUser = prop.getProperty("dbuser");
		dbPassword = prop.getProperty("dbpassword");

		System.out.println(urlDb);
	}

	public static Configuration getInstance() {
		return CONFIG;
	}

	public String getUrlDb() {
		return urlDb;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public boolean isTest() {
		return test;
	}
}
