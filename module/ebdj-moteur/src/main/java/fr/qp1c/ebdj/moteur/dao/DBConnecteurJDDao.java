package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.question.Anomalie;
import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

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
	public List<QuestionJD> listerQuestionsJouable(int nbQuestion) throws DBManagerException;

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
	 * @param referenceTheme
	 *            la référence de la question
	 * @param anomalie
	 *            les informations sur les anomalies
	 * @param lecteur
	 *            le nom du lecteur
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public void signalerAnomalie(String referenceTheme, Anomalie anomalie, String lecteur) throws DBManagerException;

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
	public int compterNbQuestionJouee();

}
