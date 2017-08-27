package fr.qp1c.ebdj.moteur.ws;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.bean.exception.BdjException;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionBdjDistanteDemande;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionTheme4ALSBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionSynchroBdjDistanteDemande;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Theme4ALSBdjDistante;

public class Synchro4ALSWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Synchro4ALSWSHelper.class);

	public Synchro4ALSWSHelper() {
		super();
	}

	public List<Theme4ALSBdjDistante> synchroniserTheme4ALS(Long referenceMaxExistante) throws BdjException {
		List<Theme4ALSBdjDistante> questions = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/4als/themes";

			QuestionSynchroBdjDistanteDemande questionSynchroBdjDistanteDemande = new QuestionSynchroBdjDistanteDemande();
			questionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			questionSynchroBdjDistanteDemande.setReference(referenceMaxExistante);

			String request = mapper.writeValueAsString(questionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			LOGGER.info(response);

			questions = mapper.readValue(response, new TypeReference<List<Theme4ALSBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
			throw new BdjException(e, "SYNCHRO_4ALS_01");
		}

		return questions;
	}

	public List<CorrectionTheme4ALSBdjDistante> synchroniserCorrections4ALS(Long indexReprise) throws BdjException {

		List<CorrectionTheme4ALSBdjDistante> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/4als/corrections";

			CorrectionQuestionBdjDistanteDemande correctionQuestionSynchroBdjDistanteDemande = new CorrectionQuestionBdjDistanteDemande();
			correctionQuestionSynchroBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			correctionQuestionSynchroBdjDistanteDemande.setIndex(indexReprise);

			String request = mapper.writeValueAsString(correctionQuestionSynchroBdjDistanteDemande);

			String response = post(urlToCall, request);

			corrections = mapper.readValue(response, new TypeReference<List<CorrectionTheme4ALSBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
			throw new BdjException(e, "SYNCHRO_4ALS_02");
		}

		return corrections;
	}

	public void synchroniserLectures4ALS(List<Lecture> lectures) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/4als/lectures";

		synchroniserLectures(urlToCall, lectures);
	}

	public void synchroniserAnomalies4ALS(List<Anomalie> anomalies) throws BdjException {

		String urlToCall = urlCockpitRest + "/synchroniser/4als/anomalies";

		synchroniserAnomalies(urlToCall, anomalies);
	}
}
