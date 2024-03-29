package fr.qp1c.ebdj.liseuse.synchronisation.service;

import fr.qp1c.ebdj.liseuse.commun.bean.exception.BdjException;

public interface SynchronisationJDService {

	void synchroniserCorrectionsJD() throws BdjException;

	void synchroniserAnomaliesJD() throws BdjException;

	void synchroniserLecturesJD() throws BdjException;

	void synchroniserQuestionsJD() throws BdjException;
}
