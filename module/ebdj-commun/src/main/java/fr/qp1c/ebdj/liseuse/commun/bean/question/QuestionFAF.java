package fr.qp1c.ebdj.liseuse.commun.bean.question;

public class QuestionFAF extends QRAvecTheme {

	// Attributs

	private String categorie;

	private Long categorieRef;

	private Long difficulte;

	// Getters - setters

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Long getCategorieRef() {
		return categorieRef;
	}

	public void setCategorieRef(Long categorieRef) {
		this.categorieRef = categorieRef;
	}

	public Long getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(Long difficulte) {
		this.difficulte = difficulte;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(categorie);
		sb.append(" - ");
		sb.append(difficulte);

		return sb.toString();
	}
}
