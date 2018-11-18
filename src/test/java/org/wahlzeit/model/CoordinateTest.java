/*
 * CoordinateTest
 * 
 * Version: 2018-10-29
 *
 * Date: Oct 29, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CoordinateTest {
	private static final int REPEAT_COUNT = 0x10_0000;
	
	/**
	 * Halve of the double precision
	 */
	private static final double ε = 1e-26;
	private static final double SqrtOf2 = Math.sqrt(2.0);
	private static final double SqrtOf3 = Math.sqrt(3.0);
	private static final double SqrtOf6 = Math.sqrt(6.0);
	
	private static final CartesianCoordinate ONE = new CartesianCoordinate(1.0, 1.0, 1.0);
	private static final CartesianCoordinate MINUS_ONE = new CartesianCoordinate(-1.0, -1.0, -1.0);
	private static final CartesianCoordinate MINUS_ZERO = new CartesianCoordinate(-0.0, -0.0, -0.0);
	private static final CartesianCoordinate INF = new CartesianCoordinate(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	private static final CartesianCoordinate MINUS_INF = new CartesianCoordinate(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	private static final CartesianCoordinate NAN = new CartesianCoordinate(Double.NaN, Double.NaN, Double.NaN);
	
	private static Random rng;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		rng = new Random();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	protected void assertDoubleEq(double d1, double d2) {
		assert(Math.abs(d1-d2) <= ε * Math.abs(d1+d2));
	}
	
	protected CartesianCoordinate newRandomCoordinate() {
		return new CartesianCoordinate(rng.nextDouble(), rng.nextDouble(), rng.nextDouble());
	}
	
	protected void runMultibletimes(Runnable r) {
		for (int i = 0; i < REPEAT_COUNT; i++) {
			r.run();
		}
	}
	
	protected CartesianCoordinate copyCoordinate(CartesianCoordinate o) {
		return new CartesianCoordinate(o.x, o.y, o.z);
	}
	
	protected double euclidDistance(double x1, double x2, double y1, double y2, double z1, double z2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	@Test
	public void testConstructorRandom() {
		runMultibletimes(() -> {
			double x = rng.nextDouble(),
					y = rng.nextDouble(),
					z = rng.nextDouble();
			
			CartesianCoordinate c = new CartesianCoordinate(x, y, z);
			
			assert(x == c.x);
			assert(y == c.y);
			assert(z == c.z);
		});
	}

	@Test
	public void testEqualityIdentity() {
		assertEquals(CartesianCoordinate.ORIGIN, CartesianCoordinate.ORIGIN);
		assertEquals(CartesianCoordinate.UNIT_X, CartesianCoordinate.UNIT_X);
		assertEquals(CartesianCoordinate.UNIT_Y, CartesianCoordinate.UNIT_Y);
		assertEquals(CartesianCoordinate.UNIT_Z, CartesianCoordinate.UNIT_Z);
		
		assertEquals(ONE, ONE);
		assertEquals(MINUS_ONE, MINUS_ONE);
		assertEquals(MINUS_ZERO, MINUS_ZERO);
		assertEquals(INF, INF);
		assertEquals(MINUS_INF, MINUS_INF);

		assertEquals(NAN.hashCode(), NAN.hashCode());
		assertEquals(NAN, NAN);
	}

	@Test
	public void testEqualityIdentityRandom() {
		runMultibletimes(() -> {
			CartesianCoordinate c = newRandomCoordinate();
			assertEquals(c, c);
		});
	}
	
	@Test
	public void testEqualityCopy() {
		assertEquals(CartesianCoordinate.ORIGIN, copyCoordinate(CartesianCoordinate.ORIGIN));
		assertEquals(CartesianCoordinate.UNIT_X, copyCoordinate(CartesianCoordinate.UNIT_X));
		assertEquals(CartesianCoordinate.UNIT_Y, copyCoordinate(CartesianCoordinate.UNIT_Y));
		assertEquals(CartesianCoordinate.UNIT_Z, copyCoordinate(CartesianCoordinate.UNIT_Z));
		
		assertEquals(ONE, copyCoordinate(ONE));
		assertEquals(MINUS_ONE, copyCoordinate(MINUS_ONE));
		assertEquals(MINUS_ZERO, copyCoordinate(MINUS_ZERO));
		assertEquals(INF, copyCoordinate(INF));
		assertEquals(MINUS_INF, copyCoordinate(MINUS_INF));
		
		assertEquals(NAN.hashCode(), copyCoordinate(NAN).hashCode());
	}
	
	@Test
	public void testEqualityWithNull() {
		assertNotEquals(CartesianCoordinate.ORIGIN, null);
		assertNotEquals(null, CartesianCoordinate.ORIGIN);
		
		assertFalse(CartesianCoordinate.ORIGIN.isEqual(null));
	}

	@Test
	public void testEqualityCopyRandom() {
		runMultibletimes(() -> {
			CartesianCoordinate c = newRandomCoordinate();
			assertEquals(c, copyCoordinate(c));
		});
	}

	@Test
	public void testInequality() {
		assertNotEquals(CartesianCoordinate.UNIT_X, CartesianCoordinate.UNIT_Y);
		assertNotEquals(CartesianCoordinate.UNIT_Y, CartesianCoordinate.UNIT_Z);
		assertNotEquals(CartesianCoordinate.UNIT_Z, CartesianCoordinate.UNIT_X);
		
		assertNotEquals(CartesianCoordinate.UNIT_X, CartesianCoordinate.UNIT_Z);
		assertNotEquals(CartesianCoordinate.UNIT_Y, CartesianCoordinate.UNIT_X);
		assertNotEquals(CartesianCoordinate.UNIT_Z, CartesianCoordinate.UNIT_Y);
		
		assertNotEquals(CartesianCoordinate.UNIT_X, CartesianCoordinate.ORIGIN);
		assertNotEquals(CartesianCoordinate.UNIT_Y, CartesianCoordinate.ORIGIN);
		assertNotEquals(CartesianCoordinate.UNIT_Z, CartesianCoordinate.ORIGIN);
		
		assertNotEquals(CartesianCoordinate.UNIT_X, ONE);
		assertNotEquals(CartesianCoordinate.UNIT_Y, ONE);
		assertNotEquals(CartesianCoordinate.UNIT_Z, ONE);
		
		assertNotEquals(ONE, MINUS_ONE);
		assertNotEquals(MINUS_ONE, ONE);
		assertNotEquals(INF, MINUS_INF);
		assertNotEquals(MINUS_INF, INF);
		
		assertNotEquals(NAN, copyCoordinate(NAN));
	}

	@Test
	public void testInequalityRandom() {
		runMultibletimes(() -> {
			CartesianCoordinate c1 = newRandomCoordinate();
			CartesianCoordinate c2 = newRandomCoordinate();
			
			if (c1.x == c2.x && c1.y == c2.y && c1.z == c2.z) {
				return;
			}
			
			assertNotEquals(c1, c2);
		});
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testNullDistance() {
		CartesianCoordinate.ORIGIN.getDistance(null);
	}
	
	@Test
	public void testZeroDistance() {
		assertTrue(CartesianCoordinate.ORIGIN.getDistance(CartesianCoordinate.ORIGIN) == 0.0);
		assertTrue(CartesianCoordinate.UNIT_X.getDistance(CartesianCoordinate.UNIT_X) == 0.0);
		assertTrue(CartesianCoordinate.UNIT_Y.getDistance(CartesianCoordinate.UNIT_Y) == 0.0);
		assertTrue(CartesianCoordinate.UNIT_Z.getDistance(CartesianCoordinate.UNIT_Z) == 0.0);

		assertTrue(ONE.getDistance(ONE) == 0.0);
		assertTrue(MINUS_ONE.getDistance(MINUS_ONE) == 0.0);
		assertTrue(CartesianCoordinate.ORIGIN.getDistance(MINUS_ZERO) == 0.0);
		assertTrue(MINUS_ZERO.getDistance(MINUS_ZERO) == 0.0);

		assertTrue(Double.isNaN(NAN.getDistance(NAN)));
	}
	
	@Test
	public void testOneDistance() {
		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(CartesianCoordinate.ORIGIN), 1.0);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(CartesianCoordinate.ORIGIN), 1.0);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(CartesianCoordinate.ORIGIN), 1.0);

		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(MINUS_ZERO), 1.0);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(MINUS_ZERO), 1.0);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(MINUS_ZERO), 1.0);
	}
	
	@Test
	public void testSomeDistance() {
		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(CartesianCoordinate.UNIT_Z), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(CartesianCoordinate.UNIT_X), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(CartesianCoordinate.UNIT_Y), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(CartesianCoordinate.UNIT_Y), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(CartesianCoordinate.UNIT_Z), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(CartesianCoordinate.UNIT_X), SqrtOf2);

		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(ONE), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(ONE), SqrtOf2);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(ONE), SqrtOf2);

		assertDoubleEq(CartesianCoordinate.ORIGIN.getDistance(ONE), SqrtOf3);
		assertDoubleEq(CartesianCoordinate.ORIGIN.getDistance(MINUS_ONE), SqrtOf3);
		assertDoubleEq(MINUS_ZERO.getDistance(ONE), SqrtOf3);
		assertDoubleEq(MINUS_ZERO.getDistance(MINUS_ONE), SqrtOf3);

		assertDoubleEq(CartesianCoordinate.UNIT_X.getDistance(MINUS_ONE), SqrtOf6);
		assertDoubleEq(CartesianCoordinate.UNIT_Y.getDistance(MINUS_ONE), SqrtOf6);
		assertDoubleEq(CartesianCoordinate.UNIT_Z.getDistance(MINUS_ONE), SqrtOf6);
	}
	
	@Test
	public void testDistanceRandom() {
		runMultibletimes(() -> {
			CartesianCoordinate c1 = newRandomCoordinate();
			CartesianCoordinate c2 = newRandomCoordinate();
			
			double dist = euclidDistance(c1.x, c2.x, c1.y, c2.y, c1.z, c2.z);

			assertDoubleEq(c1.getDistance(c2), dist);
			assertDoubleEq(c2.getDistance(c1), dist);
			assertDoubleEq(c1.getDistance(c2), c2.getDistance(c1));
		});
	}

}
