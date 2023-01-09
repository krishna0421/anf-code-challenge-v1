package com.anf.core.services.impl;

import com.anf.core.services.ContentService;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// ***Begin Code - Gopi Kotapati *** 

@Component(immediate = true, service = ContentService.class, enabled = true)
public class ContentServiceImpl implements ContentService {

    private static final String ETC_AGE = "/etc/age";
    private static final String MIN_AGE = "minAge";
    private static final String MAX_AGE = "maxAge";
    private static final String EITHER_NAME_OR_EMAIL_INVALID = "Either Name or Email Invalid";
    private static final String YOUR_DETAILS_HAS_BEEN_SAVED_SUCCESSFULLY = "Your Details has been saved Successfully";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String COUNTRY = "country";
    private static final String STRING = "_";
    private static final String YOU_ARE_NOT_ELIGIBLE = "You are not eligible";
    private static final String VAR_ANF_CODE_CHALLENGE = "/var/anf-code-challenge";

    @Override
    public String commitUserDetails(String name, String email, String age, String country, SlingHttpServletRequest request) {

         final Logger LOGGER = LoggerFactory
                .getLogger(ContentServiceImpl.class);

        Boolean isName = isValidUsername(name);
        Boolean isEmail = isValidEmail(email);
        Boolean isAge = isValidAge(Integer.valueOf(age), request);
        if (isAge == Boolean.FALSE) {
            return YOU_ARE_NOT_ELIGIBLE;
        } else if ((isName == Boolean.TRUE) && (isEmail == Boolean.TRUE) && (isAge == Boolean.TRUE)) {
            String userDetailPath = VAR_ANF_CODE_CHALLENGE;
            if(userDetailPath != null) {
                ResourceResolver resolver = request.getResourceResolver();
                if (Objects.nonNull(resolver)) {
                    Resource resource = resolver.resolve(userDetailPath);
                    Node node = resource.adaptTo(Node.class);
                    try {
                        Node userDataNode = node.addNode(name + STRING + age, JcrConstants.NT_UNSTRUCTURED);
                        userDataNode.setProperty(NAME, name);
                        userDataNode.setProperty(EMAIL, email);
                        userDataNode.setProperty(AGE, age);
                        userDataNode.setProperty(COUNTRY, country);
                        resolver.adaptTo(Session.class).save();
                    } catch (RepositoryException e) {
                        LOGGER.error("ContentServiceImpl :: RepositoryException");
                    }
                }
            }
            return YOUR_DETAILS_HAS_BEEN_SAVED_SUCCESSFULLY;
        }
        return EITHER_NAME_OR_EMAIL_INVALID;
    }

    /**
     * Validate the name syntax.
     * @param name
     * @return boolean
     */
    public static boolean isValidUsername(String name) {
        if (name == null) {
            return false;
        }
        String nameRegex = "^[a-zA-Z][a-zA-Z\\\\s]+$";
        Pattern p = Pattern.compile(nameRegex);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * Validate the email syntax.
     * @param email
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/";
        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validate the age.
     * @param age
     * @param request
     * @return boolean
     */
    public static boolean isValidAge(Integer age, SlingHttpServletRequest request) {
        String path = ETC_AGE;
        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.resolve(path);
        ValueMap vm = resource.getValueMap();
        Integer minAge = vm.get(MIN_AGE, Integer.class);
        Integer maxAge = vm.get(MAX_AGE, Integer.class);
        if ((age >= minAge) && (age <= maxAge)) {
            return true;
        } else {
            return false;
        }
    }
}


// *** END CODE 
