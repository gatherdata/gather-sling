package org.gatherdata.app.sling.sample.resource.internal;

import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

import org.gatherdata.app.sling.sample.resource.SampleResource;

/**
 * @scr.service interface=org.apache.sling.api.resource.ResourceProvider
 *
 * @scr.component
 * immediate="true"
 * metatype="false"
 *
 * @scr.property
 * name="service.description"
 * value="A sample Sling ResourceProvider, which proxies access to data stored in the SampleResourceService."
 *
 * @scr.property
 * name="service.vendor"
 * value="GATHERing"
 * 
 * @scr.property name="provider.roots" value="/gather/sample/resources"
 * @scr.property name="default.resourceTypes" value="gather/sample"
 */

public class SampleResourceProvider implements ResourceProvider {

    Log log = LogFactory.getLog(SampleResourceProvider.class);

	
	public Resource getResource(ResourceResolver resourceResolver, HttpServletRequest request, String path)
	{
		log.debug("SampleResourceProvider.getResource(*,*," + path + ")");
		return getResource(resourceResolver, path);
	}
	
	public Resource getResource(ResourceResolver resourceResolver, String path)
	{
		log.debug("SampleResourceProvider.getResource(*," + path + ")");
		Resource foundResource = null;
		// JcrResourceResourceResolver appends to the path in an attempt 
		// to find something it can display
		if (!path.endsWith("jcr:content") &&
			!path.endsWith("index.html")) {
			foundResource = new SampleResource(resourceResolver, path);
		}
		return foundResource;
	}

	public Iterator<Resource> listChildren(Resource parent)
	{
		log.debug("SampleResourceProvider.listChildren(" + parent.getPath() + ")");
		return null;
	}
}
