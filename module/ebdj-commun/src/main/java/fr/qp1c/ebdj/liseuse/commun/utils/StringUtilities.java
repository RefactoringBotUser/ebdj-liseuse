package fr.qp1c.ebdj.liseuse.commun.utils;

/**
 * Classe utilitaire permettant de réaliser des opérations élémentaires en lien
 * avec la manipulation de chaine de caractères.
 * 
 * @author NICO
 *
 */
public class StringUtilities {
	
	private StringUtilities() {
		
	}

	/**
	 * Creates a string left padded to the specified width with the supplied
	 * padding character.
	 * 
	 * @param fieldWidth
	 *            the length of the resultant padded string.
	 * @param padChar
	 *            a character to use for padding the string.
	 * @param i
	 *            the string to be padded.
	 * @return the padded string.
	 */
	public static String pad(int fieldWidth, char padChar, int i) {
		StringBuilder sb = new StringBuilder();

		String s = String.valueOf(i);
		for (int j = s.length(); j < fieldWidth; j++) {
			sb.append(padChar);
		}
		sb.append(s);

		return sb.toString();
	}

	/**
	 * Creates a string left padded to the specified width with the supplied
	 * padding character.
	 * 
	 * @param fieldWidth
	 *            the length of the resultant padded string.
	 * @param padChar
	 *            a character to use for padding the string.
	 * @param s
	 *            the string to be padded.
	 * @return the padded string.
	 */
	public static String pad(int fieldWidth, char padChar, String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = s.length(); i < fieldWidth; i++) {
			sb.append(padChar);
		}
		sb.append(s);

		return sb.toString();
	}

	public static String formaterDate(String date) {
		String result = "";

		if (date != null && date.length() >= 10) {
			// 2017-07-20
			result += date.substring(8, 10);
			result += "/";
			result += date.substring(5, 7);
			result += "/";
			result += date.substring(0, 4);
		}

		return result;
	}

	public static int compterNombreDeMots(String question) {
		return question.split(" ").length;
	}

	public static String formaterNumeroQuestion(int nbQuestion) {
		String nbQuestionString = String.valueOf(nbQuestion) + " -";
		if (nbQuestion < 10) {
			nbQuestionString = " " + nbQuestionString;
		}
		return nbQuestionString;
	}
}
