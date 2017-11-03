package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

public class DBConnecteurJDDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurJDDao {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionJD> listerQuestionsJouable(int nbQuestion){
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,theme,reference,version,club,dateReception FROM QUESTION_JD Q_JD WHERE active=1 AND NOT EXISTS(SELECT * FROM QUESTION_JD_LECTURE Q_JD_J WHERE Q_JD.reference=Q_JD_J.reference)");

		if (nbQuestion > 0) {
			query.append(" LIMIT ");
			query.append(nbQuestion);
		}
		query.append(";");
		
		ResultSetHandler<List<QuestionJD>> h = new ResultSetHandler<List<QuestionJD>>() {
			@Override
		    public List<QuestionJD> handle(ResultSet rs) throws SQLException {
				List<QuestionJD> listeQuestionsAJouer = new ArrayList<>();
		    	
		    		while (rs.next()) {
					// Ajouter la question à la liste
					listeQuestionsAJouer.add(MapperQuestion.convertirQuestionJD(rs));
				}
		        return listeQuestionsAJouer;
		    }
		};
		
		return executerRequete(query.toString(), h);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestion() {
		return compterNbQuestion("JD");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionLue() {
		return compterNbQuestionLue("JD");
	}

	@Override
	public void creerQuestion(QuestionJDBdjDistante questionJd) {
		// Création de la requête
		String requete = String.format(
				"INSERT INTO QUESTION_JD (theme,question,reponse,difficulte,reference,club,dateReception,version,active) VALUES ('%s','%s','%s',%d,'%s','%s','%s',%d,1);",DBUtils.escapeSql(questionJd.getTheme()),DBUtils.escapeSql(questionJd.getQuestion()),DBUtils.escapeSql(questionJd.getReponse()),questionJd.getDifficulte(),questionJd.getReference(),DBUtils.escapeSql(questionJd.getClub()),questionJd.getDateEnvoi(),questionJd.getVersion());

		executerUpdateOuInsert(requete);
	}

	@Override
	public void corrigerQuestion(QuestionJDBdjDistante questionJd) {
		// Création de la requête
		String requete = String.format("UPDATE QUESTION_JD SET theme='%s', question='%s', reponse='%s', difficulte=%d, club='%s', dateReception='%s', version=%d WHERE reference=%d;",DBUtils.escapeSql(questionJd.getTheme()),DBUtils.escapeSql(questionJd.getQuestion()),DBUtils.escapeSql(questionJd.getReponse()),questionJd.getDifficulte(),DBUtils.escapeSql(questionJd.getClub()),questionJd.getDateEnvoi(),questionJd.getVersion(),questionJd.getReference());

		executerUpdateOuInsert(requete);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) {
		jouerQuestion("JD", referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur) {
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
