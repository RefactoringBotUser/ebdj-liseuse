package fr.qp1c.ebdj.liseuse.bdd;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.qp1c.ebdj.liseuse.bdd.dao.DBConnecteurFAFDao;
import fr.qp1c.ebdj.liseuse.bdd.dao.impl.DBConnecteurFAFDaoImpl;

public class DatabaseTest {

	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static void createSchema() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "/home/ngendron/git/ebdj-liseuse/module/ebdj-bdd/src/test/resources/schema.sql", Charset.forName("UTF8"), false);
	}

	public static void importDataSet(String fileName) throws Exception {
		IDataSet dataSet = readDataSet(fileName);
		cleanlyInsert(dataSet);
	}

	private static IDataSet readDataSet(String fileName) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("./src/test/resources/dataset/"+fileName));
	}

	private static void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
}
