package fr.qp1c.ebdj.liseuse.commun.exchange.question;

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

	/**
	 * 
	 */
	public Theme4ALSBdjDistante() {
		super();
	}

	/**
	 * @param theme
	 * @param categorie4als
	 * @param categorie4alsRef
	 * @param groupeCategorie4ALS
	 * @param questions
	 * @param reference
	 * @param difficulte
	 * @param club
	 * @param dateEnvoi
	 * @param dateIntegration
	 * @param version
	 */
	public Theme4ALSBdjDistante(String theme, String categorie4als, Long categorie4alsRef, Long groupeCategorie4ALS,
			Map<Integer, Question4ALSBdjDistante> questions, Long reference, Long difficulte, String club,
			String dateEnvoi, String dateIntegration, Long version) {
		super();
		this.theme = theme;
		categorie4ALS = categorie4als;
		categorie4ALSRef = categorie4alsRef;
		this.groupeCategorie4ALS = groupeCategorie4ALS;
		this.questions = questions;
		this.reference = reference;
		this.difficulte = difficulte;
		this.club = club;
		this.dateEnvoi = dateEnvoi;
		this.dateIntegration = dateIntegration;
		this.version = version;
	}

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
