package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.TypeAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;

public class DBConnecteurFAFDaoImplTest extends DatabaseTest {

	private DBConnecteurFAFDao dbConnecteurFAFDao;

	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-faf.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testListerQuestionsJouable() {
		List<QuestionFAF> questions = dbConnecteurFAFDao.listerQuestionsJouable(4);
		assertNotNull(questions);
		assertEquals(1, questions.size());
	}

	@Test
	public void testDonnerQuestionsJouableWithMinEtMax() {
		List<Long> categoriesAExclure = new ArrayList<>();
		categoriesAExclure.add(Long.valueOf(9));
		categoriesAExclure.add(Long.valueOf(10));
		categoriesAExclure.add(Long.valueOf(11));

		assertNotNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(2), Long.valueOf(3)));

		categoriesAExclure.add(Long.valueOf(19));

		assertNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(2), Long.valueOf(3)));

		categoriesAExclure.add(Long.valueOf(11));

		assertNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(2), Long.valueOf(3)));
	}

	@Test
	public void testDonnerQuestionsJouable() {

		List<Long> categoriesAExclure = new ArrayList<>();
		categoriesAExclure.add(Long.valueOf(9));
		categoriesAExclure.add(Long.valueOf(10));
		categoriesAExclure.add(Long.valueOf(11));

		assertNotNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(3)));

		categoriesAExclure.add(Long.valueOf(19));

		assertNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(3)));

		categoriesAExclure.add(Long.valueOf(11));

		assertNull(dbConnecteurFAFDao.donnerQuestionsJouable(categoriesAExclure, Long.valueOf(2)));
	}

	@Test
	public void testJouerQuestion() throws Exception {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestionLue());

		dbConnecteurFAFDao.jouerQuestion("22", "Nicolas GENDRON");

		assertEquals(4, dbConnecteurFAFDao.compterNbQuestionLue());

		initDB();
	}

	@Test
	public void testSignalerAnomalie() throws Exception {
		assertEquals(1, dbConnecteurFAFDao.listerAnomalies(Long.valueOf(0)).size());

		SignalementAnomalie signalementAnomalie = new SignalementAnomalie();
		signalementAnomalie.setDescription("Réponse carrement fausse");
		signalementAnomalie.setTypeAnomalie(TypeAnomalie.REPONSE_FAUSSE);

		dbConnecteurFAFDao.signalerAnomalie("21", Long.valueOf(1), signalementAnomalie, "Nicolas GENDRON");

		assertEquals(2, dbConnecteurFAFDao.listerAnomalies(Long.valueOf(0)).size());

		initDB();
	}

	@Test
	public void testCompterNbQuestion() {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestion());
	}

	@Test
	public void testCompterNbQuestionLue() {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestionLue());
	}

	@Test
	public void testCompterParCategorie() {
		Map<String, Long> decompte = dbConnecteurFAFDao.compterParCategorie();

		assertNotNull(decompte);
		assertEquals(Long.valueOf(1), decompte.get("Histoire"));
		assertEquals(Long.valueOf(1), decompte.get("Géographie"));
		assertEquals(Long.valueOf(1), decompte.get("Littérature"));
		assertEquals(Long.valueOf(1), decompte.get("Santé-Médecine"));
	}

	@Test
	public void testCompterParCategorieLue() {
		Map<String, Long> decompte = dbConnecteurFAFDao.compterParCategorieLue();

		assertNotNull(decompte);
		assertEquals(Long.valueOf(1), decompte.get("Histoire"));
		assertEquals(Long.valueOf(1), decompte.get("Géographie"));
		assertEquals(Long.valueOf(1), decompte.get("Littérature"));
		assertNull(decompte.get("Santé-Médecine"));
	}

	@Test
	public void testCreerQuestion() throws Exception {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestion());

		QuestionFAFBdjDistante question = new QuestionFAFBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(1));
		question.setCategorieFAF("Economie");
		question.setCategorieFAFRef(Long.valueOf(2));
		question.setTheme("Economie");
		question.setQuestion(
				"Concept médiatisé à l’occasion d’un Grenelle de l’environnement, je suis à contre-courant du mode linéaire classique : extraction, production, consommation. Mon objectif est d'optimiser les ressources non-renouvelables, de réduire les déchets, de les réutiliser pour réparer ou fabriquer de nouveaux objets. L'éco-conception fait aussi partie de mes critères. Employant près de 600 000 personnes en France, je fonctionne en boucle et supprime la notion de gaspillage. Je suis...");
		question.setReponse("UNE ECONOMIE CIRCULAIRE");
		question.setReference(Long.valueOf(23));
		question.setVersion(Long.valueOf(2));
		dbConnecteurFAFDao.creerQuestion(question);

		assertEquals(4, dbConnecteurFAFDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testDesactiverQuestion() throws Exception {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestion());

		dbConnecteurFAFDao.desactiverQuestion("22");

		assertEquals(2, dbConnecteurFAFDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testCorrigerQuestion() throws Exception {
		assertEquals(3, dbConnecteurFAFDao.compterNbQuestion());

		QuestionFAFBdjDistante question = new QuestionFAFBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(1));
		question.setCategorieFAF("Economie");
		question.setCategorieFAFRef(Long.valueOf(2));
		question.setTheme("Economie");
		question.setQuestion(
				"Concept médiatisé à l’occasion d’un Grenelle de l’environnement, je suis à contre-courant du mode linéaire classique : extraction, production, consommation. Mon objectif est d'optimiser les ressources non-renouvelables, de réduire les déchets, de les réutiliser pour réparer ou fabriquer de nouveaux objets. L'éco-conception fait aussi partie de mes critères. Employant près de 600 000 personnes en France, je fonctionne en boucle et supprime la notion de gaspillage. Je suis...");
		question.setReponse("UNE ECONOMIE CIRCULAIRE");
		question.setReference(Long.valueOf(22));
		question.setVersion(Long.valueOf(2));
		dbConnecteurFAFDao.corrigerQuestion(question);

		assertEquals(3, dbConnecteurFAFDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testListerQuestionsLues() {
		assertEquals(3, dbConnecteurFAFDao.listerQuestionsLues(Long.valueOf(0)).size());
		assertEquals(2, dbConnecteurFAFDao.listerQuestionsLues(Long.valueOf(1)).size());
	}

	@Test
	public void listerAnomalies() {
		assertEquals(1, dbConnecteurFAFDao.listerAnomalies(Long.valueOf(0)).size());
		assertEquals(0, dbConnecteurFAFDao.listerAnomalies(Long.valueOf(1)).size());
	}

	@Test
	public void recupererIndexMaxAnomalie() {
		assertEquals(Long.valueOf(1), dbConnecteurFAFDao.recupererIndexMaxAnomalie());
	}

	@Test
	public void recupererIndexMaxLecture() {
		assertEquals(Long.valueOf(3), dbConnecteurFAFDao.recupererIndexMaxLecture());
	}

	@Test
	public void recupererReferenceMaxQuestion() {
		assertEquals(Long.valueOf(22), dbConnecteurFAFDao.recupererReferenceMaxQuestion());
	}

}
