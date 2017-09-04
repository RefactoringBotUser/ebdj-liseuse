package fr.qp1c.ebdj.moteur.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.moteur.bean.stats.StatsCategorieFAF;
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

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronisationJDServiceImpl.class);

	private DBConnecteurNPGDao connecterNPGDao = new DBConnecteurNPGDaoImpl();

	private DBConnecteurQALSDao connecter4ALSDao = new DBConnecteurQALSDaoImpl();

	private DBConnecteurJDDao connecterJDDao = new DBConnecteurJDDaoImpl();

	private DBConnecteurFAFDao connecterFAFDao = new DBConnecteurFAFDaoImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatsBDJ calculerStatistique() {
		LOGGER.info("[DEBUT] calculerStatistique");

		StatsBDJ stats = new StatsBDJ();

		// Stats 9PG

		StatsQuestions statsQuestions9PG_1 = new StatsQuestions();
		statsQuestions9PG_1.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(1));
		statsQuestions9PG_1.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(1));
		stats.setStats9PG_1(statsQuestions9PG_1);

		LOGGER.debug(statsQuestions9PG_1.toString());

		StatsQuestions statsQuestions9PG_2 = new StatsQuestions();
		statsQuestions9PG_2.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(2));
		statsQuestions9PG_2.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(2));
		stats.setStats9PG_2(statsQuestions9PG_2);

		LOGGER.debug(statsQuestions9PG_2.toString());

		StatsQuestions statsQuestions9PG_3 = new StatsQuestions();
		statsQuestions9PG_3.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(3));
		statsQuestions9PG_3.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(3));
		stats.setStats9PG_3(statsQuestions9PG_3);

		LOGGER.debug(statsQuestions9PG_3.toString());

		// Stats 4ALS

		StatsQuestions statsQuestions4ALS = new StatsQuestions();
		statsQuestions4ALS.setNbQuestionsJouees(connecter4ALSDao.compterNbThemeJouee());
		statsQuestions4ALS.setNbQuestionsTotal(connecter4ALSDao.compterNbTheme());
		stats.setStats4ALS(statsQuestions4ALS);

		LOGGER.debug(statsQuestions4ALS.toString());

		// Stats JD

		StatsQuestions statsQuestionsJD = new StatsQuestions();
		statsQuestionsJD.setNbQuestionsJouees(connecterJDDao.compterNbQuestionLue());
		statsQuestionsJD.setNbQuestionsTotal(connecterJDDao.compterNbQuestion());
		stats.setStatsJD(statsQuestionsJD);

		LOGGER.debug(statsQuestionsJD.toString());

		// Stats FAF

		StatsQuestions statsQuestionsFAF = new StatsQuestions();
		statsQuestionsFAF.setNbQuestionsJouees(connecterFAFDao.compterNbQuestionLue());
		statsQuestionsFAF.setNbQuestionsTotal(connecterFAFDao.compterNbQuestion());
		stats.setStatsFAF(statsQuestionsFAF);

		LOGGER.info("[FIN ] calculerStatistique : {}", statsQuestionsFAF);
		return stats;
	}

	@Override
	public List<StatsCategorieFAF> calculerStatsCategorieFAF() {
		LOGGER.info("[DEBUT] calculerStatsCategorieFAF");

		List<StatsCategorieFAF> statsCategorieFAF = new ArrayList<>();

		Map<String, Long> categoriesFAF = connecterFAFDao.compterParCategorie();
		Map<String, Long> categoriesFAFJoues = connecterFAFDao.compterParCategorieLue();

		for (Entry<String, Long> categorieFAF : categoriesFAF.entrySet()) {
			StatsCategorieFAF stats = new StatsCategorieFAF();
			stats.setCategorie(categorieFAF.getKey());

			StatsQuestions statsCategorie = new StatsQuestions();

			statsCategorie.setNbQuestionsTotal(categorieFAF.getValue().intValue());

			if (categoriesFAFJoues.containsKey(categorieFAF.getKey())) {
				statsCategorie.setNbQuestionsJouees(categoriesFAFJoues.get(categorieFAF.getKey()).intValue());
			}

			stats.setStatsCategorie(statsCategorie);

			statsCategorieFAF.add(stats);
		}

		LOGGER.info("[FIN] calculerStatsCategorieFAF");

		return statsCategorieFAF;
	}

}
