package fr.qp1c.ebdj.liseuse.bdd.dao;

import java.util.List;

import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;

public interface DBConnecteurLecteurDao {

	public boolean testerExistanteLecteur(String nom, String prenom);

	public Lecteur recupererLecteur(String reference);

	public void ajouterLecteur(Lecteur lecteur);

	public void modifierLecteur(String reference, Lecteur lecteur);

	public void supprimerLecteur(String reference);

	public List<Lecteur> listerLecteur();

}
