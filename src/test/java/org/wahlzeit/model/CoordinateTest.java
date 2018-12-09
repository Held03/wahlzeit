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
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

interface Converter {
	public Coordinate convert(Coordinate c);
	public Coordinate convertBack(Coordinate c);
	public default Coordinate convertCopy(Coordinate c) {
		return convert(convertBack(c));
	}
}

class CartesianConverter implements Converter {
	public Coordinate convert(Coordinate c) {
		return c.asCartesianCoordinate();
	}
	public Coordinate convertBack(Coordinate c) {
		return c.asSphericCoordinate();
	}
}

class SphericalConverter implements Converter {
	public Coordinate convert(Coordinate c) {
		return c.asSphericCoordinate();
	}
	public Coordinate convertBack(Coordinate c) {
		return c.asCartesianCoordinate();
	}
}

class CylindricalConverter implements Converter {
	public Coordinate convert(Coordinate c) {
		if (c instanceof CylindricalCoordinate) {
			return (CylindricalCoordinate) c;
		}
		if (c instanceof CartesianCoordinate) {
			return CylindricalCoordinate.formCartesian((CartesianCoordinate) c);
		}
		if (c instanceof SphericCoordinate) {
			return CylindricalCoordinate.fromSpheric((SphericCoordinate) c);
		}
		// Unknown coordinate type
		return c;
	}
	public Coordinate convertBack(Coordinate c) {
		return c.asCartesianCoordinate();
	}
}

@RunWith(Parameterized.class)
public class CoordinateTest {
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { new CartesianConverter() }, { new SphericalConverter() } , {new CylindricalConverter()}
           });
    }
	
	private static final int REPEAT_COUNT = 0x10_0000;
	
	/**
	 * Double significant precision
	 */
	private static final double ε = 1e-10;
	private static final double SqrtOf2 = Math.sqrt(2.0);
	private static final double SqrtOf3 = Math.sqrt(3.0);
	private static final double SqrtOf6 = Math.sqrt(6.0);
	
	private static final CartesianCoordinate ONE = new CartesianCoordinate(1.0, 1.0, 1.0);
	private static final CartesianCoordinate MINUS_ONE = new CartesianCoordinate(-1.0, -1.0, -1.0);
	private static final CartesianCoordinate MINUS_ZERO = new CartesianCoordinate(-0.0, -0.0, -0.0);
	
	private static final CartesianCoordinate SOME1 = new CartesianCoordinate( 1.2345, -4.1214, 3.14159);
	private static final CartesianCoordinate SOME2 = new CartesianCoordinate(-3.2323,  0.1212, 2.14321);
	
	private static final SphericCoordinate SOME3 = new SphericCoordinate(12.2345, 1.123, 5.2323);
	private static final SphericCoordinate SOME4 = new SphericCoordinate(0.0234243, 2.123, 1.4353);
	
	private static Random rng;
	
	private Converter converter;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		rng = new Random();
	}

	@Before
	public void setUp() throws Exception {
	}
	
	public CoordinateTest(Converter conv) {
		converter = conv;
	}

	@After
	public void tearDown() throws Exception {
	}
	
	protected void assertDoubleEq(double d1, double d2) {
		assertTrue(Math.abs(d1-d2) <= ε * Math.abs(d1+d2) || Math.abs(d1-d2) <= ε);
	}
	
	protected Coordinate newRandomCoordinate() {
		return converter.convert(new CartesianCoordinate(rng.nextDouble(), rng.nextDouble(), rng.nextDouble()));
	}
	
	protected void runMultibletimes(Runnable r) {
		for (int i = 0; i < REPEAT_COUNT; i++) {
			r.run();
		}
	}
	
	protected CartesianCoordinate copyCartesianCoordinate(CartesianCoordinate o) {
		return new CartesianCoordinate(o.x, o.y, o.z);
	}
	
	protected double euclidDistance(double x1, double x2, double y1, double y2, double z1, double z2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	
	@Test
	public void testCartesianConstructorRandom() {
		runMultibletimes(() -> {
			double a = rng.nextDouble(),
					b = rng.nextDouble(),
					c = rng.nextDouble();
			
			CartesianCoordinate cc = new CartesianCoordinate(a, b, c);
			
			assert(a == cc.x);
			assert(b == cc.y);
			assert(c == cc.z);
		});
	}

	@Test
	public void testSpericConstructorRandom() {
		runMultibletimes(() -> {
			double a = rng.nextDouble(),
					b = rng.nextDouble(),
					c = rng.nextDouble();
			
			SphericCoordinate sc = new SphericCoordinate(a, b, c);
			
			assert(a == sc.radius);
			assert(b == sc.theta);
			assert(c == sc.phi);
		});
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorInfX() {
		new CartesianCoordinate(Double.POSITIVE_INFINITY, 0, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorInfY() {
		new CartesianCoordinate(0, Double.POSITIVE_INFINITY, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorInfZ() {
		new CartesianCoordinate(0, 0, Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorNaNX() {
		new CartesianCoordinate(Double.NaN, 0, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorNaNY() {
		new CartesianCoordinate(0, Double.NaN, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testCartesianConstructorNaNZ() {
		new CartesianCoordinate(0, 0, Double.NaN);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorInfR() {
		new SphericCoordinate(Double.POSITIVE_INFINITY, 0, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorInfT() {
		new SphericCoordinate(0, Double.POSITIVE_INFINITY, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorInfP() {
		new SphericCoordinate(0, 0, Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorNaNR() {
		new SphericCoordinate(Double.NaN, 0, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorNaNT() {
		new SphericCoordinate(0, Double.NaN, 0);
	}
	@Test(expected = IllegalStateException.class)
	public void testSphericConstructorNaNP() {
		new SphericCoordinate(0, 0, Double.NaN);
	}

	@Test
	public void testEqualityIdentity() {
		Coordinate c = converter.convert(CartesianCoordinate.ORIGIN);
		assertTrue(c.isEqual(c));
		c = converter.convert(CartesianCoordinate.UNIT_X);
		assertTrue(c.isEqual(c));
		c = converter.convert(CartesianCoordinate.UNIT_Y);
		assertTrue(c.isEqual(c));
		c = converter.convert(CartesianCoordinate.UNIT_Z);
		assertTrue(c.isEqual(c));
		
		c = converter.convert(SphericCoordinate.ORIGIN);
		assertTrue(c.isEqual(c));
		c = converter.convert(SphericCoordinate.ZENITH);
		assertTrue(c.isEqual(c));
		c = converter.convert(SphericCoordinate.AZIMUTH);
		assertTrue(c.isEqual(c));

		c = converter.convert(ONE);
		assertTrue(c.isEqual(c));
		c = converter.convert(MINUS_ONE);
		assertTrue(c.isEqual(c));
		c = converter.convert(MINUS_ZERO);
		assertTrue(c.isEqual(c));
		
		c = converter.convert(SOME1);
		assertTrue(c.isEqual(c));
		c = converter.convert(SOME2);
		assertTrue(c.isEqual(c));
		c = converter.convert(SOME3);
		assertTrue(c.isEqual(c));
		c = converter.convert(SOME4);
		assertTrue(c.isEqual(c));
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
		Coordinate c = converter.convert(CartesianCoordinate.ORIGIN);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(CartesianCoordinate.UNIT_X);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(CartesianCoordinate.UNIT_Y);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(CartesianCoordinate.UNIT_Z);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		
		c = converter.convert(SphericCoordinate.ORIGIN);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(SphericCoordinate.ZENITH);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(SphericCoordinate.AZIMUTH);
		assertTrue(c.isEqual(converter.convertCopy(c)));

		c = converter.convert(ONE);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(MINUS_ONE);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(MINUS_ZERO);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		
		c = converter.convert(SOME1);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(SOME2);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(SOME3);
		assertTrue(c.isEqual(converter.convertCopy(c)));
		c = converter.convert(SOME4);
		assertTrue(c.isEqual(converter.convertCopy(c)));
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
			Coordinate c = newRandomCoordinate();
			assertTrue(c.isEqual(converter.convertCopy(c)));
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
	}

	@Test(expected = IllegalStateException.class)
	public void testNullDistance() {
		try {
			converter.convert(CartesianCoordinate.ORIGIN).getCartesianDistance(null);
		} catch (InvalidResultException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testZeroDistance() {
		try {
			Coordinate c = converter.convert(CartesianCoordinate.ORIGIN);
			assertTrue(c.getCartesianDistance(c) == 0.0);
			c = converter.convert(CartesianCoordinate.UNIT_X);
			assertTrue(c.getCartesianDistance(c) == 0.0);
			c = converter.convert(CartesianCoordinate.UNIT_Y);
			assertTrue(c.getCartesianDistance(c) == 0.0);
			c = converter.convert(CartesianCoordinate.UNIT_Z);
			assertTrue(c.getCartesianDistance(c) == 0.0);
	
			c = converter.convert(ONE);
			assertTrue(c.getCartesianDistance(c) == 0.0);
			c = converter.convert(MINUS_ONE);
			assertTrue(c.getCartesianDistance(c) == 0.0);
			c = converter.convert(CartesianCoordinate.ORIGIN);
			Coordinate c2 = converter.convert(MINUS_ZERO);
			assertTrue(c.getCartesianDistance(c2) == 0.0);
			c = converter.convert(MINUS_ZERO);
			assertTrue(c.getCartesianDistance(c) == 0.0);
		} catch (InvalidResultException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testOneDistance() {
		try {
			Coordinate c1 = converter.convert(CartesianCoordinate.UNIT_X);
			Coordinate c2 = converter.convert(CartesianCoordinate.ORIGIN);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(CartesianCoordinate.ORIGIN);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(CartesianCoordinate.ORIGIN);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
			
			c1 = converter.convert(CartesianCoordinate.UNIT_X);
			c2 = converter.convert(MINUS_ZERO);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(MINUS_ZERO);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(MINUS_ZERO);
			assertDoubleEq(c1.getCartesianDistance(c2), 1.0);
		} catch (InvalidResultException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testSomeDistance() {
		try {
			Coordinate c1 = converter.convert(CartesianCoordinate.UNIT_X);
			Coordinate c2 = converter.convert(CartesianCoordinate.UNIT_Z);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(CartesianCoordinate.UNIT_X);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(CartesianCoordinate.UNIT_Y);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			
			c1 = converter.convert(CartesianCoordinate.UNIT_X);
			c2 = converter.convert(CartesianCoordinate.UNIT_Y);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(CartesianCoordinate.UNIT_Z);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(CartesianCoordinate.UNIT_X);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			
			c1 = converter.convert(CartesianCoordinate.UNIT_X);
			c2 = converter.convert(ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf2);
	
			c1 = converter.convert(CartesianCoordinate.ORIGIN);
			c2 = converter.convert(ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf3);
			c1 = converter.convert(CartesianCoordinate.ORIGIN);
			c2 = converter.convert(MINUS_ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf3);
			c1 = converter.convert(MINUS_ZERO);
			c2 = converter.convert(ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf3);
			c1 = converter.convert(MINUS_ZERO);
			c2 = converter.convert(MINUS_ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf3);
	
			c1 = converter.convert(CartesianCoordinate.UNIT_X);
			c2 = converter.convert(MINUS_ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf6);
			c1 = converter.convert(CartesianCoordinate.UNIT_Y);
			c2 = converter.convert(MINUS_ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf6);
			c1 = converter.convert(CartesianCoordinate.UNIT_Z);
			c2 = converter.convert(MINUS_ONE);
			assertDoubleEq(c1.getCartesianDistance(c2), SqrtOf6);
		} catch (InvalidResultException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testDistanceRandom() {
		runMultibletimes(() -> {
			try {
				Coordinate c1 = newRandomCoordinate();
				Coordinate c2 = newRandomCoordinate();
				
				CartesianCoordinate cc1 = c1.asCartesianCoordinate();
				CartesianCoordinate cc2 = c2.asCartesianCoordinate();
				double dist = euclidDistance(cc1.x, cc2.x, cc1.y, cc2.y, cc1.z, cc2.z);
	
				assertDoubleEq(c1.getCartesianDistance(c2), dist);
				assertDoubleEq(c2.getCartesianDistance(c1), dist);
				assertDoubleEq(c1.getCartesianDistance(c2), c2.getCartesianDistance(c1));
			} catch (InvalidResultException e) {
				// Might occur by coordinates being to far away from each other
			}
		});
	}

	
	@Test
	public void testAngleRandom() {
		runMultibletimes(() -> {
			try {
				Coordinate c1 = newRandomCoordinate();
				Coordinate c2 = newRandomCoordinate();
				
				Coordinate cc1 = converter.convertBack(c1);
				Coordinate cc2 = converter.convertBack(c2);
	
				assertDoubleEq(c1.getCentralAngle(c2), c2.getCentralAngle(c1));
				assertDoubleEq(c1.getCentralAngle(cc2), cc2.getCentralAngle(c1));
				assertDoubleEq(cc1.getCentralAngle(c2), c2.getCentralAngle(cc1));
				assertDoubleEq(c1.getCentralAngle(cc2), c2.getCentralAngle(cc1));
			} catch (InvalidResultException e) {
				// Might occur by coordinates being to far away from each other
			}
		});
	}
	

}
