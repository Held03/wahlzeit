/*
 * MandelbrotPhotoFactory
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
			"ConcreteFactory"
		}
	)
public class MandelbrotPhotoFactory extends PhotoFactory {

	/**
	 * 
	 */
	public MandelbrotPhotoFactory() {
		
	}

	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		setInstance(new MandelbrotPhotoFactory());
	}
	
	/**
	 * @methodtype factory
	 */
	public MandelbrotPhoto createPhoto() {
		return new MandelbrotPhoto();
	}

	/**
	 * Creates a new photo with the specified id.
	 */
	public MandelbrotPhoto createPhoto(PhotoId id) {
		return new MandelbrotPhoto(id);
	}

}
