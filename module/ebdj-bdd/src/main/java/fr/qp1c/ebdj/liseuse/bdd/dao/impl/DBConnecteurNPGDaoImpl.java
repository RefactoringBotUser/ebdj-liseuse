package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Question9PGBdjDistante;

/**
 * Connecteur permettant de récupérer et persister l'ensemble des données liées
 * aux questions du 9PG.
 * 
 */
public class DBConnecteurNPGDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurNPGDao {
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion) {
		return listerQuestionsJouable(nbQuestion, -1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion, int difficulte){
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,difficulte,reference,version,club,dateReception FROM QUESTION_NPG Q_9PG WHERE active=1 AND NOT EXISTS(SELECT * FROM QUESTION_NPG_LECTURE Q_9PG_J WHERE Q_9PG.reference=Q_9PG_J.reference)");
		query.append(ajouterClauseDifficulte(difficulte));

		if (nbQuestion > 0) {
			query.append(" LIMIT ");
			query.append(nbQuestion);
		}
		query.append(";");

		ResultSetHandler<List<QuestionNPG>> h = new ResultSetHandler<List<QuestionNPG>>() {
			@Override
		    public List<QuestionNPG> handle(ResultSet rs) throws SQLException {
				List<QuestionNPG> listeQuestionsAJouer = new ArrayList<>();

				while (rs.next()) {
					// Ajouter la question à la liste
					listeQuestionsAJouer.add(MapperQuestion.convertirQuestion9PG(rs));
				}
		        return listeQuestionsAJouer;
		    }
		};
		
		return executerRequete(query.toString(), h);
	}

	@Override
	public void creerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO QUESTION_NPG (question,reponse,difficulte,reference,club,dateReception,version,active) VALUES ('");
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
	public int compterNbQuestion() {
		return compterNbQuestion(-1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int compterNbQuestionLue() {
		return compterNbQuestionLue(-1);
	}

	@Override
	public int compterNbQuestion(int difficulte) {
		return compterNbQuestion("NPG",ajouterClauseDifficulte(difficulte));
	}

	@Override
	public int compterNbQuestionLue(int difficulte) {
		return compterNbQuestionLue("NPG",ajouterClauseDifficulte(difficulte));
	}
	
	private String ajouterClauseDifficulte(int difficulte) {
		StringBuilder query = new StringBuilder();

		if (difficulte > 0) {
			query.append(" AND difficulte='");
			query.append(difficulte);
			query.append("' ");
		}
		
		return query.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void jouerQuestion(String referenceQuestion, String lecteur) {
		jouerQuestion("NPG", referenceQuestion, lecteur);
	}

	@Override
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur) {
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
