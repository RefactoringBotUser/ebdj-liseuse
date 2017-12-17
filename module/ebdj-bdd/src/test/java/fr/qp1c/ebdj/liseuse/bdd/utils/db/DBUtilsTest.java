package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBUtils;

public class DBUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEscapeSql() {
		assertEquals(null,DBUtils.escapeSql(null));
		assertEquals("L''histoire de l''humanité.",DBUtils.escapeSql("L'histoire de l'humanité."));
	}

}
