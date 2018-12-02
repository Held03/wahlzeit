/*
 * SphericCoordinate
 * 
 * Version: 2018-11-18
 *
 * Date: Nov 18, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * A coordinate is an immutable mathematical Spherical point.
 * <p>
 * The coordinate system is defined by an origin point <code>O</code>, and the
 * two orthogonal directions <i>zenith</i> and the <i>azimuth direction</i>.
 * We call the plane orthogonal to the zenith, in which the azimuth dircetion
 * is defined, the <i>reference plane</i>.
 * <p>
 * Coordinates are treated in compliance with ISO 80000-2:2009.
 * That is any point <code>P</code> is represented by <code>(r,θ,φ)</code>,
 * whereas <code>r</code> denotes the distance between <code>P</code> and
 * <code>O</code>, <code>θ</code> is the angle between the zenith and the line
 * <code>OP</code>, and <code>φ</code> is the angle between the azimuth
 * direction and the projection of the line <code>OP</code> into the reference plane.
 * <p>
 * <b>Contract:</b>
 * <code>radius</code> must be a positive finite double value.
 * <code>theta</code> must be a finite double value in the range 0 to π.
 * <code>phi</code> must be a finite double value in the range 0 to 2π.
 */
public class SphericCoordinate extends AbstractCoordinate {
	public static final SphericCoordinate ORIGIN = new SphericCoordinate(0.0, 0.0, 0.0);
	public static final SphericCoordinate ZENITH = new SphericCoordinate(1.0, 0.0, 0.0);
	public static final SphericCoordinate AZIMUTH = new SphericCoordinate(1.0, Math.PI, 0.0);

	/**
	 * The distance for this point <code>P</code> to the origin <code>O</code>.
	 */
	double radius;
	
	/**
	 * The inclination <code>θ</code>. That is the angle between the zenith and
	 * the line from the origin <code>O</code> to this point <code>P</code>.
	 */
	double theta;

	/**
	 * The azimuth <code>φ</code>. That is the angle between the azimuth
	 * direction and the projection of the line from the origin <code>O</code>
	 * to this point <code>P</code> into the reference plane.
	 */
	double phi;
	
	protected void assertValidRadius() {
		assertValidDouble(radius);
		if (radius < 0)
			throw new IllegalStateException("Radius must be positive");
	}
	
	protected void assertValidTheta() {
		assertValidDouble(theta);
		if (!(0 <= theta && theta < Math.PI)) {
			throw new IllegalStateException("Theta must between 0 and π");
		}
	}
	
	protected void assertValidPhi() {
		assertValidDouble(phi);
		if (!(0 <= phi && phi < 2*Math.PI)) {
			throw new IllegalStateException("Phi must between 0 and 2π");
		}
	}
	
	/**
	 * Asserts the validity of the class contract.
	 */
	protected void assertClassInvariants() {
		assertValidRadius();
		assertValidTheta();
		assertValidPhi();
	}
	
	/**
	 * Creates a new spherical coordinate with given parameters.
	 * 
	 * @param radius the distance to the origin <code>O</code>
	 * @param theta the angle to the zenith
	 * @param phi the angle to the azimuth
	 */
	public SphericCoordinate(double radius, double theta, double phi) {
		if (radius < 0)
			throw new IllegalArgumentException("Radius must be positive");
		if (!Double.isFinite(radius) || !Double.isFinite(theta) || !Double.isFinite(phi))
			throw new IllegalArgumentException("Coordinates must be finite");
		
		theta = theta % Math.PI;
		if (theta < 0) {
			theta += Math.PI;
		}
		assert 0 <= theta && theta < Math.PI;
		
		phi = phi % (2*Math.PI);
		if (phi < 0) {
			phi += 2*Math.PI;
		}
		assert 0 <= phi && phi < 2*Math.PI;
		
		this.radius = radius;
		this.theta = theta;
		this.phi = phi;
		
		assertClassInvariants();
	}
	
	/**
	 * Get a new spherical coordinate for a cartesian coordinate.
	 * 
	 * @param cc the cartesian coordinate
	 * @return a new equivalent spherical coordinate
	 */
	public static SphericCoordinate fromCartesian(CartesianCoordinate cc) {
		if (cc == null)
			throw new IllegalArgumentException("Coordinate must not be null");
		
		return fromCartesian(cc.getX(), cc.getY(), cc.getZ());
	}

	/**
	 * Get a new spherical coordinate for a Cartesian coordinate.
	 * 
	 * @param x the x coordinate of the Cartesian coordinate
	 * @param y the y coordinate of the Cartesian coordinate
	 * @param z the z coordinate of the Cartesian coordinate
	 * @return a new equivalent spherical coordinate
	 */
	public static SphericCoordinate fromCartesian(double x, double y, double z) {
		double r = Math.sqrt(x*x + y*y + z*z);
		double θ = Math.acos(z / r);
		if (r < εZ)
			θ = 0;
		double φ = Math.atan2(y, x);
		
		return new SphericCoordinate(r, θ, φ);
	}

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		assertClassInvariants();
		
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);
		
		return new CartesianCoordinate(x, y, z);
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		if (other == null)
			throw new IllegalArgumentException("Other coordinate must not be null");
		
		SphericCoordinate sc = other.asSphericCoordinate();
		
		assertClassInvariants();
		sc.assertClassInvariants();
		
		// Distance between (r,θ,ϕ) and (r´,θ´,φ´):
		//   sqrt(r^2+r′^2−2rr′[sin(θ)sin(θ′)cos(ϕ−ϕ′)+cos(θ)cos(θ′)])
		
		double a = Math.sin(theta) * Math.sin(sc.theta) * Math.cos(phi - sc.phi)
				+ Math.cos(theta) * Math.cos(sc.theta);
		 
		double ds = Math.pow(radius, 2) + Math.pow(sc.radius, 2)
			- 2 * radius * sc.radius * a;
		
		return Math.sqrt(ds);
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		if (other == null)
			throw new IllegalArgumentException("Other coordinate must not be null");
		
		SphericCoordinate sc = other.asSphericCoordinate();

		assertClassInvariants();
		sc.assertClassInvariants();
		
		double d = Math.cos(theta) * Math.cos(sc.theta) //
				+ Math.sin(theta) * Math.sin(sc.theta) * Math.cos(phi - sc.phi);
		
		return Math.acos(d);
	}

	@Override
	public boolean isEqual(Coordinate other) {
		if (other == null)
			return false;
		return isEqual(other.asSphericCoordinate());
	}

	public boolean isEqual(SphericCoordinate o) {
		if (o == null)
			return false;
		
		assertClassInvariants();
		o.assertClassInvariants();
		
		// Comparing the radius
		if (!cmp(radius, o.radius)) {
			return false;
		}
		if (Math.abs(radius) < εZ) {
			// If this point has no radius (aka P==O),
			// then it is useless to compare θ and φ.
			return true;
		}

		// Comparing the inclination
		if (!cmp(theta, o.theta)) {
			return false;
		}
		if (Math.abs(theta) < εZ) {
			// If this point is at the zenith (at the pole),
			// then it is useless to compare φ.
			return true;
		}

		// Comparing the azimuth
		return cmp(phi, o.phi);
	}

	public String toString() {
		return String.format("(%.15f, %.15f, %.15f)", radius, theta, phi);
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return the theta
	 */
	public double getTheta() {
		return theta;
	}

	/**
	 * @return the phi
	 */
	public double getPhi() {
		return phi;
	}

}
