package fr.qp1c.ebdj.liseuse.commun.bean.question;

public abstract class QRUnique extends QR {

	// Attributs

	private String reference;

	private Source source;

	private Long id;
	

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(id);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(source);

		return sb.toString();
	}
}
