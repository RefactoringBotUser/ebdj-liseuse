package fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.lecture;

import java.util.List;

import fr.qp1c.ebdj.liseuse.synchronisation.ws.wrapper.AuthentificationBdj;

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
