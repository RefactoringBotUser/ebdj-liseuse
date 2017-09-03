package fr.qp1c.ebdj.moteur.service;

import fr.qp1c.ebdj.moteur.bean.exception.BdjException;

public interface SynchronisationFAFService {

	void synchroniserCorrectionsFAF() throws BdjException;

	void synchroniserAnomaliesFAF() throws BdjException;

	void synchroniserLecturesFAF() throws BdjException;

	void synchroniserQuestionsFAF() throws BdjException;
}
