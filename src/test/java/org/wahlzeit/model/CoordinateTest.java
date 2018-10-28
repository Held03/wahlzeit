package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
	
	private static final Coordinate ONE = new Coordinate(1.0, 1.0, 1.0);
	private static final Coordinate MINUS_ONE = new Coordinate(-1.0, -1.0, -1.0);
	private static final Coordinate MINUS_ZERO = new Coordinate(-0.0, -0.0, -0.0);
	private static final Coordinate INF = new Coordinate(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	private static final Coordinate MINUS_INF = new Coordinate(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	private static final Coordinate NAN = new Coordinate(Double.NaN, Double.NaN, Double.NaN);
	
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
	
	protected Coordinate newRandomCoordinate() {
		return new Coordinate(rng.nextDouble(), rng.nextDouble(), rng.nextDouble());
	}
	
	protected void runMultibletimes(Runnable r) {
		for (int i = 0; i < REPEAT_COUNT; i++) {
			r.run();
		}
	}
	
	protected Coordinate copyCoordinate(Coordinate o) {
		return new Coordinate(o.x, o.y, o.z);
	}
	
	protected double euclidDistance(double x1, double x2, double y1, double y2, double z1, double z2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return Math.sqrt(dx*dx + dy*dx + dz*dz);
	}

	@Test
	public void testEqualityIdentity() {
		assertEquals(Coordinate.ORIGIN, Coordinate.ORIGIN);
		assertEquals(Coordinate.UNIT_X, Coordinate.UNIT_X);
		assertEquals(Coordinate.UNIT_Y, Coordinate.UNIT_Y);
		assertEquals(Coordinate.UNIT_Z, Coordinate.UNIT_Z);
		
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
			Coordinate c = newRandomCoordinate();
			assertEquals(c, c);
		});
	}
	
	@Test
	public void testEqualityCopy() {
		assertEquals(Coordinate.ORIGIN, copyCoordinate(Coordinate.ORIGIN));
		assertEquals(Coordinate.UNIT_X, copyCoordinate(Coordinate.UNIT_X));
		assertEquals(Coordinate.UNIT_Y, copyCoordinate(Coordinate.UNIT_Y));
		assertEquals(Coordinate.UNIT_Z, copyCoordinate(Coordinate.UNIT_Z));
		
		assertEquals(ONE, copyCoordinate(ONE));
		assertEquals(MINUS_ONE, copyCoordinate(MINUS_ONE));
		assertEquals(MINUS_ZERO, copyCoordinate(MINUS_ZERO));
		assertEquals(INF, copyCoordinate(INF));
		assertEquals(MINUS_INF, copyCoordinate(MINUS_INF));
		
		assertEquals(NAN.hashCode(), copyCoordinate(NAN).hashCode());
	}

	@Test
	public void testEqualityCopyRandom() {
		runMultibletimes(() -> {
			Coordinate c = newRandomCoordinate();
			assertEquals(c, copyCoordinate(c));
		});
	}

	@Test
	public void testInequality1() {
		assertNotEquals(Coordinate.UNIT_X, Coordinate.UNIT_Y);
		assertNotEquals(Coordinate.UNIT_Y, Coordinate.UNIT_Z);
		assertNotEquals(Coordinate.UNIT_Z, Coordinate.UNIT_X);
		
		assertNotEquals(Coordinate.UNIT_X, Coordinate.UNIT_Z);
		assertNotEquals(Coordinate.UNIT_Y, Coordinate.UNIT_X);
		assertNotEquals(Coordinate.UNIT_Z, Coordinate.UNIT_Y);
		
		assertNotEquals(Coordinate.UNIT_X, Coordinate.ORIGIN);
		assertNotEquals(Coordinate.UNIT_Y, Coordinate.ORIGIN);
		assertNotEquals(Coordinate.UNIT_Z, Coordinate.ORIGIN);
		
		assertNotEquals(Coordinate.UNIT_X, ONE);
		assertNotEquals(Coordinate.UNIT_Y, ONE);
		assertNotEquals(Coordinate.UNIT_Z, ONE);
		
		assertNotEquals(ONE, MINUS_ONE);
		assertNotEquals(MINUS_ONE, ONE);
		assertNotEquals(INF, MINUS_INF);
		assertNotEquals(MINUS_INF, INF);
		
		assertNotEquals(NAN, copyCoordinate(NAN));
	}

	@Test
	public void testInequalityRandom() {
		runMultibletimes(() -> {
			Coordinate c1 = newRandomCoordinate();
			Coordinate c2 = newRandomCoordinate();
			
			if (c1.x == c2.x && c1.y == c2.y && c1.z == c2.z) {
				return;
			}
			
			assertNotEquals(c1, c2);
		});
	}

}
