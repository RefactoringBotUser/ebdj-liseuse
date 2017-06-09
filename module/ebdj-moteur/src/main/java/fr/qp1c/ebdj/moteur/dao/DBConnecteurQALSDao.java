package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.question.Anomalie;
import fr.qp1c.ebdj.moteur.bean.question.Question4ALS;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;

public interface DBConnecteurQALSDao {

	/**
	 * Tirer au sort 4 thèmes de 4ALS dans 4 catégories différentes.
	 * 
	 * @return
	 */
	public List<Question4ALS> listerThemeJouable() throws DBManagerException;

	/**
	 * Tirer au sort un thème de 4ALS dans une catégorie donnée.
	 * 
	 * @param categorieTheme
	 *            catégorie de thème choisie
	 * @return le thème de 4ALS
	 */
	public Question4ALS donnerTheme(int categorie);

	/**
	 * 
	 * @param referenceTheme
	 * @param lecteur
	 * @throws DBManagerException
	 */
	public void jouerTheme(String referenceTheme, String lecteur) throws DBManagerException;

	/**
	 * 
	 * @param referenceTheme
	 * @param anomalie
	 * @param lecteur
	 * @throws DBManagerException
	 */
	public void signalerAnomalie(String referenceTheme, Anomalie anomalie, String lecteur) throws DBManagerException;

	/**
	 * Compter le nombre total de thème existante.
	 * 
	 * @return le nombre total de thème existante.
	 */
	public int compterNbTheme();

	/**
	 * Compter le nombre total de thème existante dans une catégorie.
	 * 
	 * @return le nombre total de thème existante dans une catégorie.
	 */
	public int compterNbTheme(int categorie);

	/**
	 * Compter le nombre total de thème jouable.
	 * 
	 * @return le nombre de thème jouable.
	 */
	public int compterNbThemeJouee();

	/**
	 * Compter le nombre total de thème jouable.
	 * 
	 * @return le nombre de thème jouable.
	 */
	public int compterNbThemeJouee(int categorie);
}
