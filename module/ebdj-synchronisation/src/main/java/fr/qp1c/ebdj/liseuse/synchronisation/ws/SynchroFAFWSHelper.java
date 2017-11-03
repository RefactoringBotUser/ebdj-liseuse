package fr.qp1c.ebdj.liseuse.synchronisation.ws;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionQuestionBdjDistanteDemande;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionQuestionFAFBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionSynchroBdjDistanteDemande;

public class SynchroFAFWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroFAFWSHelper.class);

	public SynchroFAFWSHelper() {
		super();
	}

	public List<QuestionFAFBdjDistante> synchroniserQuestionsFAF(Long referenceMaxExistante) throws BdjException {
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
			throw new BdjException(e, "SYNCHRO_FAF_01");
		}

		return questions;
	}

	public List<CorrectionQuestionFAFBdjDistante> synchroniserCorrectionsFAF(Long indexReprise) throws BdjException {

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
			throw new BdjException(e, "SYNCHRO_FAF_02");
		}

		return corrections;
	}

	public void synchroniserLecturesFAF(List<Lecture> lectures) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/faf/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomaliesFAF(List<Anomalie> anomalies) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/faf/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}

}
