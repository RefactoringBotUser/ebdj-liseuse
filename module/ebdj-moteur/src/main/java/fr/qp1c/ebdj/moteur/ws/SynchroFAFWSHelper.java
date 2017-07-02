package fr.qp1c.ebdj.moteur.ws;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.QuestionFAFBdjDistante;

public class SynchroFAFWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroFAFWSHelper.class);

	public SynchroFAFWSHelper() {
		super();
	}

	public List<QuestionFAFBdjDistante> synchroniserQuestionsFAF()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		String urlToCall = urlCockpitRest + "/synchroniser/faf/questions";
		String request = mapper.writeValueAsString(authentificationBdj);

		String response = post(urlToCall, request);

		List<QuestionFAFBdjDistante> questions = mapper.readValue(response,
				new TypeReference<List<QuestionFAFBdjDistante>>() {
				});

		return questions;
	}

	public void synchroniserLecturesFAF(List<Lecture> lectures) {

		// TODO à implementer
	}

	public void synchroniserAnomaliesFAF(List<Anomalie> anomalies) {

		// TODO à implementer
	}

	public static void main(String[] args) {

		// try {
		// SynchroWSHelper synchroWSHelper = new SynchroWSHelper();
		// synchroWSHelper.synchroniserQuestionsFAF();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
