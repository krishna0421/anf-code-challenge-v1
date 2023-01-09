package com.anf.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.anf.core.models.SearchPageByQueryModel;
import com.anf.core.services.SearchPageByQueryService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.jcr.RepositoryException;
import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

@Data
@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {SearchPageByQueryModel.class},
        resourceType = {SearchPageByQueryModelImp.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
        extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SearchPageByQueryModelImp implements SearchPageByQueryModel {

    static final String RESOURCE_TYPE = "anf-code-challenge/components/queryBuilderComponent";

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String traversingPath;

    @OSGiService
    private SearchPageByQueryService searchPageByQueryService;

    @SlingObject
    SlingHttpServletRequest request;


    @Override
    public List<String> getPageList() throws RepositoryException {
        return searchPageByQueryService.getListOfPages(traversingPath, request);
    }
}

// *** END CODE ***