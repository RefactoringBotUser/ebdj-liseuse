package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;

public class DBConnecteurSynchroDaoImpl implements DBConnecteurSynchroDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurSynchroDaoImpl.class);

	@Override
	public Long recupererIndexParCle(String cle) {

		Long index = Long.valueOf(0);

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT valeur FROM SYNCHRO WHERE cle='");
		query.append(cle);
		query.append("';");

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				index = rs.getLong(1);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return index;
	}

	@Override
	public void modifierIndexParCle(String cle, Long nouvelIndex) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE SYNCHRO SET valeur=");
		query.append(nouvelIndex);
		query.append(" WHERE cle='");
		query.append(cle);
		query.append("';");

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			stmt.executeUpdate(query.toString());

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
	}

}