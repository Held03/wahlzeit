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

/**
 * 
 */
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
	
	/**
	 * Default constructor.
	 */
	public MandelbrotPhoto() {
		incWriteCount();
	}

	/**
	 * Creates new photo with given id.
	 * 
	 * @param myId the id for the new photo
	 */
	public MandelbrotPhoto(PhotoId myId) {
		super(myId);
		incWriteCount();
		
	}


	/**
	 * Gets the description of the color theme used for this picture.
	 * 
	 * @return the color theme
	 */
	public String getColorTheme() {
		return colorTheme;
	}

	/**
	 * Set the description of the color theme used for this picture.
	 * 
	 * @param colorTheme the new color theme
	 */
	public void setColorTheme(String colorTheme) {
		if (colorTheme == null)
			throw new IllegalArgumentException("colortheme must not be null");
		
		this.colorTheme = colorTheme;
		incWriteCount();
	}
	

	/**
	 * Gets the coordinate of the center of this picture along the real axis.
	 * 
	 * @return the real coordinate
	 */
	public double getCenterReal() {
		return centerReal;
	}

	/**
	 * Set the coordinate of the center of this picture along the real axis.
	 * 
	 * @param centerReal the new real coordinate
	 */
	public void setCenterReal(double centerReal) {
		if (!Double.isFinite(radius))
			throw new IllegalArgumentException("Coordinates must be finite numbers");
		
		this.centerReal = centerReal;
		incWriteCount();
	}


	/**
	 * Get the coordinate of the center of this picture along the imaginary axis.
	 * 
	 * @return the imaginary coordinate
	 */
	public double getCenterImg() {
		return centerImg;
	}


	/**
	 * Set the coordinate of the center of this picture along the imaginary axis.
	 * 
	 * @param centerImg the new imaginary coordinate
	 */
	public void setCenterImg(double centerImg) {
		if (!Double.isFinite(radius))
			throw new IllegalArgumentException("Coordinates must be finite numbers");
		
		this.centerImg = centerImg;
		incWriteCount();
	}

	
	/**
	 * Get the size of this picture as distance between the center and the
	 * boundary of the picture.
	 * 
	 * @return the size of this picture
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the size of this picture as distance between the center and the
	 * boundary of the picture.
	 * 
	 * @param radius the size of this picture
	 */
	public void setRadius(double radius) {
		if (!Double.isFinite(radius))
			throw new IllegalArgumentException("Radius must be a finite number");
		if (radius < Double.MIN_NORMAL)
			throw new IllegalArgumentException("Radius must be a positive number");
		
		this.radius = radius;
		incWriteCount();
	}

}
