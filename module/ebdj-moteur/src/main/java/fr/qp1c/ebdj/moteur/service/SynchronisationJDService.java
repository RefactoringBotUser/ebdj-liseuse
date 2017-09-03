package fr.qp1c.ebdj.moteur.service;

import fr.qp1c.ebdj.moteur.bean.exception.BdjException;

public interface SynchronisationJDService {

	void synchroniserCorrectionsJD() throws BdjException;

	void synchroniserAnomaliesJD() throws BdjException;

	void synchroniserLecturesJD() throws BdjException;

	void synchroniserQuestionsJD() throws BdjException;
}
