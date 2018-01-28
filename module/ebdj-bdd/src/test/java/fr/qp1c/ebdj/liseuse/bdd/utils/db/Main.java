package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class Main {

	public static void main(String[] args) {
		// System.out.println(Configuration.getInstance().getCockpitUrl());
		// System.out.println(Configuration.getInstance().getCockpitBdjNom());
		// System.out.println(Configuration.getInstance().getCockpitBdjCle());

		System.out.println(String.format("UPDATE SYNCHRO SET valeur=%d, date_derniere_synchro='%s' WHERE cle='%s';", 2,
				Utils.recupererDateHeureCourante(), "cle"));
	}
}
