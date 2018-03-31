package fr.qp1c.ebdj.liseuse.stats.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurJDDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurNPGDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurQALSDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurJDDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurNPGDaoImpl;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurQALSDaoImpl;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsCategorieFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsGroupeCategorieQALS;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsQuestions;
import fr.qp1c.ebdj.liseuse.stats.service.StatistiqueService;

public class StatistiqueServiceImpl implements StatistiqueService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StatistiqueServiceImpl.class);

	private DBConnecteurNPGDao connecterNPGDao;

	private DBConnecteurQALSDao connecter4ALSDao;

	private DBConnecteurJDDao connecterJDDao;

	private DBConnecteurFAFDao connecterFAFDao;

	public StatistiqueServiceImpl() {
		this.connecterNPGDao = new DBConnecteurNPGDaoImpl();
		this.connecter4ALSDao = new DBConnecteurQALSDaoImpl();
		this.connecterJDDao = new DBConnecteurJDDaoImpl();
		this.connecterFAFDao = new DBConnecteurFAFDaoImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatsBDJ calculerStatistique() {
		LOGGER.info("[DEBUT] calculerStatistique");

		StatsBDJ stats = new StatsBDJ();

		// Stats 9PG

		StatsQuestions statsQuestions9PG1 = new StatsQuestions();
		statsQuestions9PG1.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(1));
		statsQuestions9PG1.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(1));
		stats.setStats9PG1(statsQuestions9PG1);

		LOGGER.debug("{}", statsQuestions9PG1);

		StatsQuestions statsQuestions9PG2 = new StatsQuestions();
		statsQuestions9PG2.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(2));
		statsQuestions9PG2.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(2));
		stats.setStats9PG2(statsQuestions9PG2);

		LOGGER.debug("{}", statsQuestions9PG2);

		StatsQuestions statsQuestions9PG3 = new StatsQuestions();
		statsQuestions9PG3.setNbQuestionsJouees(connecterNPGDao.compterNbQuestionLue(3));
		statsQuestions9PG3.setNbQuestionsTotal(connecterNPGDao.compterNbQuestion(3));
		stats.setStats9PG3(statsQuestions9PG3);

		LOGGER.debug("{}", statsQuestions9PG3);

		// Stats 4ALS

		StatsQuestions statsQuestions4ALS = new StatsQuestions();
		statsQuestions4ALS.setNbQuestionsJouees(connecter4ALSDao.compterNbThemeJoue());
		statsQuestions4ALS.setNbQuestionsTotal(connecter4ALSDao.compterNbTheme());
		stats.setStats4ALS(statsQuestions4ALS);

		LOGGER.debug("{}", statsQuestions4ALS);

		// Stats JD

		StatsQuestions statsQuestionsJD = new StatsQuestions();
		statsQuestionsJD.setNbQuestionsJouees(connecterJDDao.compterNbQuestionLue());
		statsQuestionsJD.setNbQuestionsTotal(connecterJDDao.compterNbQuestion());
		stats.setStatsJD(statsQuestionsJD);

		LOGGER.debug("{}", statsQuestionsJD);

		// Stats FAF

		StatsQuestions statsQuestionsFAF = new StatsQuestions();
		statsQuestionsFAF.setNbQuestionsJouees(connecterFAFDao.compterNbQuestionLue());
		statsQuestionsFAF.setNbQuestionsTotal(connecterFAFDao.compterNbQuestion());
		stats.setStatsFAF(statsQuestionsFAF);

		LOGGER.debug("{}", statsQuestionsFAF);

		LOGGER.info("[FIN ] calculerStatistique : {}", stats);
		return stats;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
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

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<StatsGroupeCategorieQALS> calculerStatsGroupeCategorieQALS() {
		LOGGER.info("[DEBUT] calculerStatsGroupeCategorieQALS");

		List<StatsGroupeCategorieQALS> statsGroupeCategorieQALS = new ArrayList<>();

		Map<String, Map<String, Long>> groupeCategoriesQALS = connecter4ALSDao.compterParGroupeCategorie();
		Map<String, Map<String, Long>> groupeCategoriesQALSJoues = connecter4ALSDao.compterParGroupeCategorieLue();

		for (Entry<String, Map<String, Long>> groupeCategorieQALS : groupeCategoriesQALS.entrySet()) {
			StatsGroupeCategorieQALS stats = new StatsGroupeCategorieQALS();
			stats.setGroupeCategorie(groupeCategorieQALS.getKey());

			StatsQuestions statsNiveau1 = new StatsQuestions();
			statsNiveau1.setNbQuestionsTotal(groupeCategorieQALS.getValue().get("1").intValue());

			if (groupeCategoriesQALSJoues.containsKey(groupeCategorieQALS.getKey())) {
				statsNiveau1.setNbQuestionsJouees(
						groupeCategoriesQALSJoues.get(groupeCategorieQALS.getKey()).get("1").intValue());
			}

			stats.setStatsNiveau1(statsNiveau1);

			StatsQuestions statsNiveau2 = new StatsQuestions();
			statsNiveau2.setNbQuestionsTotal(groupeCategorieQALS.getValue().get("2").intValue());

			if (groupeCategoriesQALSJoues.containsKey(groupeCategorieQALS.getKey())) {
				statsNiveau2.setNbQuestionsJouees(
						groupeCategoriesQALSJoues.get(groupeCategorieQALS.getKey()).get("2").intValue());
			}

			stats.setStatsNiveau2(statsNiveau2);

			StatsQuestions statsNiveau3 = new StatsQuestions();
			statsNiveau3.setNbQuestionsTotal(groupeCategorieQALS.getValue().get("3").intValue());

			if (groupeCategoriesQALSJoues.containsKey(groupeCategorieQALS.getKey())) {
				statsNiveau3.setNbQuestionsJouees(
						groupeCategoriesQALSJoues.get(groupeCategorieQALS.getKey()).get("3").intValue());
			}

			stats.setStatsNiveau3(statsNiveau3);

			StatsQuestions statsNiveau4 = new StatsQuestions();
			statsNiveau4.setNbQuestionsTotal(groupeCategorieQALS.getValue().get("4").intValue());

			if (groupeCategoriesQALSJoues.containsKey(groupeCategorieQALS.getKey())) {
				statsNiveau4.setNbQuestionsJouees(
						groupeCategoriesQALSJoues.get(groupeCategorieQALS.getKey()).get("4").intValue());
			}

			stats.setStatsNiveau4(statsNiveau4);

			statsGroupeCategorieQALS.add(stats);
		}

		LOGGER.info("[FIN] calculerStatsGroupeCategorieQALS");

		return statsGroupeCategorieQALS;
	}

}
