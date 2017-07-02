package fr.qp1c.ebdj.moteur.ws;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Correction;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.AnomaliesBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.BdjDistanteAnomalie;
import fr.qp1c.ebdj.moteur.ws.wrapper.BdjDistanteLecture;
import fr.qp1c.ebdj.moteur.ws.wrapper.LecturesBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.Question9PGBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.Question9PGBdjDistanteDemande;

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

			Question9PGBdjDistanteDemande question9PGBdjDistanteDemande = new Question9PGBdjDistanteDemande();
			question9PGBdjDistanteDemande.setAuthentificationBdj(authentificationBdj);
			question9PGBdjDistanteDemande.setReference(referenceMaxExistante);

			String request = mapper.writeValueAsString(question9PGBdjDistanteDemande);

			String response = post(urlToCall, request);

			LOGGER.info(response);

			questions = mapper.readValue(response, new TypeReference<List<Question9PGBdjDistante>>() {
			});

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return questions;
	}

	public List<Correction> synchroniserCorrections9PG() {

		List<Correction> corrections = new ArrayList<>();

		try {

			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/9pg/corrections";
			String request = mapper.writeValueAsString(authentificationBdj);

			// TODO prendre en compte l'id max

			String response = post(urlToCall, request);

			AnomaliesBdjDistante anomalies = mapper.readValue(response, AnomaliesBdjDistante.class);

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}

		return corrections;
	}

	public void synchroniserLectures9PG(List<Lecture> lectures) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/9pg/lectures";

			LecturesBdjDistante lecturesBdjDistante = new LecturesBdjDistante();
			lecturesBdjDistante.setAuthentificationBdj(authentificationBdj);

			List<BdjDistanteLecture> bdjDistanteLectures = new ArrayList<>();

			for (Lecture lecture : lectures) {
				bdjDistanteLectures.add(convertirLecture(lecture));
			}

			lecturesBdjDistante.setLectures(bdjDistanteLectures);

			String request = mapper.writeValueAsString(lecturesBdjDistante);

			post(urlToCall, request);

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}
	}

	public void synchroniserAnomalies9PG(List<Anomalie> anomalies) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			String urlToCall = urlCockpitRest + "/synchroniser/9pg/anomalies";

			AnomaliesBdjDistante anomaliesBdjDistante = new AnomaliesBdjDistante();
			anomaliesBdjDistante.setAuthentificationBdj(authentificationBdj);

			List<BdjDistanteAnomalie> bdjDistanteAnomalies = new ArrayList<>();

			for (Anomalie anomalie : anomalies) {
				bdjDistanteAnomalies.add(convertirAnomalie(anomalie));
			}

			anomaliesBdjDistante.setAnomalies(bdjDistanteAnomalies);

			String request = mapper.writeValueAsString(anomaliesBdjDistante);

			post(urlToCall, request);

		} catch (Exception e) {
			LOGGER.error("Exception : an exception has occured : " + e.getMessage());
		}
	}

	private BdjDistanteAnomalie convertirAnomalie(Anomalie anomalie) {
		BdjDistanteAnomalie anomalieWs = new BdjDistanteAnomalie();
		anomalieWs.setCommentaire(anomalie.getCause());
		anomalieWs.setLecteur(anomalie.getLecteur());
		anomalieWs.setReference(anomalie.getReference());
		anomalieWs.setTypeAnomalie(anomalie.getTypeAnomalie());
		// TODO : Convertir la date de l'anomalie
		anomalieWs.setDateAnomalie(new Date(Calendar.getInstance().getTimeInMillis()));
		anomalieWs.setVersion(anomalie.getVersion());
		return anomalieWs;
	}

	private BdjDistanteLecture convertirLecture(Lecture lecture) {
		BdjDistanteLecture lectureWs = new BdjDistanteLecture();
		lectureWs.setReference(lecture.getReference());
		lectureWs.setLecteur(lecture.getLecteur());
		lectureWs.setDateLecture(new Date(Calendar.getInstance().getTimeInMillis()));

		return lectureWs;
	}

	public static void main(String[] args) {

		Synchro9PGWSHelper synchroWSHelper = new Synchro9PGWSHelper();
		synchroWSHelper.synchroniserQuestions9PG(Long.valueOf(0));
	}
}
