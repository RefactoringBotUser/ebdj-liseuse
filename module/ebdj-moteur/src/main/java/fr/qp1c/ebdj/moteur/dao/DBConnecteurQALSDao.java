package fr.qp1c.ebdj.moteur.dao;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.question.SignalementAnomalie;
import fr.qp1c.ebdj.moteur.bean.question.Theme4ALS;
import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.utils.exception.DBManagerException;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Theme4ALSBdjDistante;

public interface DBConnecteurQALSDao {

	/**
	 * Tirer au sort 4 thèmes de 4ALS dans 4 catégories différentes.
	 * 
	 * @return
	 */
	public List<Theme4ALS> listerThemeJouable() throws DBManagerException;

	/**
	 * Tirer au sort un thème de 4ALS dans une catégorie donnée.
	 * 
	 * @param categorieTheme
	 *            catégorie de thème choisie
	 * @return le thème de 4ALS
	 */
	public Theme4ALS donnerTheme(int categorie);

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
	public void signalerAnomalie(String referenceTheme, Long version, SignalementAnomalie anomalie, String lecteur)
			throws DBManagerException;

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

	public void creerTheme(Theme4ALSBdjDistante theme4alsBdjDistante);

	public void desactiverTheme(String reference);

	public void corrigerTheme(Theme4ALSBdjDistante theme4alsBdjDistante);

	public List<Anomalie> listerAnomalies(Long indexDebut);

	public List<Lecture> listerQuestionsLues(Long indexDebut);

	public Long recupererIndexMaxAnomalie();

	public Long recupererIndexMaxLecture();

	public Long recupererReferenceMaxQuestion();
}
