package fr.qp1c.ebdj.moteur.utils.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	/**
	 * Le nom du fichier de base de données.
	 */
	private String dBFileName = "";

	// Constructeur

	/**
	 * Constructeur.
	 * 
	 * @param dBFileName
	 *            le nom du fichier de la base de données
	 */
	public DBManager(String dBFileName) {
		this.dBFileName = dBFileName;
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

			File f = new File(".");
			LOGGER.debug("Chemin correspondant à la base de données : {}", f.getAbsolutePath());

			// TODO : recuperer le nom de la base de données
			String urlDb = "jdbc:sqlite:" + f.getAbsolutePath() + "/db/" + dBFileName;

			connection = DriverManager.getConnection(urlDb);

			LOGGER.debug("Connexion  avec succès à la base de données {}", urlDb);
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
