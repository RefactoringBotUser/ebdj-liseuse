package fr.qp1c.ebdj.moteur.ws;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionBdjDistanteDemande;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionJDBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionJDBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionSynchroBdjDistanteDemande;

public class SynchroJDWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroJDWSHelper.class);

	public SynchroJDWSHelper() {
		super();
	}

	public List<QuestionJDBdjDistante> synchroniserQuestionsJD(Long referenceMaxExistante) {
		List<QuestionJDBdjDistante> questions = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/jd/questions";

			QuestionSynchroBdjDistanteDemande questionSynchroBdjDistanteDemande = new QuestionSynchroBdjDistanteDemande();
			questionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			questionSynchroBdjDistanteDemande.setReference(referenceMaxExistante);

			String request = mapper.writeValueAsString(questionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			LOGGER.info(response);

			questions = mapper.readValue(response, new TypeReference<List<QuestionJDBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return questions;
	}

	public List<CorrectionQuestionJDBdjDistante> synchroniserCorrectionsJD(Long indexReprise) {

		List<CorrectionQuestionJDBdjDistante> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/jd/corrections";

			CorrectionQuestionBdjDistanteDemande correctionQuestionSynchroBdjDistanteDemande = new CorrectionQuestionBdjDistanteDemande();
			correctionQuestionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			correctionQuestionSynchroBdjDistanteDemande.setIndex(indexReprise);

			String request = mapper.writeValueAsString(correctionQuestionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			corrections = mapper.readValue(response, new TypeReference<List<CorrectionQuestionJDBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return corrections;
	}

	public void synchroniserLecturesJD(List<Lecture> lectures) {

		String urlToCall = urlCockpitRest + "/synchroniser/jd/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomaliesJD(List<Anomalie> anomalies) {

		String urlToCall = urlCockpitRest + "/synchroniser/jd/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}
}
