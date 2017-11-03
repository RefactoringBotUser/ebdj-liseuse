package fr.qp1c.ebdj.liseuse.ihm.controller.jeu.phase.preferences;

import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.liseuse.ihm.view.TaillePolice;

public interface PreferencesLecteur {

	void modifierTaille(TaillePolice taille);

	void definirNiveauPartie(NiveauPartie niveauPartie);

	void definirLecteur(Lecteur lecteur);
}
