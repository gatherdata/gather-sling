package org.gatherdata.app.sling.rprtp.osgi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.ops4j.peaberry.Import;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceProviderWatcher extends AbstractWatcher<ResourceProvider> {

    Log log = LogFactory.getLog(ResourceProviderWatcher.class);
    
    public static String DEFAULT_RESOURCETYPES = "default.resourceTypes";
    
    @Inject MappedResourceTypeProvider rprtp;
    
    Map<ResourceProvider, List<String>> providerToPathsMap = new HashMap<ResourceProvider, List<String>>();

    @Override
    protected ResourceProvider adding(Import<ResourceProvider> service) {
        ResourceProvider instance = service.get();

        Map<String, ?> attrs = service.attributes();
        if (attrs.containsKey(DEFAULT_RESOURCETYPES)) {
            String[] defaultResourceTypes = OsgiUtil.toStringArray(attrs.get(DEFAULT_RESOURCETYPES));
            String[] resourceRoots = OsgiUtil.toStringArray(attrs.get(ResourceProvider.ROOTS));
            
            providerToPathsMap.put(instance, Arrays.asList(resourceRoots));
            
            for (int i=0; i<resourceRoots.length; i++) {
                rprtp.mapPathToType(resourceRoots[i], defaultResourceTypes[i]);
            }
        }         
        return instance;
    }

    @Override
    protected void modified(ResourceProvider instance, Map<String, ?> attributes) {
        log.info("MODIFIED ResourceProvider:" + instance);
    }

    @Override
    protected void removed(ResourceProvider instance) {
        if (providerToPathsMap.containsKey(instance)) {
            for (String pathToRemove : providerToPathsMap.get(instance)) {
                rprtp.unmapPath(pathToRemove);
            }
            providerToPathsMap.remove(instance);
        }
    }         

}
