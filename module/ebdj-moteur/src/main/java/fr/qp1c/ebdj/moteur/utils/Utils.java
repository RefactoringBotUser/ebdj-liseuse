package fr.qp1c.ebdj.moteur.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import fr.qp1c.ebdj.moteur.bean.question.TypePhase;

public class Utils {

	public static String recupererAdresseMac() {
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
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

	public static String formatDate() {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = now.format(formatter);

		return formatDateTime;
	}

	public static String formaterDate(String date) {
		String result = "";

		if (date != null && date.length() >= 10) {
			// 2017-07-20
			result += date.substring(8, 10);
			result += "/";
			result += date.substring(5, 7);
			result += "/";
			result += date.substring(0, 4);
		}

		return result;
	}

}
