package fr.qp1c.ebdj.moteur.bean.authentification;

public class CleAuthentification {

	private String token;

	private String adresseMac;

	public CleAuthentification(String token, String adresseMac) {
		this.token = token;
		this.adresseMac = adresseMac;
	}

	public String getAdresseMac() {
		return adresseMac;
	}

	public void setAdresseMac(String adresseMac) {
		this.adresseMac = adresseMac;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
