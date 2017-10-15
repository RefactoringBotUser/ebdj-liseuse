package fr.qp1c.ebdj.liseuse.bdd.dao;

import java.util.List;

import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;

public interface DBConnecteurLecteurDao {

	public boolean testerExistanteLecteur(String nom, String prenom) throws DBManagerException;

	public Lecteur recupererLecteur(String reference) throws DBManagerException;

	public void ajouterLecteur(Lecteur Lecteur) throws DBManagerException;

	public void modifierLecteur(String reference, Lecteur lecteur) throws DBManagerException;

	public void supprimerLecteur(String reference) throws DBManagerException;

	public List<Lecteur> listerLecteur() throws DBManagerException;

}
