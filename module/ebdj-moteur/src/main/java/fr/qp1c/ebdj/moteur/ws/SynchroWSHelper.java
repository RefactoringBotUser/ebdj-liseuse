package fr.qp1c.ebdj.moteur.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.ws.wrapper.AuthentificationBdj;

public class SynchroWSHelper {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchroWSHelper.class);

	protected String urlCockpitRest;

	protected AuthentificationBdj authentificationBdj;

	public SynchroWSHelper() {
		urlCockpitRest = "http://localhost:8080/";
		authentificationBdj = new AuthentificationBdj();
		authentificationBdj.setNomBdj("TEST");
		authentificationBdj.setCleAuthentification("45fa69e4-1a99-4b02-996e-b1985a05ddbb");
	}

	protected String post(String urlToCall, String request) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
