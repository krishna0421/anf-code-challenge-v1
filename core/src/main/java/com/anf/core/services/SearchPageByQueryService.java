package com.anf.core.services;

import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.RepositoryException;
import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

public interface SearchPageByQueryService {

    /**
     * Method to fetch the list of pages containing a specified property.
     *
     * @param traversingPath
     * @param request
     * @return List<String>
     * @throws RepositoryException
     */
    List<String> getListOfPages(String traversingPath, SlingHttpServletRequest request) throws RepositoryException;
}

// *** END CODE 