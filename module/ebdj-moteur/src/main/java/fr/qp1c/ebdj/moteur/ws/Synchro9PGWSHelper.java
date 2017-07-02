package fr.qp1c.ebdj.moteur.ws;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestion9PGBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionBdjDistanteDemande;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Question9PGBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionSynchroBdjDistanteDemande;

public class Synchro9PGWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Synchro9PGWSHelper.class);

	public Synchro9PGWSHelper() {
		super();
	}

	public List<Question9PGBdjDistante> synchroniserQuestions9PG(Long referenceMaxExistante) {
		List<Question9PGBdjDistante> questions = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/9pg/questions";

			QuestionSynchroBdjDistanteDemande questionSynchroBdjDistanteDemande = new QuestionSynchroBdjDistanteDemande();
			questionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			questionSynchroBdjDistanteDemande.setReference(referenceMaxExistante);

			String request = mapper.writeValueAsString(questionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			LOGGER.info(response);

			questions = mapper.readValue(response, new TypeReference<List<Question9PGBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return questions;
	}

	public List<CorrectionQuestion9PGBdjDistante> synchroniserCorrections9PG(Long indexReprise) {

		List<CorrectionQuestion9PGBdjDistante> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/9pg/corrections";

			CorrectionQuestionBdjDistanteDemande correctionQuestionSynchroBdjDistanteDemande = new CorrectionQuestionBdjDistanteDemande();
			correctionQuestionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			correctionQuestionSynchroBdjDistanteDemande.setIndex(indexReprise);

			String request = mapper.writeValueAsString(correctionQuestionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			corrections = mapper.readValue(response, new TypeReference<List<CorrectionQuestion9PGBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return corrections;
	}

	public void synchroniserLectures9PG(List<Lecture> lectures) {

		String urlToCall = urlCockpitRest + "/synchroniser/9pg/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomalies9PG(List<Anomalie> anomalies) {

		String urlToCall = urlCockpitRest + "/synchroniser/9pg/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}
}
