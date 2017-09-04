package fr.qp1c.ebdj.controller.jeu.phase.preferences;

import fr.qp1c.ebdj.moteur.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.moteur.bean.partie.NiveauPartie;
import fr.qp1c.ebdj.view.TaillePolice;

public interface PreferencesLecteur {

	void modifierTaille(TaillePolice taille);

	void definirNiveauPartie(NiveauPartie niveauPartie);

	void definirLecteur(Lecteur lecteur);
}
