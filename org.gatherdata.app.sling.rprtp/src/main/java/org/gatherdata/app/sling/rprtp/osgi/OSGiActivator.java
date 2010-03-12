package org.gatherdata.app.sling.rprtp.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceDecorator;
import org.apache.sling.api.resource.ResourceProvider;

import com.google.inject.Inject;

/**
 * OSGi bundle activator.
 */
public final class OSGiActivator
    implements BundleActivator
{
    Log log = LogFactory.getLog(OSGiActivator.class);
    
    @Inject
    Iterable<ResourceProvider> resourceProviders;

    @Inject
    Export<ResourceDecorator> resourceTypeProvider;
    
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
        log.info( "STOPPING org.gatherdata.app.sling.rprtp" );
    }
}

