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
	static double εZ = 1e-14;
	
	/**
	 * Convert this coordinate into a Cartesian coordinate.
	 * 
	 * @return a Cartesian coordinate representation of this coordinate
	 */
	public CartesianCoordinate asCartesianCoordinate();
	
	/**
	 * Convert this coordinate into a Spherical coordinate.
	 * 
	 * @return a Spherical coordinate representation of this coordinate
	 */
	public SphericCoordinate asSphericCoordinate();
	
	/**
	 * Calculates the Euclidean distance between this and the given coordinate.
	 * 
	 * Contract: <code>other</code> must be a valid coordinate, especially,
	 * <code>other</code> must not be <code>null</code>.
	 * Returns a finite distance.
	 * 
	 * @param other the other coordinate to compare
	 * @return the distance
	 * @throws InvalidResultException thrown if the expected result can not be
	 * computed using double values
	 */
	public double getCartesianDistance(Coordinate other) throws InvalidResultException;
	
	/**
	 * Calculates the shortest angle between this and the given coordinate in radiant.
	 * 
	 * Contract: <code>other</code> must be a valid coordinate, especially, <code>other</code>
	 * must not be <code>null</code>. Returns a finite angle.
	 * 
	 * @param other the other coordinate to compare
	 * @return the angle in radiant
	 * @throws InvalidResultException thrown if the expected result can not be
	 * computed using double values
	 */
	public double getCentralAngle(Coordinate other) throws InvalidResultException;
	
	/**
	 * Tests if this coordinate is equal to the given coordinate.
	 * 
	 * Due to potential floating-point errors, the two coordinates are compared
	 * on a best-effort basis via an epsilon comparison. Thus this method does
	 * not provide the semantics of an since it is not transitive.
	 * 
	 * No contract.
	 * 
	 * @param other the  other coordinate to compare
	 * @return <code>true</code> if this and the other coordinate are similar,
	 * <code>false</code> otherwise
	 */
	public boolean isEqual(Coordinate other);
}





