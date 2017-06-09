package fr.qp1c.ebdj.moteur.bean.synchro;

public class SynchroArtefact {

	private Integer indexBdjLocale;

	private Integer indexBdjDistante;

	public Integer getIndexBdjLocale() {
		return indexBdjLocale;
	}

	public void setIndexBdjLocale(Integer indexBdjLocale) {
		this.indexBdjLocale = indexBdjLocale;
	}

	public Integer getIndexBdjDistante() {
		return indexBdjDistante;
	}

	public void setIndexBdjDistante(Integer indexBdjDistante) {
		this.indexBdjDistante = indexBdjDistante;
	}

	public boolean estSynchronise() {
		if (indexBdjLocale.compareTo(indexBdjDistante) == 0) {
			return true;
		}
		return false;
	}

	public Integer recupererNbQuestionASynchro() {
		return indexBdjDistante - indexBdjLocale;
	}
}
