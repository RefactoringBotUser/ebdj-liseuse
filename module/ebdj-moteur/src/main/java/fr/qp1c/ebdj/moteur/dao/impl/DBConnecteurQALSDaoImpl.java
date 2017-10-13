package fr.qp1c.ebdj.moteur.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.question.Question;
import fr.qp1c.ebdj.moteur.bean.question.Source;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.moteur.utils.db.DBConstantes;
import fr.qp1c.ebdj.moteur.utils.db.DBManager;
import fr.qp1c.ebdj.moteur.utils.db.DBUtils;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Question4ALSBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Theme4ALSBdjDistante;

public class DBConnecteurQALSDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurQALSDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurQALSDaoImpl.class);

	/**
	 * {@inheritDoc} - V1
	 * 
	 */
	@Override
	public Map<String, Theme4ALS> listerThemesJouables(NiveauPartie niveauPartie) throws DBManagerException {

		Map<String, Theme4ALS> themes4ALS = new HashMap<>();

		// Lister les niveaux possibles en fonction du niveau
		int niveau = donnerNiveauJouable(niveauPartie);
				
		System.out.println("Niveau du questionnaire : "+niveau);
		
		// TODO gérer si il manque un questionnaire dans une catégorie pour un niveau
		// donné
		// TODO prendre en compte les thèmes présentés
		// TODO jouer en priorité dans des catégories manquantes

		for (int categorie = 1; categorie < 4; categorie++) {
			themes4ALS.put(String.valueOf(categorie), donnerTheme(categorie, niveau));
		}

		return themes4ALS;
	}

	private int donnerNiveauJouable(NiveauPartie niveauPartie) {
		List<Integer> niveauJouable = new ArrayList<>();

		if (NiveauPartie.FACILE.equals(niveauPartie)) {
			niveauJouable.add(2);
			niveauJouable.add(3);
			niveauJouable.add(4);
		} else if (NiveauPartie.MOYEN.equals(niveauPartie)) {
			niveauJouable.add(1);
			niveauJouable.add(2);
			niveauJouable.add(3);
			niveauJouable.add(4);
		} else if (NiveauPartie.MOYEN.equals(niveauPartie)) {
			niveauJouable.add(1);
			niveauJouable.add(2);
			niveauJouable.add(3);
		}


		return niveauJouable.get(new Random().nextInt(niveauJouable.size()) +1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Theme4ALS donnerTheme(int categorie, int niveau) {
		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT reference FROM THEME_QALS where reference NOT IN (SELECT reference FROM THEME_QALS_LECTURE) AND NOT IN reference NOT IN (SELECT reference FROM THEME_QALS_PRESENTE) AND categorie=");
		query.append(categorie);
		query.append(" AND difficulte=");
		query.append(niveau);
		query.append(";");

		LOGGER.debug(query.toString());

		String reference=null;
		
		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				// Convertir chaque question
				reference= rs.getString("reference");
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		Theme4ALS theme4als = recupererTheme4ALS(reference);

		return theme4als;
	}
	
	
	
	public Theme4ALS recupererTheme4ALS(String reference) {
		
		Theme4ALS theme4ALS=recupererPartieTheme4ALS(reference);
		if(theme4ALS.getId()==null) {
			return null;
		}
		
		theme4ALS.setQuestions(recupererPartieQuestions4ALS(theme4ALS.getId()));
		return theme4ALS;
	}
	
	private Theme4ALS recupererPartieTheme4ALS(String reference) {
		Theme4ALS theme4als = new Theme4ALS();
		
		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT id, categorie, theme, difficulte, reference, club, dateReception, version, active FROM THEME_QALS WHERE reference=\"");
		query.append(reference);
		query.append("\";");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			if (rs.next()) {
				// Convertir chaque question
				theme4als=convertirTheme4ALS(rs);
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return theme4als;
	}
	
	private Map<String,Question> recupererPartieQuestions4ALS(Long themeId) {
		Map<String,Question> questions4als = new HashMap<>();
		
		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT seq, question, reponse, FROM QUESTION_QALS WHERE theme_id=\"");
		query.append(themeId);
		query.append("\" ORDER BY seq ASC;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			DBManager dbManager = new DBManager(DBConstantes.DB_NAME);
			Connection connection = dbManager.connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			int indexQuestion=1;
			while (rs.next()) {
				// Convertir chaque question
				Question question4als=new Question();
				question4als.setQuestion(rs.getString("question"));
				question4als.setReponse(rs.getString("reponse"));
				question4als.setVersion(Long.valueOf(1));
				questions4als.put(String.valueOf(indexQuestion), question4als);
				
				indexQuestion+=1;
			}

			// Fermeture des connections.
			stmt.close();
			dbManager.close(connection);
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
	public int compterNbTheme(int categorie) {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(1) FROM THEME_QALS T_QALS");

		if (categorie > 0) {
			query.append(" WHERE T_QALS.categorie=");
			query.append(categorie);
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
	public int compterNbThemeJouee(int categorie) {

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT count(1) FROM THEME_QALS T_QALS WHERE EXISTS(SELECT DISTINCT * FROM THEME_QALS_LECTURE T_QALS_J WHERE T_QALS.id=T_QALS_J.theme_id) ");

		if (categorie > 0) {
			query.append(" AND T_QALS.categorie='");
			query.append(categorie);
			query.append("' ");
		}

		query.append(";");

		return compterNbQuestion(query.toString());
	}

	@Override
	public void creerTheme(Theme4ALSBdjDistante theme4als) {
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO THEME_QALS ('categorie','categorieRef','theme','difficulte','reference','club','dateReception','version','active') VALUES ('");
		query.append(DBUtils.escapeSql(theme4als.getCategorie4ALS()));
		query.append("',");
		query.append(theme4als.getCategorie4ALSRef());
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
			queryQuestion.append("INSERT INTO QUESTION_QALS ('seq','question','reponse','theme_ref') VALUES ('");
			queryQuestion.append(question4ALS.getKey());
			queryQuestion.append(",'");
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
		query.append(", club='");
		query.append(DBUtils.escapeSql(theme4als.getClub()));
		query.append("', dateReception='");
		query.append(theme4als.getDateEnvoi());
		query.append("', version=");
		query.append(theme4als.getVersion());
		query.append(", categorie='");
		query.append(DBUtils.escapeSql(theme4als.getCategorie4ALS()));
		query.append("', categorieRef=");
		query.append(theme4als.getCategorie4ALSRef());
		query.append(" WHERE reference=");
		query.append(theme4als.getReference());
		query.append(";");

		executerUpdateOuInsert(query.toString());

		executerUpdateOuInsert("delete from QUESTION_QALS WHERE theme_ref=" + theme4als.getReference() + ";");

		for (Entry<Integer, Question4ALSBdjDistante> question4ALS : theme4als.getQuestions().entrySet()) {
			StringBuilder queryQuestion = new StringBuilder();
			queryQuestion.append("INSERT INTO QUESTION_QALS ('seq','question','reponse','theme_ref') VALUES ('");
			queryQuestion.append(question4ALS.getKey());
			queryQuestion.append(",'");
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
		// TODO Annuler le theme non joue

	}
	

	private Theme4ALS convertirTheme4ALS(ResultSet rs) throws SQLException {
		// Convertir chaque question
		Theme4ALS theme=new Theme4ALS();
		theme.setId(rs.getLong("id"));
		theme.setCategorieRef(rs.getLong("categorie"));
		theme.setCategorie(String.valueOf(theme.getCategorieRef()));
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

}
