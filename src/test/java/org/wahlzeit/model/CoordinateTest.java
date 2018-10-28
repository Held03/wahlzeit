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
	private static final double SqrtOf2 = Math.sqrt(2.0);
	private static final double SqrtOf3 = Math.sqrt(3.0);
	private static final double SqrtOf6 = Math.sqrt(6.0);
	
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
	
	protected void assertDoubleEq(double d1, double d2) {
		assert(Math.abs(d1-d2) <= ε * Math.abs(d1+d2));
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
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	@Test
	public void testConstructorRandom() {
		runMultibletimes(() -> {
			double x = rng.nextDouble(),
					y = rng.nextDouble(),
					z = rng.nextDouble();
			
			Coordinate c = new Coordinate(x, y, z);
			
			assert(x == c.x);
			assert(y == c.y);
			assert(z == c.z);
		});
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
	public void testInequality() {
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
	
	@Test
	public void testZeroDistance() {
		assert(Coordinate.ORIGIN.getDistance(Coordinate.ORIGIN) == 0.0);
		assert(Coordinate.UNIT_X.getDistance(Coordinate.UNIT_X) == 0.0);
		assert(Coordinate.UNIT_Y.getDistance(Coordinate.UNIT_Y) == 0.0);
		assert(Coordinate.UNIT_Z.getDistance(Coordinate.UNIT_Z) == 0.0);

		assert(ONE.getDistance(ONE) == 0.0);
		assert(MINUS_ONE.getDistance(MINUS_ONE) == 0.0);
		assert(Coordinate.ORIGIN.getDistance(MINUS_ZERO) == 0.0);
		assert(MINUS_ZERO.getDistance(MINUS_ZERO) == 0.0);

		assert(Double.isNaN(NAN.getDistance(NAN)));
	}
	
	@Test
	public void testOneDistance() {
		assertDoubleEq(Coordinate.UNIT_X.getDistance(Coordinate.ORIGIN), 1.0);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(Coordinate.ORIGIN), 1.0);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(Coordinate.ORIGIN), 1.0);

		assertDoubleEq(Coordinate.UNIT_X.getDistance(MINUS_ZERO), 1.0);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(MINUS_ZERO), 1.0);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(MINUS_ZERO), 1.0);
	}
	
	@Test
	public void testSomeDistance() {
		assertDoubleEq(Coordinate.UNIT_X.getDistance(Coordinate.UNIT_Z), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(Coordinate.UNIT_X), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(Coordinate.UNIT_Y), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_X.getDistance(Coordinate.UNIT_Y), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(Coordinate.UNIT_Z), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(Coordinate.UNIT_X), SqrtOf2);

		assertDoubleEq(Coordinate.UNIT_X.getDistance(ONE), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(ONE), SqrtOf2);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(ONE), SqrtOf2);

		assertDoubleEq(Coordinate.ORIGIN.getDistance(ONE), SqrtOf3);
		assertDoubleEq(Coordinate.ORIGIN.getDistance(MINUS_ONE), SqrtOf3);
		assertDoubleEq(MINUS_ZERO.getDistance(ONE), SqrtOf3);
		assertDoubleEq(MINUS_ZERO.getDistance(MINUS_ONE), SqrtOf3);

		assertDoubleEq(Coordinate.UNIT_X.getDistance(MINUS_ONE), SqrtOf6);
		assertDoubleEq(Coordinate.UNIT_Y.getDistance(MINUS_ONE), SqrtOf6);
		assertDoubleEq(Coordinate.UNIT_Z.getDistance(MINUS_ONE), SqrtOf6);
	}
	
	@Test
	public void testDistanceRandom() {
		runMultibletimes(() -> {
			Coordinate c1 = newRandomCoordinate();
			Coordinate c2 = newRandomCoordinate();
			
			double dist = euclidDistance(c1.x, c2.x, c1.y, c2.y, c1.z, c2.z);

			assertDoubleEq(c1.getDistance(c2), dist);
			assertDoubleEq(c2.getDistance(c1), dist);
			assertDoubleEq(c1.getDistance(c2), c2.getDistance(c1));
		});
	}

}
