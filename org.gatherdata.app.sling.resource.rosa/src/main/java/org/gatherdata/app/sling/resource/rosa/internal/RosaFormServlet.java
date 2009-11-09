package org.gatherdata.app.sling.resource.rosa.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceNotFoundException;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HtmlResponse;
import org.apache.sling.api.servlets.OptingServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.forms.core.model.FormTemplateStyle;
import org.gatherdata.forms.core.model.impl.MutableFormTemplate;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.joda.time.DateTime;

import com.google.inject.Inject;

/**
 * RosaFormResource servlet - provides access to rosa-styled FormTemplate raw xhtml
 * 
 * 
 */
public class RosaFormServlet extends SlingAllMethodsServlet implements OptingServlet {

    private static final long serialVersionUID = 1837674982291697074L;

    private final Log log = LogFactory.getLog(RosaFormServlet.class);

    @Inject
    FormCatalogService formCatalog;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        // dumpRequestAsProperties(request, response);
        // debugRequest(request);

        Resource resource = request.getResource();
        if (resource != null) {
            RosaFormResource rosaResource = request.getResource().adaptTo(RosaFormResource.class);
            if (rosaResource.exists()) {
                ServletOutputStream os = response.getOutputStream();
                log.debug("sending contents of " + rosaResource);
                os.print((String) rosaResource.getForm().getFormTemplate());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        Resource resource = request.getResource();
        if (resource != null) {
            RosaFormResource rosaResource = request.getResource().adaptTo(RosaFormResource.class);
            if (rosaResource.exists()) {
                ServletOutputStream os = response.getOutputStream();
                log.debug("sending contents of " + rosaResource);
                os.print((String) rosaResource.getForm().getFormTemplate());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        dumpRequestAsProperties(request, response);
        debugRequest(request);
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
        dumpRequestAsProperties(request, response);
        debugRequest(request);
    }

    protected void debugRequest(SlingHttpServletRequest request) {
        log.debug("Attributes...");
        Enumeration attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = (String) attrNames.nextElement();
            log.debug("\t" + attrName + "=" + request.getAttribute(attrName));
        }
        log.debug("AuthType: " + request.getAuthType());
        log.debug("CharacterEncoding:" + request.getCharacterEncoding());
        log.debug("ContentLength: " + request.getContentLength());
        log.debug("ContentType:" + request.getContentType());
        log.debug("Cookies...");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.debug(cookie);
            }
        }
        log.debug("Headers...");
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            log.debug("\t" + header + "=" + request.getHeader(header));
        }
        InputStream in = null;
        try {
            in = request.getInputStream();
        } catch (IOException e) {
            log.debug("problem reading inputstream", e);
        }
        if (in != null) {
            log.debug(convertStreamToString(in));
        }
        log.debug("Method: " + request.getMethod());
        log.debug("Parameters...");
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            log.debug("\t" + paramName + "=" + request.getParameter(paramName));
        }
        log.debug("PathInfo: " + request.getPathInfo());
        log.debug("Protocol: " + request.getProtocol());
        log.debug("QueryString: " + request.getQueryString());
        log.debug("RemoteAddr: " + request.getRemoteAddr());
        log.debug("RemoteHost: " + request.getRemoteHost());
        log.debug("RemoteUser: " + request.getRemoteUser());
        log.debug("RequestedSessionId: " + request.getRequestedSessionId());
        log.debug("RequestURI: " + request.getRequestURI());
        log.debug("Scheme: " + request.getScheme());
        log.debug("ServletPath: " + request.getServletPath());

        Resource resource = request.getResource();
        if (resource != null) {
            debugResource(resource);
        }
    }

    public void debugResource(Resource resource) {
        log.debug("Sling Resource...");
        log.debug("Resource: " + resource);
        log.debug("ResourceType: " + resource.getResourceType());
        log.debug("ResourceSuperType: " + resource.getResourceSuperType());
        log.debug("Path: " + resource.getPath());
        log.debug("Metadata: " + resource.getResourceMetadata());
    }

    public String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    protected void dumpRequestAsProperties(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        final Properties props = new Properties();
        response.setContentType("text/plain");
        props.put("servlet.class.name", getClass().getName());

        final Resource r = request.getResource();
        props.put("sling.resource.path", r == null ? "" : r.getPath());
        props.put("sling.resource.type", r == null ? "" : r.getResourceType());
        props.put("http.request.method", request.getMethod());

        props.store(response.getOutputStream(), "Data created by " + getClass().getName() + " at " + new Date());
        response.getOutputStream().flush();
    }

    public boolean accepts(SlingHttpServletRequest request) {
        log.debug("accepts(" + request.getRequestPathInfo().getResourcePath() + ")");
        if (request.getRequestPathInfo().getResourcePath().startsWith(RosaResourceProvider.PROVIDER_ROOT)) {
            return true;
        }
        return false;
    }

}