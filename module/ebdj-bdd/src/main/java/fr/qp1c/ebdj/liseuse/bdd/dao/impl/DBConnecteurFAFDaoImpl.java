package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Source;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;

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
				"SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT active=1 AND EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference)");

		if (nbQuestion > 0) {
			query.append(" LIMIT ");
			query.append(nbQuestion);
		}
		query.append(";");

		LOGGER.debug(query.toString());

		try {
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				QuestionFAF question = convertirQuestionFAF(rs);

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
	public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveauMin, Long niveauMax)
			throws DBManagerException {

		QuestionFAF question = null;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) AND difficulte>="
						+ niveauMin + " AND difficulte<=" + niveauMax + "");

		if (categoriesAExclure != null && categoriesAExclure.size() > 0) {
			query.append(" AND Q_FAF.categorieRef NOT IN (");
			StringJoiner clauseIn = new StringJoiner(",", "", "");

			for (Long categorie : categoriesAExclure) {
				clauseIn.add(categorie.toString());
			}
			query.append(clauseIn);
			query.append(')');
		}
		query.append(" LIMIT 1;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {

				// Convertir chaque question
				question = convertirQuestionFAF(rs);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
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
	public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveau) throws DBManagerException {

		QuestionFAF question = null;

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,difficulte,categorie,categorieRef,reference,version,club,dateReception FROM QUESTION_FAF Q_FAF WHERE NOT EXISTS(SELECT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) AND difficulte="
						+ niveau + "");

		if (categoriesAExclure != null && categoriesAExclure.size() > 0) {
			query.append(" AND Q_FAF.categorieRef NOT IN (");
			StringJoiner clauseIn = new StringJoiner(",", "", "");

			for (Long categorie : categoriesAExclure) {
				clauseIn.add(categorie.toString());
			}
			query.append(clauseIn);
			query.append(')');
		}
		query.append(" LIMIT 1;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				// Convertir chaque question
				question = convertirQuestionFAF(rs);

				LOGGER.debug("Question : " + question);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
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
	public Map<String, Long> compterParCategorie() {

		Map<String, Long> inventaireParCategorie = new HashMap<>();

		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("SELECT categorie, count(1) FROM QUESTION_FAF GROUP BY categorie order by categorie;");

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				String categorie = rs.getString(1);
				Long nbQuestion = rs.getLong(2);

				inventaireParCategorie.put(categorie, nbQuestion);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return inventaireParCategorie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Map<String, Long> compterParCategorieLue() {

		Map<String, Long> inventaireParCategorie = new HashMap<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT categorie, count(1) FROM QUESTION_FAF Q_FAF WHERE EXISTS(SELECT DISTINCT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference) GROUP BY categorie order by categorie;");

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				String categorie = rs.getString(1);
				Long nbQuestion = rs.getLong(2);

				inventaireParCategorie.put(categorie, nbQuestion);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return inventaireParCategorie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestion() {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM QUESTION_FAF Q_FAF WHERE Q_FAF.active=1;");

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
				"SELECT count(1) FROM QUESTION_FAF Q_FAF WHERE EXISTS(SELECT DISTINCT * FROM QUESTION_FAF_LECTURE Q_FAF_J WHERE Q_FAF.reference=Q_FAF_J.reference);");

		return compterNbQuestion(query.toString());
	}

	@Override
	public void creerQuestion(QuestionFAFBdjDistante questionFaf) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_FAF (categorie,categorieRef,theme,question,reponse,difficulte,reference,club,dateReception,version,active) VALUES ('");
		query.append(DBUtils.escapeSql(questionFaf.getCategorieFAF()));
		query.append("',");
		query.append(questionFaf.getCategorieFAFRef());
		query.append(",'");
		query.append(DBUtils.escapeSql(questionFaf.getTheme()));
		query.append("','");
		query.append(DBUtils.escapeSql(questionFaf.getQuestion()));
		query.append("','");
		query.append(DBUtils.escapeSql(questionFaf.getReponse()));
		query.append("',");
		query.append(questionFaf.getDifficulte());
		query.append(",'");
		query.append(questionFaf.getReference());
		query.append("','");
		query.append(DBUtils.escapeSql(questionFaf.getClub()));
		query.append("','");
		query.append(questionFaf.getDateEnvoi());
		query.append("',");
		query.append(questionFaf.getVersion());
		query.append(",1);"); // question active

		executerUpdateOuInsert(query.toString());
	}

	@Override
	public void corrigerQuestion(QuestionFAFBdjDistante questionFaf) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE QUESTION_FAF SET categorie='");
		query.append(questionFaf.getCategorieFAF());
		query.append("', categorieRef=");
		query.append(questionFaf.getCategorieFAFRef());
		query.append(", theme='");
		query.append(DBUtils.escapeSql(questionFaf.getTheme()));
		query.append("', question='");
		query.append(DBUtils.escapeSql(questionFaf.getQuestion()));
		query.append("', reponse='");
		query.append(DBUtils.escapeSql(questionFaf.getReponse()));
		query.append("', difficulte=");
		query.append(questionFaf.getDifficulte());
		query.append(", club='");
		query.append(DBUtils.escapeSql(questionFaf.getClub()));
		query.append("', dateReception='");
		query.append(questionFaf.getDateEnvoi());
		query.append("', version=");
		query.append(questionFaf.getVersion());
		query.append(" WHERE reference=");
		query.append(questionFaf.getReference());
		query.append(";");

		executerUpdateOuInsert(query.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException {
		jouerQuestion("FAF", referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur)
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

	private QuestionFAF convertirQuestionFAF(ResultSet rs) throws SQLException {
		// Convertir chaque question
		QuestionFAF question = new QuestionFAF();
		question.setId(rs.getLong("id"));
		question.setCategorie(rs.getString("categorie"));
		question.setCategorieRef(rs.getLong("categorieRef"));
		question.setTheme(rs.getString("theme"));
		question.setQuestion(rs.getString("question"));
		question.setReponse(rs.getString("reponse"));
		question.setReference(rs.getString("reference"));
		question.setDifficulte(rs.getLong("difficulte"));
		question.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		question.setSource(source);

		LOGGER.debug("Question : " + question);

		return question;
	}
}
