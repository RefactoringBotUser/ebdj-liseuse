package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.DatabaseTest;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurParametrageDao;

public class DBConnecteurParametrageDaoImplTest  extends DatabaseTest {

	private DBConnecteurParametrageDao dbConnecteurParametrageDao;

	@BeforeClass
	public static void initDB() throws Exception {
		createSchema();
		importDataSet("dataset-parametrage.xml");
	}

	@Before
	public void setUp() throws Exception {
		dbConnecteurParametrageDao = new DBConnecteurParametrageDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRecupererParametrage() {
		assertEquals("1936a18b-898d-467a-bc1b-f5d91604f8a8",dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION"));
	}

	@Test
	public void testModifierParametrage() throws Exception {
		dbConnecteurParametrageDao.modifierParametrage("CLE_ACTIVATION","1936a18b-898d-467a-bc1b-f5d91604f8a9");
		assertEquals("1936a18b-898d-467a-bc1b-f5d91604f8a9",dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION"));
		
		initDB() ;
	}
}
