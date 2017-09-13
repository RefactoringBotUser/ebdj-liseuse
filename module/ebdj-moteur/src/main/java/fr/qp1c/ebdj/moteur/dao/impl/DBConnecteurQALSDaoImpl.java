package fr.qp1c.ebdj.moteur.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
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
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Map<String, Theme4ALS> listerThemesJouables() throws DBManagerException {
		// TODO A implementer

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Theme4ALS donnerTheme(int categorie) {
		// TODO A implementer

		// Récupérer 1 theme dans cette catégorie

		// lister les questions de ce theme par ordre de priorité croissante

		return null;
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
	public void marquerThemePropose(String referenceTheme, String lecteur) throws DBManagerException {
		jouerQuestion("QALS", referenceTheme, lecteur);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void marquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void annulerMarquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

}
