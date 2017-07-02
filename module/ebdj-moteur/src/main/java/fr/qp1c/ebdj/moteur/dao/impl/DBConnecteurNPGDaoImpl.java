package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.Question9PGBdjDistante;

/**
 * Connecteur permettant de récupérer et persister l'ensemble des données liées
 * aux questions du 9PG.
 * 
 */
public class DBConnecteurNPGDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurNPGDao {

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
				"SELECT id,question,reponse,difficulte,reference,club,dateReception FROM QUESTION_NPG Q_9PG WHERE NOT EXISTS(SELECT * FROM QUESTION_NPG_LECTURE Q_9PG_J WHERE Q_9PG.id=Q_9PG_J.question_id)");

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

				Source source = new Source();
				source.setClub(rs.getString("club"));
				source.setDateReception(rs.getString("dateReception"));
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
	public int compterNbQuestion() {

		return compterNbQuestion(-1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionLue() {

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
	public int compterNbQuestionLue(int difficulte) {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_NPG Q_9PG WHERE NOT EXISTS(SELECT DISTINCT * FROM QUESTION_NPG_LECTURE Q_9PG_J WHERE Q_9PG.id=Q_9PG_J.question_id) ");

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

	@Override
	public void creerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_NPG ('question','reponse','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(Utils.escapeSql(question9pg.getQuestion()));
		query.append("','");
		query.append(Utils.escapeSql(question9pg.getReponse()));
		query.append("',");
		query.append(question9pg.getDifficulte());
		query.append(",'");
		query.append(question9pg.getReference());
		query.append("','");
		query.append(Utils.escapeSql(question9pg.getClub()));
		query.append("','");
		query.append(question9pg.getDateEnvoi());
		query.append("',");
		query.append(question9pg.getVersion());
		query.append(",1);"); // question active

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

	@Override
	public void corrigerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE QUESTION_NPG SET question='");
		query.append(Utils.escapeSql(question9pg.getQuestion()));
		query.append("', reponse='");
		query.append(Utils.escapeSql(question9pg.getReponse()));
		query.append("', difficulte=");
		query.append(question9pg.getDifficulte());
		query.append(", club='");
		query.append(Utils.escapeSql(question9pg.getClub()));
		query.append("', dateReception='");
		query.append(question9pg.getDateEnvoi());
		query.append("', version=");
		query.append(question9pg.getVersion());
		query.append("' WHERE reference=");
		query.append(question9pg.getReference());
		query.append(";");

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

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(Long idQuestion, String referenceQuestion, String lecteur) throws DBManagerException {

		jouerQuestion("NPG", idQuestion, referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, String version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException {

		signalerAnomalie("NPG", reference, version, anomalie, lecteur);
	}

	@Override
	public void desactiverQuestion(String reference) {
		desactiverQuestion("NPG", reference);
	}

	@Override
	public List<Lecture> listerQuestionsLues(Long indexDebut) {

		return listerQuestionsLues("NPG", indexDebut);
	}

	@Override
	public List<Anomalie> listerAnomalies(Long indexDebut) {

		return listerAnomalies("NPG", indexDebut);
	}

	@Override
	public Long recupererIndexMaxAnomalie() {

		return recupererIndexMaxAnomalie("NPG");
	}

	@Override
	public Long recupererIndexMaxLecture() {

		return recupererIndexMaxLecture("NPG");
	}

	@Override
	public Long recupererReferenceMaxQuestion() {

		return recupererReferenceMaxQuestion("NPG");
	}

}
