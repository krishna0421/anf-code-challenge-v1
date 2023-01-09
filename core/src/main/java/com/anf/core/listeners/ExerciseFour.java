package com.anf.core.listeners;


import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.*;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// ***Begin Code - Gopi Kotapati *** 

@Component(immediate = true, service = EventHandler.class, property = {
        Constants.SERVICE_DESCRIPTION + "= This event handler listens the events on page activation",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_FILTER + "(&" + "(path=/content/anf-code-challenge/*)"})

public class ExerciseFour implements EventHandler{

    static final Logger LOGGER = LoggerFactory
            .getLogger(ExerciseFour.class);

    private static final String ROOT = "/root";
    private static final String ANFSYSTEMUSER = "anfsystemuser";
    private static final String PAGE_CREATED = "pageCreated";
    private static final String TRUE = "true";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(Event event) {
        String path = (String) event.getProperty(SlingConstants.PROPERTY_PATH);
        if(path != null) {
            int index = path.indexOf(ROOT);
            String newPath = path.substring(0, index);
            ResourceResolver resolver = getResolver(ANFSYSTEMUSER, resourceResolverFactory);
            if(Objects.nonNull(resolver)) {
                Resource resource = resolver.getResource(newPath);
                ModifiableValueMap vm = resource.adaptTo(ModifiableValueMap.class);
                vm.put(PAGE_CREATED, TRUE);
                try {
                    resolver.commit();
                    resolver.close();
                } catch (PersistenceException e) {
                    LOGGER.error("CountriesServlet :: handleEvent() :: IOException");
                }
            }
        }
    }

    public static ResourceResolver getResolver(final String subService,
                                               final ResourceResolverFactory resourceResolverFactory) {
        ResourceResolver resourceResolver = null;
        if (null != resourceResolverFactory
                && StringUtils.isNotBlank(subService)) {
            try {
                Map<String, Object> authMap = new HashMap<>();
                authMap.put(ResourceResolverFactory.SUBSERVICE, subService);

                try {
                    resourceResolver = resourceResolverFactory
                            .getServiceResourceResolver(authMap);
                } catch (LoginException e) {
                    LOGGER.error("CountriesServlet :: getResolver() :: LoginException");
                }
            } catch (Exception e) {
                LOGGER.error("CountriesServlet :: getResolver() :: Exception");
            }
        }
        return resourceResolver;
    }
}

// *** END CODE*** 


