/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/saveUserDetails"
)
public class UserServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String COUNTRY = "country";

    @Reference
    private ContentService contentService;

    /**
     * Method to validate the user details entered.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {

     // ***Begin Code - Gopi Kotapati *** 

        String name = req.getParameter(NAME);
        String email = req.getParameter(EMAIL);
        String age = req.getParameter(AGE);
        String country = req.getParameter(COUNTRY);

        // Following methods to check the validations and return a message based on validations.
        String message = contentService.commitUserDetails(name, email, age, country, req);
        resp.getWriter().write(message);
    }

    // *** END CODE ***
}
