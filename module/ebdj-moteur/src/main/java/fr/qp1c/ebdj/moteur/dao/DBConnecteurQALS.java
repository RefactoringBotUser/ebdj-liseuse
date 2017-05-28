package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.question.Question4ALS;

public interface DBConnecteurQALS {

	/**
	 * Tirer au sort 4 thèmes de 4ALS dans 4 catégories différentes.
	 * 
	 * @return
	 */
	public List<Question4ALS> listerTheme();

	/**
	 * Tirer au sort un thème de 4ALS dans une catégorie donnée.
	 * 
	 * @param categorieTheme
	 *            catégorie de thème choisie
	 * @return le thème de 4ALS
	 */
	public Question4ALS donnerTheme(String categorieTheme);

	public boolean marquerTheme(String referenceTheme, String etat);
}
