package fr.qp1c.ebdj.liseuse.synchronisation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Anomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.synchro.Lecture;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.CorrectionQuestionJDBdjDistante;
import fr.qp1c.ebdj.liseuse.commun.exchange.correction.TypeCorrection;
import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;
import fr.qp1c.ebdj.liseuse.synchronisation.service.SynchronisationJDService;
import fr.qp1c.ebdj.liseuse.synchronisation.utils.SynchronisationConstants;
import fr.qp1c.ebdj.liseuse.synchronisation.ws.SynchroJDWSHelper;

public class SynchronisationJDServiceImpl implements SynchronisationJDService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationJDServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao;

	private DBConnecteurJDDao dbConnecteurJDDao;

	private SynchroJDWSHelper wsCockpitJDHelper;

	public SynchronisationJDServiceImpl() {
		this.wsCockpitJDHelper = new SynchroJDWSHelper();
		this.dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();
		this.dbConnecteurJDDao = new DBConnecteurJDDaoImpl();
	}

	@Override
	public void synchroniserQuestionsJD() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserQuestionsJD");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteurJDDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<QuestionJDBdjDistante> questions = wsCockpitJDHelper.synchroniserQuestionsJD(referenceMaxExistante);

		for (QuestionJDBdjDistante question : questions) {
			dbConnecteurJDDao.creerQuestion(question);
		}

		LOGGER.info("[FIN] synchroniserQuestionsJD");
	}

	@Override
	public void synchroniserCorrectionsJD() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserCorrectionsJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long indexReprise = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_JD_CORRECTION);

		List<CorrectionQuestionJDBdjDistante> corrections = wsCockpitJDHelper.synchroniserCorrectionsJD(indexReprise);

		Long indexMax = Long.valueOf(0);

		for (CorrectionQuestionJDBdjDistante correction : corrections) {
			if (TypeCorrection.DESACTIVER.equals(correction.getTypeCorrection())) {
				dbConnecteurJDDao.desactiverQuestion(correction.getReference());
			} else if (TypeCorrection.REMPLACER.equals(correction.getTypeCorrection())) {
				dbConnecteurJDDao.corrigerQuestion(correction.getQuestionJdBdjDistante());
			}

			if (correction.getIndex().longValue() > indexMax.longValue()) {
				indexMax = correction.getIndex();
			}
		}

		if (indexMax > indexReprise) {
			dbConnecteurSynchroDao.modifierIndexParCle(SynchronisationConstants.CLE_JD_CORRECTION, indexMax);
		}

		LOGGER.info("[FIN] synchroniserCorrectionsJD");
	}

	@Override
	public void synchroniserAnomaliesJD() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserAnomaliesJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_JD_ANOMALIE);

		Long nouveauDernierIndex = dbConnecteurJDDao.recupererIndexMaxAnomalie();

		if (dernierIndex < nouveauDernierIndex) {
			// Lister les anomalies à synchroniser
			List<Anomalie> anomalies = dbConnecteurJDDao.listerAnomalies(dernierIndex);

			// Pusher les lectures sur le cockpit
			wsCockpitJDHelper.synchroniserAnomaliesJD(anomalies);

			// Mettre à jour l'index de la dernière question synchronisée.
			dbConnecteurSynchroDao.modifierIndexParCle(SynchronisationConstants.CLE_JD_ANOMALIE, nouveauDernierIndex);
		}
		LOGGER.info("[FIN] synchroniserAnomaliesJD");
	}

	@Override
	public void synchroniserLecturesJD() throws BdjException {
		LOGGER.info("[DEBUT] synchroniserLecturesJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle(SynchronisationConstants.CLE_JD_LECTURE);

		Long nouveauDernierIndex = dbConnecteurJDDao.recupererIndexMaxLecture();

		if (dernierIndex < nouveauDernierIndex) {
			// Lister les lectures à synchroniser
			List<Lecture> lectures = dbConnecteurJDDao.listerQuestionsLues(dernierIndex);

			// Pusher les lectures sur le cockpit
			wsCockpitJDHelper.synchroniserLecturesJD(lectures);

			// Mettre à jour l'index de la dernière question synchronisée.
			dbConnecteurSynchroDao.modifierIndexParCle("JD_LECTURE", nouveauDernierIndex);
		}

		LOGGER.info("[FIN] synchroniserLecturesJD");
	}

}
