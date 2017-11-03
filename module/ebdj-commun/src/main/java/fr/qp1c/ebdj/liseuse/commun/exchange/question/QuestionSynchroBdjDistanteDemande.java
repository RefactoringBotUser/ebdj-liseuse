package fr.qp1c.ebdj.liseuse.commun.exchange.question;

import fr.qp1c.ebdj.liseuse.commun.exchange.AuthentificationBdj;

public class QuestionSynchroBdjDistanteDemande {

	private Long reference;

	private AuthentificationBdj authentificationBdj;

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public AuthentificationBdj getAuthentificationBdj() {
		return authentificationBdj;
	}

	public void setAuthentificationBdj(AuthentificationBdj authentificationBdj) {
		this.authentificationBdj = authentificationBdj;
	}

}
