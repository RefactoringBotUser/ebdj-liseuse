package fr.qp1c.ebdj.liseuse.bdd.dao;

import java.util.List;
import java.util.Map;

import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.question.Theme4ALS;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Theme4ALSBdjDistante;

public interface DBConnecteurQALSDao {

	/**
	 * Tirer au sort un thème de 4ALS dans une catégorie donnée.
	 * 
	 * @param categorieTheme
	 *            catégorie de thème choisie
	 * @return le thème de 4ALS
	 */
	public Theme4ALS donnerTheme(int categorie, int niveau);

	/**
	 * 
	 * 
	 * @param referenceTheme
	 * @param lecteur
	 * @throws DBManagerException
	 */
	public void marquerThemePresente(String referenceTheme, String lecteur) throws DBManagerException;

	/**
	 * 
	 * 
	 * @param referenceTheme
	 * @param lecteur
	 * @throws DBManagerException
	 */
	public void marquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException;

	/**
	 * 
	 * 
	 * @param referenceTheme
	 * @param lecteur
	 * @throws DBManagerException
	 */
	public void annulerMarquerThemeJoue(String referenceTheme, String lecteur) throws DBManagerException;

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

	public Map<String, Map<String, Long>> compterParGroupeCategorie();

	public Map<String, Map<String, Long>> compterParGroupeCategorieLue();
}
