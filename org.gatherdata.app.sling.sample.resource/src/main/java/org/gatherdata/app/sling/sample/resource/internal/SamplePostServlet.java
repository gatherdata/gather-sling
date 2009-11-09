package org.gatherdata.app.sling.sample.resource.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

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

/**
 * Sample POST servlet.
 * 
 * @scr.component immediate="true" label="Sample POST Servlet"
 *                description="A sample Sling servlet that handles POST requests."
 * @scr.service interface="javax.servlet.Servlet"
 * @scr.property name="service.description" value="Sample POST Servlet"
 * @scr.property name="service.vendor" value="GATHERing"
 * 
 * @scr.property name="sling.servlet.resourceTypes"
 *               value="sling/servlet/default"
 * @scr.property name="sling.servlet.methods" value="POST"
 * 
 * @scr.property name="sling.servlet.selectors" value="gather"
 * 
 */
public class SamplePostServlet extends SlingAllMethodsServlet implements OptingServlet {

    private static final long serialVersionUID = 1837674982291697074L;

	private final Log log = LogFactory.getLog(SamplePostServlet.class);

    @Override
    protected void doPost(SlingHttpServletRequest request,
            SlingHttpServletResponse response) throws IOException 
	{
	    dumpRequestAsProperties(request, response);
	}
	
	  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
  throws ServletException, IOException {
    dumpRequestAsProperties(request, response);
  }
  
  protected void dumpRequestAsProperties(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
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
    if (request.getRequestPathInfo().getResourcePath().startsWith("/gather/sample")) {
        return true;
    }
    return false;
}

}