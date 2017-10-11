package fr.qp1c.ebdj.moteur;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.qp1c.ebdj.moteur.service.ParametrageService;
import fr.qp1c.ebdj.moteur.service.impl.ParametrageServiceImpl;

public class ParametrageServiceTest {

	private static ParametrageService parametrageService=new ParametrageServiceImpl();
	
    protected void setUp() throws Exception {
		
	}
	
	@Test
	public void testAfficherVersionApplication() {
		assertEquals("1.0.0-SNAPSHOT", parametrageService.afficherVersionApplication());
	}

}
