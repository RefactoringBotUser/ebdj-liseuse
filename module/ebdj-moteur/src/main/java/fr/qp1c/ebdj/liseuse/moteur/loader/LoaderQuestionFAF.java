package fr.qp1c.ebdj.liseuse.moteur.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;

public class LoaderQuestionFAF {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderQuestionFAF.class);

    private LoaderQuestionFAF() {

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
