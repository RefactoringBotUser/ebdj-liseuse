package fr.qp1c.ebdj.moteur.bean.anomalie;

public enum TypeAnomalie {

	REPONSE_FAUSSE("Réponse fausse"), REPONSE_INCOMPLETE("Réponse incomplète"), REPONSE_INEXACTE(
			"Réponse inexacte"), QUESTION_FAUSSE("Réponse fausse"), QUESTION_PERIMEE(
					"Question périmée"), QUESTION_MAL_REDIGEE("Question mal rédigée"), MULTI_ANOMALIE("Autre problème");

	private final String name;

	private TypeAnomalie(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	@Override
	public String toString() {
		return name;
	}
}
