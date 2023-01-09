package com.anf.core.servlets;


import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.anf.core.services.impl.ContentServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.Gson;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

// ***Begin Code - Gopi Kotapati *** 

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/apps/countryName"})

public class CountriesServlet extends SlingSafeMethodsServlet {

    private static final String DATASOURCE = "datasource";
    private static final String DAM_PATH = "damPath";
    private static final String VALUE = "value";
    private static final String TEXT = "text";

    /**
     * method to fetch all the countries' names and to show them into the dropdown.
     * @param request
     * @param response
     */
    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) {

        final Logger LOGGER = LoggerFactory
                .getLogger(CountriesServlet.class);

        List<KeyValue> dropdownList = new ArrayList<>();
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource parentResource = request.getResource();
        Resource childResource = parentResource.getChild(DATASOURCE);
        ValueMap vm = childResource.getValueMap();
        String path = vm.get(DAM_PATH, String.class);
        Resource resource = resourceResolver.getResource(path);

        // Fetched the json file path and converted into asset.
        Asset asset = resource.adaptTo(Asset.class);
        Rendition rendition = asset.getOriginal();
        InputStream inputStream = rendition.adaptTo(InputStream.class);
        StringBuilder stringBuilder = new StringBuilder();
        String data = StringUtils.EMPTY;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        while (true) {
            try {
                if (!((data = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                LOGGER.error("CountriesServlet :: IOException");
            }
            stringBuilder.append(data);
        }

        // json data changed into json object.
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            LOGGER.error("CountriesServlet::JSONException");
        }
        Map<String,String> countryMap= new HashMap<>();
        countryMap = new Gson().fromJson(String.valueOf(jsonObject), HashMap.class);

        // country list fetched.
        List<String> countriesList = new ArrayList<String>(countryMap.values());
        if (Objects.nonNull(resourceResolver)) {

            countriesList.forEach(bikeName -> {
                dropdownList.add(new KeyValue(bikeName, bikeName));
            });
        }

        // Datasource used to show the countries in the dropdown.
        DataSource ds =
                new SimpleDataSource(
                        new TransformIterator(dropdownList.iterator(),
                                input -> {
                                    KeyValue keyValue = (KeyValue) input;
                                    ValueMap vm1 = new ValueMapDecorator(new HashMap<>());
                                    vm1.put(VALUE, keyValue.key);
                                    vm1.put(TEXT, keyValue.value);
                                    return new ValueMapResource(
                                            resourceResolver, new ResourceMetadata(),
                                            JcrConstants.NT_UNSTRUCTURED, vm1);
                                }));
        request.setAttribute(DataSource.class.getName(), ds);
    }

    class KeyValue {

        private String key;
        private String value;

        protected KeyValue(final String newKey,
                           final String newValue) {
            this.key = newKey;
            this.value = newValue;
        }
    }
}

// *** END CODE ***
