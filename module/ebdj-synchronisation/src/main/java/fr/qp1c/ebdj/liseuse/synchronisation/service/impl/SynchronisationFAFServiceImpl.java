package fr.qp1c.ebdj.liseuse.synchronisation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionQuestionFAFBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.TypeCorrection;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;
import fr.qp1c.ebdj.liseuse.synchronisation.service.SynchronisationFAFService;
import fr.qp1c.ebdj.liseuse.synchronisation.ws.SynchroFAFWSHelper;

public class SynchronisationFAFServiceImpl implements SynchronisationFAFService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationFAFServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao;

	private DBConnecteurFAFDao dbConnecteurFAFDao;

	private SynchroFAFWSHelper wsCockpitFAFHelper;

	public SynchronisationFAFServiceImpl() {
		this.wsCockpitFAFHelper = new SynchroFAFWSHelper();
		this.dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();
		this.dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();
	}

	@Override
	public void synchroniserQuestionsFAF() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserQuestionsFAF");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteurFAFDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<QuestionFAFBdjDistante> questions = wsCockpitFAFHelper.synchroniserQuestionsFAF(referenceMaxExistante);

		for (QuestionFAFBdjDistante question : questions) {
			dbConnecteurFAFDao.creerQuestion(question);
		}

		LOGGER.info("[FIN] synchroniserQuestionsFAF");
	}

	@Override
	public void synchroniserCorrectionsFAF() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserCorrectionsFAF");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long indexReprise = dbConnecteurSynchroDao.recupererIndexParCle("FAF_CORRECTION");

		List<CorrectionQuestionFAFBdjDistante> corrections = wsCockpitFAFHelper
				.synchroniserCorrectionsFAF(indexReprise);

		Long indexMax = Long.valueOf(0);

		for (CorrectionQuestionFAFBdjDistante correction : corrections) {
			if (TypeCorrection.DESACTIVER.equals(correction.getTypeCorrection())) {
				dbConnecteurFAFDao.desactiverQuestion(correction.getReference());
			} else if (TypeCorrection.REMPLACER.equals(correction.getTypeCorrection())) {
				dbConnecteurFAFDao.corrigerQuestion(correction.getQuestionFafBdjDistante());
			}

			if (correction.getIndex().longValue() > indexMax.longValue()) {
				indexMax = correction.getIndex();
			}
		}

		dbConnecteurSynchroDao.modifierIndexParCle("FAF_CORRECTION", indexMax);

		LOGGER.info("[FIN] synchroniserCorrectionsFAF");
	}

	@Override
	public void synchroniserAnomaliesFAF() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserAnomaliesFAF");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("FAF_ANOMALIE");

		// Lister les anomalies à synchroniser
		List<Anomalie> anomalies = dbConnecteurFAFDao.listerAnomalies(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurFAFDao.recupererIndexMaxAnomalie();

		// Pusher les lectures sur le cockpit
		wsCockpitFAFHelper.synchroniserAnomaliesFAF(anomalies);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("FAF_ANOMALIE", nouveauDernierIndex);

		LOGGER.info("[FIN] synchroniserAnomaliesFAF");
	}

	@Override
	public void synchroniserLecturesFAF() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserLecturesFAF");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("FAF_LECTURE");

		// Lister les lectures à synchroniser
		List<Lecture> lectures = dbConnecteurFAFDao.listerQuestionsLues(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurFAFDao.recupererIndexMaxLecture();

		// Pusher les lectures sur le cockpit
		wsCockpitFAFHelper.synchroniserLecturesFAF(lectures);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("FAF_LECTURE", nouveauDernierIndex);

		LOGGER.info("[FIN] synchroniserLecturesFAF");
	}

}
