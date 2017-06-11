package fr.qp1c.ebdj.moteur.service.impl;

import fr.qp1c.ebdj.moteur.bean.authentification.CleAuthentification;
import fr.qp1c.ebdj.moteur.bean.synchro.SynchroArtefact;
import fr.qp1c.ebdj.moteur.dao.DBConnecteurSynchroDao;
import fr.qp1c.ebdj.moteur.dao.impl.DBConnecteurSynchroDaoImpl;
import fr.qp1c.ebdj.moteur.service.AuthentificationService;
import fr.qp1c.ebdj.moteur.service.SynchronisationService;

public class SynchronisationServiceImpl implements SynchronisationService {

	private AuthentificationService authentificationService = new AuthentificationServiceImpl();

	private DBConnecteurSynchroDao dbConnecteurSynchroDao = new DBConnecteurSynchroDaoImpl();

	@Override
	public boolean synchroniser() {

		// 9PG

		// 1. retrouver la référence max (table synchronisation
		// 2. vérifier qu'il n'existe pas de référence max
		// 3. récupérer la référence max dans le référentiel centralisé de
		// question
		// 4. compter le nombre de question à récupérer

		// 5. Récupérer en mémoire 100 questions
		// - insérer en base
		// - mettre à jour la table de synchronisation
		// - mettre à jour barre d'avancement

		// Uploader les questions jouées

		// Consommation d'un service REST
		// Clé = adresse MAC + clé de service

		CleAuthentification cleAuthentification = authentificationService.recupererCleActivation();

		SynchroArtefact synchroArtefact = retrouverIndexQuestion(cle, cleAuthentification);
		
		if()

		return true;
	}

	// public IndexSynchro recupererInfosSynchro(){
	//
	// ret
	//
	// }

	public SynchroArtefact retrouverIndexQuestion(String cle, CleAuthentification cleAuthentification) {

		Integer indexBdjLocale = dbConnecteurSynchroDao.recupererIndexParCle(cle);

		// TODO appeler WS
		Integer indexBbjDistante = null;

		SynchroArtefact artefact = new SynchroArtefact();
		artefact.setIndexBdjLocale(indexBdjLocale);
		artefact.setIndexBdjDistante(indexBdjDistante);

		// Compter le nombre de questions entre 2 indexs

		return artefact;
	}

}
