package org.gatherdata.app.sling.config.osgi;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Attributes.properties;
import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.app.sling.config.GatherSlingConfiguration;
import org.gatherdata.app.sling.config.internal.GatherSlingConfigurationImpl;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Import;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedService;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GuiceBindingModule extends AbstractModule {
    Log log = LogFactory.getLog(GuiceBindingModule.class);

    @Override
    protected void configure() {
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put(Constants.SERVICE_PID, GatherSlingConfigurationImpl.SERVICE_PID);
        bind(export(GatherSlingConfiguration.class)).toProvider(service(GatherSlingConfigurationImpl.class).attributes(attrs).export());
    }
    
}
