package fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.correction;

import fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.question.Theme4ALSBdjDistante;

public class CorrectionTheme4ALSBdjDistante {

	private Theme4ALSBdjDistante theme4ALSBdjDistante;

	private String reference;

	private Long index;

	private TypeCorrection typeCorrection;

	public Theme4ALSBdjDistante getTheme4ALSBdjDistante() {
		return theme4ALSBdjDistante;
	}

	public void setTheme4ALSBdjDistante(Theme4ALSBdjDistante theme4alsBdjDistante) {
		theme4ALSBdjDistante = theme4alsBdjDistante;
	}

	public TypeCorrection getTypeCorrection() {
		return typeCorrection;
	}

	public void setTypeCorrection(TypeCorrection typeCorrection) {
		this.typeCorrection = typeCorrection;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
