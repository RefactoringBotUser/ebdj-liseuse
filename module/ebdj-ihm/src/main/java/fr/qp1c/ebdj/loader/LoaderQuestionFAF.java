package fr.qp1c.ebdj.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;
import fr.qp1c.ebdj.moteur.bean.question.Source;

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

		List<QuestionFAF> questionsFAF = new ArrayList<>();

		try {
			List<String> lignes = Files.readAllLines(
					Paths.get("src/main/resources/questions/", "questionFAF.txt").toAbsolutePath(),
					StandardCharsets.UTF_8);
			for (String ligne : lignes) {
				QuestionFAF questionFAF = convertirQuestionFAF(ligne);
				questionsFAF.add(questionFAF);
			}
		} catch (IOException e) {
			LOGGER.error("Une erreur est survenue :", e);
		}
		LOGGER.debug("[FIN] Chargement des questions de FAF.");

		return questionsFAF;
	}

	/**
	 * 
	 * 
	 * @param ligne
	 * @return
	 */
	private static QuestionFAF convertirQuestionFAF(String ligne) {
		LOGGER.debug("[DEBUT] Conversion de la question.");

		String[] data = ligne.split("§");

		QuestionFAF question = new QuestionFAF();
		question.setTheme(data[0]);
		question.setQuestion(data[1]);
		question.setReponse(data[2]);
		question.setReference("");
		Source source = new Source();
		source.setClub("Dunkerque");
		source.setDateEnvoi("29.04.2016");
		source.setDateIntegration("");
		question.setSource(source);

		LOGGER.debug("[FIN] Conversion de la question : {}", question);
		return question;
	}
}
