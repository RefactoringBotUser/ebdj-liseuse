package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import java.io.File;
import java.io.IOException;

import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ConfigTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void test() {
		// assertNotNull("jdbc:sqlite:/home/ngendron/git/ebdj-liseuse/module/ebdj-bdd/db/",
		// Configuration.getInstance().getUrlDb());
	}

	@Test
	public void testFile() throws IOException {
		File f = new File(".");
		System.out.println(f.getAbsoluteFile());

		System.out.println(FilePath.get("/src/test/resources/schema.sql").toString());
		FileUtils.newInputStream("/src/test/resources/schema.sql");

	}

}