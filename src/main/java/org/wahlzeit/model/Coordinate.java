/**
 * 
 */
package org.wahlzeit.model;

/**
 * A coordinate is an immutable mathematical Cartesian point.
 */
public class Coordinate {

	/*
	 * 
	 */
	public static final Coordinate ORIGIN = new Coordinate(0.0, 0.0, 0.0);
	public static final Coordinate UNIT_X = new Coordinate(1.0, 0.0, 0.0);
	public static final Coordinate UNIT_Y = new Coordinate(0.0, 1.0, 0.0);
	public static final Coordinate UNIT_Z = new Coordinate(0.0, 0.0, 1.0);

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
	
	/**
	 * Return the Euclidean distance between this and the given coordinate.
	 * 
	 * @param other the other coordinate to compare
	 * @return the distance
	 */
	public double getDistance(Coordinate other) {
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
		Coordinate other = (Coordinate) obj;
		return isEqual(other);
	}
	
	public boolean isEqual(Coordinate other) {
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
	
}
