package fr.qp1c.ebdj.moteur.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe permettant de gérer les connexions à la base de données.
 * 
 * @author NICO
 *
 */
public class DBManager {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBManager.class);

	/**
	 * Le nom du fichier de base de données.
	 */
	private String DBFileName = "";

	// Constructeur

	/**
	 * Constructeur.
	 * 
	 * @param dBFileName
	 *            le nom du fichier de la base de données
	 */
	public DBManager(String dBFileName) {
		DBFileName = dBFileName;
	}

	// Méthodes

	/**
	 * Créer une connexion à la base de données.
	 * 
	 * @return la connexion initialisée
	 */
	public Connection connect() {
		Connection connection = null;

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DBConstantes.DB_PATH + DBFileName);

			// LOGGER.debug("Connexion a " + DBConstantes.DB_PATH + DBFileName +
			// " avec succès");
		} catch (ClassNotFoundException notFoundException) {
			LOGGER.error("Erreur lors de l'ouverture de la connexion : ", notFoundException);
		} catch (SQLException sqlException) {
			LOGGER.error("Erreur lors de l'ouverture de la connexion : ", sqlException);
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
