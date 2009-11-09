package org.gatherdata.app.sling.sample.resource;

import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;


public class SampleResource implements Resource {
	
	String path = "sample/path";
	ResourceMetadata metadata = null;
	ResourceResolver resolver = null;
	String resourceType = "gather/StandardPage";
	String resourceSuperType = null;
	
	public SampleResource(ResourceResolver resolver, String path)
	{
		this.resolver = resolver;
		this.path = path;
		
		metadata = new ResourceMetadata();
		metadata.setResolutionPath(path);
	}
	public String getPath()
	{
		System.out.println("SampleResource.getPath()");
		return path;
	}
	
	public ResourceMetadata getResourceMetadata()
	{
		System.out.println("SampleResource.getResourceMetadata()");
		return metadata;
	}
	
	public ResourceResolver getResourceResolver()
	{
		System.out.println("SampleResource.getResourceResolver()");
		return resolver;
	}
	
	public String getResourceSuperType()
	{
		System.out.println("SampleResource.getResourceSuperType()");
		return resourceSuperType;
	}
	
	public String getResourceType()
	{
		System.out.println("SampleResource.getResourceType()");
		return resourceType;
	}
	
	public <AdapterType> AdapterType adaptTo(java.lang.Class<AdapterType> type)
	{
		System.out.println("SampleResource.adaptTo(" + type + ")");
		return null;
	}
}