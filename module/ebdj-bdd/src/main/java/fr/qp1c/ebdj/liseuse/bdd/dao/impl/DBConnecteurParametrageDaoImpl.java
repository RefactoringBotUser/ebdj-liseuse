package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurParametrageDao;

public class DBConnecteurParametrageDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurParametrageDao {

    @Override
    public String recupererParametrage(String cle) {
        // Création de la requête
        String requete = String.format("SELECT valeur FROM PARAMETRAGE WHERE cle='%s';", cle);

        ResultSetHandler<String> h = new ResultSetHandler<String>() {
            @Override
            public String handle(ResultSet rs) throws SQLException {
                String valeur = "";

                if (rs.next()) {
                    valeur = rs.getString(1);
                }
                return valeur;
            }
        };

        return executerRequete(requete, h);
    }

    @Override
    public void modifierParametrage(String cle, String valeur) {
        // Création de la requête
        String requete = String.format("UPDATE PARAMETRAGE SET valeur='%s' WHERE cle='%s';", valeur, cle);

        executerUpdateOuInsert(requete);
    }

}
