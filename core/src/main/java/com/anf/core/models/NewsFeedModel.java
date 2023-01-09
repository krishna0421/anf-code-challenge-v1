package com.anf.core.models;

import com.anf.core.models.internals.NewsFeedEntity;

import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

public interface NewsFeedModel {

    /**
     * Method fetch the list of news feeds.
     * @return List<NewsFeedEntity>
     */
    List<NewsFeedEntity> getNewsListEntity();
}

// *** End Code***
