package fr.qp1c.ebdj.moteur.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.moteur.service.SynchronisationJDService;
import fr.qp1c.ebdj.moteur.ws.SynchroJDWSHelper;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestionJDBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.TypeCorrection;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.QuestionJDBdjDistante;

public class SynchronisationJDServiceImpl implements SynchronisationJDService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationJDServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();

	private DBConnecteurJDDao dbConnecteurJDDao = new DBConnecteurJDDaoImpl();

	private SynchroJDWSHelper wsCockpitJDHelper;

	public SynchronisationJDServiceImpl() {
		wsCockpitJDHelper = new SynchroJDWSHelper();
	}

	@Override
	public void synchroniserJD() {

		synchroniserAnomaliesJD();

		synchroniserLecturesJD();

		synchroniserCorrectionsJD();

		synchroniserQuestionsJD();
	}

	private void synchroniserQuestionsJD() {

		LOGGER.debug("[DEBUT] synchroniserQuestionsJD");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteurJDDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<QuestionJDBdjDistante> questions = wsCockpitJDHelper.synchroniserQuestionsJD(referenceMaxExistante);

		for (QuestionJDBdjDistante question : questions) {
			dbConnecteurJDDao.creerQuestion(question);
		}

		LOGGER.debug("[FIN] synchroniserQuestionsJD");
	}

	private void synchroniserCorrectionsJD() {

		LOGGER.debug("[DEBUT] synchroniserCorrectionsJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long indexReprise = dbConnecteurSynchroDao.recupererIndexParCle("JD_CORRECTION");

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

		dbConnecteurSynchroDao.modifierIndexParCle("JD_CORRECTION", indexMax);

		LOGGER.debug("[FIN] synchroniserCorrectionsJD");
	}

	private void synchroniserAnomaliesJD() {
		LOGGER.debug("[DEBUT] synchroniserAnomaliesJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("JD_ANOMALIE");

		// Lister les anomalies à synchroniser
		List<Anomalie> anomalies = dbConnecteurJDDao.listerAnomalies(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurJDDao.recupererIndexMaxAnomalie();

		// Pusher les lectures sur le cockpit
		wsCockpitJDHelper.synchroniserAnomaliesJD(anomalies);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("JD_ANOMALIE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserAnomaliesJD");
	}

	private void synchroniserLecturesJD() {
		LOGGER.debug("[DEBUT] synchroniserLecturesJD");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("JD_LECTURE");

		// Lister les lectures à synchroniser
		List<Lecture> lectures = dbConnecteurJDDao.listerQuestionsLues(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurJDDao.recupererIndexMaxLecture();

		// Pusher les lectures sur le cockpit
		wsCockpitJDHelper.synchroniserLecturesJD(lectures);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("JD_LECTURE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserLecturesJD");
	}

}
