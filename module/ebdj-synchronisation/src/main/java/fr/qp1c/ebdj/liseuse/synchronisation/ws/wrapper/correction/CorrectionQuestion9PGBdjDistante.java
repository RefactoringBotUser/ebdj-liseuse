package fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.correction;

import fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.question.Question9PGBdjDistante;

public class CorrectionQuestion9PGBdjDistante {

	private Question9PGBdjDistante question9pgBdjDistante;

	private String reference;

	private Long index;

	private TypeCorrection typeCorrection;

	public Question9PGBdjDistante getQuestion9pgBdjDistante() {
		return question9pgBdjDistante;
	}

	public void setQuestion9pgBdjDistante(Question9PGBdjDistante question9pgBdjDistante) {
		this.question9pgBdjDistante = question9pgBdjDistante;
	}

	public TypeCorrection getTypeCorrection() {
		return typeCorrection;
	}

	public void setTypeCorrection(TypeCorrection typeCorrection) {
		this.typeCorrection = typeCorrection;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

}
