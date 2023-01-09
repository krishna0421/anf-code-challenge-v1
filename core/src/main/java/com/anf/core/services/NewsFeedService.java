package com.anf.core.services;

import com.anf.core.models.internals.NewsFeedEntity;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

public interface NewsFeedService {

    /**
     * method to fetch the list of all news feeds.
     * @param path
     * @param request
     * @return List<NewsFeedEntity>
     */
    List<NewsFeedEntity> getListOfNewsFeed(String path, SlingHttpServletRequest request);
}

// *** END CODE ***
