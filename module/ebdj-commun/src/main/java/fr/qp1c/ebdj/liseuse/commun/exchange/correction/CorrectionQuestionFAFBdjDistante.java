package fr.qp1c.ebdj.liseuse.commun.exchange.correction;

import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionFAFBdjDistante;

public class CorrectionQuestionFAFBdjDistante extends CorrectIonQuestionGeneriqueBdjDistante{

	private QuestionFAFBdjDistante questionFafBdjDistante;


	public QuestionFAFBdjDistante getQuestionFafBdjDistante() {
		return questionFafBdjDistante;
	}

	public void setQuestionFafBdjDistante(QuestionFAFBdjDistante questionFafBdjDistante) {
		this.questionFafBdjDistante = questionFafBdjDistante;
	}

}
