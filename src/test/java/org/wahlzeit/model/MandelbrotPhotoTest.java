/**
 * 
 */
package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class MandelbrotPhotoTest {
	MandelbrotPhoto mbp;
	
	@Before
	public final void setup() {
		mbp = new MandelbrotPhoto();
		mbp.resetWriteCount();
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#MandelbrotPhoto(org.wahlzeit.model.PhotoId)}.
	 */
	@Test
	public final void testMandelbrotPhotoPhotoId() {
		PhotoId id = new PhotoId(42);
		mbp = new MandelbrotPhoto(id);
		assertEquals(id, mbp.id);
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#getColorTheme()}.
	 */
	@Test
	public final void testGetColorTheme() {
		mbp.colorTheme = "something";
		assertEquals("something", mbp.getColorTheme());
		assertFalse(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#setColorTheme(java.lang.String)}.
	 */
	@Test
	public final void testSetColorTheme() {
		mbp.setColorTheme("AnyThing");
		assertEquals("AnyThing", mbp.colorTheme);
		assertTrue(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#setColorTheme(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSetWrongColorTheme() {
		mbp.setColorTheme(null);
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#getCenterReal()}.
	 */
	@Test
	public final void testGetCenterReal() {
		double v = 123.456;
		mbp.centerReal = v;
		assertTrue((v == mbp.getCenterReal()));
		assertFalse(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#setCenterReal(double)}.
	 */
	@Test
	public final void testSetCenterReal() {
		double v = 234.567;
		mbp.setCenterReal(v);
		assertTrue(v == mbp.centerReal);
		assertTrue(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#getCenterImg()}.
	 */
	@Test
	public final void testGetCenterImg() {
		double v = 345.678;
		mbp.centerImg = v;
		assertTrue((v == mbp.getCenterImg()));
		assertFalse(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#setCenterImg(double)}.
	 */
	@Test
	public final void testSetCenterImg() {
		double v = 456.789;
		mbp.setCenterImg(v);
		assertTrue(v == mbp.centerImg);
		assertTrue(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#getRadius()}.
	 */
	@Test
	public final void testGetRadius() {
		double v = 567.890;
		mbp.radius = v;
		assertTrue((v == mbp.getRadius()));
		assertFalse(mbp.isDirty());
	}

	/**
	 * Test method for {@link org.wahlzeit.model.MandelbrotPhoto#setRadius(double)}.
	 */
	@Test
	public final void testSetRadius() {
		double v = 678.901;
		mbp.setRadius(v);
		assertTrue(v == mbp.radius);
		assertTrue(mbp.isDirty());
	}

}
