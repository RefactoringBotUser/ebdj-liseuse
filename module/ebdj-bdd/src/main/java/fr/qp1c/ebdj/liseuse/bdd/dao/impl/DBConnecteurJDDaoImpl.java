package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Source;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

public class DBConnecteurJDDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurJDDao {

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
				"SELECT id,question,reponse,theme,reference,version,club,dateReception FROM QUESTION_JD Q_JD WHERE NOT EXISTS(SELECT * FROM QUESTION_JD_LECTURE Q_JD_J WHERE Q_JD.reference=Q_JD_J.reference)");

		if (nbQuestion > 0) {
			query.append(" LIMIT ");
			query.append(nbQuestion);
		}
		query.append(";");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {

				// Convertir chaque question
				QuestionJD question = new QuestionJD();
				question.setId(rs.getLong("id"));
				question.setTheme(rs.getString("theme"));
				question.setQuestion(rs.getString("question"));
				question.setReponse(rs.getString("reponse"));
				question.setReference(rs.getString("reference"));
				question.setVersion(rs.getLong("version"));

				Source source = new Source();
				source.setClub(rs.getString("club"));
				source.setDateReception(rs.getString("dateReception"));
				question.setSource(source);

				LOGGER.debug("Question : " + question);

				// Ajouter la question à la liste
				listeQuestionsAJouer.add(question);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
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

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM QUESTION_JD Q_JD;");

		return compterNbQuestion(query.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionLue() {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_JD Q_JD WHERE EXISTS(SELECT DISTINCT * FROM QUESTION_JD_LECTURE Q_JD_J WHERE Q_JD.reference=Q_JD_J.reference);");

		return compterNbQuestion(query.toString());
	}

	@Override
	public void creerQuestion(QuestionJDBdjDistante questionJd) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_JD ('theme','question','reponse','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(DBUtils.escapeSql(questionJd.getTheme()));
		query.append("','");
		query.append(DBUtils.escapeSql(questionJd.getQuestion()));
		query.append("','");
		query.append(DBUtils.escapeSql(questionJd.getReponse()));
		query.append("',");
		query.append(questionJd.getDifficulte());
		query.append(",'");
		query.append(questionJd.getReference());
		query.append("','");
		query.append(DBUtils.escapeSql(questionJd.getClub()));
		query.append("','");
		query.append(questionJd.getDateEnvoi());
		query.append("',");
		query.append(questionJd.getVersion());
		query.append(",1);"); // question active

		executerUpdateOuInsert(query.toString());
	}

	@Override
	public void corrigerQuestion(QuestionJDBdjDistante questionJd) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE QUESTION_JD SET theme='");
		query.append(DBUtils.escapeSql(questionJd.getTheme()));
		query.append("', question='");
		query.append(DBUtils.escapeSql(questionJd.getQuestion()));
		query.append("', reponse='");
		query.append(DBUtils.escapeSql(questionJd.getReponse()));
		query.append("', difficulte=");
		query.append(questionJd.getDifficulte());
		query.append(", club='");
		query.append(DBUtils.escapeSql(questionJd.getClub()));
		query.append("', dateReception='");
		query.append(questionJd.getDateEnvoi());
		query.append("', version=");
		query.append(questionJd.getVersion());
		query.append(" WHERE reference=");
		query.append(questionJd.getReference());
		query.append(";");

		executerUpdateOuInsert(query.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException {
		jouerQuestion("JD", referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException {
		signalerAnomalie("JD", reference, version, anomalie, lecteur);
	}

	@Override
	public void desactiverQuestion(String reference) {
		desactiverQuestion("JD", reference);
	}

	@Override
	public List<Lecture> listerQuestionsLues(Long indexDebut) {
		return listerQuestionsLues("JD", indexDebut);
	}

	@Override
	public List<Anomalie> listerAnomalies(Long indexDebut) {
		return listerAnomalies("JD", indexDebut);
	}

	@Override
	public Long recupererIndexMaxAnomalie() {
		return recupererIndexMaxAnomalie("JD");
	}

	@Override
	public Long recupererIndexMaxLecture() {
		return recupererIndexMaxLecture("JD");
	}

	@Override
	public Long recupererReferenceMaxQuestion() {

		return recupererReferenceMaxQuestion("JD");
	}

}
