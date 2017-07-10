package fr.qp1c.ebdj.model.stats;

import fr.qp1c.ebdj.moteur.bean.stats.StatsCategorieFAF;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockCategorieFAF {

	private final SimpleStringProperty categorie;
	private final SimpleLongProperty quantiteTotale;
	private final SimpleLongProperty quantiteDisponible;
	private final SimpleLongProperty quantiteJouee;

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockCategorieFAF(String type, long quantiteTotale, long quantiteDispo, long quantiteJouee) {
		super();
		this.categorie = new SimpleStringProperty(type);
		this.quantiteTotale = new SimpleLongProperty(quantiteTotale);
		this.quantiteDisponible = new SimpleLongProperty(quantiteDispo);
		this.quantiteJouee = new SimpleLongProperty(quantiteJouee);
	}

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockCategorieFAF(StatsCategorieFAF statsCategorieFAF) {
		super();
		this.categorie = new SimpleStringProperty(statsCategorieFAF.getCategorie());
		this.quantiteTotale = new SimpleLongProperty(statsCategorieFAF.getNbQuestionsTotal());
		this.quantiteDisponible = new SimpleLongProperty(statsCategorieFAF.getNbQuestionsInedites());
		this.quantiteJouee = new SimpleLongProperty(statsCategorieFAF.getNbQuestionsJouees());
	}

	public String getCategorie() {
		return categorie.get();
	}

	public Long getQuantiteTotale() {
		return quantiteTotale.get();
	}

	public Long getQuantiteDisponible() {
		return quantiteDisponible.get();
	}

	public Long getQuantiteJouee() {
		return quantiteJouee.get();
	}

	public void setCategorie(String newCategorie) {
		categorie.set(newCategorie);
	}

	public void setQuantiteTotale(Long newQuantite) {
		quantiteTotale.set(newQuantite);
	}

	public void setQuantiteDisponible(Long newQuantite) {
		quantiteDisponible.set(newQuantite);
	}

	public void setQuantiteJouee(Long newQuantite) {
		quantiteJouee.set(newQuantite);
	}

}
