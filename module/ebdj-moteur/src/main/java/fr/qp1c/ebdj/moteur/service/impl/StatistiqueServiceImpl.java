package fr.qp1c.ebdj.moteur.service.impl;

import fr.qp1c.ebdj.moteur.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.moteur.bean.stats.StatsQuestions;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurQALSDaoImpl;
import fr.qp1c.ebdj.moteur.service.StatistiqueService;

public class StatistiqueServiceImpl implements StatistiqueService {

	private DBConnecteurNPGDao connecterNPGDao = new DBConnecteurNPGDaoImpl();

	private DBConnecteurQALSDao connecter4ALSDao = new DBConnecteurQALSDaoImpl();

	private DBConnecteurJDDao connecterJDDao = new DBConnecteurJDDaoImpl();

	private DBConnecteurFAFDao connecterFAFDao = new DBConnecteurFAFDaoImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatsBDJ calculerStatistique() {

		StatsBDJ stats = new StatsBDJ();

		// Stats 9PG

		StatsQuestions statsQuestions9PG_1 = new StatsQuestions();
		statsQuestions9PG_1.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionJouee(1));
		statsQuestions9PG_1.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(1));
		stats.setStats9PG_1(statsQuestions9PG_1);

		StatsQuestions statsQuestions9PG_2 = new StatsQuestions();
		statsQuestions9PG_2.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionJouee(2));
		statsQuestions9PG_2.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(2));
		stats.setStats9PG_2(statsQuestions9PG_2);

		StatsQuestions statsQuestions9PG_3 = new StatsQuestions();
		statsQuestions9PG_3.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionJouee(3));
		statsQuestions9PG_3.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(3));
		stats.setStats9PG_3(statsQuestions9PG_3);

		// Stats 4ALS

		StatsQuestions statsQuestions4ALS = new StatsQuestions();
		statsQuestions4ALS.setNbQuestionsJouees(connecter4ALSDao.compterNbThemeJouee());
		statsQuestions4ALS.setNbQuestionsTotal(connecter4ALSDao.compterNbTheme());
		stats.setStats4ALS(statsQuestions4ALS);

		// Stats JD

		StatsQuestions statsQuestionsJD = new StatsQuestions();
		statsQuestionsJD.setNbQuestionsJouees(connecterJDDao.compterNbQuestionJouee());
		statsQuestionsJD.setNbQuestionsTotal(connecterJDDao.compterNbQuestion());
		stats.setStatsJD(statsQuestionsJD);

		// Stats FAF

		StatsQuestions statsQuestionsFAF = new StatsQuestions();
		statsQuestionsFAF.setNbQuestionsJouees(connecterFAFDao.compterNbQuestionJouee());
		statsQuestionsFAF.setNbQuestionsTotal(connecterFAFDao.compterNbQuestion());
		stats.setStatsFAF(statsQuestionsFAF);

		return stats;
	}

}
