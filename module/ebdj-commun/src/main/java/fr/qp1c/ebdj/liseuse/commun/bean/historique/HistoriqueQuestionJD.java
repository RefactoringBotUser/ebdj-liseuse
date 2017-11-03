package fr.qp1c.ebdj.liseuse.commun.bean.historique;

import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionJD;

public class HistoriqueQuestionJD extends HistoriqueQuestion {

	private QuestionJD question;

	public QuestionJD getQuestion() {
		return question;
	}

	public void setQuestion(QuestionJD question) {
		this.question = question;
	}
	
}
