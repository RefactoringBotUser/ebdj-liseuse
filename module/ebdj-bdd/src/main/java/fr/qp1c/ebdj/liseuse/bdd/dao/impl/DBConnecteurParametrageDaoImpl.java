package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurParametrageDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;

public class DBConnecteurParametrageDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurParametrageDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurParametrageDaoImplTest.class);

	@Override
	public String recupererParametrage(String cle) {

		String valeur = "";

		// Création de la requête		
		String requete= String .format("SELECT valeur FROM PARAMETRAGE WHERE cle='%d';", cle);

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(requete);
			if (rs.next()) {
				valeur = rs.getString(1);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}
		return valeur;
	}

	@Override
	public void modifierParametrage(String cle, String valeur) {
		// Création de la requête
		String requete=String.format("UPDATE PARAMETRAGE SET valeur='%d' WHERE cle='%d';", valeur, cle);
		
		executerUpdateOuInsert(requete);
	}

}
