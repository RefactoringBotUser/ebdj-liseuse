package fr.qp1c.ebdj.moteur.bean.stats;

public class StatsBDJ {

	private StatsQuestions stats9PG_1;

	private StatsQuestions stats9PG_2;

	private StatsQuestions stats9PG_3;

	private StatsQuestions stats4ALS;

	private StatsQuestions statsJD;

	private StatsQuestions statsFAF;
	
	// Getters - setters

	public StatsQuestions getStats9PG_1() {
		return stats9PG_1;
	}

	public void setStats9PG_1(StatsQuestions stats9pg_1) {
		stats9PG_1 = stats9pg_1;
	}

	public StatsQuestions getStats9PG_2() {
		return stats9PG_2;
	}

	public void setStats9PG_2(StatsQuestions stats9pg_2) {
		stats9PG_2 = stats9pg_2;
	}

	public StatsQuestions getStats9PG_3() {
		return stats9PG_3;
	}

	public void setStats9PG_3(StatsQuestions stats9pg_3) {
		stats9PG_3 = stats9pg_3;
	}

	public StatsQuestions getStats4ALS() {
		return stats4ALS;
	}

	public void setStats4ALS(StatsQuestions stats4als) {
		stats4ALS = stats4als;
	}

	public StatsQuestions getStatsJD() {
		return statsJD;
	}

	public void setStatsJD(StatsQuestions statsJD) {
		this.statsJD = statsJD;
	}

	public StatsQuestions getStatsFAF() {
		return statsFAF;
	}

	public void setStatsFAF(StatsQuestions statsFAF) {
		this.statsFAF = statsFAF;
	}

	@Override
	public String toString() {
		return "StatsBoiteDeJeu [stats9PG_1=" + stats9PG_1 + ", stats9PG_2=" + stats9PG_2 + ", stats9PG_3=" + stats9PG_3
				+ ", stats4ALS=" + stats4ALS + ", statsJD=" + statsJD + ", statsFAF=" + statsFAF + "]";
	}

}
