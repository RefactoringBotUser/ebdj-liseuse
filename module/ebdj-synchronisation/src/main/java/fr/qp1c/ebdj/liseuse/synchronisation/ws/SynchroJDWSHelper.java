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
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionQuestionJDBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionSynchroBdjDistanteDemande;

public class SynchroJDWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroJDWSHelper.class);

	public SynchroJDWSHelper() {
		super();
	}

	public List<QuestionJDBdjDistante> synchroniserQuestionsJD(Long referenceMaxExistante) throws BdjException {
		List<QuestionJDBdjDistante> questions = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/liseuse/jd/questions";

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
			throw new BdjException(e, "SYNCHRO_JD_01");
		}

		return questions;
	}

	public List<CorrectionQuestionJDBdjDistante> synchroniserCorrectionsJD(Long indexReprise) throws BdjException {

		List<CorrectionQuestionJDBdjDistante> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/liseuse/jd/corrections";

			CorrectionQuestionBdjDistanteDemande correctionQuestionSynchroBdjDistanteDemande = new CorrectionQuestionBdjDistanteDemande();
			correctionQuestionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			correctionQuestionSynchroBdjDistanteDemande.setIndex(indexReprise);

			String request = mapper.writeValueAsString(correctionQuestionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			corrections = mapper.readValue(response, new TypeReference<List<CorrectionQuestionJDBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
			throw new BdjException(e, "SYNCHRO_JD_02");
		}

		return corrections;
	}

	public void synchroniserLecturesJD(List<Lecture> lectures) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/liseuse/jd/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomaliesJD(List<Anomalie> anomalies) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/liseuse/jd/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}
}
