package fr.qp1c.ebdj.moteur.service;

import fr.qp1c.ebdj.bean.exception.BdjException;

public interface Synchronisation4ALSService {

	void synchroniserCorrections4ALS() throws BdjException;

	void synchroniserAnomalies4ALS() throws BdjException;

	void synchroniserLectures4ALS() throws BdjException;

	void synchroniserQuestions4ALS() throws BdjException;
}
