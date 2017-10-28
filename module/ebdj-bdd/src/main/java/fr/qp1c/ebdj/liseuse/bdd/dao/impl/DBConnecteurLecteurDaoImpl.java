package fr.qp1c.ebdj.liseuse.bdd.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurLecteurDao;
import fr.qp1c.ebdj.liseuse.bdd.utils.db.DBManager;
import fr.qp1c.ebdj.liseuse.bdd.utils.exception.DBManagerException;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;

public class DBConnecteurLecteurDaoImpl implements DBConnecteurLecteurDao {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConnecteurLecteurDaoImpl.class);

	@Override
	public void ajouterLecteur(Lecteur lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifierLecteur(String reference, Lecteur lecteur) throws DBManagerException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Lecteur> listerLecteur() throws DBManagerException {

		List<Lecteur> listeLecteurs = new ArrayList<>();

		// Création de la requête

		StringBuilder query = new StringBuilder();
		query.append("SELECT id,nom,prenom FROM LECTEUR ORDER BY nom, prenom;");

		LOGGER.debug(query.toString());

		try {
			// Connexion à la base de données SQLite
			Connection connection = DBManager.getInstance().connect();
			Statement stmt = connection.createStatement();

			// Executer la requête
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {

				// Convertir chaque lecteur
				Lecteur lecteur = new Lecteur();
				lecteur.setId(rs.getLong("id"));
				lecteur.setNom(rs.getString("nom"));
				lecteur.setPrenom(rs.getString("prenom"));

				LOGGER.info("Lecteur: " + lecteur);

				// Ajouter la lecteur à la liste
				listeLecteurs.add(lecteur);
			}

			// Fermeture des connections.
			stmt.close();
			DBManager.getInstance().close(connection);
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
			throw new DBManagerException();
		}

		return listeLecteurs;
	}

	@Override
	public Lecteur recupererLecteur(String reference) throws DBManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testerExistanteLecteur(String nom, String prenom) throws DBManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void supprimerLecteur(String reference) throws DBManagerException {
		// TODO Auto-generated method stub

	}

}
