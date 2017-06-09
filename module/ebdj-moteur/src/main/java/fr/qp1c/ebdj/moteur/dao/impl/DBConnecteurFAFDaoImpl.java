package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.Anomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurFAFDaoImpl implements DBConnecteurFAFDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurFAFDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public QuestionFAF donnerQuestionsJouable(Set<Integer> categorieAExclure) throws DBManagerException {

		QuestionFAF question = null;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,reference,source FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_JOUEE Q_FAF_J WHERE Q_FAF.id=Q_FAF_J.question_id)");

		if (categorieAExclure != null) {
			query.append(" AND Q_FAF.categorie IN '");
			StringJoiner clauseIn = new StringJoiner(",", "(", ")");

			for (Integer categorie : categorieAExclure) {
				clauseIn.add(categorie.toString());
			}
			query.append(clauseIn);
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
				question = new QuestionFAF();
				question.setTheme(rs.getString("theme"));
				question.setQuestion(rs.getString("question"));
				question.setReponse(rs.getString("reponse"));
				question.setReference(rs.getString("reference"));

				Source source = new Source(rs.getString("source"));
				question.setSource(source);

				LOGGER.info("Question : " + question);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return question;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("INTO QUESTION_FAF_JOUEE VALUES (");
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
	public void signalerAnomalie(String referenceQuestion, Anomalie anomalie, String lecteur)
			throws DBManagerException {
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
		query.append("SELECT count(1) FROM QUESTION_FAF Q_FAF");
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
				"SELECT count(1) FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT DISTINCT * FROM QUESTION_FAF_JOUEE Q_FAF_J WHERE Q_FAF.id=Q_FAF_J.question_id) ");
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
