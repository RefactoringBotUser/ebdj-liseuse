package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.Anomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurJDDaoImpl implements DBConnecteurJDDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurJDDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionJD> listerQuestionsJouable(int nbQuestion) throws DBManagerException {

		List<QuestionJD> listeQuestionsAJouer = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,reference,source FROM QUESTION_JD Q_JD WHERE NOT EXISTS(SELECT * FROM QUESTION_JD_JOUEE Q_JD_J WHERE Q_JD.id=Q_JD_J.question_id)");

		if (nbQuestion > 0) {
			query.append(" LIMIT ");
			query.append(nbQuestion);
		}
		query.append(";");

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
				QuestionJD question = new QuestionJD();
				question.setTheme(rs.getString("theme"));
				question.setQuestion(rs.getString("question"));
				question.setReponse(rs.getString("reponse"));
				question.setReference(rs.getString("reference"));

				Source source = new Source(rs.getString("source"));
				question.setSource(source);

				LOGGER.info("Question : " + question);

				// Ajouter la question à la liste
				listeQuestionsAJouer.add(question);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return listeQuestionsAJouer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("INTO QUESTION_JD_JOUEE VALUES (");
		query.append(referenceQuestion);
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
	public void signalerAnomalie(String referenceTheme, Anomalie anomalie, String lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestion() {

		int nbQuestion = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM QUESTION_JD Q_JD");
		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				nbQuestion = rs.getInt(0);
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

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionJouee() {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_JD Q_JD WHERE NOT EXISTS(SELECT DISTINCT * FROM QUESTION_JD_JOUEE Q_JD_J WHERE Q_JD.id=Q_JD_J.question_id) ");
		query.append(";");

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				nbQuestionJouee = rs.getInt(0);
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
