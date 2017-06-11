package fr.qp1c.ebdj.moteur.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurLecteurDaoImpl;
import fr.qp1c.ebdj.moteur.service.LecteurService;

public class LecteurServiceImpl implements LecteurService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LecteurServiceImpl.class);

	private DBConnecteurLecteurDao dbConnecteurLecteurDao = new DBConnecteurLecteurDaoImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void ajouterLecteur(Lecteur lecteur) {

		if (lecteur == null) {
			LOGGER.error("Le paramètre lecteur est null.");
		} else if (lecteur.getNom() == null) {
			LOGGER.error("Le paramètre lecteur.nom est null.");
		} else if (lecteur.getPrenom() == null) {
			LOGGER.error("Le paramètre lecteur.prenom est null.");
		}

		// Vérifier si le lecteur existe déjà
		if (!dbConnecteurLecteurDao.testerExistanteLecteur(lecteur.getNom(), lecteur.getPrenom())) {
			dbConnecteurLecteurDao.ajouterLecteur(lecteur);
		} else {
			LOGGER.error("Le lecteur %s %s existe déjà.", lecteur.getNom(), lecteur.getPrenom());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifierLecteur(String reference, Lecteur lecteur) {

		if (reference == null) {
			LOGGER.error("Le paramètre reference est null.");
		} else if (lecteur == null) {
			LOGGER.error("Le paramètre lecteur est null.");
		} else if (lecteur.getNom() == null) {
			LOGGER.error("Le paramètre lecteur.nom est null.");
		} else if (lecteur.getPrenom() == null) {
			LOGGER.error("Le paramètre lecteur.prenom est null.");
		}

		// Vérifier si le lecteur existe déjà
		Lecteur lecteurExistant = dbConnecteurLecteurDao.recupererLecteur(reference);

		if (lecteurExistant == null) {
			dbConnecteurLecteurDao.ajouterLecteur(lecteur);
		} else {
			lecteurExistant.setNom(lecteur.getNom());
			lecteurExistant.setPrenom(lecteur.getPrenom());

			dbConnecteurLecteurDao.modifierLecteur(reference, lecteur);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerLecteur(String reference) {

		if (reference == null) {
			LOGGER.error("Le paramètre reference est null.");
		} else {
			dbConnecteurLecteurDao.supprimerLecteur(reference);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Lecteur> listerLecteur() {

		return dbConnecteurLecteurDao.listerLecteur();
	}
}