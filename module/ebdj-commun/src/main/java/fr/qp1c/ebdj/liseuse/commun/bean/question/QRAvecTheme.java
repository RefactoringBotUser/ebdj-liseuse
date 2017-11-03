package fr.qp1c.ebdj.liseuse.commun.bean.question;

public abstract class QRAvecTheme extends QRUnique {

	protected String theme;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(theme);

		return sb.toString();
	}
}
