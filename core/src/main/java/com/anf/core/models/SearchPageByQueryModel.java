package com.anf.core.models;

import javax.jcr.RepositoryException;
import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

public interface SearchPageByQueryModel {

    /**
     * Method fetch the list of pages.
     *
     * @return List<String>
     */
    List<String> getPageList() throws RepositoryException;
}

// *** END CODE ***
