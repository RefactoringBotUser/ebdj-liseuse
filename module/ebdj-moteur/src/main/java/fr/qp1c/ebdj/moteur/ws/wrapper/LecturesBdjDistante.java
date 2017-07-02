package fr.qp1c.ebdj.moteur.ws.wrapper;

import java.util.List;

public class LecturesBdjDistante {

	private List<BdjDistanteLecture> lectures;

	private AuthentificationBdj authentificationBdj;

	public List<BdjDistanteLecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<BdjDistanteLecture> lectures) {
		this.lectures = lectures;
	}

	public AuthentificationBdj getAuthentificationBdj() {
		return authentificationBdj;
	}

	public void setAuthentificationBdj(AuthentificationBdj authentificationBdj) {
		this.authentificationBdj = authentificationBdj;
	}

}
