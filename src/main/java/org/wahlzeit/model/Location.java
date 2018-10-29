/**
 * Location
 * 
 * Version: 2018-10-29
 *
 * Date: Oct 29, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * A location represents a position in the real world (i.e., a position on the
 * Earth surface).
 */
public class Location {
	/*
	 * The coordinate of this location.
	 */
	protected Coordinate coordinate;
	
	private Location() {
		// do nothing, necessary for Objectify to load it
	}

	public Location(Coordinate coordinate) {
		if (coordinate == null)
			throw new IllegalArgumentException("Coordinate must not be null");
		
		this.coordinate = coordinate;
	}
	
	/**
	 * @return the coordinate
	 * @methodtype get
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 * @methodtype set
	 */
	public void setCoordinate(Coordinate coordinate) {
		if (coordinate == null)
			throw new IllegalArgumentException("Coordinate must not be null");
		
		this.coordinate = coordinate;
	}
	
	
}
