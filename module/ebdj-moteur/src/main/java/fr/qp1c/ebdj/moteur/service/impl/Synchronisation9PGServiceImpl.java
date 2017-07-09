package fr.qp1c.ebdj.moteur.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.synchro.Anomalie;
import fr.qp1c.ebdj.moteur.bean.synchro.Lecture;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.moteur.service.Synchronisation9PGService;
import fr.qp1c.ebdj.moteur.ws.Synchro9PGWSHelper;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.CorrectionQuestion9PGBdjDistante;
import fr.qp1c.ebdj.moteur.ws.wrapper.correction.TypeCorrection;
import fr.qp1c.ebdj.moteur.ws.wrapper.question.Question9PGBdjDistante;

public class Synchronisation9PGServiceImpl implements Synchronisation9PGService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationJDServiceImpl.class);

	private DBConnecteurSynchroDao dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();

	private DBConnecteurNPGDao dbConnecteurNPGDao = new DBConnecteurNPGDaoImpl();

	private Synchro9PGWSHelper wsCockpit9PGHelper;

	public Synchronisation9PGServiceImpl() {
		wsCockpit9PGHelper = new Synchro9PGWSHelper();
	}

	@Override
	public void synchroniserQuestions9PG() {

		LOGGER.debug("[DEBUT] synchroniserQuestions9PG");

		// Retrouver la référence max de la question
		Long referenceMaxExistante = dbConnecteurNPGDao.recupererReferenceMaxQuestion();

		// Récuperer les questions depuis le cockpit
		List<Question9PGBdjDistante> questions = wsCockpit9PGHelper.synchroniserQuestions9PG(referenceMaxExistante);

		for (Question9PGBdjDistante question : questions) {
			dbConnecteurNPGDao.creerQuestion(question);
		}

		LOGGER.debug("[FIN] synchroniserQuestions9PG");
	}

	@Override
	public void synchroniserCorrections9PG() {

		LOGGER.debug("[DEBUT] synchroniserCorrections9PG");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long indexReprise = dbConnecteurSynchroDao.recupererIndexParCle("9PG_CORRECTION");

		List<CorrectionQuestion9PGBdjDistante> corrections = wsCockpit9PGHelper
				.synchroniserCorrections9PG(indexReprise);

		Long indexMax = Long.valueOf(0);

		for (CorrectionQuestion9PGBdjDistante correction : corrections) {
			if (TypeCorrection.DESACTIVER.equals(correction.getTypeCorrection())) {
				dbConnecteurNPGDao.desactiverQuestion(correction.getReference());
			} else if (TypeCorrection.REMPLACER.equals(correction.getTypeCorrection())) {
				dbConnecteurNPGDao.corrigerQuestion(correction.getQuestion9pgBdjDistante());
			}

			if (correction.getIndex().longValue() > indexMax.longValue()) {
				indexMax = correction.getIndex();
			}
		}

		dbConnecteurSynchroDao.modifierIndexParCle("9PG_CORRECTION", indexMax);

		LOGGER.debug("[FIN] synchroniserCorrections9PG");
	}

	@Override
	public void synchroniserAnomalies9PG() {
		LOGGER.debug("[DEBUT] synchroniserAnomalies9PG");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("9PG_ANOMALIE");

		// Lister les anomalies à synchroniser
		List<Anomalie> anomalies = dbConnecteurNPGDao.listerAnomalies(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurNPGDao.recupererIndexMaxAnomalie();

		// Pusher les lectures sur le cockpit
		wsCockpit9PGHelper.synchroniserAnomalies9PG(anomalies);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("9PG_ANOMALIE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserAnomalies9PG");
	}

	@Override
	public void synchroniserLectures9PG() {
		LOGGER.debug("[DEBUT] synchroniserLectures9PG");

		// Retrouver l'index de la derniere lecture synchronisée.
		Long dernierIndex = dbConnecteurSynchroDao.recupererIndexParCle("9PG_LECTURE");

		// Lister les lectures à synchroniser
		List<Lecture> lectures = dbConnecteurNPGDao.listerQuestionsLues(dernierIndex);

		Long nouveauDernierIndex = dbConnecteurNPGDao.recupererIndexMaxLecture();

		// Pusher les lectures sur le cockpit
		wsCockpit9PGHelper.synchroniserLectures9PG(lectures);

		// Mettre à jour l'index de la dernière question synchronisée.
		dbConnecteurSynchroDao.modifierIndexParCle("9PG_LECTURE", nouveauDernierIndex);

		LOGGER.debug("[FIN] synchroniserLectures9PG");
	}

}