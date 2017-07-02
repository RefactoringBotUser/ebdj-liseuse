package fr.qp1c.ebdj.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;
import fr.qp1c.ebdj.moteur.bean.question.Source;

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

		List<QuestionJD> questionsJD = new ArrayList<>();

		try {
			List<String> lignes = Files.readAllLines(
					Paths.get("src/main/resources/questions/", "questionJD.txt").toAbsolutePath(),
					StandardCharsets.UTF_8);
			for (String ligne : lignes) {
				QuestionJD questionJD = convertirQuestionJD(ligne);
				questionsJD.add(questionJD);
			}
		} catch (IOException e) {
			LOGGER.error("Une erreur est survenue :", e);
		}
		LOGGER.debug("[FIN] Chargement des questions de JD.");

		return questionsJD;
	}

	/**
	 * 
	 * 
	 * @param ligne
	 * @return
	 */
	private static QuestionJD convertirQuestionJD(String ligne) {
		LOGGER.debug("[DEBUT] Conversion de la question.");

		String[] data = ligne.split("§");

		QuestionJD question = new QuestionJD();
		question.setTheme(data[0]);
		question.setQuestion(data[1]);
		question.setReponse(data[2]);
		question.setReference("");
		Source source = new Source();
		source.setClub("Dunkerque");
		source.setDateReception("29.04.2016");
		source.setDateIntegration("");
		question.setSource(source);

		LOGGER.debug("[FIN] Conversion de la question : {}", question);
		return question;
	}
}
