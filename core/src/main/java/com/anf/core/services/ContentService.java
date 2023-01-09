package com.anf.core.services;

import org.apache.sling.api.SlingHttpServletRequest;

public interface ContentService {

	// ***Begin Code - Gopi Kotapati *** 

	/**
	 * Method to validate the form fields entered by the user.
	 * @param name
	 * @param email
	 * @param age
	 * @param country
	 * @param request
	 * @return String
	 */
	String commitUserDetails(String name, String email, String age, String country, SlingHttpServletRequest request);
}

// *** END CODE ***
