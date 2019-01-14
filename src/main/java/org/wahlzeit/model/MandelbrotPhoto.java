/*
 * MandelbrotPhoto
 * 
 * Version: 2018-11-11
 *
 * Date: Nov 11, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

/**
 * 
 */
@PatternInstance(
			patternName = "Abstract Factory",
			participants = {
				"ConcreteProduct"
			}
		)
public class MandelbrotPhoto extends Photo {

	/**
	 * Describes the color theme used for this picture.
	 */
	protected String colorTheme = "none";
	
	/**
	 * The coordinate of the center of this picture along the real axis.
	 */
	protected double centerReal = 0.0;

	/**
	 * The coordinate of the center of this picture along the imaginary axis.
	 */
	protected double centerImg = 0.0;
	
	/**
	 * The size of this picture as distance between the center and the boundary
	 * of the picture.
	 */
	protected double radius = 1.0;
	
	protected void assertNonNull(Object o) {
		if (o == null) {
			throw new IllegalStateException("Given object must not be null");
		}
	}
	
	protected void assertValidDouble(double d) {
		if (!Double.isFinite(d)) {
			throw new IllegalStateException("Given double must be finite");
		}
	}
	
	protected void assertPositiveDouble(double d) {
		if (!(d > 0)) {
			throw new IllegalStateException("Given double must be positive");
		}
	}
	
	protected void assertClassInvariant() {
		assertNonNull(colorTheme);
		assertValidDouble(centerReal);
		assertValidDouble(centerImg);
		assertValidDouble(radius);
		assertPositiveDouble(radius);
	}
	
	/**
	 * Default constructor.
	 */
	public MandelbrotPhoto() {
		incWriteCount();
	}

	/**
	 * Creates new photo with given id.
	 * 
	 * Contract: Returns valid instance.
	 * 
	 * @param myId the id for the new photo
	 */
	public MandelbrotPhoto(PhotoId myId) {
		super(myId);
		
		// Ensure post-condition
		assertClassInvariant();
		
		incWriteCount();
		
	}


	/**
	 * Gets the description of the color theme used for this picture.
	 * 
	 * Contract: none
	 * 
	 * @return the color theme
	 */
	public String getColorTheme() {
		// Ensure class invariant
		assertClassInvariant();
		
		return colorTheme;
	}

	/**
	 * Set the description of the color theme used for this picture.
	 * 
	 * Contract: colorTheme must be not null.
	 * 
	 * @param colorTheme the new color theme
	 */
	public void setColorTheme(String colorTheme) {
		// Ensure pre-condition
		assertNonNull(colorTheme);
		
		// Ensure class invariant
		assertClassInvariant();
		
		this.colorTheme = colorTheme;
		incWriteCount();
		
		// Ensure class invariant
		assertClassInvariant();
	}
	

	/**
	 * Gets the coordinate of the center of this picture along the real axis.
	 * 
	 * Contract: none.
	 * 
	 * @return the real coordinate
	 */
	public double getCenterReal() {
		
		// Ensure class invariant
		assertClassInvariant();
		
		return centerReal;
	}

	/**
	 * Set the coordinate of the center of this picture along the real axis.
	 * 
	 * Contract: centerReal must be a finite value.
	 * 
	 * @param centerReal the new real coordinate
	 */
	public void setCenterReal(double centerReal) {
		// Ensure pre-condition
		assertValidDouble(centerReal);

		// Ensure class invariant
		assertClassInvariant();
		
		this.centerReal = centerReal;
		incWriteCount();
		
		// Ensure class invariant
		assertClassInvariant();
	}


	/**
	 * Get the coordinate of the center of this picture along the imaginary axis.
	 * 
	 * Contract: none.
	 * 
	 * @return the imaginary coordinate
	 */
	public double getCenterImg() {
		// Ensure class invariant
		assertClassInvariant();
		
		return centerImg;
	}


	/**
	 * Set the coordinate of the center of this picture along the imaginary axis.
	 * 
	 * Contract: centerImg must be a finite double.
	 * 
	 * @param centerImg the new imaginary coordinate
	 */
	public void setCenterImg(double centerImg) {
		// Ensure pre-condition
		assertValidDouble(centerImg);

		// Ensure class invariant
		assertClassInvariant();
		
		this.centerImg = centerImg;
		incWriteCount();

		// Ensure class invariant
		assertClassInvariant();
	}

	
	/**
	 * Get the size of this picture as distance between the center and the
	 * boundary of the picture.
	 * 
	 * Contract: none.
	 * 
	 * @return the size of this picture
	 */
	public double getRadius() {
		// Ensure class invariant
		assertClassInvariant();
		
		return radius;
	}

	/**
	 * Set the size of this picture as distance between the center and the
	 * boundary of the picture.
	 * 
	 * Contract: radius must be a positive finite double.
	 * 
	 * @param radius the size of this picture
	 */
	public void setRadius(double radius) {
		assertValidDouble(radius);
		assertPositiveDouble(radius);

		// Ensure class invariant
		assertClassInvariant();
		
		this.radius = radius;
		incWriteCount();

		// Ensure class invariant
		assertClassInvariant();
	}

}
