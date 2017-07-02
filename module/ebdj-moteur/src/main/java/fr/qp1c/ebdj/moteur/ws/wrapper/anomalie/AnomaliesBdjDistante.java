package fr.qp1c.ebdj.moteur.ws.wrapper.anomalie;

import java.util.List;

import fr.qp1c.ebdj.moteur.ws.wrapper.AuthentificationBdj;

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
