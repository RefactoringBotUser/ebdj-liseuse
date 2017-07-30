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

	public static QuestionFAF chargerQuestions(List<Long> categoriesAExclure, Long niveauMin, Long niveauMax) {
		LOGGER.debug("[DEBUT] Chargement d'une question de FAF.");

		DBConnecteurFAFDaoImpl dao = new DBConnecteurFAFDaoImpl();
		QuestionFAF questionFAF = dao.donnerQuestionsJouable(categoriesAExclure, niveauMin, niveauMax);

		LOGGER.debug("[FIN] Chargement d'une question de FAF.");

		return questionFAF;
	}

	public static QuestionFAF chargerQuestions(List<Long> categoriesAExclure, Long niveau) {
		LOGGER.debug("[DEBUT] Chargement d'une question de FAF.");

		DBConnecteurFAFDaoImpl dao = new DBConnecteurFAFDaoImpl();
		QuestionFAF questionFAF = dao.donnerQuestionsJouable(categoriesAExclure, niveau);

		LOGGER.debug("[FIN] Chargement d'une question de FAF.");

		return questionFAF;
	}

}
