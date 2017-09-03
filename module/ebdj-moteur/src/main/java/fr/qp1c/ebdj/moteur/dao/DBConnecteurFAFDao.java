package fr.qp1c.ebdj.moteur.dao;

import java.util.List;
import java.util.Map;

import fr.qp1c.ebdj.moteur.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionFAFBdjDistante;

/**
 * Cette interface liste des fonctionnalités possibles permettant le traitement
 * en base de données des questions de FAF.
 * 
 * @author NICO
 *
 */
public interface DBConnecteurFAFDao {

	/**
	 * Lister les questions jouables.
	 * 
	 * @param nbQuestion
	 * @return la liste des questions jouables.
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public List<QuestionFAF> listerQuestionsJouable(int nbQuestion) throws DBManagerException;

	/**
	 * Lister les questions jouables.
	 * 
	 * @param nbQuestion
	 * @return la liste des questions jouables.
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveauMin, Long niveauMax)
			throws DBManagerException;

	public QuestionFAF donnerQuestionsJouable(List<Long> categoriesAExclure, Long niveau) throws DBManagerException;

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
	public void jouerQuestion(String referenceQuestion, String lecteur) throws DBManagerException;

	/**
	 * Signaler une anomalie sur une question.
	 * 
	 * @param referenceQuestion
	 *            la référence de la question
	 * @param anomalie
	 *            les informations sur les anomalies
	 * @param lecteur
	 *            le nom du lecteur
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public void signalerAnomalie(String reference, Long version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException;

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

	public Map<String, Long> compterParCategorie();

	public Map<String, Long> compterParCategorieLue();

	public void creerQuestion(QuestionFAFBdjDistante questionFafBdjDistante);

	public void desactiverQuestion(String reference);

	public void corrigerQuestion(QuestionFAFBdjDistante questionFafBdjDistante);

	public List<Lecture> listerQuestionsLues(Long indexDebut);

	public List<Anomalie> listerAnomalies(Long indexDebut);

	public Long recupererIndexMaxAnomalie();

	public Long recupererIndexMaxLecture();

	public Long recupererReferenceMaxQuestion();

}
