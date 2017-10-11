package fr.qp1c.ebdj.moteur;

import fr.qp1c.ebdj.moteur.service.ParametrageService;
import fr.qp1c.ebdj.moteur.service.impl.ParametrageServiceImpl;

public class TestMain {

	public static void main(String[] args) {
		// Synchronisation9PGService synchronisation9pgService = new
		// Synchronisation9PGServiceImpl();
		// SynchronisationJDService synchronisationJdService = new
		// SynchronisationJDServiceImpl();
		// SynchronisationFAFService synchronisationFafService = new
		// SynchronisationFAFServiceImpl();

		// System.out.println(Utils.formaterDate("2017-11-23"));
		ParametrageService ps=new ParametrageServiceImpl();
		
		System.out.println(ps.afficherVersionApplication());
		
		System.out.println(ps.afficherFichierParametrage());
	}

}
