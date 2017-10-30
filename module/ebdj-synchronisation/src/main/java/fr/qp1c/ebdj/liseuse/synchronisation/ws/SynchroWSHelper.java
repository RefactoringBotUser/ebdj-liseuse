package fr.qp1c.ebdj.liseuse.synchronisation.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.AuthentificationBdj;
import fr.qp1c.ebdj.liseuse.commun.exchange.anomalie.AnomaliesBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.anomalie.BdjDistanteAnomalie;
import fr.qp1c.ebdj.liseuse.commun.exchange.lecture.BdjDistanteLecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.lecture.LecturesBdjDistante;

public class SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroWSHelper.class);

	protected String urlCockpitRest;

	protected AuthentificationBdj authentificationBdj;

	public SynchroWSHelper() {
		// TODO : recuperer la configuration dans le fichier de properties
		urlCockpitRest = "http://localhost:9000/";
		authentificationBdj = new AuthentificationBdj();

		// BDJ CLUB

		// authentificationBdj.setNomBdj("E-BDJ - PILOTE");
		// authentificationBdj.setCleAuthentification("45fa69e4-1a99-4b02-996e-b1985a05ddbb");

		// BDJ TEST

		authentificationBdj.setNomBdj("EBDJ - TEST");
		authentificationBdj.setCleAuthentification("1936a18b-898d-467a-bc1b-f5d91604f8a8");
	}

	protected String post(String urlToCall, String request) throws MalformedURLException, IOException {
		String response = null;

		try {
			Long deb = System.nanoTime();
			LOGGER.info("URL :" + urlToCall + " Request : " + request);

			URL url = new URL(urlToCall);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();
			os.write(request.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED
					&& conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT
					&& conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			if ((output = br.readLine()) != null) {
				response = output;
			}

			conn.disconnect();

			Long fin = System.nanoTime();

			LOGGER.info("Duree de l'appel : " + (fin - deb) / 100000 + " ms.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}

	protected void synchroniserLectures(String urlToCall, List<Lecture> lectures) throws BdjException {

		try {
			ObjectMapper mapper = new ObjectMapper();

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
			throw new BdjException(e, "SYNCHRO_LECTURE_01");
		}
	}

	protected void synchroniserAnomalies(String urlToCall, List<Anomalie> anomalies) throws BdjException {

		try {
			ObjectMapper mapper = new ObjectMapper();

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
			throw new BdjException(e, "SYNCHRO_ANOMALIE_01");
		}
	}

	protected BdjDistanteAnomalie convertirAnomalie(Anomalie anomalie) {
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

	protected BdjDistanteLecture convertirLecture(Lecture lecture) {
		BdjDistanteLecture lectureWs = new BdjDistanteLecture();
		lectureWs.setReference(lecture.getReference());
		lectureWs.setLecteur(lecture.getLecteur());
		lectureWs.setDateLecture(new Date(Calendar.getInstance().getTimeInMillis()));

		return lectureWs;
	}

}