package fr.qp1c.ebdj.moteur.service;

import fr.qp1c.ebdj.bean.exception.BdjException;

public interface Synchronisation9PGService {

	void synchroniserCorrections9PG() throws BdjException;

	void synchroniserAnomalies9PG() throws BdjException;

	void synchroniserLectures9PG() throws BdjException;

	void synchroniserQuestions9PG() throws BdjException;
}
