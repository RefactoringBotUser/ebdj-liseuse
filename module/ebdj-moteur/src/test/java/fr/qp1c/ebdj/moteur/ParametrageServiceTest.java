package fr.qp1c.ebdj.moteur;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;
import fr.qp1c.ebdj.liseuse.bdd.service.impl.ParametrageServiceImpl;

public class ParametrageServiceTest {

	private static ParametrageService parametrageService=new ParametrageServiceImpl();
	
    protected void setUp() throws Exception {
		
	}
	
    @Ignore
	@Test
	public void testAfficherVersionApplication() {
		assertEquals("1.0.0-SNAPSHOT", parametrageService.afficherVersionApplication());
	}

}
