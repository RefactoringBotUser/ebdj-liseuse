package fr.qp1c.ebdj.liseuse.moteur.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;

public class LoaderQuestion9PG {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderQuestion9PG.class);

	/**
	 * 
	 * @return
	 */
	public static List<QuestionNPG> chargerQuestions1Etoile() {
		LOGGER.debug("[DEBUT] Chargement des questions de 9PG - 1 étoile.");

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(20, 1);

		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}

	/**
	 * 
	 * @return
	 */
	public static List<QuestionNPG> chargerQuestions2Etoiles() {
		LOGGER.debug("[DEBUT] Chargement des questions de 9PG - 2 étoiles.");

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(15, 2);

		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}

	/**
	 * 
	 * @return
	 */
	public static List<QuestionNPG> chargerQuestions3Etoiles() {
		LOGGER.debug("[DEBUT] Chargement des questions de 9PG - 3 étoiles.");

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(10, 3);

		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}
}
