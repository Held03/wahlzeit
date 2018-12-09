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

	/**
	 * Creates a new Cartesian coordinate with given values.
	 * 
	 * Contract: x, y, and z must be finite values. Returns a valid coordinate.
	 * 
	 * @param x the location of this coordinate along the X-axis
	 * @param y the location of this coordinate along the Y-axis
	 * @param z the location of this coordinate along the Z-axis
	 */
	public CartesianCoordinate(double x, double y, double z) {
		super();

		// Ensure valid pre-condition of the Method contract
		assertValidDouble(x);
		assertValidDouble(y);
		assertValidDouble(z);
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		// Ensure valid class invariant and valid post-condition
		assertClassInvariants();
	}
	
	/**
	 * Return the Euclidean distance between this and the given coordinate.
	 * 
	 * Contract: <code>other</code> must be a valid coordinate, especially, <code>other</code>
	 * must not be <code>null</code>. Returns a finite distance.
	 * 
	 * @param other the other coordinate to compare
	 * @return the distance
	 * @throws InvalidResultException thrown if the expected result can not be computed using double values
	 */
	public double getDistance(CartesianCoordinate other) throws InvalidResultException {
		// Ensure pre-condition of the contract
		assertValidCoordinate(other);
		other.assertClassInvariants();
		
		// Ensure the class invariant
		assertClassInvariants();
		
		double dx = this.x - other.x;
		double dy = this.y - other.y;
		double dz = this.z - other.z;
		
		double sqDist = dx*dx + dy*dy + dz*dz;
		
		double result = Math.sqrt(sqDist);
		
		// Ensure post-condition of the contract
		assertValidDoubleResult(result);
		
		return result;
	}

	/**
	 * @return the x
	 * @methodtype get
	 */
	public double getX() {
		// Ensure the class invariant
		assertClassInvariants();
		
		return x;
	}

	/**
	 * @return the y
	 * @methodtype get
	 */
	public double getY() {
		// Ensure the class invariant
		assertClassInvariants();
		
		return y;
	}

	/**
	 * @return the z
	 * @methodtype get
	 */
	public double getZ() {
		// Ensure the class invariant
		assertClassInvariants();
		
		return z;
	}

	public String toString() {
		return String.format("[%.3f, %.3f, %.3f]", x, y, z);
	}

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		// Ensure the class invariant
		assertClassInvariants();
		
		return this;
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		// Ensure the class invariant
		assertClassInvariants();
		
		return SphericCoordinate.fromCartesian(this);
	}

	@Override
	public double getCartesianDistance(Coordinate other) throws InvalidResultException {
		// Ensures pre-condition
		assertValidCoordinate(other);

		// Ensures the class invariant
		assertClassInvariants();
		
		CartesianCoordinate ccOther = other.asCartesianCoordinate();
		return getDistance(ccOther);
	}

	@Override
	public double getCentralAngle(Coordinate other) throws InvalidResultException {
		// Ensure pre-condition of the contract
		assertValidCoordinate(other);
		CartesianCoordinate cc = other.asCartesianCoordinate();
		cc.assertClassInvariants();
		
		// Ensure class invariants
		assertClassInvariants();
		
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
		double result = Math.atan(cpd / dpd);
		
		// Ensure post-condition
		assertValidDoubleResult(result);
		
		return result;
	}
	
}
