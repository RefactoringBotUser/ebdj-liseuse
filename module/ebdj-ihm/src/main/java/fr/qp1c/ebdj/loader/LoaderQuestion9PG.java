package fr.qp1c.ebdj.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;

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

		// try {
		// List<String> lignes = Files.readAllLines(
		// Paths.get("src/main/resources/questions/",
		// "question9PG.txt").toAbsolutePath(),
		// StandardCharsets.UTF_8);
		// for (String ligne : lignes) {
		// QuestionNPG question9PG = convertirQuestion9PG(ligne);
		// if ("1".equals(question9PG.getDifficulte())) {
		// questions9PG.add(question9PG);
		// }
		// }

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(5, 1);

		// }catch(
		//
		// IOException e)
		// {
		// LOGGER.error("Une erreur est survenue :", e);
		// }
		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}

	/**
	 * 
	 * @return
	 */
	public static List<QuestionNPG> chargerQuestions2Etoiles() {
		LOGGER.debug("[DEBUT] Chargement des questions de 9PG - 2 étoiles.");
		//
		// List<QuestionNPG> questions9PG = new ArrayList<>();
		//
		// try {
		// List<String> lignes = Files.readAllLines(
		// Paths.get("src/main/resources/questions/",
		// "question9PG.txt").toAbsolutePath(),
		// StandardCharsets.UTF_8);
		// for (String ligne : lignes) {
		// QuestionNPG question9PG = convertirQuestion9PG(ligne);
		// if ("2".equals(question9PG.getDifficulte())) {
		// questions9PG.add(question9PG);
		// }
		// }
		// } catch (IOException e) {
		// LOGGER.error("Une erreur est survenue :", e);
		// }

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(5, 2);

		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}

	/**
	 * 
	 * @return
	 */
	public static List<QuestionNPG> chargerQuestions3Etoiles() {
		LOGGER.debug("[DEBUT] Chargement des questions de 9PG - 3 étoiles.");
		//
		// List<QuestionNPG> questions9PG = new ArrayList<>();
		//
		// try {
		// List<String> lignes = Files.readAllLines(
		// Paths.get("src/main/resources/questions/",
		// "question9PG.txt").toAbsolutePath(),
		// StandardCharsets.UTF_8);
		// for (String ligne : lignes) {
		// QuestionNPG question9PG = convertirQuestion9PG(ligne);
		// if ("3".equals(question9PG.getDifficulte())) {
		// questions9PG.add(question9PG);
		// }
		// }
		// } catch (IOException e) {
		// LOGGER.error("Une erreur est survenue :", e);
		// }

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		List<QuestionNPG> questions9PG = dao.listerQuestionsJouable(5, 3);

		LOGGER.debug("[FIN] Chargement des questions de 9PG.");

		return questions9PG;
	}

	// /**
	// *
	// *
	// * @param ligne
	// * @return
	// */
	// private static QuestionNPG convertirQuestion9PG(String ligne) {
	// LOGGER.debug("[DEBUT] Conversion de la question.");
	//
	// String[] data = ligne.split("§");
	//
	// QuestionNPG question = new QuestionNPG();
	// question.setDifficulte(data[0]);
	// question.setQuestion(data[1]);
	// question.setReponse(data[2]);
	// question.setReference("");
	// Source source = new Source();
	// source.setClub("Dunkerque");
	// source.setDateEnvoi("29.04.2016");
	// source.setDateIntegration("");
	// question.setSource(source);
	//
	// LOGGER.debug("[FIN] Conversion de la question : {}", question);
	// return question;
	// }
}
