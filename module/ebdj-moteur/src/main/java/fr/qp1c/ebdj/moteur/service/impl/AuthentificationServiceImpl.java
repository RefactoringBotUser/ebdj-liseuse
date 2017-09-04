package fr.qp1c.ebdj.moteur.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.dao.DBConnecteurParametrageDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurParametrageDaoImpl;
import fr.qp1c.ebdj.moteur.service.AuthentificationService;
import fr.qp1c.ebdj.moteur.utils.Utils;
import fr.qp1c.ebdj.moteur.ws.wrapper.AuthentificationBdj;

public class AuthentificationServiceImpl implements AuthentificationService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationServiceImpl.class);

	private DBConnecteurParametrageDao dbConnecteurParametrageDao = new DBConnecteurParametrageDaoImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthentificationBdj recupererAuthentificationBdj() {
		LOGGER.info("[DEBUT] Récuperer les informations d'authentification.");

		String nomBdj = dbConnecteurParametrageDao.recupererParametrage("NOM_BDJ");
		String cleActivation = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		String adresseMac = Utils.recupererAdresseMac();

		LOGGER.debug("Adresse mac : {}", adresseMac);

		AuthentificationBdj authentificationBdj = new AuthentificationBdj();
		authentificationBdj.setNomBdj(nomBdj);
		authentificationBdj.setCleAuthentification(cleActivation);

		LOGGER.info("[FIN] Récuperer les informations d'authentification.");

		return authentificationBdj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validerCleActivation() {
		LOGGER.info("[DEBUT] Valider la clé d'authentification.");

		// Faire un appel à un service rest de la boite pour la valider

		String cleActivation = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		LOGGER.debug("Cle activation : {}", cleActivation);

		// TODO : a implementer

		LOGGER.info("[FIN] Valider la clé d'authentification.");

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remplacerCleActivation(String ancienneCle, String nouvelleCle) {
		LOGGER.info("[DEBUT] Remplacer la clé d'authentification.");

		String cleExistante = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		if (cleExistante.equals(ancienneCle)) {
			dbConnecteurParametrageDao.modifierParametrage("CLE_ACTIVATION", nouvelleCle);
		} else {
			LOGGER.error("La clé existante ne correspond pas à la clé fournie");
		}

		LOGGER.info("[FIN] Remplacer la clé d'authentification.");
	}

}
