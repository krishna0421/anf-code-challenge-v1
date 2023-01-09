package com.anf.core.services.impl;

import com.anf.core.services.SearchPageByQueryService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;


// ***Begin Code - Gopi Kotapati *** 

@Component(service = SearchPageByQueryService.class,
        immediate = true,
        enabled = true)
public class SearchPageByQueryServiceImpl implements SearchPageByQueryService {

    private static final String PATH = "path";
    private static final String PROPERTY = "property";
    private static final String ANF_CODE_CHALLENGE = "anfCodeChallenge";
    private static final String PROPERTY_VALUE = "property.value";
    private static final String TRUE = "true";
    private static final String P_LIMIT = "p.limit";
    private static final String VALUE = "10";
    private static final String JCR_CONTENT = "/jcr:content";
    private static final String HTML = ".html";

    @Reference
    private QueryBuilder builder;

    @Override
    public List<String> getListOfPages(String traversingPath, SlingHttpServletRequest request) throws RepositoryException {
        ResourceResolver resolver = null;
        if (request != null) {
            resolver = request.getResourceResolver();
        }
        Query query = builder.createQuery(PredicateGroup.create(getQuery(traversingPath)), resolver.adaptTo(Session.class));
        List<String> pagesList = new ArrayList<>();
        if(query != null) {
            SearchResult searchResult = query.getResult();
            if(searchResult != null) {
                Iterator<Resource> resourceIterator = searchResult.getResources();
                for (Iterator<Resource> parentResource = resourceIterator; parentResource.hasNext(); ) {
                    Resource resource1 = parentResource.next();
                    String pagePath = resource1.getPath();
                    pagesList.add(pagePath.replace(JCR_CONTENT, HTML));
                }
            }
        }
        return pagesList;
    }

    /**
     * method to give the query map.
     * @param path
     * @return Map<String, String>
     */
    private Map<String, String> getQuery(String path) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(PATH, path);
        queryMap.put(PROPERTY, ANF_CODE_CHALLENGE);
        queryMap.put(PROPERTY_VALUE, TRUE);
        queryMap.put(P_LIMIT, VALUE);
        return queryMap;
    }
}

// *** END CODE ***
