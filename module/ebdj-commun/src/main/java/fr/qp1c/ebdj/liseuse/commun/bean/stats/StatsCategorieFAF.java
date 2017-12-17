package fr.qp1c.ebdj.liseuse.commun.bean.stats;

public class StatsCategorieFAF {

	private String categorie;

	private StatsQuestions statsCategorie;

	// Getters - setters

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public StatsQuestions getStatsCategorie() {
		return statsCategorie;
	}

	public void setStatsCategorie(StatsQuestions statsCategorie) {
		this.statsCategorie = statsCategorie;
	}

	@Override
	public String toString() {
		return "StatsCategorieFAF [categorie=" + categorie + "," + statsCategorie + "]";
	}
}
