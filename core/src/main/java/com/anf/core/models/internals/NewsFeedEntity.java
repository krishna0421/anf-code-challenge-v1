package com.anf.core.models.internals;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

// ***Begin Code - Gopi Kotapati *** 

@Data
@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsFeedEntity {

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String author;

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String content;

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private  String description;

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String title;

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private String url;

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    private  String urlImage;

}


// *** END CODE ***