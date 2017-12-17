package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.TypeAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Question9PGBdjDistante;

public class DBConnecteurNPGDaoImplTest extends DatabaseTest {

	private DBConnecteurNPGDao dbConnecteurNPGDao;

	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-npg.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompterNbQuestion() {
		assertEquals(9, dbConnecteurNPGDao.compterNbQuestion());
	}

	@Test
	public void testCompterNbQuestionByDifficulte() {
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(1));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(2));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(3));
	}

	@Test
	public void testCompterNbQuestionLue() {
		assertEquals(4, dbConnecteurNPGDao.compterNbQuestionLue());
	}

	@Test
	public void testCompterNbQuestionLueByDifficulte() {
		assertEquals(2, dbConnecteurNPGDao.compterNbQuestionLue(1));
		assertEquals(1, dbConnecteurNPGDao.compterNbQuestionLue(2));
		assertEquals(1, dbConnecteurNPGDao.compterNbQuestionLue(3));
	}

	@Test
	public void testListerQuestionsJouableWithNbQuestion() {
		List<QuestionNPG> questions = dbConnecteurNPGDao.listerQuestionsJouable(4);
		assertNotNull(questions);
		assertEquals(4, questions.size());
	}

	@Test
	public void testListerQuestionsJouableWithNbQuestionAndDifficulte() {
		List<QuestionNPG> questions = dbConnecteurNPGDao.listerQuestionsJouable(4, 1);
		assertNotNull(questions);
		assertEquals(1, questions.size());
		for (QuestionNPG question : questions) {
			assertEquals("1", question.getDifficulte());
		}

		questions = dbConnecteurNPGDao.listerQuestionsJouable(4, 2);
		assertNotNull(questions);
		assertEquals(2, questions.size());
		for (QuestionNPG question : questions) {
			assertEquals("2", question.getDifficulte());
		}

		questions = dbConnecteurNPGDao.listerQuestionsJouable(4, 3);
		assertNotNull(questions);
		assertEquals(2, questions.size());
		for (QuestionNPG question : questions) {
			assertEquals("3", question.getDifficulte());
		}
	}

	@Test
	public void testRecupererIndexMaxAnomalie() {
		assertEquals(Long.valueOf(1), dbConnecteurNPGDao.recupererIndexMaxAnomalie());
	}

	@Test
	public void testRecupererIndexMaxLecture() {
		assertEquals(Long.valueOf(4), dbConnecteurNPGDao.recupererIndexMaxLecture());
	}

	@Test
	public void testRecupererReferenceMaxQuestion() {
		assertEquals(Long.valueOf(28), dbConnecteurNPGDao.recupererReferenceMaxQuestion());
	}

	@Test
	public void testListerQuestionsLues() {
		assertEquals(4, dbConnecteurNPGDao.listerQuestionsLues(Long.valueOf(0)).size());
		assertEquals(3, dbConnecteurNPGDao.listerQuestionsLues(Long.valueOf(1)).size());
	}

	@Test
	public void testListerAnomalies() {
		assertEquals(1, dbConnecteurNPGDao.listerAnomalies(Long.valueOf(0)).size());
		assertEquals(0, dbConnecteurNPGDao.listerAnomalies(Long.valueOf(1)).size());
	}

	@Test
	public void testDesactiverQuestion() throws Exception {
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(1));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(2));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(3));

		dbConnecteurNPGDao.desactiverQuestion("25");

		assertEquals(2, dbConnecteurNPGDao.compterNbQuestion(1));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(2));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(3));

		initDB();
	}

	@Test
	public void testSignalerAnomalie() throws Exception {
		assertEquals(1, dbConnecteurNPGDao.listerAnomalies(Long.valueOf(0)).size());

		SignalementAnomalie signalementAnomalie = new SignalementAnomalie();
		signalementAnomalie.setDescription("Réponse carrement fausse");
		signalementAnomalie.setTypeAnomalie(TypeAnomalie.REPONSE_FAUSSE);

		dbConnecteurNPGDao.signalerAnomalie("25", Long.valueOf(1), signalementAnomalie, "Nicolas GENDRON");

		assertEquals(2, dbConnecteurNPGDao.listerAnomalies(Long.valueOf(0)).size());

		initDB();
	}

	@Test
	public void testJouerQuestion() throws Exception {
		assertEquals(4, dbConnecteurNPGDao.compterNbQuestionLue());

		dbConnecteurNPGDao.jouerQuestion("25", "Nicolas GENDRON");

		assertEquals(5, dbConnecteurNPGDao.compterNbQuestionLue());

		initDB();
	}

	@Test
	public void testCorrigerQuestion() throws Exception {
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(2));
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(3));

		Question9PGBdjDistante question = new Question9PGBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(3));
		question.setQuestion("Dans quel sport le néerlandais Tom Dumoulin s’illustre-t-il ?");
		question.setReponse("CYCLISME (SUR ROUTE)");
		question.setReference(Long.valueOf(26));
		question.setVersion(Long.valueOf(2));
		dbConnecteurNPGDao.corrigerQuestion(question);

		assertEquals(2, dbConnecteurNPGDao.compterNbQuestion(2));
		assertEquals(4, dbConnecteurNPGDao.compterNbQuestion(3));

		initDB();
	}

	@Test
	public void testCreerQuestion() throws Exception {
		assertEquals(3, dbConnecteurNPGDao.compterNbQuestion(3));

		Question9PGBdjDistante question = new Question9PGBdjDistante();
		question.setClub("LILLE");
		question.setDateEnvoi("2017-10-01");
		question.setDateIntegration("2017-11-01");
		question.setDifficulte(Long.valueOf(3));
		question.setQuestion("Dans quel sport le néerlandais Tom Dumoulin s’illustre-t-il ?");
		question.setReponse("CYCLISME (SUR ROUTE)");
		question.setReference(Long.valueOf(30));
		question.setVersion(Long.valueOf(2));

		dbConnecteurNPGDao.creerQuestion(question);

		assertEquals(4, dbConnecteurNPGDao.compterNbQuestion(3));

		initDB();
	}
}
