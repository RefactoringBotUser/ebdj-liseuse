package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurGeneriqueImpl {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurGeneriqueImpl.class);

	protected Long recupererIndexMaxAnomalie(String type) {
		return recupererIndexMax(donnerPrefixeTable(type) + "_ANOMALIE");

	}

	protected Long recupererIndexMaxLecture(String type) {
		return recupererIndexMax(donnerPrefixeTable(type) + "_LECTURE");

	}

	protected Long recupererIndexMax(String table) {

		Long indexMax = Long.valueOf(0);

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT max(id) FROM ");
		query.append(table);
		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			LOGGER.info(query.toString());

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				indexMax = rs.getLong(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return indexMax;
	}

	protected Long recupererReferenceMaxQuestion(String type) {

		Long referenceMax = Long.valueOf(0);

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT max(reference) FROM ");
		query.append(donnerPrefixeTable(type));
		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				referenceMax = rs.getLong(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return referenceMax;
	}

	private String donnerPrefixeTable(String type) {
		if ("QALS".equals(type)) {
			return "THEME_" + type;
		}
		return "QUESTION_" + type;
	}

	protected List<Anomalie> listerAnomalies(String type, Long indexDebut) {
		List<Anomalie> listeAnomalies = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT reference,version,date_anomalie,type_anomalie,cause,lecteur FROM "
				+ donnerPrefixeTable(type) + "_ANOMALIE WHERE id>");
		query.append(indexDebut);
		query.append(" ORDER BY id ASC;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {

				// Convertir chaque question
				Anomalie anomalie = new Anomalie();
				anomalie.setReference(rs.getLong("reference"));
				anomalie.setVersion(rs.getLong("version"));
				anomalie.setDateAnomalie(rs.getString("date_anomalie"));
				anomalie.setTypeAnomalie(rs.getLong("type_anomalie"));
				anomalie.setCause(rs.getString("cause"));
				anomalie.setLecteur(rs.getString("lecteur"));

				LOGGER.info("Anomalie : " + anomalie);

				// Ajouter la question à la liste
				listeAnomalies.add(anomalie);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return listeAnomalies;
	}

	public List<Lecture> listerQuestionsLues(String type, Long indexDebut) {
		List<Lecture> listeQuestionsLues = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT reference, date_lecture, lecteur FROM FROM " + donnerPrefixeTable(type) + "_LECTURE WHERE id>");
		query.append(indexDebut);
		query.append(" ORDER BY id ASC;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {

				// Convertir chaque question
				Lecture lecture = new Lecture();
				lecture.setReference(rs.getLong("reference"));
				lecture.setDateLecture(rs.getString("date_lecture"));
				lecture.setLecteur(rs.getString("lecteur"));

				LOGGER.info("Lecture : " + lecture);

				// Ajouter la question à la liste
				listeQuestionsLues.add(lecture);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return listeQuestionsLues;
	}

	public void desactiverQuestion(String type, String reference) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE " + donnerPrefixeTable(type) + " SET active=0 WHERE reference=");
		query.append(reference);
		query.append(";"); // question active

		executerUpdateOuInsert(query.toString());
	}

	public void jouerQuestion(String type, String referenceQuestion, String lecteur) throws DBManagerException {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO " + donnerPrefixeTable(type) + "_LECTURE ('reference','date_lecture','lecteur') VALUES (");
		query.append(referenceQuestion);
		query.append(",'");
		query.append(Utils.formatDate());
		query.append("','");
		query.append(lecteur);
		query.append("');");

		executerUpdateOuInsert(query.toString());
	}

	public void signalerAnomalie(String type, String reference, Long version, SignalementAnomalie anomalie,
			String lecteur) throws DBManagerException {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + donnerPrefixeTable(type)
				+ "_ANOMALIE ('reference','version','date_anomalie','type_anomalie','cause','lecteur') VALUES ('");
		query.append(reference);
		query.append("',");
		query.append(version);
		query.append(",'");
		query.append(Utils.formatDate());
		query.append("','");
		query.append(anomalie.getTypeAnomalie());
		query.append("','");
		query.append(Utils.escapeSql(anomalie.getDescription()));
		query.append("','");
		query.append(Utils.escapeSql(lecteur));
		query.append("');");

		executerUpdateOuInsert(query.toString());
	}

	protected int compterNbQuestion(String query) {

		int nbQuestion = 0;

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				nbQuestion = rs.getInt(1);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return nbQuestion;
	}

	protected void executerUpdateOuInsert(String requete) {
		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			stmt.executeUpdate(requete);

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
	}

}
