package fr.qp1c.ebdj.liseuse.bdd.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.qp1c.ebdj.liseuse.bdd.service.ParametrageService;

public class ParametrageServiceImpl implements ParametrageService {

	/**
	 * Default logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParametrageServiceImpl.class);
	
	@Override
	public String afficherFichierParametrage() {
		StringBuilder sb = new StringBuilder();

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("configuration.properties")))){
			byte[] buf = new byte[8];

			while (bis.read(buf) != -1) {
				sb.append(buf);
			}
		} catch (Exception e) {
			LOGGER.error("An error has occured :", e);
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
			input = this.getClass().getResourceAsStream("/configuration.properties");

			// load a properties file
			props.load(input);

			version = props.getProperty("version");
		} catch (IOException ex) {
			LOGGER.error("An error has occured :", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.error("An error has occured :", e);
				}
			}
		}

		return version;
	}

}
