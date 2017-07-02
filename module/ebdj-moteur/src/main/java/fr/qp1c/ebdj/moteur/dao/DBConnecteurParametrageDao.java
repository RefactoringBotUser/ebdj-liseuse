package fr.qp1c.ebdj.moteur.dao;

public interface DBConnecteurParametrageDao {
	public String recupererParametrage(String cle);

	public void modifierParametrage(String cle, String valeur);
}
