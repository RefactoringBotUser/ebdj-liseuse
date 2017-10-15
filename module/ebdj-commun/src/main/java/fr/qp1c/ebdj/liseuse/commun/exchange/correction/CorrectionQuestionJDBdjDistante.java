package fr.qp1c.ebdj.liseuse.commun.exchange.correction;

import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

public class CorrectionQuestionJDBdjDistante {

	private QuestionJDBdjDistante questionJdBdjDistante;

	private String reference;

	private Long index;

	private TypeCorrection typeCorrection;

	public QuestionJDBdjDistante getQuestionJdBdjDistante() {
		return questionJdBdjDistante;
	}

	public void setQuestionJdBdjDistante(QuestionJDBdjDistante questionJdBdjDistante) {
		this.questionJdBdjDistante = questionJdBdjDistante;
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
