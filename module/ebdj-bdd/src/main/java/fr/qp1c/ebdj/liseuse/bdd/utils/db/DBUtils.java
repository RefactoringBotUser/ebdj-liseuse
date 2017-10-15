package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire permettant de réaliser des opérations élémentaires en lien
 * avec la base de données.
 * 
 * @author NICO
 *
 */
public class DBUtils {

	/**
	 * Protéger une valeur SQL comportant des quotes simples.
	 * 
	 * @param str
	 *            la chaine à protéger
	 * @return la chaine protégée
	 */
	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}
}
