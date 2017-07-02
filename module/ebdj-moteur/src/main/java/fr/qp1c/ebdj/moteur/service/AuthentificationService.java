package fr.qp1c.ebdj.moteur.service;

import fr.qp1c.ebdj.moteur.ws.wrapper.AuthentificationBdj;

public interface AuthentificationService {

	/**
	 * Récuperer la clé d'activation de la boite de jeu.
	 * 
	 * @return la clé d'activation configurée.
	 */
	public AuthentificationBdj recupererAuthentificationBdj();

	/**
	 * Valider la clé d'activation configuré.
	 * 
	 * @return true - la clé est valide<br>
	 *         false - sinon
	 */
	public boolean validerCleActivation();

	/**
	 * Modifier la clé d'activation par une nouvelle.
	 * 
	 * @param ancienneCle
	 *            la clé actuelle
	 * @param nouvelleCle
	 *            la nouvelle clé
	 */
	public void remplacerCleActivation(String ancienneCle, String nouvelleCle);
}
