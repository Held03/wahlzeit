/*
 * CartesianCoordinate
 * 
 * Version: 2018-11-18
 *
 * Date: Oct 29, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * A coordinate is an immutable mathematical Cartesian point.
 */
public class CartesianCoordinate implements Coordinate {

	/*
	 * 
	 */
	public static final CartesianCoordinate ORIGIN = new CartesianCoordinate(0.0, 0.0, 0.0);
	public static final CartesianCoordinate UNIT_X = new CartesianCoordinate(1.0, 0.0, 0.0);
	public static final CartesianCoordinate UNIT_Y = new CartesianCoordinate(0.0, 1.0, 0.0);
	public static final CartesianCoordinate UNIT_Z = new CartesianCoordinate(0.0, 0.0, 1.0);

	/*
	 * 
	 */
	protected double x;
	protected double y;
	protected double z;

	private CartesianCoordinate() {
		// do nothing, necessary for Objectify to load it
	}

	public CartesianCoordinate(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Return the Euclidean distance between this and the given coordinate.
	 * 
	 * @param other the other coordinate to compare
	 * @return the distance
	 */
	public double getDistance(CartesianCoordinate other) {
		if (other == null) {
			throw new IllegalArgumentException("Other coordinate must not be null");
		}
		
		double dx = this.x - other.x;
		double dy = this.y - other.y;
		double dz = this.z - other.z;
		
		double sqDist = dx*dx + dy*dy + dz*dz;
		
		return Math.sqrt(sqDist);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartesianCoordinate other = (CartesianCoordinate) obj;
		return isEqual(other);
	}
	
	public boolean isEqual(CartesianCoordinate other) {
		if (other == null)
			return false;
		return (x == other.x && y == other.y && z == other.z);
	}

	/**
	 * @return the x
	 * @methodtype get
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 * @methodtype get
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 * @methodtype get
	 */
	public double getZ() {
		return z;
	}

	public String toString() {
		return String.format("[%.3f, %.3f, %.3f]", x, y, z);
	}

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return this;
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		// TODO implement
		return null;
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		CartesianCoordinate ccOther = other.asCartesianCoordinate();
		return getDistance(ccOther);
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		// TODO implement
		return 0;
	}

	@Override
	public boolean isEqual(Coordinate other) {
		CartesianCoordinate ccOther = other.asCartesianCoordinate();
		return isEqual(ccOther);
	}
	
}
