package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.configuration.Configuration;

/**
 * Classe permettant de gérer les connexions à la base de données SQLite.
 * 
 * @author NICO
 *
 */
public class DBManager {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBManager.class);


	private static DBManager dbManager;
	
	// Constructeur

	/**
	 * Constructeur.
	 * 
	 */
	private DBManager() {
	}

	// Méthodes

	public static DBManager getInstance() {
		if(dbManager==null) {
			dbManager=new DBManager();
		}
		return dbManager;
	}

	/**
	 * Créer une connexion à la base de données.
	 * 
	 * @return la connexion initialisée
	 */
	public Connection connect() {
		Connection connection = null;

		try {
			if(Configuration.getInstance().isTest()) {
				Class.forName("org.h2.Driver");
			} else {
				Class.forName("org.sqlite.JDBC");
			}
						
			connection = DriverManager.getConnection(Configuration.getInstance().getUrlDb(),Configuration.getInstance().getDbUser(),Configuration.getInstance().getDbPassword());

			LOGGER.trace("Connexion  avec succès à la base de données {}", Configuration.getInstance().getUrlDb());
		} catch (ClassNotFoundException notFoundException) {
			LOGGER.error("Erreur lors de l'ouverture de la connexion: ", notFoundException);
		} catch (SQLException sqlException) {
			LOGGER.error("Erreur lors de l'ouverture de la connexion: ", sqlException);
		}

		return connection;
	}

	/**
	 * Fermer la connexion à la base de données.
	 * 
	 * @param connection
	 *            la connexion à fermer
	 */
	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException sqlException) {
			LOGGER.error("Erreur lors de la fermeture de la connexion : ", sqlException);
		}
	}
}
