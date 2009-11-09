package org.gatherdata.app.sling.config.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import java.util.Dictionary;
import java.util.Properties;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.gatherdata.app.sling.config.GatherSlingConfiguration;
import org.gatherdata.app.sling.config.internal.GatherSlingConfigurationImpl;

import com.google.inject.Inject;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
    
    @Inject
    Export<GatherSlingConfiguration> gatherSlingConfiguration;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        createInjector(osgiModule(bc), new GuiceBindingModule()).injectMembers(this);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        // no need to unregister our service - the OSGi framework handles it for us
    }
}

