package fr.qp1c.ebdj.moteur.dao;

public interface DBConnecteurSynchroDao {

	public Integer recupererIndexParCle(String cle);

	public void modifierIndexParCle(String cle, Integer nouvelIndex);
}
