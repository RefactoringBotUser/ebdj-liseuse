package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.liseuse.commun.utils.Utils;

public class DBConnecteurSynchroDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurSynchroDao {

	@Override
	public Long recupererIndexParCle(String cle) {
		// Création de la requête
		String requete = String.format("SELECT valeur FROM SYNCHRO WHERE cle='%s';", cle);

		ResultSetHandler<Long> h = new ResultSetHandler<Long>() {
			@Override
			public Long handle(ResultSet rs) throws SQLException {
				Long index = Long.valueOf(0);

				if (rs.next()) {
					index = rs.getLong(1);
				}
				return index;
			}
		};

		return executerRequete(requete, h);
	}

	@Override
	public void modifierIndexParCle(String cle, Long nouvelIndex) {
		// Création de la requête
		String requete = String.format("UPDATE SYNCHRO SET valeur=%d, date_derniere_synchro='%s' WHERE cle='%s';",
				nouvelIndex, Utils.recupererDateHeureCourante(), cle);

		executerUpdateOuInsert(requete);
	}

}
