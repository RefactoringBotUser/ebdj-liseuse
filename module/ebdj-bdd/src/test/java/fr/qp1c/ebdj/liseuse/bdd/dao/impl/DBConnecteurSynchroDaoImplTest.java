package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;

public class DBConnecteurSynchroDaoImplTest extends DatabaseTest {

	private DBConnecteurSynchroDao dbConnecteurSynchroDao;

	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-synchro.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testRecupererIndexParCle() {
		assertEquals(Long.valueOf(0),dbConnecteurSynchroDao.recupererIndexParCle("JD_CORRECTION"));
		assertEquals(Long.valueOf(4),dbConnecteurSynchroDao.recupererIndexParCle("JD_ANOMALIE"));
		assertEquals(Long.valueOf(1234),dbConnecteurSynchroDao.recupererIndexParCle("JD_LECTURE"));
	}

	@Test
	public void testModifierIndexParCle() {
		dbConnecteurSynchroDao.modifierIndexParCle("JD_LECTURE", Long.valueOf(1235));
		assertEquals(Long.valueOf(1235),dbConnecteurSynchroDao.recupererIndexParCle("JD_LECTURE"));
	}

}
