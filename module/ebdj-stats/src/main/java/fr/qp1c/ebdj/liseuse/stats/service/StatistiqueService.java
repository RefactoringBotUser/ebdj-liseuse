package fr.qp1c.ebdj.liseuse.stats.service;

import java.util.List;

import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsCategorieFAF;
import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsGroupeCategorieQALS;

public interface StatistiqueService {

	/**
	 * 1. Afficher le nombre de question 9PG (par 2.
	 * 
	 * 
	 * 
	 */
	public StatsBDJ calculerStatistique();

	public List<StatsCategorieFAF> calculerStatsCategorieFAF();

	public List<StatsGroupeCategorieQALS> calculerStatsGroupeCategorieQALS();

}
