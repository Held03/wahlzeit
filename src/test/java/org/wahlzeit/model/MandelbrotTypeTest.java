/**
 * 
 */
package org.wahlzeit.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 */
public class MandelbrotTypeTest {

	/**
	 * 
	 */
	public MandelbrotTypeTest() {
		
	}

	@Test
	public void testIsSubtypeOf() {
		MandelbrotType mtSuper = new MandelbrotType(null);
		MandelbrotType mtSub = new MandelbrotType(mtSuper);

		assertTrue(mtSub.isSubtypeOf(mtSub));
		assertTrue(mtSub.isSubtypeOf(mtSuper));
		assertFalse(mtSuper.isSubtypeOf(mtSub));
		assertTrue(mtSuper.isSubtypeOf(mtSuper));

		MandelbrotType mtOther = new MandelbrotType(mtSuper);
		assertTrue(mtOther.isSubtypeOf(mtSuper));
		assertFalse(mtSuper.isSubtypeOf(mtOther));
		assertFalse(mtSub.isSubtypeOf(mtOther));
		assertFalse(mtOther.isSubtypeOf(mtSub));
		assertTrue(mtOther.isSubtypeOf(mtOther));
	}
}
