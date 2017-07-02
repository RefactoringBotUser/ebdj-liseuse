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
import fr.qp1c.ebdj.moteur.ws.wrapper.QuestionJDBdjDistante;

public class SynchroJDWSHelper extends SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroJDWSHelper.class);

	public SynchroJDWSHelper() {
		super();
	}

	public List<QuestionJDBdjDistante> synchroniserQuestionsJD()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		String urlToCall = urlCockpitRest + "/synchroniser/jd/questions";
		String request = mapper.writeValueAsString(authentificationBdj);

		String response = post(urlToCall, request);

		List<QuestionJDBdjDistante> questions = mapper.readValue(response,
				new TypeReference<List<QuestionJDBdjDistante>>() {
				});

		return questions;
	}

	public void synchroniserLecturesJD(List<Lecture> lectures) {

		// TODO à implementer
	}

	public void synchroniserAnomaliesJD(List<Anomalie> anomalies) {

		// TODO à implementer
	}

	public static void main(String[] args) {

		// try {
		// SynchroWSHelper synchroWSHelper = new SynchroWSHelper();
		// // synchroWSHelper.synchroniserQuestionsFAF();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
