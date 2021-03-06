package org.gatherdata.app.sling.resource.rosa.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import java.util.logging.Logger;

import javax.servlet.Servlet;

import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ResourceProvider;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

/**
 * OSGi bundle activator, which registers a Felix Shell command for interacting
 * with the shared CamelContext.
 */
public final class OSGiActivator
    implements BundleActivator
{
    private static final transient Logger log = Logger.getLogger(OSGiActivator.class.getName());
    
    @Inject
    FormCatalogService formCatalogService;
    
    @Inject
    Export<ResourceProvider> rosaResourceProvider;
    
    @Inject
    Export<Servlet> rosaFormServlet;
    
//    @Inject
//    Export<AdapterFactory> rosaAdapterFactory;

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

