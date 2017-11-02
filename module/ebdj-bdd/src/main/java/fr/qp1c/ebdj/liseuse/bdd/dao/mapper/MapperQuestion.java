package fr.qp1c.ebdj.liseuse.bdd.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Source;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;

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
		question.setId(rs.getLong("id"));
		question.setCategorie(rs.getString("categorie"));
		question.setCategorieRef(rs.getLong("categorieRef"));
		question.setTheme(rs.getString("theme"));
		question.setQuestion(rs.getString("question"));
		question.setReponse(rs.getString("reponse"));
		question.setReference(rs.getString("reference"));
		question.setDifficulte(rs.getLong("difficulte"));
		question.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		question.setSource(source);

		LOGGER.debug("Question : " + question);

		return question;
	}

	public static QuestionJD convertirQuestionJD(ResultSet rs) throws SQLException {
		// Convertir chaque question
		QuestionJD question = new QuestionJD();
		question.setId(rs.getLong("id"));
		question.setTheme(rs.getString("theme"));
		question.setQuestion(rs.getString("question"));
		question.setReponse(rs.getString("reponse"));
		question.setReference(rs.getString("reference"));
		question.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		question.setSource(source);

		LOGGER.debug("Question : " + question);

		return question;
	}

	public static Theme4ALS convertirTheme4ALS(ResultSet rs) throws SQLException {
		// Convertir chaque question
		Theme4ALS theme = new Theme4ALS();
		theme.setId(rs.getLong("id"));
		theme.setCategorie(rs.getString("categorie"));
		theme.setCategorieRef(rs.getLong("categorieRef"));
		theme.setGroupeCategorieRef(rs.getLong("groupeCategorieRef"));
		theme.setTheme(rs.getString("theme"));
		theme.setReference(rs.getString("reference"));
		theme.setDifficulte(rs.getLong("difficulte"));
		theme.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		theme.setSource(source);

		LOGGER.debug("Theme : " + theme);

		return theme;
	}
	
	public static QuestionNPG convertirQuestion9PG(ResultSet rs) throws SQLException {
		// Convertir chaque question
		QuestionNPG question = new QuestionNPG();
		question.setId(rs.getLong("id"));
		question.setDifficulte(rs.getInt("difficulte") + "");
		question.setQuestion(rs.getString("question"));
		question.setReponse(rs.getString("reponse"));
		question.setReference(rs.getString("reference"));
		question.setVersion(rs.getLong("version"));

		Source source = new Source();
		source.setClub(rs.getString("club"));
		source.setDateReception(rs.getString("dateReception"));
		question.setSource(source);

		LOGGER.debug("Question : " + question);
		
		return question;
	}
}
