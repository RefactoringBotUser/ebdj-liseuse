package fr.qp1c.ebdj.liseuse.commun.exchange.correction;

import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;

public class CorrectionQuestionFAFBdjDistante {

	private QuestionFAFBdjDistante questionFafBdjDistante;

	private String reference;

	private Long index;

	private TypeCorrection typeCorrection;

	public QuestionFAFBdjDistante getQuestionFafBdjDistante() {
		return questionFafBdjDistante;
	}

	public void setQuestionFafBdjDistante(QuestionFAFBdjDistante questionFafBdjDistante) {
		this.questionFafBdjDistante = questionFafBdjDistante;
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
