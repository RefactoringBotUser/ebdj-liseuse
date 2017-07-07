package fr.qp1c.ebdj.moteur;

import fr.qp1c.ebdj.moteur.service.Synchronisation9PGService;
import fr.qp1c.ebdj.moteur.service.SynchronisationFAFService;
import fr.qp1c.ebdj.moteur.service.SynchronisationJDService;
import fr.qp1c.ebdj.moteur.service.impl.Synchronisation9PGServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationFAFServiceImpl;
import fr.qp1c.ebdj.moteur.service.impl.SynchronisationJDServiceImpl;

public class TestMain {

	public static void main(String[] args) {
		Synchronisation9PGService synchronisation9pgService = new Synchronisation9PGServiceImpl();
		SynchronisationJDService synchronisationJdService = new SynchronisationJDServiceImpl();
		SynchronisationFAFService synchronisationFafService = new SynchronisationFAFServiceImpl();

	}

}
