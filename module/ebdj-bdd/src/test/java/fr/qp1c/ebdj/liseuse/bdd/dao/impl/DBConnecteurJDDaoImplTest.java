package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.TypeAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

public class DBConnecteurJDDaoImplTest extends DatabaseTest {

	private DBConnecteurJDDao dbConnecteurJDDao;

	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-jd.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListerQuestionsJouable() {
		List<QuestionJD> questions = dbConnecteurJDDao.listerQuestionsJouable(4);
		assertNotNull(questions);
		assertEquals(1, questions.size());
	}

	@Test
	public void testJouerQuestion() throws Exception {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestionLue());

		dbConnecteurJDDao.jouerQuestion("22", "Nicolas GENDRON");

		assertEquals(4, dbConnecteurJDDao.compterNbQuestionLue());

		initDB();
	}

	@Test
	public void testSignalerAnomalie() throws Exception {
		assertEquals(1, dbConnecteurJDDao.listerAnomalies(Long.valueOf(0)).size());

		SignalementAnomalie signalementAnomalie = new SignalementAnomalie();
		signalementAnomalie.setDescription("Réponse carrement fausse");
		signalementAnomalie.setTypeAnomalie(TypeAnomalie.REPONSE_FAUSSE);

		dbConnecteurJDDao.signalerAnomalie("21", Long.valueOf(1), signalementAnomalie, "Nicolas GENDRON");

		assertEquals(2, dbConnecteurJDDao.listerAnomalies(Long.valueOf(0)).size());

		initDB();
	}

	@Test
	public void testCompterNbQuestion() {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestion());
	}

	@Test
	public void testCompterNbQuestionLue() {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestionLue());
	}

	@Test
	public void testCreerQuestion() throws Exception {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestion());

		QuestionJDBdjDistante question = new QuestionJDBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(1));
		question.setTheme("Que suis-je ?");
		question.setQuestion(
				"Espace public, lieu de rencontres et d’échanges, je mets en relation divers quartiers d’une ville. Plus petite qu’une avenue et plus grande qu’une ruelle, je suis...");
		question.setReponse("UNE RUE");
		question.setReference(Long.valueOf(23));
		question.setVersion(Long.valueOf(2));
		dbConnecteurJDDao.creerQuestion(question);

		assertEquals(4, dbConnecteurJDDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testDesactiverQuestion() throws Exception {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestion());

		dbConnecteurJDDao.desactiverQuestion("22");

		assertEquals(2, dbConnecteurJDDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testCorrigerQuestion() throws Exception {
		assertEquals(3, dbConnecteurJDDao.compterNbQuestion());

		QuestionJDBdjDistante question = new QuestionJDBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(1));
		question.setTheme("Que suis-je ?");
		question.setQuestion(
				"Espace public, lieu de rencontres et d’échanges, je mets en relation divers quartiers d’une ville. Plus petite qu’une avenue et plus grande qu’une ruelle, je suis...");
		question.setReponse("UNE RUE");
		question.setReference(Long.valueOf(22));
		question.setVersion(Long.valueOf(2));
		dbConnecteurJDDao.corrigerQuestion(question);

		assertEquals(3, dbConnecteurJDDao.compterNbQuestion());

		initDB();
	}

	@Test
	public void testListerQuestionsLues() {
		assertEquals(3, dbConnecteurJDDao.listerQuestionsLues(Long.valueOf(0)).size());
		assertEquals(2, dbConnecteurJDDao.listerQuestionsLues(Long.valueOf(1)).size());
	}

	@Test
	public void testListerAnomalies() {
		assertEquals(1, dbConnecteurJDDao.listerAnomalies(Long.valueOf(0)).size());
		assertEquals(0, dbConnecteurJDDao.listerAnomalies(Long.valueOf(1)).size());
	}

	@Test
	public void testRecupererIndexMaxAnomalie() {
		assertEquals(Long.valueOf(1), dbConnecteurJDDao.recupererIndexMaxAnomalie());
	}

	@Test
	public void testRecupererIndexMaxLecture() {
		assertEquals(Long.valueOf(3), dbConnecteurJDDao.recupererIndexMaxLecture());
	}

	@Test
	public void testRecupererReferenceMaxQuestion() {
		assertEquals(Long.valueOf(22), dbConnecteurJDDao.recupererReferenceMaxQuestion());
	}

}
