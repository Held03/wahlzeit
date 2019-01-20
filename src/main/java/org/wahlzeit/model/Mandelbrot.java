/**
 * Mandelbrot
 * 
 * Version: 2019-01-20
 *
 * Date: Jan 20, 2019
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * Specifies a Mandelbrot image.
 */
public class Mandelbrot {
	/**
	 * The type of this Mandelbrot.
	 */
	protected final MandelbrotType type;

	/**
	 * 
	 */
	public Mandelbrot(MandelbrotType type) {
		if (type == null) {
			throw new IllegalArgumentException("Type must not be null");
		}
		
		this.type = type;
	}

	/**
	 * Returns the type of this Mandelbrot.
	 * @return the type of this instance
	 */
	public  MandelbrotType getType() {
		return type;
	}
	
	/**
	 * Test if this (type) object is instance of the given type.
	 * This includes any super type of the concrete type of this (type) object.
	 * 
	 * @param type the type to test for parenthood
	 * @return <code>true</code> if this instance is of the given type or its
	 *   descendants
	 */
	public boolean isInstanceOf(MandelbrotType type) {
		return this.type.isSubtypeOf(type);
	}
}
