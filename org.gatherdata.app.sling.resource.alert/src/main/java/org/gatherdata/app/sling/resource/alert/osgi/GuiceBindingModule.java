package org.gatherdata.app.sling.resource.alert.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ValueMap;
import org.gatherdata.alert.core.model.ActionPlan;
import org.gatherdata.alert.core.spi.AlertService;
import org.gatherdata.app.sling.resource.alert.internal.AlertServiceResourceProvider;
import org.gatherdata.app.sling.resource.alert.internal.ActionPlanAdapterFactory;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import java.util.Map;
import java.util.Properties;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);

    @Override
    protected void configure() {
        // import the StorageService
        bind(AlertService.class).toProvider(service(AlertService.class).single());
        
        // export the ResourceProvider
        Properties serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling ResourceProvider for AlertService entities.");
        serviceAttrs.put("provider.roots", AlertServiceResourceProvider.PROVIDER_ROOT); // ABKTODO: move the keys into public static constants
        serviceAttrs.put("default.resourceTypes", AlertServiceResourceProvider.RESOURCE_TYPES);
        bind(export(ResourceProvider.class))
            .toProvider(service(AlertServiceResourceProvider.class)
                .attributes(properties(serviceAttrs))
                .export());
        

        // export the AdapterFactory
        serviceAttrs = new Properties();
        serviceAttrs.put(Constants.SERVICE_DESCRIPTION, "Sling AdapterFactory for ActionPlans.");
        serviceAttrs.put(AdapterFactory.ADAPTABLE_CLASSES, 
                new String[] { ActionPlan.class.getName(), Map.class.getName()}); // can adapt from
        serviceAttrs.put(AdapterFactory.ADAPTER_CLASSES, 
                new String[] { ValueMap.class.getName(), ActionPlan.class.getName()}); // can adapt to

        bind(export(AdapterFactory.class))
            .toProvider(service(ActionPlanAdapterFactory.class)
                .attributes(properties(serviceAttrs))
                .export());
    }
    
}
