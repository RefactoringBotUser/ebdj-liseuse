package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.Question4ALS;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurQALSDaoImpl implements DBConnecteurQALSDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurQALSDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Question4ALS> listerThemeJouable() throws DBManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Question4ALS donnerTheme(int categorie) {
		// TODO Auto-generated method stub
		// Récupérer 1 theme dans cette catégorie

		// lister les questions de ce theme par ordre de priorité croissante

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerTheme(String referenceTheme, String lecteur) throws DBManagerException {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("INTO THEME_QALS_JOUEE VALUES (");
		query.append(referenceTheme);
		query.append(",");
		// TODO : calculer la date du jour
		query.append("date du jour");
		query.append(",'");
		query.append(lecteur);
		query.append("');");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();
			stmt.execute(query.toString());

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void signalerAnomalie(String reference, String version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbTheme() {

		return compterNbTheme(-1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbTheme(int categorie) {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM THEME_QALS T_QALS");

		if (categorie > 0) {
			query.append(" WHERE T_QALS.categorie=");
			query.append(categorie);
			query.append(" ");
		}

		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				nbQuestionJouee = rs.getInt(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return nbQuestionJouee;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbThemeJouee() {

		return compterNbThemeJouee(-1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbThemeJouee(int categorie) {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM THEME_QALS T_QALS WHERE NOT EXISTS(SELECT DISTINCT * FROM THEME_QALS_LECTURE T_QALS_J WHERE T_QALS.id=T_QALS_J.theme_id) ");

		if (categorie > 0) {
			query.append(" AND T_QALS.categorie='");
			query.append(categorie);
			query.append("' ");
		}

		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				nbQuestionJouee = rs.getInt(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return nbQuestionJouee;
	}

}
