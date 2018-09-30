package fr.qp1c.ebdj.liseuse.bdd.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;

public class ParametrageServiceImplTest {

	private ParametrageService parametrageService;

	@Before
	public void setUp() throws Exception {
		parametrageService = new ParametrageServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAfficherFichierParametrage() {
		// fail("Not yet implemented");
	}

	@Test
	public void testAfficherVersionApplication() {
		System.out.println(parametrageService.afficherVersionApplication());
	}

}
