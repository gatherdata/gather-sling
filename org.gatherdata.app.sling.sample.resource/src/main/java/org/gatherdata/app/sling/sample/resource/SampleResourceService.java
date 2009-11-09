package org.gatherdata.app.sling.sample.resource;

/**
 * An OSGI Service interface for storing and retrieving samples. 
 */
public interface SampleResourceService 
{
	
	public void saveSample(SampleResource sample);
	
	public SampleResource findSampleAtPath(String path);
	
}