/**
 * MandelbrotType
 * 
 * Version: 2019-01-20
 *
 * Date: Jan 20, 2019
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * @author cryptjar
 *
 */
public class MandelbrotType {
	protected final MandelbrotType superType;

	/**
	 * 
	 */
	public MandelbrotType(MandelbrotType superType) {
		this.superType = superType;
	}
	
	/**
	 * Tests if the given type is the same or an ancestor type of this type.
	 * 
	 * @param type the type to test for inheritance
	 * @return <code>true</code> if this type is a descendant of the given type
	 */
	public boolean isSubtypeOf(MandelbrotType type) {
		return this.equals(type) || this.superType.equals(type) || this.superType.isSubtypeOf(type);
	}

}
