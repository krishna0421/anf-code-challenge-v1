package com.anf.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.anf.core.models.NewsFeedModel;
import com.anf.core.models.internals.NewsFeedEntity;
import com.anf.core.services.NewsFeedService;
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

import java.util.List;

// ***Begin Code - Gopi Kotapati *** 

@Data
@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {NewsFeedModel.class},
        resourceType = {NewsFeedModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
        extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class NewsFeedModelImpl implements NewsFeedModel {

    static final String RESOURCE_TYPE = "anf-code-challenge/components/newsFeedComponent";

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String newsPath;

    @OSGiService
    private NewsFeedService newsFeedService;

    @SlingObject
    SlingHttpServletRequest request;

    @Override
    public List<NewsFeedEntity> getNewsListEntity() {
        return newsFeedService.getListOfNewsFeed(newsPath, request);
    }
}

// *** END CODE ***