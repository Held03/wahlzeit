package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class MandelbrotTest {
	static MandelbrotType mtSuper;
	static MandelbrotType mtSub;
	static MandelbrotType mtOther;

	public MandelbrotTest() {
		
	}

	@BeforeClass
	public static void setup() {
		mtSuper = new MandelbrotType(null);
		mtSub = new MandelbrotType(mtSuper);
		mtOther = new MandelbrotType(mtSuper);
	}
	
	@Test
	public void testIsInstanceOfSuper() {
		Mandelbrot mSuper = new Mandelbrot(mtSuper);

		assertTrue(mSuper.isInstanceOf(mtSuper));
		assertFalse(mSuper.isInstanceOf(mtSub));
		assertFalse(mSuper.isInstanceOf(mtOther));
	}

	@Test
	public void testIsInstanceOfSub() {
		Mandelbrot mSub = new Mandelbrot(mtSub);

		assertTrue(mSub.isInstanceOf(mtSub));
		assertEquals(mtSub.isSubtypeOf(mtSuper), mSub.isInstanceOf(mtSuper));
		assertFalse(mSub.isInstanceOf(mtOther));
	}
}
