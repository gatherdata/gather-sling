package org.gatherdata.app.sling.resource.archiver.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ValueMap;
import org.gatherdata.app.sling.resource.archiver.internal.ArchiverResourceProvider;
import org.gatherdata.app.sling.resource.archiver.internal.GatherArchiveAdapterFactory;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import java.util.Properties;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);

    @Override
    protected void configure() {
        // import the ArchiverService
        bind(ArchiverService.class).toProvider(service(ArchiverService.class).single());
        
        // export the ArchiverResourceProvider
        Properties serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling ResourceProvider for GatherArchive entitites.");
        serviceAttrs.put("provider.roots", ArchiverResourceProvider.PROVIDER_ROOT); // ABKTODO: move the keys into public static constants
        serviceAttrs.put("default.resourceTypes", ArchiverResourceProvider.RESOURCE_TYPES);
        bind(export(ResourceProvider.class))
            .toProvider(service(ArchiverResourceProvider.class)
                .attributes(properties(serviceAttrs))
                .export());
        

        // export the ArchiverResourceProvider
        serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling AdapterFactory for GatherArchive entitites.");
        serviceAttrs.put(AdapterFactory.ADAPTABLE_CLASSES, GatherArchive.class.getName()); // can adapt from
        serviceAttrs.put(AdapterFactory.ADAPTER_CLASSES, ValueMap.class.getName()); // can adapt to

        bind(export(AdapterFactory.class))
            .toProvider(service(GatherArchiveAdapterFactory.class)
                .attributes(properties(serviceAttrs))
                .export());
    }
    
}
