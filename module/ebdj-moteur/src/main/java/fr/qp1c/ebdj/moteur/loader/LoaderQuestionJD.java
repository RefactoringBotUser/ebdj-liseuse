package fr.qp1c.ebdj.moteur.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurJDDaoImpl;

public class LoaderQuestionJD {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderQuestionJD.class);

	/**
	 * 
	 * @return
	 */
	public static List<QuestionJD> chargerQuestions() {
		LOGGER.debug("[DEBUT] Chargement des questions de JD.");

		DBConnecteurJDDaoImpl dao = new DBConnecteurJDDaoImpl();
		List<QuestionJD> questionsJD = dao.listerQuestionsJouable(10);

		LOGGER.debug("[FIN] Chargement des questions de JD.");

		return questionsJD;
	}

}
