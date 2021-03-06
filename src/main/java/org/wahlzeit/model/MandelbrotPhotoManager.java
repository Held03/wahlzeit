/*
 * MandelbrotPhotoManager
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
		patternName = "Singelton",
		participants = {
			"Singelton"
		}
	)
public class MandelbrotPhotoManager extends PhotoManager {

	/**
	 * 
	 */
	public MandelbrotPhotoManager() {
		
	}

}
