package fr.qp1c.ebdj.moteur.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.moteur.bean.question.TypePhase;

public class Utils {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	/**
	 * Récupérer l'adresse MAC de la machine.
	 * 
	 * @return l'adresse MAC - en cas d'erreur, la méthode retournera null.
	 */
	public static String recupererAdresseMac() {
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			LOGGER.debug("Adresse IP courante: {}", ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			String adresseMac = sb.toString();

			LOGGER.debug("Adresse MAC courante: {}", adresseMac);

			return adresseMac;
		} catch (UnknownHostException e) {
			LOGGER.error("UnknownHostException - une erreur est survenue : ", e);
		} catch (SocketException e) {
			LOGGER.error("SocketException - une erreur est survenue : ", e);
		}
		return null;
	}

	public static String formaterReference(String reference, TypePhase typePhase) {

		switch (typePhase) {
		case NPG:
			return "Q_9PG_" + StringUtilities.pad(5, '0', reference);
		case QALS:
			return "Q_4ALS_" + StringUtilities.pad(4, '0', reference);
		case JD:
			return "Q_JD_" + StringUtilities.pad(5, '0', reference);
		case FAF:
			return "Q_FAF_" + StringUtilities.pad(5, '0', reference);
		}
		return null;
	}

	/**
	 * Calculer la date et l'heure courante et la formater sous forme de chaine
	 * de caractères (format: yyyy-MM-dd HH:mm:ss).
	 * 
	 * @return la date et l'heure courante formatée
	 */
	public static String recupererDateHeureCourante() {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = now.format(formatter);

		return formatDateTime;
	}

}
