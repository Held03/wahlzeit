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
	public double getCartesianDistance(Coordinate other) {
		return this.asCartesianCoordinate().getCartesianDistance(other);
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#getCentralAngle(org.wahlzeit.model.Coordinate)
	 */
	@Override
	public double getCentralAngle(Coordinate other) {
		return this.asSphericCoordinate().getCentralAngle(other);
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.Coordinate#isEqual(org.wahlzeit.model.Coordinate)
	 */
	@Override
	public boolean isEqual(Coordinate other) {
		return getCartesianDistance(other) < εZ;
	}

}
