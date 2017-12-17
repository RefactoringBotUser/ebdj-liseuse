package fr.qp1c.ebdj.liseuse.bdd.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QR;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Source;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;

public class MapperQuestion {

    /**
     * Default logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperQuestion.class);

    private MapperQuestion() {

    }

    public static QuestionFAF convertirQuestionFAF(ResultSet rs) throws SQLException {
        // Convertir chaque question
        QuestionFAF question = new QuestionFAF();
        question.setId(rs.getLong(MapperConstants.ID));
        question.setCategorie(rs.getString("categorie"));
        question.setCategorieRef(rs.getLong("categorieRef"));
        question.setTheme(rs.getString(MapperConstants.THEME));
        question.setQuestion(rs.getString(MapperConstants.QUESTION));
        question.setReponse(rs.getString(MapperConstants.REPONSE));
        question.setReference(rs.getString(MapperConstants.REFERENCE));
        question.setDifficulte(rs.getLong(MapperConstants.DIFFICULTE));
        question.setVersion(rs.getLong(MapperConstants.VERSION));
        question.setSource(convertirSource(rs));

        LOGGER.debug("Question FAF : {}", question);

        return question;
    }

    private static Source convertirSource(ResultSet rs) throws SQLException {
        Source source = new Source();
        source.setClub(rs.getString("club"));
        source.setDateReception(rs.getString("dateReception"));
        return source;
    }

    public static QuestionJD convertirQuestionJD(ResultSet rs) throws SQLException {
        // Convertir chaque question
        QuestionJD question = new QuestionJD();
        question.setId(rs.getLong(MapperConstants.ID));
        question.setTheme(rs.getString(MapperConstants.THEME));
        question.setQuestion(rs.getString(MapperConstants.QUESTION));
        question.setReponse(rs.getString(MapperConstants.REPONSE));
        question.setReference(rs.getString(MapperConstants.REFERENCE));
        question.setVersion(rs.getLong(MapperConstants.VERSION));
        question.setSource(convertirSource(rs));

        LOGGER.debug("Question JD : {}", question);

        return question;
    }

    public static Theme4ALS convertirTheme4ALS(ResultSet rs) throws SQLException {
        // Convertir chaque question
        Theme4ALS theme = new Theme4ALS();
        theme.setId(rs.getLong(MapperConstants.ID));
        theme.setCategorie(rs.getString("categorie"));
        theme.setCategorieRef(rs.getLong("categorieRef"));
        theme.setGroupeCategorieRef(rs.getLong("groupeCategorieRef"));
        theme.setTheme(rs.getString(MapperConstants.THEME));
        theme.setReference(rs.getString(MapperConstants.REFERENCE));
        theme.setDifficulte(rs.getLong(MapperConstants.DIFFICULTE));
        theme.setVersion(rs.getLong(MapperConstants.VERSION));
        theme.setSource(convertirSource(rs));

        LOGGER.debug("Theme : {}", theme);

        return theme;
    }

    public static QuestionNPG convertirQuestion9PG(ResultSet rs) throws SQLException {
        // Convertir chaque question
        QuestionNPG question = new QuestionNPG();
        question.setId(rs.getLong(MapperConstants.ID));
        question.setDifficulte(Integer.toString(rs.getInt(MapperConstants.DIFFICULTE)));
        question.setQuestion(rs.getString(MapperConstants.QUESTION));
        question.setReponse(rs.getString(MapperConstants.REPONSE));
        question.setReference(rs.getString(MapperConstants.REFERENCE));
        question.setVersion(rs.getLong(MapperConstants.VERSION));
        question.setSource(convertirSource(rs));

        LOGGER.debug("Question 9PG : {}", question);

        return question;
    }

    public static Lecteur convertirLecteur(ResultSet rs) throws SQLException {
        // Convertir chaque lecteur
        Lecteur lecteur = new Lecteur();
        lecteur.setId(rs.getLong(MapperConstants.ID));
        lecteur.setNom(rs.getString("nom"));
        lecteur.setPrenom(rs.getString("prenom"));

        LOGGER.info("Lecteur: {}", lecteur);

        return lecteur;
    }

    public static Anomalie convertirAnomalie(ResultSet rs) throws SQLException {
        // Convertir chaque question
        Anomalie anomalie = new Anomalie();
        anomalie.setReference(rs.getLong(MapperConstants.REFERENCE));
        anomalie.setVersion(rs.getLong(MapperConstants.VERSION));
        anomalie.setDateAnomalie(rs.getString("date_anomalie"));
        anomalie.setTypeAnomalie(rs.getLong("type_anomalie"));
        anomalie.setCause(rs.getString("cause"));
        anomalie.setLecteur(rs.getString(MapperConstants.LECTEUR));

        LOGGER.info("Anomalie : {}", anomalie);

        return anomalie;
    }

    public static Lecture convertirLecture(ResultSet rs) throws SQLException {
        // Convertir chaque question
        Lecture lecture = new Lecture();
        lecture.setReference(rs.getLong(MapperConstants.REFERENCE));
        lecture.setDateLecture(rs.getString("date_lecture"));
        lecture.setLecteur(rs.getString(MapperConstants.LECTEUR));

        LOGGER.info("Lecture : {}", lecture);

        return lecture;
    }
    
    public static QR convertirQR(ResultSet rs) throws SQLException {
        // Convertir chaque question
	    QR question4als = new QR();
	    question4als.setQuestion(rs.getString("question"));
	    question4als.setReponse(rs.getString("reponse"));
	    question4als.setVersion(Long.valueOf(1));
	    
	    return question4als;
    }

}
