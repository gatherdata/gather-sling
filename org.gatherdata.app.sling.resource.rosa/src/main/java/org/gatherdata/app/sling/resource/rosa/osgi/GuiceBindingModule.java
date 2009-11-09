package org.gatherdata.app.sling.resource.rosa.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ValueMap;
import org.gatherdata.app.sling.resource.rosa.internal.RosaFormServlet;
import org.gatherdata.app.sling.resource.rosa.internal.RosaResourceProvider;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import java.util.Properties;

import javax.servlet.Servlet;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);

    @Override
    protected void configure() {
        // import the service
        bind(FormCatalogService.class).toProvider(service(FormCatalogService.class).single());
        
        // export the resource provider
        Properties serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling ResourceProvider for Rosa-styled forms.");
        serviceAttrs.put("provider.roots", RosaResourceProvider.PROVIDER_ROOT); // ABKTODO: move the keys into public static constants
        serviceAttrs.put("default.resourceTypes", RosaResourceProvider.RESOURCE_TYPES);
        bind(export(ResourceProvider.class))
            .toProvider(service(RosaResourceProvider.class)
                .attributes(properties(serviceAttrs))
                .export());
        
        serviceAttrs = new Properties();
        serviceAttrs.put("sling.servlet.resourceTypes", RosaResourceProvider.RESOURCE_TYPES);
        serviceAttrs.put("sling.servlet.methods", new String[] {"GET", "POST"});
        bind(export(Servlet.class))
            .toProvider(service(RosaFormServlet.class)
                .attributes(properties(serviceAttrs))
                .export());


//        serviceAttrs = new Properties();
//        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling AdapterFactory for GatherArchive entitites.");
//        serviceAttrs.put(AdapterFactory.ADAPTABLE_CLASSES, GatherArchive.class.getName()); // can adapt from
//        serviceAttrs.put(AdapterFactory.ADAPTER_CLASSES, ValueMap.class.getName()); // can adapt to
//        bind(export(AdapterFactory.class))
//            .toProvider(service(GatherArchiveAdapterFactory.class)
//                .attributes(properties(serviceAttrs))
//                .export());
    }
    
}
