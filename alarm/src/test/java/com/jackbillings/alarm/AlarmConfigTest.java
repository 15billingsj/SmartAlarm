package com.jackbillings.alarm;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlarmConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testBasic() throws IOException {
		AlarmConfig config = new AlarmConfig();
		config.setChanceOfPrecipitationMax(123);
		assertEquals(123, config.getChanceOfPrecipitationMax());
		
		config = new AlarmConfig("src/test/resources/testconfig.properties");
		assertEquals(456, config.getChanceOfPrecipitationMax());
	}
	
	/**
	 * Confirm that it's ok if the file does not exist, and that we get reasonable defaults.
	 * @throws IOException
	 */
	@Test
	public void testNonExistentFile() throws IOException {
		AlarmConfig config = new AlarmConfig("bogus");
		assertEquals(100, config.getChanceOfPrecipitationMax());
		assertEquals(0, config.getChanceOfPrecipitationMin());
	}
	
	

}
