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
	public CartesianCoordinate asCartesianCoordinate();
	public SphericCoordinate asSphericCoordinate();
	public double getCartesianDistance(Coordinate other);
	public double getCentralAngle(Coordinate other);
	public boolean isEqual(Coordinate other);
}
