package fr.qp1c.ebdj.moteur.ws.wrapper.question;

import java.util.Map;

public class Theme4ALSBdjDistante {

	private String theme;

	private String categorie4ALS;

	private Long categorie4ALSRef;

	private Long groupeCategorie4ALS;

	private Map<Integer, Question4ALSBdjDistante> questions;

	private Long reference;

	private Long difficulte;

	private String club;

	private String dateEnvoi;

	private String dateIntegration;

	private Long version;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Long getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(Long difficulte) {
		this.difficulte = difficulte;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(String dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public String getDateIntegration() {
		return dateIntegration;
	}

	public void setDateIntegration(String dateIntegration) {
		this.dateIntegration = dateIntegration;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCategorie4ALS() {
		return categorie4ALS;
	}

	public void setCategorie4ALS(String categorie4als) {
		categorie4ALS = categorie4als;
	}

	public Long getCategorie4ALSRef() {
		return categorie4ALSRef;
	}

	public void setCategorie4ALSRef(Long categorie4alsRef) {
		categorie4ALSRef = categorie4alsRef;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getGroupeCategorie4ALS() {
		return groupeCategorie4ALS;
	}

	public void setGroupeCategorie4ALS(Long groupeCategorie4ALS) {
		this.groupeCategorie4ALS = groupeCategorie4ALS;
	}

	public Map<Integer, Question4ALSBdjDistante> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<Integer, Question4ALSBdjDistante> questions) {
		this.questions = questions;
	}

}
