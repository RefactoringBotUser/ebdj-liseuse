package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.Anomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

/**
 * Connecteur permettant de récupérer et persister l'ensemble des données liées
 * aux questions du 9PG.
 * 
 */
public class DBConnecteurNPGDaoImpl implements DBConnecteurNPGDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurNPGDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion) throws DBManagerException {

		return listerQuestionsJouable(nbQuestion, -1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion, int difficulte) throws DBManagerException {

		List<QuestionNPG> listeQuestionsAJouer = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,difficulte,reference,source FROM QUESTION_NPG Q_9PG WHERE NOT EXISTS(SELECT * FROM QUESTION_NPG_JOUEE Q_9PG_J WHERE Q_9PG.id=Q_9PG_J.question_id)");

		if (difficulte > 0) {
			query.append(" AND Q_9PG.difficulte='");
			query.append(difficulte);
			query.append("' ");
		}

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
				QuestionNPG question = new QuestionNPG();
				question.setDifficulte(rs.getInt("difficulte") + "");
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
		query.append("INTO QUESTION_NPG_JOUEE VALUES (");
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

	@Override
	public void signalerAnomalie(String referenceTheme, Anomalie anomalie, String lecteur) throws DBManagerException {
		// TODO A implementer

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestion() {

		return compterNbQuestion(-1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionJouee() {

		return compterNbQuestion(-1);
	}

	@Override
	public int compterNbQuestion(int difficulte) {

		int nbQuestion = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM QUESTION_NPG Q_9PG");

		if (difficulte > 0) {
			query.append(" WHERE Q_9PG.difficulte='");
			query.append(difficulte);
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

	@Override
	public int compterNbQuestionJouee(int difficulte) {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_NPG Q_9PG WHERE NOT EXISTS(SELECT DISTINCT * FROM QUESTION_NPG_JOUEE Q_9PG_J WHERE Q_9PG.id=Q_9PG_J.question_id) ");

		if (difficulte > 0) {
			query.append(" AND Q_9PG.difficulte='");
			query.append(difficulte);
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
