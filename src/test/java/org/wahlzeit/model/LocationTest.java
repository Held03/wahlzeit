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
		origin = new Location(CartesianCoordinate.ORIGIN);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testContstructor() {
		Location l = new Location(CartesianCoordinate.UNIT_X);
		assertEquals(l.coordinate, CartesianCoordinate.UNIT_X);
		assertNotEquals(l.coordinate, CartesianCoordinate.ORIGIN);
		
		l = new Location(CartesianCoordinate.UNIT_Y);
		assertEquals(l.coordinate, CartesianCoordinate.UNIT_Y);
		assertNotEquals(l.coordinate, CartesianCoordinate.ORIGIN);
		
		l = new Location(CartesianCoordinate.UNIT_Z);
		assertEquals(l.coordinate, CartesianCoordinate.UNIT_Z);
		assertNotEquals(l.coordinate, CartesianCoordinate.ORIGIN);
	}

	@Test
	public void testSet() {
		assertEquals(origin.coordinate, CartesianCoordinate.ORIGIN);
		
		origin.setCoordinate(CartesianCoordinate.UNIT_X);
		assertEquals(origin.coordinate, CartesianCoordinate.UNIT_X);
		
		origin.setCoordinate(CartesianCoordinate.UNIT_Y);
		assertEquals(origin.coordinate, CartesianCoordinate.UNIT_Y);
		
		origin.setCoordinate(CartesianCoordinate.UNIT_Z);
		assertEquals(origin.coordinate, CartesianCoordinate.UNIT_Z);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContstructorNull() {
		Location l = new Location(null);
		assertNotEquals(l.coordinate, CartesianCoordinate.ORIGIN);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNull() {
		assertNotEquals(origin.coordinate, null);
		origin.setCoordinate(null);
		assertNotEquals(origin.coordinate, CartesianCoordinate.ORIGIN);
		fail();
	}

}
