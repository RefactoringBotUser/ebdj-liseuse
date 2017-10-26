package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Question;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Source;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Question4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Theme4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class DBConnecteurQALSDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurQALSDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurQALSDaoImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Theme4ALS donnerTheme(int groupeCategorie, int niveau) {
		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT reference FROM THEME_QALS where reference NOT IN (SELECT reference FROM THEME_QALS_LECTURE) AND reference NOT IN (SELECT reference FROM THEME_QALS_PRESENTE) AND groupeCategorieRef=");
		query.append(groupeCategorie);
		query.append(" AND difficulte=");
		query.append(niveau);
		query.append(";");

		LOGGER.debug(query.toString());

		String reference = null;

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				// Convertir chaque question
				reference = rs.getString("reference");
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		Theme4ALS theme4als = recupererTheme4ALS(reference);

		return theme4als;
	}

	public Theme4ALS recupererTheme4ALS(String reference) {

		Theme4ALS theme4ALS = recupererPartieTheme4ALS(reference);
		if (theme4ALS.getReference() == null) {
			LOGGER.error("Le theme avec la référence {} est introuvable !", reference);
			return null;
		}

		theme4ALS.setQuestions(recupererPartieQuestions4ALS(theme4ALS.getReference()));
		return theme4ALS;
	}

	private Theme4ALS recupererPartieTheme4ALS(String reference) {
		Theme4ALS theme4als = new Theme4ALS();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id, categorie, categorieRef, groupeCategorieRef, theme, difficulte, reference, club, dateReception, version, active FROM THEME_QALS WHERE reference=");
		query.append(reference);
		query.append(";");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				// Convertir chaque question
				theme4als = convertirTheme4ALS(rs);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return theme4als;
	}

	private Map<String, Question> recupererPartieQuestions4ALS(String reference) {
		Map<String, Question> questions4als = new HashMap<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT seq, question, reponse FROM QUESTION_QALS WHERE reference=");
		query.append(reference);
		query.append(" ORDER BY seq ASC;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			int indexQuestion = 1;
			while (rs.next()) {
				// Convertir chaque question
				Question question4als = new Question();
				question4als.setQuestion(rs.getString("question"));
				question4als.setReponse(rs.getString("reponse"));
				question4als.setVersion(Long.valueOf(1));
				questions4als.put(String.valueOf(indexQuestion), question4als);

				indexQuestion += 1;
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return questions4als;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException {
		signalerAnomalie("QALS", reference, version, anomalie, lecteur);
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
	public int compterNbTheme(int groupeCategorieRef) {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM THEME_QALS T_QALS");

		if (groupeCategorieRef > 0) {
			query.append(" WHERE T_QALS.groupeCategorieRef=");
			query.append(groupeCategorieRef);
			query.append(" ");
		}

		query.append(";");

		return compterNbQuestion(query.toString());
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
	public int compterNbThemeJouee(int groupeCategorieRef) {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM THEME_QALS T_QALS WHERE EXISTS(SELECT DISTINCT * FROM THEME_QALS_LECTURE T_QALS_J WHERE T_QALS.id=T_QALS_J.reference) ");

		if (groupeCategorieRef > 0) {
			query.append(" AND T_QALS.groupeCategorieRef=");
			query.append(groupeCategorieRef);
		}

		query.append(";");

		return compterNbQuestion(query.toString());
	}

	@Override
	public void creerTheme(Theme4ALSBdjDistante theme4als) {
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO THEME_QALS ('categorie','categorieRef','groupeCategorieRef','theme','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(DBUtils.escapeSql(theme4als.getCategorie4ALS()));
		query.append("',");
		query.append(theme4als.getCategorie4ALSRef());
		query.append(",");
		query.append(theme4als.getGroupeCategorie4ALS());
		query.append(",'");
		query.append(DBUtils.escapeSql(theme4als.getTheme()));
		query.append("',");
		query.append(theme4als.getDifficulte());
		query.append(",'");
		query.append(theme4als.getReference());
		query.append("','");
		query.append(DBUtils.escapeSql(theme4als.getClub()));
		query.append("','");
		query.append(theme4als.getDateEnvoi());
		query.append("',");
		query.append(theme4als.getVersion());
		query.append(",1);"); // question active

		executerUpdateOuInsert(query.toString());

		for (Entry<Integer, Question4ALSBdjDistante> question4ALS : theme4als.getQuestions().entrySet()) {
			StringBuilder queryQuestion = new StringBuilder();
			queryQuestion.append("INSERT INTO QUESTION_QALS ('seq','question','reponse','reference') VALUES ('");
			queryQuestion.append(question4ALS.getKey());
			queryQuestion.append("','");
			queryQuestion.append(DBUtils.escapeSql(question4ALS.getValue().getQuestion()));
			queryQuestion.append("','");
			queryQuestion.append(DBUtils.escapeSql(question4ALS.getValue().getReponse()));
			queryQuestion.append("',");
			queryQuestion.append(theme4als.getReference());
			queryQuestion.append(");");

			executerUpdateOuInsert(queryQuestion.toString());
		}
	}

	@Override
	public void desactiverTheme(String reference) {
		desactiverQuestion("QALS", reference);
	}

	@Override
	public void corrigerTheme(Theme4ALSBdjDistante theme4als) {

		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("UPDATE THEME_QALS SET difficulte=");
		query.append(theme4als.getDifficulte());
		query.append(", theme='");
		query.append(DBUtils.escapeSql(theme4als.getTheme()));
		query.append("', club='");
		query.append(DBUtils.escapeSql(theme4als.getClub()));
		query.append("', dateReception='");
		query.append(theme4als.getDateEnvoi());
		query.append("', version=");
		query.append(theme4als.getVersion());
		query.append(", categorie='");
		query.append(DBUtils.escapeSql(theme4als.getCategorie4ALS()));
		query.append("', categorieRef=");
		query.append(theme4als.getCategorie4ALSRef());
		query.append(", groupeCategorieRef=");
		query.append(theme4als.getGroupeCategorie4ALS());
		query.append(" WHERE reference=");
		query.append(theme4als.getReference());
		query.append(";");

		executerUpdateOuInsert(query.toString());

		executerUpdateOuInsert("delete from QUESTION_QALS WHERE reference=" + theme4als.getReference() + ";");

		for (Entry<Integer, Question4ALSBdjDistante> question4ALS : theme4als.getQuestions().entrySet()) {
			StringBuilder queryQuestion = new StringBuilder();
			queryQuestion.append("INSERT INTO QUESTION_QALS ('seq','question','reponse','reference') VALUES ('");
			queryQuestion.append(question4ALS.getKey());
			queryQuestion.append("','");
			queryQuestion.append(DBUtils.escapeSql(question4ALS.getValue().getQuestion()));
			queryQuestion.append("','");
			queryQuestion.append(DBUtils.escapeSql(question4ALS.getValue().getReponse()));
			queryQuestion.append("',");
			queryQuestion.append(theme4als.getReference());
			queryQuestion.append(");");

			executerUpdateOuInsert(queryQuestion.toString());
		}
	}

	@Override
	public List<Anomalie> listerAnomalies(Long indexDebut) {
		return listerAnomalies("QALS", indexDebut);
	}

	@Override
	public List<Lecture> listerQuestionsLues(Long indexDebut) {
		return listerQuestionsLues("QALS", indexDebut);
	}

	@Override
	public Long recupererIndexMaxAnomalie() {
		return recupererIndexMaxAnomalie("QALS");
	}

	@Override
	public Long recupererIndexMaxLecture() {
		return recupererIndexMaxLecture("QALS");
	}

	@Override
	public Long recupererReferenceMaxQuestion() {
		return recupererReferenceMaxQuestion("QALS");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void marquerThemePresente(String referenceTheme, String lecteur) throws DBManagerException {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO THEME_QALS_PRESENTE ('reference','date_presentation','lecteur') VALUES (");
		query.append(referenceTheme);
		query.append(",'");
		query.append(Utils.recupererDateHeureCourante());
		query.append("','");
		query.append(lecteur);
		query.append("');");

		executerUpdateOuInsert(query.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void marquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException {
		jouerQuestion("QALS", referenceTheme, lecteur);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void annulerMarquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM THEME_QALS_LECTURE where reference=");
		query.append(referenceTheme);
		query.append("';");

		executerUpdateOuInsert(query.toString());
	}

	private Theme4ALS convertirTheme4ALS(ResultSet rs) throws SQLException {
		// Convertir chaque question
		Theme4ALS theme = new Theme4ALS();
		theme.setId(rs.getLong("id"));
		theme.setCategorie(rs.getString("categorie"));
		theme.setCategorieRef(rs.getLong("categorieRef"));
		theme.setGroupeCategorieRef(rs.getLong("groupeCategorieRef"));
		theme.setTheme(rs.getString("theme"));
		theme.setReference(rs.getString("reference"));
		theme.setDifficulte(rs.getLong("difficulte"));
		theme.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		theme.setSource(source);

		LOGGER.debug("Theme : " + theme);

		return theme;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Map<String, Map<String, Long>> compterParGroupeCategorie() {

		Map<String, Map<String, Long>> inventaireParGroupeCategorie = new HashMap<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT groupeCategorieRef, difficulte, count(difficulte) FROM THEME_QALS GROUP BY groupeCategorieRef, difficulte order by groupeCategorieRef;");

		for (int i = 1; i <= 4; i++) {

			Map<String, Long> mapGroupeCategorie = new HashMap<>();
			for (int j = 1; j <= 4; j++) {
				mapGroupeCategorie.put(String.valueOf(j), Long.valueOf(0));
			}

			inventaireParGroupeCategorie.put(String.valueOf(i), mapGroupeCategorie);
		}

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				String groupeCategorie = rs.getString("groupeCategorieRef");
				String difficulte = rs.getString("difficulte");
				Long nbQuestion = rs.getLong(3);

				Map<String, Long> mapTemp = inventaireParGroupeCategorie.get(groupeCategorie);
				mapTemp.put(difficulte, nbQuestion);
				inventaireParGroupeCategorie.put(groupeCategorie, mapTemp);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return inventaireParGroupeCategorie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Map<String, Map<String, Long>> compterParGroupeCategorieLue() {

		Map<String, Map<String, Long>> inventaireParGroupeCategorie = new HashMap<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT groupeCategorieRef, difficulte, count(difficulte) FROM THEME_QALS WHERE reference IN (SELECT reference FROM THEME_QALS_LECTURE)  GROUP BY groupeCategorieRef, difficulte order by groupeCategorieRef;");

		for (int i = 1; i <= 4; i++) {

			Map<String, Long> mapGroupeCategorie = new HashMap<>();
			for (int j = 1; j <= 4; j++) {
				mapGroupeCategorie.put(String.valueOf(j), Long.valueOf(0));
			}

			inventaireParGroupeCategorie.put(String.valueOf(i), mapGroupeCategorie);
		}

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				String groupeCategorie = rs.getString("groupeCategorieRef");
				String difficulte = rs.getString("difficulte");
				Long nbQuestion = rs.getLong(3);

				Map<String, Long> mapTemp = inventaireParGroupeCategorie.get(groupeCategorie);
				mapTemp.put(difficulte, nbQuestion);
				inventaireParGroupeCategorie.put(groupeCategorie, mapTemp);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return inventaireParGroupeCategorie;
	}

}
