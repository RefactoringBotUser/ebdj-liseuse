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
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionFAFBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionFAFBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionSynchroBdjDistanteDemande;

public class SynchroFAFWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroFAFWSHelper.class);

	public SynchroFAFWSHelper() {
		super();
	}

	public List<QuestionFAFBdjDistante> synchroniserQuestionsFAF(Long referenceMaxExistante) {
		List<QuestionFAFBdjDistante> questions = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/faf/questions";

			QuestionSynchroBdjDistanteDemande questionSynchroBdjDistanteDemande = new QuestionSynchroBdjDistanteDemande();
			questionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			questionSynchroBdjDistanteDemande.setReference(referenceMaxExistante);

			String request = mapper.writeValueAsString(questionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			LOGGER.info(response);

			questions = mapper.readValue(response, new TypeReference<List<QuestionFAFBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return questions;
	}

	public List<CorrectionQuestionFAFBdjDistante> synchroniserCorrectionsFAF(Long indexReprise) {

		List<CorrectionQuestionFAFBdjDistante> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/faf/corrections";

			CorrectionQuestionBdjDistanteDemande correctionQuestionSynchroBdjDistanteDemande = new CorrectionQuestionBdjDistanteDemande();
			correctionQuestionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			correctionQuestionSynchroBdjDistanteDemande.setIndex(indexReprise);

			String request = mapper.writeValueAsString(correctionQuestionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			corrections = mapper.readValue(response, new TypeReference<List<CorrectionQuestionFAFBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return corrections;
	}

	public void synchroniserLecturesFAF(List<Lecture> lectures) {

		String urlToCall = urlCockpitRest + "/synchroniser/faf/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomaliesFAF(List<Anomalie> anomalies) {

		String urlToCall = urlCockpitRest + "/synchroniser/faf/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}

}
