package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionFAFBdjDistante;

public class DBConnecteurFAFDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurFAFDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurFAFDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionFAF> listerQuestionsJouable(int nbQuestion) throws DBManagerException {

		List<QuestionFAF> listeQuestionsAJouer = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,reference,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.id=Q_FAF_J.question_id)");

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
				QuestionFAF question = new QuestionFAF();
				question.setId(rs.getLong("id"));
				question.setTheme(rs.getString("theme"));
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
	public QuestionFAF donnerQuestionsJouable(Set<Integer> categorieAExclure) throws DBManagerException {

		QuestionFAF question = null;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,reference,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.id=Q_FAF_J.question_id)");

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

				Source source = new Source();
				source.setClub(rs.getString("club"));
				source.setDateReception(rs.getString("dateReception"));
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

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionLue() {

		int nbQuestionJouee = 0;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT DISTINCT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.id=Q_FAF_J.question_id) ");
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

	@Override
	public void creerQuestion(QuestionFAFBdjDistante questionFaf) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_FAF ('categorie','theme','question','reponse','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(Utils.escapeSql(questionFaf.getCategorieFAF()));
		query.append("','");
		query.append(Utils.escapeSql(questionFaf.getTheme()));
		query.append("','");
		query.append(Utils.escapeSql(questionFaf.getQuestion()));
		query.append("','");
		query.append(Utils.escapeSql(questionFaf.getReponse()));
		query.append("',");
		query.append(questionFaf.getDifficulte());
		query.append(",'");
		query.append(questionFaf.getReference());
		query.append("','");
		query.append(Utils.escapeSql(questionFaf.getClub()));
		query.append("','");
		query.append(questionFaf.getDateEnvoi());
		query.append("',");
		query.append(questionFaf.getVersion());
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
	public void corrigerQuestion(QuestionFAFBdjDistante questionFaf) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE QUESTION_FAF SET categorie=");
		query.append(questionFaf.getCategorieFAF());
		query.append(", theme='");
		query.append(Utils.escapeSql(questionFaf.getTheme()));
		query.append("', question='");
		query.append(Utils.escapeSql(questionFaf.getQuestion()));
		query.append("', reponse='");
		query.append(Utils.escapeSql(questionFaf.getReponse()));
		query.append("', difficulte=");
		query.append(questionFaf.getDifficulte());
		query.append(", club='");
		query.append(Utils.escapeSql(questionFaf.getClub()));
		query.append("', dateReception='");
		query.append(questionFaf.getDateEnvoi());
		query.append("', version=");
		query.append(questionFaf.getVersion());
		query.append("' WHERE reference=");
		query.append(questionFaf.getReference());
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
		jouerQuestion("FAF", idQuestion, referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, String version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException {
		signalerAnomalie("FAF", reference, version, anomalie, lecteur);
	}

	@Override
	public void desactiverQuestion(String reference) {
		desactiverQuestion("FAF", reference);
	}

	@Override
	public List<Lecture> listerQuestionsLues(Long indexDebut) {
		return listerQuestionsLues("FAF", indexDebut);
	}

	@Override
	public List<Anomalie> listerAnomalies(Long indexDebut) {
		return listerAnomalies("FAF", indexDebut);
	}

	@Override
	public Long recupererIndexMaxAnomalie() {
		return recupererIndexMaxAnomalie("FAF");
	}

	@Override
	public Long recupererIndexMaxLecture() {
		return recupererIndexMaxLecture("FAF");
	}

	@Override
	public Long recupererReferenceMaxQuestion() {

		return recupererReferenceMaxQuestion("FAF");
	}
}
