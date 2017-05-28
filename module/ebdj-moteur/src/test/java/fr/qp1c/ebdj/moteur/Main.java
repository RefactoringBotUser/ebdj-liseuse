package fr.qp1c.ebdj.moteur;

import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;

public class Main {

	// TODO : ajouter des tests unitaires sur la partie BDD

	public static void main(String[] args) {

		DBConnecteurNPGDaoImpl dao = new DBConnecteurNPGDaoImpl();
		dao.listerQuestionsJouable(5, -1);
	}

}
