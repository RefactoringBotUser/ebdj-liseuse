package fr.qp1c.ebdj.moteur;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;
import fr.qp1c.ebdj.liseuse.bdd.service.impl.ParametrageServiceImpl;

public class TestMain {

	public static void main(String[] args) {

		ParametrageService ps=new ParametrageServiceImpl();
		
		System.out.println(ps.afficherVersionApplication());
		
		System.out.println(ps.afficherFichierParametrage());
	}

}
