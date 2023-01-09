package com.anf.core.services.impl;

import com.anf.core.models.internals.NewsFeedEntity;
import com.anf.core.services.NewsFeedService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

@Component(service = NewsFeedService.class,
        immediate = true,
        enabled = true)
public class NewsFeedServiceImpl
        implements NewsFeedService {

    @Override
    public List<NewsFeedEntity> getListOfNewsFeed(String path, SlingHttpServletRequest request) {
        ResourceResolver resolver = null;
        if (request != null) {
            resolver = request.getResourceResolver();
        }
        Resource parentResource = resolver.getResource(path);
        List<NewsFeedEntity> newsFeedEntityList =
                new ArrayList<>();
        if (parentResource != null && parentResource.hasChildren()) {
            NewsFeedEntity newsFeedEntity;
            for (Resource childResource : parentResource.getChildren()) {
                newsFeedEntity = childResource.adaptTo(NewsFeedEntity.class);
                newsFeedEntityList.add(newsFeedEntity);
            }
        }
        return newsFeedEntityList;
    }
}

// *** END CODE ***