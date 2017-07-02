package fr.qp1c.ebdj.moteur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Question9PGBdjDistante;

public class Main {

	// TODO : ajouter des tests unitaires sur la partie BDD

	public static void main(String[] args) {

		// Question9PGBdjDistante question = new Question9PGBdjDistante();
		// question.setClub("SAINT-AMAND-LES-EAUX");
		// question.setDateEnvoi("");
		// question.setDateIntegration("");
		// question.setDifficulte(Long.valueOf(3));
		// question.setQuestion("Ma question ?");
		// question.setReponse("");
		// question.setVersion(Long.valueOf(1));
		// question.setReference(Long.valueOf(3));
		//
		// DBConnecteurNPGDao dao = new DBConnecteurNPGDaoImpl();
		// dao.creerQuestion(question);

		try {
			List<Question9PGBdjDistante> questions = synchroniserQuestions9PG();
			DBConnecteurNPGDao dao = new DBConnecteurNPGDaoImpl();

			Long deb = System.nanoTime();
			for (Question9PGBdjDistante question : questions) {
				dao.creerQuestion(question);
			}

			Long fin = System.nanoTime();

			System.out.println("Duree de l'appel : " + (fin - deb) / 100000 + " ms.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void afficherReponse(Object objectToPrint) throws JsonProcessingException {
		// Pretty print
		ObjectMapper mapper = new ObjectMapper();
		String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectToPrint);
		System.out.println(prettyStaff1);
	}

	private static List<Question9PGBdjDistante> synchroniserQuestions9PG()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		String urlToCall = "http://localhost:8080/synchroniser/9pg/questions";
		String request = "{\"nomBdj\":\"TEST\",\"cleAuthentification\":\"45fa69e4-1a99-4b02-996e-b1985a05ddbb\"}";

		String response = post(urlToCall, request);

		List<Question9PGBdjDistante> questions = mapper.readValue(response,
				new TypeReference<List<Question9PGBdjDistante>>() {
				});

		return questions;
	}

	private static String post(String urlToCall, String request) {
		String response = null;

		try {
			Long deb = System.nanoTime();
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

			System.out.println("Duree de l'appel : " + (fin - deb) / 100000 + " ms.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

}
