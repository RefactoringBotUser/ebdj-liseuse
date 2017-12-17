package fr.qp1c.ebdj.liseuse.ihm.model.stats;

import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsGroupeCategorieQALS;
import javafx.beans.property.SimpleStringProperty;

public class StockGroupeCategorieQALS {

	private final SimpleStringProperty groupeCategorie;
	private final SimpleStringProperty niveau1;
	private final SimpleStringProperty niveau2;
	private final SimpleStringProperty niveau3;
	private final SimpleStringProperty niveau4;

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockGroupeCategorieQALS(String groupeCategorie, String niveau1, String niveau2, String niveau3,
			String niveau4) {
		super();
		this.groupeCategorie = new SimpleStringProperty(groupeCategorie);
		this.niveau1 = new SimpleStringProperty(niveau1);
		this.niveau2 = new SimpleStringProperty(niveau2);
		this.niveau3 = new SimpleStringProperty(niveau3);
		this.niveau4 = new SimpleStringProperty(niveau4);
	}

	/**
	 *
	 */
	public StockGroupeCategorieQALS(StatsGroupeCategorieQALS statsGroupeCategorieQALS) {
		super();
		
		switch(statsGroupeCategorieQALS.getGroupeCategorie()) {
		case "1":
			this.groupeCategorie = new SimpleStringProperty("Histoire, géo, religion,...");
			break;
		case "2":
			this.groupeCategorie = new SimpleStringProperty("Arts, ciné, musique, litt...");
			break;
		case "3":
			this.groupeCategorie = new SimpleStringProperty("Sciences et techniques, sport...");
			break;
		case "4":
			this.groupeCategorie = new SimpleStringProperty("Société, varia, vocabulaire...");
			break;
		default:
			this.groupeCategorie = new SimpleStringProperty("");
		}
		
		this.niveau1 = new SimpleStringProperty(statsGroupeCategorieQALS.getStatsNiveau1().afficherQuantites());
		this.niveau2 = new SimpleStringProperty(statsGroupeCategorieQALS.getStatsNiveau2().afficherQuantites());
		this.niveau3 = new SimpleStringProperty(statsGroupeCategorieQALS.getStatsNiveau3().afficherQuantites());
		this.niveau4 = new SimpleStringProperty(statsGroupeCategorieQALS.getStatsNiveau4().afficherQuantites());
	}

	public String getGroupeCategorie() {
		return groupeCategorie.get();
	}

	public String getNiveau1() {
		return niveau1.get();
	}

	public String getNiveau2() {
		return niveau2.get();
	}

	public String getNiveau3() {
		return niveau3.get();
	}

	public String getNiveau4() {
		return niveau4.get();
	}

}
