package org.gatherdata.app.sling.rprtp.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.jcr.resource.JcrResourceTypeProvider;
import org.ops4j.peaberry.util.AbstractWatcher;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);
    

    @Override
    protected void configure() {
        log.info("GuiceBindingModule.configure()");
        
        // bind the ResourceProviderWatcher to instances of ResourceProviders
        bind(iterable(ResourceProvider.class)).toProvider(
                service(ResourceProvider.class).out(new ResourceProviderWatcher()).multiple());
        
        // export the ResourceTypeProvider
        Properties mrtpAttrs = new Properties();
        mrtpAttrs.put(Constants.SERVICE_RANKING, new Long(100));
        bind(export(JcrResourceTypeProvider.class))
            .toProvider(service(new MappedResourceTypeProvider())
                .attributes(properties(mrtpAttrs))
                .export());
    }

}
