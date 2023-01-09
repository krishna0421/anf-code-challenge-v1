package com.anf.core.models;

import com.anf.core.constants.TestConstants;
import com.anf.core.models.impl.NewsFeedModelImpl;
import com.anf.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// ***Begin Code - Gopi Kotapati *** 

@ExtendWith({AemContextExtension.class , MockitoExtension.class})
public class NewsFeedModelTest {

    public static final String NEWS_FEED_MODEL_JSON = "newsFeedModel.json";
    private final AemContext ctx = AppAemContext.newAemContext();
    private NewsFeedModelImpl newsFeedModelImpl;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(NewsFeedModelImpl.class);
        ctx.load().json(TestConstants.JSON_FILE_PATH + NEWS_FEED_MODEL_JSON, TestConstants.CONTENT);
    }

    @Test
    void testVariationType() {
        newsFeedModelImpl = getModel("/content/newsFeed");
        assertEquals("/var/commerce/products/anf-code-challenge/newsData",newsFeedModelImpl.getNewsPath());
    }

    @Test
    void negativeTestVariationType() {
        newsFeedModelImpl = getModel("/content/newsFeed");
        assertNotEquals("",newsFeedModelImpl.getNewsPath());
    }

    private NewsFeedModelImpl getModel(String currentResource){
        ctx.currentResource(currentResource);
        return ctx.request().adaptTo(NewsFeedModelImpl.class);
    }
}

// *** END CODE***
