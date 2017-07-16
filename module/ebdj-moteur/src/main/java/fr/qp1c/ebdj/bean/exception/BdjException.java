package fr.qp1c.ebdj.bean.exception;

public class BdjException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codeInterne;

	public BdjException(String codeInterne) {
		super();
		this.codeInterne = codeInterne;
	}

	public BdjException(String message, String codeInterne) {
		super(message);
		this.codeInterne = codeInterne;
	}

	public BdjException(Throwable cause, String codeInterne) {
		super(cause);
		this.codeInterne = codeInterne;
	}

	public BdjException(String message, Throwable cause, String codeInterne) {
		super(message, cause);
		this.codeInterne = codeInterne;
	}

	public BdjException(Exception e, String codeInterne) {
		super(e.getMessage(), e.getCause());
		this.codeInterne = codeInterne;
	}

	// Getters - setters

	public String getCodeInterne() {
		return codeInterne;
	}

	public void setCodeInterne(String codeInterne) {
		this.codeInterne = codeInterne;
	}
}
