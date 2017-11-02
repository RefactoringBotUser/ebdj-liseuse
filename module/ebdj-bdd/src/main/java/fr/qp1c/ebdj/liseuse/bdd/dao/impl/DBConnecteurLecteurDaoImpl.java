package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.mapper.MapperQuestion;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;

public class DBConnecteurLecteurDaoImpl extends DBConnecteurGeneriqueImpl implements DBConnecteurLecteurDao {

    @Override
    public void ajouterLecteur(Lecteur lecteur) {
        // TODO Auto-generated method stub

    }

    @Override
    public void modifierLecteur(String reference, Lecteur lecteur) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Lecteur> listerLecteur() {
        // Création de la requête
        String requete = "SELECT id,nom,prenom FROM LECTEUR ORDER BY nom, prenom;";

        ResultSetHandler<List<Lecteur>> h = new ResultSetHandler<List<Lecteur>>() {
            @Override
            public List<Lecteur> handle(ResultSet rs) throws SQLException {
                List<Lecteur> listeLecteurs = new ArrayList<>();

                while (rs.next()) {
                    // Ajouter la lecteur à la liste
                    listeLecteurs.add(MapperQuestion.convertirLecteur(rs));
                }
                return listeLecteurs;
            }
        };

        return executerRequete(requete, h);
    }

    @Override
    public Lecteur recupererLecteur(String reference) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean testerExistanteLecteur(String nom, String prenom) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void supprimerLecteur(String reference) {
        // TODO Auto-generated method stub

    }

}
