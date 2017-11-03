package fr.ebdj.liseuse.synchronisation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.commun.utils.StringUtilities;

public class StringUtilitiesTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompterNombreDeMots() {
		assertEquals(4,StringUtilities.compterNombreDeMots("Ceci est un test."));
	}

	@Test
	public void testFormaterDate() {
		assertEquals("20/07/2017",StringUtilities.formaterDate("2017-07-20"));
	}

	@Test	
	public void testFormaterNumeroQuestion() {
		assertEquals(" 1 -",StringUtilities.formaterNumeroQuestion(1));
		assertEquals("15 -",StringUtilities.formaterNumeroQuestion(15));
	}
	
	@Test	
	public void testPadWithInt() {
		assertEquals("    2", StringUtilities.pad(5, ' ', 2));
		assertEquals("2", StringUtilities.pad(1, ' ', 2));
	}
	
	@Test	
	public void testPadWithString() {		
		assertEquals("     ref", StringUtilities.pad(8, ' ', "ref"));
		assertEquals("ref", StringUtilities.pad(3, ' ', "ref"));
	}
}
