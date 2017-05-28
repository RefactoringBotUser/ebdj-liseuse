package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

/**
 * Cette interface liste des fonctionnalités possibles permettant le traitement
 * en base de données des questions du 9PG.
 * 
 * @author NICO
 *
 */
public interface DBConnecteurNPGDao {

	/**
	 * Lister les questions jouables.
	 * 
	 * @param nbQuestion
	 * @return la liste des questions jouables.
	 * @throws DBManagerException
	 *             en cas d'exception lors de la récupération en BDD.
	 */
	public List<QuestionNPG> listerQuestionsJouable(int nbQuestion, int difficulte) throws DBManagerException;

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

	// TODO : compter par niveau

	// TODO : identifier les doublons

	// TODO : gérer les anomalies

}
