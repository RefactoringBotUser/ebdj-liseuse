package fr.ebdj.liseuse.synchronisation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.commun.bean.question.TypePhase;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFormaterReference() {
		assertEquals("Q_9PG_00001", Utils.formaterReference("1", TypePhase.NPG));
		assertEquals("Q_4ALS_0001", Utils.formaterReference("1", TypePhase.QALS));
		assertEquals("Q_JD_00001", Utils.formaterReference("1", TypePhase.JD));
		assertEquals("Q_FAF_00001", Utils.formaterReference("1", TypePhase.FAF));
	}

	@Test
	@Ignore
	public void testRecupererAdresseMac() {
		assertNotNull(Utils.recupererAdresseMac());
	}

	@Test
	public void testRecupererDateHeureCourante() {
		assertNotNull(Utils.recupererDateHeureCourante());
	}

}
