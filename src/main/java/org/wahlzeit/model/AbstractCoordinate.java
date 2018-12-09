/**
 * AbstractCoordinate.java
 * 
 * Version: 2018-11-26
 *
 * Date: Nov 26, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * Represents an abstract coordinate which provides some forwarding default
 * implementations.
 */
public abstract class AbstractCoordinate implements Coordinate {

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#asCartesianCoordinate()
	 */
	@Override
	public abstract CartesianCoordinate asCartesianCoordinate();

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#asSphericCoordinate()
	 */
	@Override
	public abstract SphericCoordinate asSphericCoordinate();

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#getCartesianDistance(org.wahlzeit.model.Coordinate)
	 */
	@Override
	public double getCartesianDistance(Coordinate other) throws InvalidResultException {
		return this.asCartesianCoordinate().getCartesianDistance(other);
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#getCentralAngle(org.wahlzeit.model.Coordinate)
	 */
	@Override
	public double getCentralAngle(Coordinate other) throws InvalidResultException {
		return this.asSphericCoordinate().getCentralAngle(other);
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#isEqual(org.wahlzeit.model.Coordinate)
	 */
	@Override
	public boolean isEqual(Coordinate other) {
		// Null is a valid parameter, but unequal to this
		if (other == null)
			return false;
		
		try {
			// Compare the two coordinates via distance, as a generic mean
			// of equality.
			return getCartesianDistance(other) < εZ;
			
		} catch (InvalidResultException e) {
			// This exception is intended for clients interested in the distance.
			// Here, we just want to compare the two coordinates, an infinite
			// result thus is just an indicator that the two coordinates are
			// far from each other, and thus unequal.
			return false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if (obj instanceof Coordinate) {
			Coordinate other = (Coordinate) obj;
			return isEqual(other);
		} else {
			return false;
		}
	}

	/**
	 * Compares the to floating point numbers a and b for quasi-equality.
	 * 
	 * @param a the first value to be compared
	 * @param b the second value to be compared
	 * @return <code>true</code> if the two values are quasi-equal.
	 */
	protected static boolean cmp(double a, double b) {
		if (Math.abs(a) < εZ & Math.abs(b) < εZ) {
			return true;
		} else if (Math.abs(a-b) <= εS) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Asserts that the given double is a finite value.
	 * 
	 * Throws an illegal state exception if <code>f</code> is not a finite
	 * double value. If <code>f</code> is a valid double value, this method
	 * returns immediately.
	 * 
	 * @param f the value to test
	 */
	protected static void assertValidDouble(double f) {
		if (!Double.isFinite(f)) {
			throw new IllegalStateException("Double must be finite, got "+f);
		}
	}
	
	/**
	 * Asserts that the given double is a positive value.
	 * 
	 * Throws an illegal state exception if <code>f</code> is not a positive
	 * double value. If <code>f</code> is a positive double value, this method
	 * returns immediately.
	 * 
	 * @param f the value to test
	 */
	protected static void assertPositiveDouble(double f) {
		if (!(f >= 0)) {
			throw new IllegalStateException("Double must be positive, got "+f);
		}
	}
	
	protected static void assertValidDoubleResult(double f) throws InvalidResultException {
		if (!Double.isFinite(f)) {
			throw new InvalidResultException("Double must be finite, got "+f);
		}
	}
	
	/**
	 * Ensures that the given coordinate is not null.
	 * 
	 * Throws an illegal state exception if <code>c</code> is <code>null</code>.
	 * If <code>c</code> is not null, this method returns immediately.
	 * 
	 * @param c reference to test
	 */
	protected static void assertValidCoordinate(Coordinate c) {
		if (c == null) {
			throw new IllegalStateException("Coordinate must not be null");
		}
	}
}

