package fr.qp1c.ebdj.liseuse.synchronisation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurQALSDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.synchronisation.service.SynchronisationService;
import fr.qp1c.ebdj.liseuse.synchronisation.ws.SynchroWSHelper;

public class SynchronisationServiceImpl implements SynchronisationService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationServiceImpl.class);

	private DBConnecteurNPGDao dbConnecteur9PGDao;

	private DBConnecteurQALSDao dbConnecteur4ALSDao;

	private DBConnecteurJDDao dbConnecteurJDDao;

	private DBConnecteurFAFDao dbConnecteurFAFDao;

	private SynchroWSHelper wsCockpitHelper;

	public SynchronisationServiceImpl() {
		this.wsCockpitHelper = new SynchroWSHelper();
		this.dbConnecteur9PGDao = new DBConnecteurNPGDaoImpl();
		this.dbConnecteur4ALSDao = new DBConnecteurQALSDaoImpl();
		this.dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
		this.dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
	}

	@Override
	public void revelerStatut() throws BdjException {
		LOGGER.info("[DEBUT] revelerStatut");

		// Retrouver la référence max de la question
		Long referenceMax9PGExistante = dbConnecteur9PGDao.recupererReferenceMaxQuestion();

		// Retrouver la référence max de la question
		Long referenceMax4ALSExistante = dbConnecteur4ALSDao.recupererReferenceMaxQuestion();

		// Retrouver la référence max de la question
		Long referenceMaxJDExistante = dbConnecteurJDDao.recupererReferenceMaxQuestion();

		// Retrouver la référence max de la question
		Long referenceMaxFAFExistante = dbConnecteurFAFDao.recupererReferenceMaxQuestion();

		wsCockpitHelper.synchroniserStatutRelever(referenceMax9PGExistante, referenceMax4ALSExistante,
				referenceMaxJDExistante, referenceMaxFAFExistante);

		LOGGER.info("[FIN] revelerStatut");
	}

}
