package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public interface DBConnecteurLecteurDao {

	public boolean testerExistanteLecteur(String nom, String prenom) throws DBManagerException;

	public Lecteur recupererLecteur(String reference) throws DBManagerException;

	public void ajouterLecteur(Lecteur Lecteur) throws DBManagerException;

	public void modifierLecteur(String reference, Lecteur lecteur) throws DBManagerException;

	public void supprimerLecteur(String reference) throws DBManagerException;

	public List<Lecteur> listerLecteur() throws DBManagerException;

}
