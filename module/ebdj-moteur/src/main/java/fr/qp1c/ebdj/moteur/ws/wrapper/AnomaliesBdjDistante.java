package fr.qp1c.ebdj.moteur.ws.wrapper;

import java.util.List;

public class AnomaliesBdjDistante {

	private AuthentificationBdj authentificationBdj;

	private List<BdjDistanteAnomalie> anomalies;

	public List<BdjDistanteAnomalie> getAnomalies() {
		return anomalies;
	}

	public void setAnomalies(List<BdjDistanteAnomalie> anomalies) {
		this.anomalies = anomalies;
	}

	public AuthentificationBdj getAuthentificationBdj() {
		return authentificationBdj;
	}

	public void setAuthentificationBdj(AuthentificationBdj authentificationBdj) {
		this.authentificationBdj = authentificationBdj;
	}

}
