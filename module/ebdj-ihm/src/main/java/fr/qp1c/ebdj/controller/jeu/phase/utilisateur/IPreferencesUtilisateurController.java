package fr.qp1c.ebdj.controller.jeu.phase.utilisateur;

import fr.qp1c.ebdj.model.NiveauPartie;
import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.view.TaillePolice;

public interface IPreferencesUtilisateurController {

	void modifierTaille(TaillePolice taille);

	void definirNiveauPartie(NiveauPartie niveauPartie);

	void definirLecteur(Lecteur lecteur);
}
