/*
 * LocationTest
 * 
 * Version: 2018-10-29
 *
 * Date: Oct 29, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {
	Location origin;

	@Before
	public void setUp() throws Exception {
		origin = new Location(Coordinate.ORIGIN);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testContstructor() {
		Location l = new Location(Coordinate.UNIT_X);
		assertEquals(l.coordinate, Coordinate.UNIT_X);
		assertNotEquals(l.coordinate, Coordinate.ORIGIN);
		
		l = new Location(Coordinate.UNIT_Y);
		assertEquals(l.coordinate, Coordinate.UNIT_Y);
		assertNotEquals(l.coordinate, Coordinate.ORIGIN);
		
		l = new Location(Coordinate.UNIT_Z);
		assertEquals(l.coordinate, Coordinate.UNIT_Z);
		assertNotEquals(l.coordinate, Coordinate.ORIGIN);
	}

	@Test
	public void testSet() {
		assertEquals(origin.coordinate, Coordinate.ORIGIN);
		
		origin.setCoordinate(Coordinate.UNIT_X);
		assertEquals(origin.coordinate, Coordinate.UNIT_X);
		
		origin.setCoordinate(Coordinate.UNIT_Y);
		assertEquals(origin.coordinate, Coordinate.UNIT_Y);
		
		origin.setCoordinate(Coordinate.UNIT_Z);
		assertEquals(origin.coordinate, Coordinate.UNIT_Z);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContstructorNull() {
		Location l = new Location(null);
		assertNotEquals(l.coordinate, Coordinate.ORIGIN);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNull() {
		assertNotEquals(origin.coordinate, null);
		origin.setCoordinate(null);
		assertNotEquals(origin.coordinate, Coordinate.ORIGIN);
		fail();
	}

}
