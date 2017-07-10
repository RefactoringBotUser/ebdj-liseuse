package fr.qp1c.ebdj.moteur.service;

import java.util.List;

import fr.qp1c.ebdj.moteur.bean.stats.StatsBDJ;
import fr.qp1c.ebdj.moteur.bean.stats.StatsCategorieFAF;

public interface StatistiqueService {

	/**
	 * 1. Afficher le nombre de question 9PG (par 2.
	 * 
	 * 
	 * 
	 */
	public StatsBDJ calculerStatistique();

	public List<StatsCategorieFAF> calculerStatsCategorieFAF();

}
