package org.gatherdata.app.sling.resource.orbeon.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ValueMap;
import org.gatherdata.app.sling.resource.orbeon.internal.OrbeonCrudServlet;
import org.gatherdata.app.sling.resource.orbeon.internal.OrbeonResourceProvider;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import java.util.Arrays;
import java.util.Properties;

import javax.servlet.Servlet;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);

    @Override
    protected void configure() {
        // import the services
        bind(ArchiverService.class).toProvider(service(ArchiverService.class).single());
        bind(FormCatalogService.class).toProvider(service(FormCatalogService.class).single());
        
        // export the OrbeonResourceProvider
        Properties serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Handle Orbeon persistence service REST API.");
        serviceAttrs.put(Constants.SERVICE_VENDOR, "GATHERing");
        serviceAttrs.put("provider.roots", OrbeonResourceProvider.PROVIDER_ROOT); // ABKTODO: move the keys into public static constants
        serviceAttrs.put("default.resourceTypes", OrbeonResourceProvider.RESOURCE_TYPES);
        bind(export(ResourceProvider.class))
            .toProvider(service(OrbeonResourceProvider.class)
                .attributes(properties(serviceAttrs))
                .export());
        
        serviceAttrs = new Properties();
        serviceAttrs.put("sling.servlet.resourceTypes", OrbeonResourceProvider.RESOURCE_TYPES);
        serviceAttrs.put("sling.servlet.methods", new String[] {"PUT","GET","DELETE","POST"});
        bind(export(Servlet.class))
            .toProvider(service(OrbeonCrudServlet.class)
                .attributes(properties(serviceAttrs))
                .export());
        
    }
    
}
