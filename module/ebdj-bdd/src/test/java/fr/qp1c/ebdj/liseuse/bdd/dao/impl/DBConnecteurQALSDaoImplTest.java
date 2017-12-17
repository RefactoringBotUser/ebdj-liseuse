package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.TypeAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Question4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Theme4ALSBdjDistante;

public class DBConnecteurQALSDaoImplTest extends DatabaseTest {

	private DBConnecteurQALSDao dbConnecteurQALSDao;


	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-qals.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurQALSDao = new DBConnecteurQALSDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMarquerThemePresente() throws Exception{
		assertEquals(4,dbConnecteurQALSDao.compterNbThemePresente());
		
		dbConnecteurQALSDao.marquerThemePresente("23", "GENDRON Nicolas");

		assertEquals(5,dbConnecteurQALSDao.compterNbThemePresente());

		initDB();
	}

	@Test
	public void testMarquerThemeJoue() throws Exception{
		assertEquals(3,dbConnecteurQALSDao.compterNbThemeJoue());
		
		dbConnecteurQALSDao.marquerThemeJoue("22", "GENDRON Nicolas");

		assertEquals(4,dbConnecteurQALSDao.compterNbThemeJoue());

		initDB();
	}

	@Test
	public void testAnnulerMarquerThemeJoue() throws Exception{
		assertEquals(3,dbConnecteurQALSDao.compterNbThemeJoue());
		
		dbConnecteurQALSDao.annulerMarquerThemeJoue("21", "GENDRON Nicolas");

		assertEquals(2,dbConnecteurQALSDao.compterNbThemeJoue());

		initDB();
	}

	@Test
	public void testDonnerTheme() {
		Theme4ALS theme=dbConnecteurQALSDao.donnerTheme(1, 2);
		
		assertNotNull(theme);
		assertEquals(Long.valueOf(2),theme.getDifficulte());
		assertEquals(Long.valueOf(1),theme.getGroupeCategorieRef());
		assertEquals("L’HISTOIRE DU MEXIQUE",theme.getTheme());
	}

	@Test
	public void testCreerTheme() throws Exception{
		assertEquals(2,dbConnecteurQALSDao.compterNbTheme(1));
		
		Map<Integer, Question4ALSBdjDistante> questions=new HashMap<>();
		
		for(int i=0;i<12;i++) {
			Question4ALSBdjDistante question=new Question4ALSBdjDistante();
			question.setSeq(Integer.valueOf(i));
			question.setReponse("En tennis, des soeurs Williams, quelle est l’aînée ?");
			question.setQuestion("VENUS");
			
			questions.put(Integer.valueOf(i), question);
		}
		
		Theme4ALSBdjDistante theme=new Theme4ALSBdjDistante();
		theme.setCategorie4ALS("Musique");
		theme.setCategorie4ALSRef(Long.valueOf(4));
		theme.setClub("LILLE");
		theme.setDateEnvoi("2017-10-01");
		theme.setDateIntegration("2017-11-01");
		theme.setDifficulte(Long.valueOf(3));
		theme.setGroupeCategorie4ALS(Long.valueOf(1));
		theme.setQuestions(questions);
		theme.setReference(Long.valueOf(28));
		theme.setTheme("AUTOUR DU PIANO");
		theme.setVersion(Long.valueOf(2));
		
		dbConnecteurQALSDao.creerTheme(theme);
		
		assertEquals(3,dbConnecteurQALSDao.compterNbTheme(1));

		initDB();
	}

	@Test
	public void testCorrigerTheme() throws Exception{
		assertEquals(2,dbConnecteurQALSDao.compterNbTheme(1));
		
		Map<Integer, Question4ALSBdjDistante> questions=new HashMap<>();
		
		for(int i=0;i<12;i++) {
			Question4ALSBdjDistante question=new Question4ALSBdjDistante();
			question.setSeq(Integer.valueOf(i));
			question.setReponse("En tennis, des soeurs Williams, quelle est l’aînée ?");
			question.setQuestion("VENUS");
			
			questions.put(Integer.valueOf(i), question);
		}
		
		Theme4ALSBdjDistante theme=new Theme4ALSBdjDistante();
		theme.setCategorie4ALS("Musique");
		theme.setCategorie4ALSRef(Long.valueOf(4));
		theme.setClub("LILLE");
		theme.setDateEnvoi("2017-10-01");
		theme.setDateIntegration("2017-11-01");
		theme.setDifficulte(Long.valueOf(3));
		theme.setGroupeCategorie4ALS(Long.valueOf(1));
		theme.setQuestions(questions);
		theme.setReference(Long.valueOf(28));
		theme.setTheme("AUTOUR DU PIANO");
		theme.setVersion(Long.valueOf(2));
		
		dbConnecteurQALSDao.corrigerTheme(theme);
		
		assertEquals(2,dbConnecteurQALSDao.compterNbTheme(1));

		initDB();
	}

	@Test
	public void testSignalerAnomalie() throws Exception{
		assertEquals(1, dbConnecteurQALSDao.listerAnomalies(Long.valueOf(0)).size());

		SignalementAnomalie signalementAnomalie = new SignalementAnomalie();
		signalementAnomalie.setDescription("Réponse carrement fausse");
		signalementAnomalie.setTypeAnomalie(TypeAnomalie.REPONSE_FAUSSE);

		dbConnecteurQALSDao.signalerAnomalie("20", Long.valueOf(1), signalementAnomalie, "Nicolas GENDRON");

		assertEquals(2, dbConnecteurQALSDao.listerAnomalies(Long.valueOf(0)).size());

		initDB();
	}

	@Test
	public void testCompterNbTheme() {
		assertEquals(8, dbConnecteurQALSDao.compterNbTheme());
	}

	@Test
	public void testCompterNbThemeByCategorie() {
		assertEquals(2, dbConnecteurQALSDao.compterNbTheme(1));
		assertEquals(2, dbConnecteurQALSDao.compterNbTheme(2));
		assertEquals(2, dbConnecteurQALSDao.compterNbTheme(3));
		assertEquals(2, dbConnecteurQALSDao.compterNbTheme(4));
	}

	@Test
	public void testCompterNbThemeJouee() {
		assertEquals(3, dbConnecteurQALSDao.compterNbThemeJoue());
	}

	@Test
	public void testCompterNbThemeJoueByCategorie() {
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(1));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(2));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(3));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(4));
	}

	@Test
	public void testCompterNbThemePresente() {
		assertEquals(3, dbConnecteurQALSDao.compterNbThemeJoue());
	}

	@Test
	public void testCompterNbThemePresenteByCategorie() {
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(1));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(2));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(3));
		assertEquals(1, dbConnecteurQALSDao.compterNbThemePresente(4));
	}

	@Test
	public void testDesactiverTheme() throws Exception{
		assertEquals(8, dbConnecteurQALSDao.compterNbTheme());

		dbConnecteurQALSDao.desactiverTheme("25");

		assertEquals(7, dbConnecteurQALSDao.compterNbTheme());

		initDB();
	}

	@Test
	public void testListerAnomalies() {
		assertEquals(1, dbConnecteurQALSDao.listerAnomalies(Long.valueOf(0)).size());
		assertEquals(0, dbConnecteurQALSDao.listerAnomalies(Long.valueOf(1)).size());
	}

	@Test
	public void testListerQuestionsLues() {
		assertEquals(3, dbConnecteurQALSDao.listerQuestionsLues(Long.valueOf(0)).size());
		assertEquals(2, dbConnecteurQALSDao.listerQuestionsLues(Long.valueOf(1)).size());
	}

	@Test
	public void testRecupererIndexMaxAnomalie() {
		assertEquals(Long.valueOf(1), dbConnecteurQALSDao.recupererIndexMaxAnomalie());
	}

	@Test
	public void testRecupererIndexMaxLecture() {
		assertEquals(Long.valueOf(3), dbConnecteurQALSDao.recupererIndexMaxLecture());
	}

	@Test
	public void testRecupererReferenceMaxQuestion() {
		assertEquals(Long.valueOf(27), dbConnecteurQALSDao.recupererReferenceMaxQuestion());
	}

	@Test
	public void testCompterParGroupeCategorie() {
		Map<String,Map<String,Long>> results=dbConnecteurQALSDao.compterParGroupeCategorie();
		
		assertNotNull(results);
		
		// Groupe 1
		assertNotNull(results.get("1"));
		assertEquals(Long.valueOf(0),results.get("1").get("1"));
		assertEquals(Long.valueOf(2),results.get("1").get("2"));
		assertEquals(Long.valueOf(0),results.get("1").get("3"));
		assertEquals(Long.valueOf(0),results.get("1").get("4"));
		
		// Groupe 2
		assertNotNull(results.get("2"));
		assertEquals(Long.valueOf(0),results.get("2").get("1"));
		assertEquals(Long.valueOf(2),results.get("2").get("2"));
		assertEquals(Long.valueOf(0),results.get("2").get("3"));
		assertEquals(Long.valueOf(0),results.get("2").get("4"));
		
		// Groupe 3
		assertNotNull(results.get("3"));
		assertEquals(Long.valueOf(0),results.get("3").get("1"));
		assertEquals(Long.valueOf(2),results.get("3").get("2"));
		assertEquals(Long.valueOf(0),results.get("3").get("3"));
		assertEquals(Long.valueOf(0),results.get("3").get("4"));
		
		// Groupe 4
		assertNotNull(results.get("4"));
		assertEquals(Long.valueOf(0),results.get("4").get("1"));
		assertEquals(Long.valueOf(2),results.get("4").get("2"));
		assertEquals(Long.valueOf(0),results.get("4").get("3"));
		assertEquals(Long.valueOf(0),results.get("4").get("4"));
	}

	@Test
	public void testCompterParGroupeCategorieLue() {
		Map<String,Map<String,Long>> results=dbConnecteurQALSDao.compterParGroupeCategorieLue();
		
		assertNotNull(results);
		
		// Groupe 1
		assertNotNull(results.get("1"));
		assertEquals(Long.valueOf(0),results.get("1").get("1"));
		assertEquals(Long.valueOf(1),results.get("1").get("2"));
		assertEquals(Long.valueOf(0),results.get("1").get("3"));
		assertEquals(Long.valueOf(0),results.get("1").get("4"));
		
		// Groupe 2
		assertNotNull(results.get("2"));
		assertEquals(Long.valueOf(0),results.get("2").get("1"));
		assertEquals(Long.valueOf(1),results.get("2").get("2"));
		assertEquals(Long.valueOf(0),results.get("2").get("3"));
		assertEquals(Long.valueOf(0),results.get("2").get("4"));
		
		// Groupe 3
		assertNotNull(results.get("3"));
		assertEquals(Long.valueOf(0),results.get("3").get("1"));
		assertEquals(Long.valueOf(1),results.get("3").get("2"));
		assertEquals(Long.valueOf(0),results.get("3").get("3"));
		assertEquals(Long.valueOf(0),results.get("3").get("4"));
		
		// Groupe 4
		assertNotNull(results.get("4"));
		assertEquals(Long.valueOf(0),results.get("4").get("1"));
		assertEquals(Long.valueOf(0),results.get("4").get("2"));
		assertEquals(Long.valueOf(0),results.get("4").get("3"));
		assertEquals(Long.valueOf(0),results.get("4").get("4"));
	}
}
