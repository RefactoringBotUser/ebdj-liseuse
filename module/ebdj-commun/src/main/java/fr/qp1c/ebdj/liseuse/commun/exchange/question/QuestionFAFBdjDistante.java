package fr.qp1c.ebdj.liseuse.commun.exchange.question;

public class QuestionFAFBdjDistante extends QRGeneriqueBdjDistante{

	private String theme;

	private String categorieFAF;

	private Long categorieFAFRef;



	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCategorieFAF() {
		return categorieFAF;
	}

	public void setCategorieFAF(String categorieFAF) {
		this.categorieFAF = categorieFAF;
	}

	public Long getCategorieFAFRef() {
		return categorieFAFRef;
	}

	public void setCategorieFAFRef(Long categorieFAFRef) {
		this.categorieFAFRef = categorieFAFRef;
	}

}
