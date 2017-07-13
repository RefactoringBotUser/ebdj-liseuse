package fr.qp1c.ebdj.utils;

public class StringUtilities {

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

	public static String formaterNumeroQuestion(int nbQuestion) {
		String nbQuestionString = String.valueOf(nbQuestion) + " -";
		if (nbQuestion < 10) {
			nbQuestionString = " " + nbQuestionString;
		}
		return nbQuestionString;
	}
}
