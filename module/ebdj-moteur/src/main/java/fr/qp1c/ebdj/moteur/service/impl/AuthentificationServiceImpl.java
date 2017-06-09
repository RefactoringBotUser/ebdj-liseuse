package fr.qp1c.ebdj.moteur.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.authentification.CleAuthentification;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurParametrageDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurParametrageDaoImpl;
import fr.qp1c.ebdj.moteur.service.AuthentificationService;
import fr.qp1c.ebdj.moteur.utils.Utils;

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
	public CleAuthentification recupererCleActivation() {

		String token = dbConnecteurParametrageDao.recupererCleActivation();

		String adresseMac = Utils.recupererAdresseMac();

		return new CleAuthentification(token, adresseMac);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validerCleActivation() {
		// TODO Auto-generated method stub

		// Faire un appel à un service rest de la boite pour la valider

		String cleActivation = dbConnecteurParametrageDao.recupererCleActivation();

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remplacerCleActivation(String ancienneCle, String nouvelleCle) {

		String cleExistante = dbConnecteurParametrageDao.recupererCleActivation();

		if (cleExistante.equals(ancienneCle)) {
			dbConnecteurParametrageDao.modifierCleActivation(nouvelleCle);
		} else {
			LOGGER.error("La clé existante ne correspond pas à la clé fournie");
		}
	}

}
