package fr.qp1c.ebdj.liseuse.synchronisation.service;

import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;

public interface SynchronisationFAFService {

	void synchroniserCorrectionsFAF() throws BdjException;

	void synchroniserAnomaliesFAF() throws BdjException;

	void synchroniserLecturesFAF() throws BdjException;

	void synchroniserQuestionsFAF() throws BdjException;
}
