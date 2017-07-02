package fr.qp1c.ebdj.moteur.dao;

public interface DBConnecteurSynchroDao {

	public Long recupererIndexParCle(String cle);

	public void modifierIndexParCle(String cle, Long nouvelIndex);
}
