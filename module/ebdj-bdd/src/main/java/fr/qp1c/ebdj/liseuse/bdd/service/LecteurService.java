package fr.qp1c.ebdj.liseuse.bdd.service;

import java.util.List;

import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;

public interface LecteurService {

	/**
	 * Créer un nouveau lecteur avec son paramétrage associé.
	 * 
	 * @param lecteur
	 *            le lecteur à créer
	 */
	public void ajouterLecteur(Lecteur lecteur);

	/**
	 * Modifier un lecteur existant ou l'ajouter si il n'existe pas encore.
	 * 
	 * @param reference
	 *            la référence du lecteur
	 * @param lecteur
	 *            le lecteur à modifier ou créer
	 */
	public void modifierLecteur(String reference, Lecteur lecteur);

	/**
	 * Supprimer le lecteur donné.
	 * 
	 * @param reference
	 *            la référence du lecteur
	 */
	public void supprimerLecteur(String reference);

	/**
	 * Lister l'ensemble des lecteurs.
	 * 
	 * @return la liste des lecteurs
	 */
	public List<Lecteur> listerLecteur();
}
