package fr.qp1c.ebdj.moteur.service;

public interface Synchronisation9PGService {

	public void synchroniser9PG();

	void synchroniserCorrections9PG();

	void synchroniserAnomalies9PG();

	void synchroniserLectures9PG();

	void synchroniserQuestions9PG();
}
