package fr.qp1c.ebdj.liseuse.synchronisation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurQALSDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionTheme4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.TypeCorrection;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.Theme4ALSBdjDistante;
import fr.qp1c.ebdj.liseuse.synchronisation.service.Synchronisation4ALSService;
import fr.qp1c.ebdj.liseuse.synchronisation.utils.SynchronisationConstants;
import fr.qp1c.ebdj.liseuse.synchronisation.ws.Synchro4ALSWSHelper;

public class Synchronisation4ALSServiceImpl implements Synchronisation4ALSService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Synchronisation4ALSServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao;

	private DBConnecteurQALSDao dbConnecteur4ALSDao;

	private Synchro4ALSWSHelper wsCockpit4ALSHelper;

	public Synchronisation4ALSServiceImpl() {
		this.wsCockpit4ALSHelper = new Synchro4ALSWSHelper();
		this.dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();
		this.dbConnecteur4ALSDao = new DBConnecteurQALSDaoImpl();
	}

	@Override
	public void synchroniserCorrections4ALS() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserCorrections4ALS");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long indexReprise = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_4ALS_CORRECTION);

		List<CorrectionTheme4ALSBdjDistante> corrections = wsCockpit4ALSHelper
				.synchroniserCorrections4ALS(indexReprise);

		Long indexMax = Long.valueOf(0);

		for (CorrectionTheme4ALSBdjDistante correction : corrections) {
			if (TypeCorrection.DESACTIVER.equals(correction.getTypeCorrection())) {
				dbConnecteur4ALSDao.desactiverTheme(correction.getReference());
			} else if (TypeCorrection.REMPLACER.equals(correction.getTypeCorrection())) {
				dbConnecteur4ALSDao.corrigerTheme(correction.getTheme4ALSBdjDistante());
			}

			if (correction.getIndex().longValue() > indexMax.longValue()) {
				indexMax = correction.getIndex();
			}
		}

		if (indexMax > indexReprise) {
			dbConnecteurSynchroDao.modifierIndexParCle(SynchronisationConstants.CLE_4ALS_CORRECTION, indexMax);
		}

		LOGGER.info("[FIN] synchroniserCorrections4ALS");
	}

	@Override
	public void synchroniserAnomalies4ALS() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserAnomalies4ALS");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_4ALS_ANOMALIE);

		Long nouveauDernierIndex = dbConnecteur4ALSDao.recupererIndexMaxAnomalie();

		if (dernierIndex < nouveauDernierIndex) {
			// Lister les anomalies à synchroniser
			List<Anomalie> anomalies = dbConnecteur4ALSDao.listerAnomalies(dernierIndex);

			// Pusher les lectures sur le cockpit
			wsCockpit4ALSHelper.synchroniserAnomalies4ALS(anomalies);

			// Mettre à jour l'index de la dernière question synchronisée.
			dbConnecteurSynchroDao.modifierIndexParCle(SynchronisationConstants.CLE_4ALS_ANOMALIE, nouveauDernierIndex);
		}

		LOGGER.info("[FIN] synchroniserAnomalies4ALS");
	}

	@Override
	public void synchroniserLectures4ALS() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserLectures4ALS");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_4ALS_LECTURE);

		Long nouveauDernierIndex = dbConnecteur4ALSDao.recupererIndexMaxLecture();

		if (dernierIndex < nouveauDernierIndex) {
			// Lister les lectures à synchroniser
			List<Lecture> lectures = dbConnecteur4ALSDao.listerQuestionsLues(dernierIndex);

			// Pusher les lectures sur le cockpit
			wsCockpit4ALSHelper.synchroniserLectures4ALS(lectures);

			// Mettre à jour l'index de la dernière question synchronisée.
			dbConnecteurSynchroDao.modifierIndexParCle(SynchronisationConstants.CLE_4ALS_LECTURE, nouveauDernierIndex);
		}

		LOGGER.info("[FIN] synchroniserLectures4ALS");
	}

	@Override
	public void synchroniserThemes4ALS() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserThemes4ALS");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteur4ALSDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<Theme4ALSBdjDistante> themes = wsCockpit4ALSHelper.synchroniserTheme4ALS(referenceMaxExistante);

		for (Theme4ALSBdjDistante theme : themes) {
			dbConnecteur4ALSDao.creerTheme(theme);
		}

		LOGGER.info("[FIN] synchroniserThemes4ALS");
	}

}
