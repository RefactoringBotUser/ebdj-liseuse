package fr.qp1c.ebdj.liseuse.commun.exchange.correction;

import fr.qp1c.ebdj.liseuse.commun.exchange.question.QuestionJDBdjDistante;

public class CorrectionQuestionJDBdjDistante extends CorrectIonQuestionGeneriqueBdjDistante{

	private QuestionJDBdjDistante questionJdBdjDistante;

	public QuestionJDBdjDistante getQuestionJdBdjDistante() {
		return questionJdBdjDistante;
	}

	public void setQuestionJdBdjDistante(QuestionJDBdjDistante questionJdBdjDistante) {
		this.questionJdBdjDistante = questionJdBdjDistante;
	}

}
