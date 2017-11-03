package fr.qp1c.ebdj.liseuse.commun.bean.stats;

public class StatsBDJ {

	private StatsQuestions stats9PG1;

	private StatsQuestions stats9PG2;

	private StatsQuestions stats9PG3;

	private StatsQuestions stats4ALS;

	private StatsQuestions statsJD;

	private StatsQuestions statsFAF;
	
	// Getters - setters

	public StatsQuestions getStats4ALS() {
		return stats4ALS;
	}

	public StatsQuestions getStats9PG1() {
		return stats9PG1;
	}

	public void setStats9PG1(StatsQuestions stats9pg1) {
		stats9PG1 = stats9pg1;
	}

	public StatsQuestions getStats9PG2() {
		return stats9PG2;
	}

	public void setStats9PG2(StatsQuestions stats9pg2) {
		stats9PG2 = stats9pg2;
	}

	public StatsQuestions getStats9PG3() {
		return stats9PG3;
	}

	public void setStats9PG3(StatsQuestions stats9pg3) {
		stats9PG3 = stats9pg3;
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
		return "StatsBoiteDeJeu [stats9PG_1=" + stats9PG1 + ", stats9PG_2=" + stats9PG2 + ", stats9PG_3=" + stats9PG3
				+ ", stats4ALS=" + stats4ALS + ", statsJD=" + statsJD + ", statsFAF=" + statsFAF + "]";
	}

}
