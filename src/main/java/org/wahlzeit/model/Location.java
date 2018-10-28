/**
 * 
 */
package org.wahlzeit.model;

/**
 * A location represents a position in the real world (i.e., a position on the
 * Earth surface).
 */
public class Location {
	/*
	 * 
	 */
	protected Coordinate coordinate;
	
	private Location() {
		// do nothing, necessary for Objectify to load it
	}

	public Location(Coordinate coordinate) {
		super();
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	
}
