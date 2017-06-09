package fr.qp1c.ebdj.moteur.dao.impl;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public class DBConnecteurLecteurDaoImpl implements DBConnecteurLecteurDao {

	@Override
	public void ajouterLecteur(Lecteur lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifierLecteur(String reference, Lecteur lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Lecteur> listerLecteur() throws DBManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lecteur recupererLecteur(String reference) throws DBManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testerExistanteLecteur(String nom, String prenom) throws DBManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void supprimerLecteur(String reference) throws DBManagerException {
		// TODO Auto-generated method stub

	}

}
