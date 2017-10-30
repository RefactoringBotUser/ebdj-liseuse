package fr.qp1c.ebdj.liseuse.bdd.dao;

import java.util.List;

import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

/**
 * Cette interface liste des fonctionnalités possibles permettant le traitement
 * en base de données des questions de JD.
 * 
 * @author NICO
 *
 */
public interface DBConnecteurJDDao {

	/**
	 * Lister les questions jouables.
	 * 
	 * @param nbQuestion
	 * @return la liste des questions jouables.
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public List<QuestionJD> listerQuestionsJouable(int nbQuestion);

	/**
	 * Marquer une question comme jouée.
	 * 
	 * @param referenceQuestion
	 *            la référence de la question
	 * @param lecteur
	 *            le nom du lecteur
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public void jouerQuestion(String referenceQuestion, String lecteur);

	/**
	 * Signaler une anomalie sur une question.
	 * 
	 * @param referenceTheme
	 *            la référence de la question
	 * @param anomalie
	 *            les informations sur les anomalies
	 * @param lecteur
	 *            le nom du lecteur
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur);

	/**
	 * Compter le nombre total de question existante.
	 * 
	 * @return le nombre total de question existante.
	 */
	public int compterNbQuestion();

	/**
	 * Compter le nombre total de question jouable.
	 * 
	 * @return le nombre de question jouable.
	 */
	public int compterNbQuestionLue();

	public void creerQuestion(QuestionJDBdjDistante questionJdBdjDistante);

	public void desactiverQuestion(String reference);

	public void corrigerQuestion(QuestionJDBdjDistante questionJdBdjDistante);

	public List<Lecture> listerQuestionsLues(Long indexDebut);

	public List<Anomalie> listerAnomalies(Long indexDebut);

	public Long recupererIndexMaxAnomalie();

	public Long recupererIndexMaxLecture();

	public Long recupererReferenceMaxQuestion();
}
