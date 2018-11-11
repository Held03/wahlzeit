package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class MandelbrotPhotoFactoryTest {
	@BeforeClass
	public static final void setupClass() {
		PhotoFactory.instance = null;
		MandelbrotPhotoFactory.initialize();
	}

	@Test
	public final void testInitialize() {
		assertEquals(MandelbrotPhotoFactory.class, PhotoFactory.getInstance().getClass());
	}

	@Test
	public final void testCreatePhoto() {
		
		Photo p1 = PhotoFactory.getInstance().createPhoto();
		Photo p2 = PhotoFactory.getInstance().createPhoto();
		
		assertEquals(MandelbrotPhotoFactory.class, PhotoFactory.getInstance().getClass());
		
		assertNotNull(p1);
		assertNotNull(p2);
		assertEquals(MandelbrotPhoto.class, p1.getClass());
		assertEquals(MandelbrotPhoto.class, p2.getClass());
		assertNotEquals(p1, p2);
	}

	@Test
	public final void testCreatePhotoPhotoId() {

		PhotoId id = new PhotoId(42);
		Photo p = PhotoFactory.getInstance().createPhoto(id);
		
		assertEquals(MandelbrotPhotoFactory.class, PhotoFactory.getInstance().getClass());
		
		assertEquals(id, p.id);
		assertEquals(MandelbrotPhoto.class, p.getClass());
		
	}

}
