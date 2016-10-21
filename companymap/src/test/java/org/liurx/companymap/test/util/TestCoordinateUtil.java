package org.liurx.companymap.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.liurx.jobmap.data.Coordinate;
import org.liurx.jobmap.util.CoordinateUtil;

public class TestCoordinateUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Coordinate cor = new Coordinate("116.331254", "39.951229");
		assertTrue(CoordinateUtil.isInBJ(cor));
		
		cor.setLongitude("120.331254");
		assertFalse(CoordinateUtil.isInBJ(cor));
		
		cor.setLongitude("116.331254");
		cor.setLatitude("42.951229");
		assertFalse(CoordinateUtil.isInBJ(cor));
	}

}
