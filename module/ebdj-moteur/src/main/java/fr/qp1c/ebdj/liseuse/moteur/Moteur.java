package fr.qp1c.ebdj.liseuse.moteur;

import fr.qp1c.ebdj.liseuse.commun.bean.anomalie.SignalementAnomalie;
import fr.qp1c.ebdj.liseuse.commun.bean.lecteur.Lecteur;
import fr.qp1c.ebdj.liseuse.commun.bean.partie.NiveauPartie;

public interface Moteur {

	void definirLecteur(Lecteur lecteur);
	
	void definirNiveauPartie(NiveauPartie niveauPartie);
	
	void signalerAnomalie(SignalementAnomalie signalementAnomalie);
}
