package fr.qp1c.ebdj.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurFAFDaoImpl;

public class LoaderQuestionFAF {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderQuestionFAF.class);

	/**
	 * 
	 * @return
	 */
	public static List<QuestionFAF> chargerQuestions() {
		LOGGER.debug("[DEBUT] Chargement des questions de FAF.");

		DBConnecteurFAFDaoImpl dao = new DBConnecteurFAFDaoImpl();
		List<QuestionFAF> questionsFAF = dao.listerQuestionsJouable(10);

		LOGGER.debug("[FIN] Chargement des questions de FAF.");

		return questionsFAF;
	}

}
