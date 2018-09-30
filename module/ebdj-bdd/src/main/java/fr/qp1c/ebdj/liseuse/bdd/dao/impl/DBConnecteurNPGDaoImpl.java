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
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion, int difficulte) {
		// Création de la requête
		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id,question,reponse,difficulte,reference,version,club,dateReception FROM QUESTION_NPG Q_9PG WHERE active=1 AND Q_9PG.reference NOT IN (SELECT DISTINCT Q_9PG_J.reference FROM QUESTION_NPG_LECTURE Q_9PG_J)");
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
		String requete = String.format(
				"INSERT INTO QUESTION_NPG (question,reponse,difficulte,reference,club,dateReception,version,active) VALUES ('%s','%s',%d,'%s','%s','%s',%d,1);",
				DBUtils.escapeSql(question9pg.getQuestion()), DBUtils.escapeSql(question9pg.getReponse()),
				question9pg.getDifficulte(), question9pg.getReference(), DBUtils.escapeSql(question9pg.getClub()),
				question9pg.getDateEnvoi(), question9pg.getVersion());

		executerUpdateOuInsert(requete);
	}

	@Override
	public void corrigerQuestion(Question9PGBdjDistante question9pg) {
		// Création de la requête
		String requete = String.format(
				"UPDATE QUESTION_NPG SET question='%s', reponse='%s', difficulte=%d, club='%s', dateReception='%s', version=%d WHERE reference=%d;",
				DBUtils.escapeSql(question9pg.getQuestion()), DBUtils.escapeSql(question9pg.getReponse()),
				question9pg.getDifficulte(), DBUtils.escapeSql(question9pg.getClub()), question9pg.getDateEnvoi(),
				question9pg.getVersion(), question9pg.getReference());

		executerUpdateOuInsert(requete);
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
		return compterNbQuestion("NPG", ajouterClauseDifficulte(difficulte));
	}

	@Override
	public int compterNbQuestionLue(int difficulte) {
		return compterNbQuestionLue("NPG", ajouterClauseDifficulte(difficulte));
	}

	private String ajouterClauseDifficulte(int difficulte) {
		StringBuilder query = new StringBuilder();

		if (difficulte > 0) {
			query.append(" AND difficulte=");
			query.append(difficulte);
			query.append(" ");
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
