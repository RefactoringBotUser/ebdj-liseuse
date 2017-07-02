package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.dao.DBConnecteurParametrageDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurParametrageDaoImpl implements DBConnecteurParametrageDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurParametrageDaoImpl.class);

	@Override
	public String recupererParametrage(String cle) {

		String valeur = "";

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT valeur FROM PARAMETRAGE WHERE cle='");
		query.append(cle);
		query.append("';");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				valeur = rs.getString(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return valeur;
	}

	@Override
	public void modifierParametrage(String cle, String valeur) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE PARAMETRAGE SET valeur='");
		query.append(valeur);
		query.append("' WHERE cle='");
		query.append(cle);
		query.append("';");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			stmt.executeUpdate(query.toString());

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
	}

}
