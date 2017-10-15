package fr.qp1c.ebdj.liseuse.bdd.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;

public class ParametrageServiceImpl implements ParametrageService {

	@Override
	public String afficherFichierParametrage() {
		// Nous déclarons nos objets en dehors du bloc try/catch
		BufferedInputStream bis;
		StringBuffer sb = new StringBuffer();

		try {
			bis = new BufferedInputStream(new FileInputStream(new File("configuration.properties")));
			byte[] buf = new byte[8];

			while (bis.read(buf) != -1) {
				sb.append(buf);
			}
			;
			// On ferme nos flux de données
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	@Override
	public String afficherVersionApplication() {
		Properties props = new Properties();

		InputStream input = null;

		String version = null;

		try {
			// http://blog.soebes.de/blog/2014/01/02/version-information-into-your-appas-with-maven/
			// File f=new File(".");
			// System.out.println(f.getAbsolutePath());
			input = this.getClass().getResourceAsStream("/configuration.properties");

			// load a properties file
			props.load(input);

			version = props.getProperty("version");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return version;
	}

}
