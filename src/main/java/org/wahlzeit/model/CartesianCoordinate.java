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
 * <p>
 * <b>Contract:</b>
 * <code>x</code> must be a finite double value.
 * <code>y</code> must be a finite double value.
 * <code>z</code> must be a finite double value.
 */
public class CartesianCoordinate extends AbstractCoordinate {

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


	/**
	 * Asserts the validity of the class contract.
	 */
	protected void assertClassInvariants() {
		assertValidDouble(x);
		assertValidDouble(y);
		assertValidDouble(z);
	}

	public CartesianCoordinate(double x, double y, double z) {
		super();
		
		if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z))
			throw new IllegalArgumentException("Coordinates must be finite");
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		assertClassInvariants();
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
		
		assertClassInvariants();
		other.assertClassInvariants();
		
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
		
		assertClassInvariants();
		other.assertClassInvariants();

		return (cmp(x, other.x) & cmp(y, other.y) & cmp(z, other.z));
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
		return SphericCoordinate.fromCartesian(this);
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		if (other == null)
			throw new IllegalArgumentException("Other coordinate must not be null");
		
		CartesianCoordinate ccOther = other.asCartesianCoordinate();
		return getDistance(ccOther);
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		if (other == null)
			throw new IllegalArgumentException("Other coordinate must not be null");
		
		CartesianCoordinate cc = other.asCartesianCoordinate();
		
		assertClassInvariants();
		cc.assertClassInvariants();
		
		// the central angle between a and b is equal to
		//   arctan(|a corss b| / (a dot b))
		
		// cross product
		double cpx = this.y * cc.z - this.z * cc.y;
		double cpy = this.z * cc.x - this.x * cc.z;
		double cpz = this.x * cc.y - this.y * cc.x;
		double cpd = Math.sqrt(cpx*cpx + cpy*cpy + cpz*cpz);
		
		// dot product
		double dpx = this.x * cc.x;
		double dpy = this.y * cc.y;
		double dpz = this.z * cc.z;
		double dpd = dpx + dpy + dpz;
		
		// final result
		return Math.atan(cpd / dpd);
	}

	@Override
	public boolean isEqual(Coordinate other) {
		if (other == null)
			return false;
		
		CartesianCoordinate ccOther = other.asCartesianCoordinate();
		return isEqual(ccOther);
	}
	
}
