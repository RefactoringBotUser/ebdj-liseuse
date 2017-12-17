package fr.qp1c.ebdj.liseuse.commun.bean.stats;

public class StatsGroupeCategorieQALS {

	private String groupeCategorie;

	private StatsQuestions statsNiveau1;

	private StatsQuestions statsNiveau2;

	private StatsQuestions statsNiveau3;

	private StatsQuestions statsNiveau4;

	// Getters - setters

	public String getGroupeCategorie() {
		return groupeCategorie;
	}

	public void setGroupeCategorie(String groupeCategorie) {
		this.groupeCategorie = groupeCategorie;
	}

	public StatsQuestions getStatsNiveau1() {
		return statsNiveau1;
	}

	public void setStatsNiveau1(StatsQuestions statsNiveau1) {
		this.statsNiveau1 = statsNiveau1;
	}

	public StatsQuestions getStatsNiveau2() {
		return statsNiveau2;
	}

	public void setStatsNiveau2(StatsQuestions statsNiveau2) {
		this.statsNiveau2 = statsNiveau2;
	}

	public StatsQuestions getStatsNiveau3() {
		return statsNiveau3;
	}

	public void setStatsNiveau3(StatsQuestions statsNiveau3) {
		this.statsNiveau3 = statsNiveau3;
	}

	public StatsQuestions getStatsNiveau4() {
		return statsNiveau4;
	}

	public void setStatsNiveau4(StatsQuestions statsNiveau4) {
		this.statsNiveau4 = statsNiveau4;
	}

	@Override
	public String toString() {
		return "StatsCategorieQALS [groupeCategorie=" + groupeCategorie + "," + statsNiveau1 + "," + statsNiveau2 + ","
				+ statsNiveau3 + "," + statsNiveau4 + "]";
	}
}
