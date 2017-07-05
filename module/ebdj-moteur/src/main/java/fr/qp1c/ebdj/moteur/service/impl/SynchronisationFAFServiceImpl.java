package fr.qp1c.ebdj.moteur.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.moteur.service.SynchronisationFAFService;
import fr.qp1c.ebdj.moteur.ws.SynchroFAFWSHelper;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionFAFBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.TypeCorrection;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionFAFBdjDistante;

public class SynchronisationFAFServiceImpl implements SynchronisationFAFService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationFAFServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();

	private DBConnecteurFAFDao dbConnecteurFAFDao = new DBConnecteurFAFDaoImpl();

	private SynchroFAFWSHelper wsCockpitFAFHelper;

	public SynchronisationFAFServiceImpl() {
		wsCockpitFAFHelper = new SynchroFAFWSHelper();
	}

	@Override
	public void synchroniserQuestionsFAF() {

		LOGGER.debug("[DEBUT] synchroniserQuestionsFAF");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteurFAFDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<QuestionFAFBdjDistante> questions = wsCockpitFAFHelper.synchroniserQuestionsFAF(referenceMaxExistante);

		for (QuestionFAFBdjDistante question : questions) {
			dbConnecteurFAFDao.creerQuestion(question);
		}

		LOGGER.debug("[FIN] synchroniserQuestionsFAF");
	}

	@Override
	public void synchroniserCorrectionsFAF() {

		LOGGER.debug("[DEBUT] synchroniserCorrectionsFAF");

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

		LOGGER.debug("[FIN] synchroniserCorrectionsFAF");
	}

	@Override
	public void synchroniserAnomaliesFAF() {
		LOGGER.debug("[DEBUT] synchroniserAnomaliesFAF");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("FAF_ANOMALIE");

		// Lister les anomalies à synchroniser
		List<Anomalie> anomalies = dbConnecteurFAFDao.listerAnomalies(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurFAFDao.recupererIndexMaxAnomalie();

		// Pusher les lectures sur le cockpit
		wsCockpitFAFHelper.synchroniserAnomaliesFAF(anomalies);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("FAF_ANOMALIE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserAnomaliesFAF");
	}

	@Override
	public void synchroniserLecturesFAF() {
		LOGGER.debug("[DEBUT] synchroniserLecturesFAF");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("FAF_LECTURE");

		// Lister les lectures à synchroniser
		List<Lecture> lectures = dbConnecteurFAFDao.listerQuestionsLues(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurFAFDao.recupererIndexMaxLecture();

		// Pusher les lectures sur le cockpit
		wsCockpitFAFHelper.synchroniserLecturesFAF(lectures);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("FAF_LECTURE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserLecturesFAF");
	}

}
