package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.db.DBUtils;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Question9PGBdjDistante;

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
				"SELECT id,question,reponse,difficulte,reference,version,club,dateReception FROM QUESTION_NPG Q_9PG WHERE NOT EXISTS(SELECT * FROM QUESTION_NPG_LECTURE Q_9PG_J WHERE Q_9PG.reference=Q_9PG_J.reference)");

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
				question.setId(rs.getLong("id"));
				question.setDifficulte(rs.getInt("difficulte") + "");
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

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM QUESTION_NPG Q_9PG");

		if (difficulte > 0) {
			query.append(" WHERE Q_9PG.difficulte='");
			query.append(difficulte);
			query.append("' ");
		}

		query.append(";");

		return compterNbQuestion(query.toString());
	}

	@Override
	public int compterNbQuestionLue(int difficulte) {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM QUESTION_NPG Q_9PG WHERE EXISTS(SELECT DISTINCT * FROM QUESTION_NPG_LECTURE Q_9PG_J WHERE Q_9PG.reference=Q_9PG_J.reference) ");

		if (difficulte > 0) {
			query.append(" AND Q_9PG.difficulte='");
			query.append(difficulte);
			query.append("' ");
		}

		query.append(";");

		return compterNbQuestion(query.toString());
	}

	@Override
	public void creerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_NPG ('question','reponse','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(DBUtils.escapeSql(question9pg.getQuestion()));
		query.append("','");
		query.append(DBUtils.escapeSql(question9pg.getReponse()));
		query.append("',");
		query.append(question9pg.getDifficulte());
		query.append(",'");
		query.append(question9pg.getReference());
		query.append("','");
		query.append(DBUtils.escapeSql(question9pg.getClub()));
		query.append("','");
		query.append(question9pg.getDateEnvoi());
		query.append("',");
		query.append(question9pg.getVersion());
		query.append(",1);"); // question active

		executerUpdateOuInsert(query.toString());
	}

	@Override
	public void corrigerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE QUESTION_NPG SET question='");
		query.append(DBUtils.escapeSql(question9pg.getQuestion()));
		query.append("', reponse='");
		query.append(DBUtils.escapeSql(question9pg.getReponse()));
		query.append("', difficulte=");
		query.append(question9pg.getDifficulte());
		query.append(", club='");
		query.append(DBUtils.escapeSql(question9pg.getClub()));
		query.append("', dateReception='");
		query.append(question9pg.getDateEnvoi());
		query.append("', version=");
		query.append(question9pg.getVersion());
		query.append(" WHERE reference=");
		query.append(question9pg.getReference());
		query.append(";");

		executerUpdateOuInsert(query.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException {
		jouerQuestion("NPG", referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur)
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
