/*
 * Coordinate
 * 
 * Version: 2018-11-18
 *
 * Date: Nov 18, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

public interface Coordinate {
	/**
	 * Comparison epsilon for the significant.
	 * <p>
	 * Comparison example of value <code>a</code> and <code>b</code>:
	 * <pre>
	 * if (Math.abs(a-b) < Math.abs(a+b)*εS) {
	 *   // quasi-equals
	 * }
	 * </pre>
	 */
	static double εS = 1e-10;
	
	/**
	 * Comparison epsilon for zero.
	 * <p>
	 * Comparison example of value <code>a</code>:
	 * <pre>
	 * if (Math.abs(a) < εZ) {
	 *   // quasi-zero
	 * }
	 * </pre>
	 */
	static double εZ = 1e-300;
	
	public CartesianCoordinate asCartesianCoordinate();
	public SphericCoordinate asSphericCoordinate();
	public double getCartesianDistance(Coordinate other);
	public double getCentralAngle(Coordinate other);
	public boolean isEqual(Coordinate other);
}
