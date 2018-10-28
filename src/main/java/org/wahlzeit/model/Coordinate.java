/**
 * 
 */
package org.wahlzeit.model;

/**
 * A coordinate is a mathematical Cartesian location.
 */
public class Coordinate {

	/*
	 * 
	 */
	public static final Coordinate ORIGIN = new Coordinate(0.0, 0.0, 0.0);
	public static final Coordinate UINT_X = new Coordinate(1.0, 0.0, 0.0);
	public static final Coordinate UINT_Y = new Coordinate(0.0, 1.0, 0.0);
	public static final Coordinate UINT_Z = new Coordinate(0.0, 0.0, 1.0);

	/*
	 * 
	 */
	protected double x;
	protected double y;
	protected double z;

	private Coordinate() {
		// do nothing, necessary for Objectify to load it
	}

	public Coordinate(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
