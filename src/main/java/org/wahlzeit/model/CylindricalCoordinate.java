/**
 * CylindricCoordinate
 * 
 * Version: 2018-11-26
 *
 * Date: Nov 26, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * Represents a Cylindrical coordinate.
 * 
 * A Cylindrical coordinate defines points on the corner of a cylinder which
 * is centered in the origin of the coordinate system. Each coordinate specifies
 * a cylinder by its radius and height. Where as height means the distance from
 * the reference plane (e.g. the XY-plane) in "up" direction (e.g. positive
 * Z-axis). A negative height value means that the respective point is on the
 * bottom side of the cylinder (e.g. in negative Z-direction).
 * The phi value defines the position of the point in the respective top or
 * bottom circle of the cylinder.
 * 
 * In mathematics a cylindrical coordinate is represented by the tuple (ρ,φ,z),
 * whereas ρ is the cylindrical radius, φ the azimuth within the XY-plane, and
 * z means the signed distance form the XY-plane in positive Z-direction.
 */
public class CylindricalCoordinate extends AbstractCoordinate {
	/**
	 * The radius of the cylinder of this point. 
	 */
	protected double radius;
	
	/**
	 * The azimuth of this point within the cylinder top or bottom.
	 */
	protected double phi;
	
	/**
	 * The signed height of the point from the XY-plane.
	 */
	protected double height;

	/**
	 * 
	 */
	public CylindricalCoordinate(double radius, double phi, double height) {
		if (radius < 0)
			throw new IllegalArgumentException("Radius must be positive");
		if (!Double.isFinite(radius) || !Double.isFinite(height) || !Double.isFinite(phi))
			throw new IllegalArgumentException("Coordinates must be finite");
		
		phi = phi % (2*Math.PI);
		if (phi < 0) {
			phi += 2*Math.PI;
		}
		assert 0 <= phi && phi < 2*Math.PI;
		
		this.radius = radius;
		this.phi = phi;
		this.height = height;
		
		
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.AbstractCoordinate#asCartesianCoordinate()
	 */
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = radius * Math.cos(phi);
		double y = radius * Math.sin(phi);
		double z = height;
		
		return new CartesianCoordinate(x, y, z);
	}
	
	public static CylindricalCoordinate formCartesian(CartesianCoordinate cc) {
		double ρ = Math.sqrt(cc.getX()*cc.getX() + cc.getY()*cc.getY());
		double φ = Math.atan2(cc.getY(), cc.getX());
		double z = cc.getZ();
		
		return new CylindricalCoordinate(ρ, φ, z);
	}

	/* (non-Javadoc)
	 * @see org.wahlzeit.model.AbstractCoordinate#asSphericCoordinate()
	 */
	@Override
	public SphericCoordinate asSphericCoordinate() {
		double r = Math.sqrt(radius*radius + height*height);
		double θ = Math.atan(radius / height);
		double φ = phi;
		
		return new SphericCoordinate(r, θ, φ);
	}
	
	public static CylindricalCoordinate fromSpheric(SphericCoordinate sc) {
		double ρ = sc.getRadius() * Math.sin(sc.getTheta());
		double φ = sc.getPhi();
		double z = sc.getRadius() * Math.cos(sc.getTheta());
		
		return new CylindricalCoordinate(ρ, φ, z);
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return the phi
	 */
	public double getPhi() {
		return phi;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	
	

}
