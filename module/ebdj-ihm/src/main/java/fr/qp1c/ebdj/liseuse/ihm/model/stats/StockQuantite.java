package fr.qp1c.ebdj.liseuse.ihm.model.stats;

import fr.qp1c.ebdj.liseuse.commun.bean.stats.StatsQuestions;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockQuantite {

	private final SimpleStringProperty type;
	private final SimpleLongProperty quantiteTotale;
	private final SimpleLongProperty quantiteDisponible;
	private final SimpleLongProperty quantiteJouee;
	private final SimpleStringProperty derniereSynchro;

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockQuantite(String type, long quantiteTotale, long quantiteDispo, long quantiteJouee) {
		super();
		this.type = new SimpleStringProperty(type);
		this.quantiteTotale = new SimpleLongProperty(quantiteTotale);
		this.quantiteDisponible = new SimpleLongProperty(quantiteDispo);
		this.quantiteJouee = new SimpleLongProperty(quantiteJouee);
		this.derniereSynchro = new SimpleStringProperty("");
	}

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockQuantite(String type, StatsQuestions statsQuestions) {
		super();
		this.type = new SimpleStringProperty(type);
		this.quantiteTotale = new SimpleLongProperty(statsQuestions.getNbQuestionsTotal());
		this.quantiteDisponible = new SimpleLongProperty(statsQuestions.getNbQuestionsInedites());
		this.quantiteJouee = new SimpleLongProperty(statsQuestions.getNbQuestionsJouees());
		this.derniereSynchro = new SimpleStringProperty("");
	}

	/**
	 * @param type
	 * @param quantiteTotale
	 * @param quantiteDispo
	 * @param quantiteJouee
	 */
	public StockQuantite(String type, long quantiteTotale, long quantiteDispo, long quantiteJouee,
			String derniereSynchro) {
		super();
		this.type = new SimpleStringProperty(type);
		this.quantiteTotale = new SimpleLongProperty(quantiteTotale);
		this.quantiteDisponible = new SimpleLongProperty(quantiteDispo);
		this.quantiteJouee = new SimpleLongProperty(quantiteJouee);
		this.derniereSynchro = new SimpleStringProperty(derniereSynchro);
	}

	public String getType() {
		return type.get();
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

	public String getDerniereSyncho() {
		return derniereSynchro.get();
	}

	public void setType(String newType) {
		type.set(newType);
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

	public void setDerniereSynchro(String derniereSynchro) {
		this.derniereSynchro.set(derniereSynchro);
	}

}
