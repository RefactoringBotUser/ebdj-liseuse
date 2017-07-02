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

		String nomBdj = dbConnecteurParametrageDao.recupererParametrage("NOM_BDJ");
		String cleActivation = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		String adresseMac = Utils.recupererAdresseMac();

		AuthentificationBdj authentificationBdj = new AuthentificationBdj();
		authentificationBdj.setNomBdj(nomBdj);
		authentificationBdj.setCleAuthentification(cleActivation);

		return authentificationBdj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validerCleActivation() {

		// Faire un appel à un service rest de la boite pour la valider

		String cleActivation = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remplacerCleActivation(String ancienneCle, String nouvelleCle) {

		String cleExistante = dbConnecteurParametrageDao.recupererParametrage("CLE_ACTIVATION");

		if (cleExistante.equals(ancienneCle)) {
			dbConnecteurParametrageDao.modifierParametrage("CLE_ACTIVATION", nouvelleCle);
		} else {
			LOGGER.error("La clé existante ne correspond pas à la clé fournie");
		}
	}

}
