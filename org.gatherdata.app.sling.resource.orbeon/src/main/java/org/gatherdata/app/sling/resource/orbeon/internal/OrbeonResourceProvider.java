package org.gatherdata.app.sling.resource.orbeon.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceProvider;
import org.apache.sling.api.resource.ResourceResolver;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.archiver.core.spi.ArchiverService;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.spi.FormCatalogService;

import com.google.inject.Inject;

public class OrbeonResourceProvider implements ResourceProvider {

    public static final String PROVIDER_ROOT = "/gather/orbeon";

    public static final String[] RESOURCE_TYPES = new String[] {
        OrbeonFormResource.RESOURCE_TYPE,
        OrbeonDataResource.RESOURCE_TYPE,
        OrbeonSearchResource.RESOURCE_TYPE 
        };

    @Inject
    ArchiverService archiverService;

    @Inject
    FormCatalogService formCatalog;

    /**
     * /crud/[APPLICATION]/[FORM_TYPE]/form/data.xml
     */
    private final Pattern FORM_URL_PATTERN = Pattern.compile("^" + PROVIDER_ROOT + "/crud/(.*)/(.*)/form/(.*)$");

    /**
     * /crud/[APPLICATION]/[FORM_TYPE]/data/[FORM_DATA_ID]/data.xml
     */
    private final Pattern DATA_URL_PATTERN = Pattern.compile("^" + PROVIDER_ROOT + "/crud/(.*)/(.*)/data/(.*)/(.*)$");

    private final Pattern SEARCH_URL_PATTERN = Pattern.compile("^" + PROVIDER_ROOT + "/search/(.*)/(.*)$");

    private Log log = LogFactory.getLog(OrbeonResourceProvider.class);

    public Resource getResource(ResourceResolver resourceResolver, HttpServletRequest request, String path) {
        log.debug("getResource(*,*," + path + ")");
        return getResource(resourceResolver, path);
    }

    public Resource getResource(ResourceResolver resourceResolver, String path) {
        log.debug("getResource(*," + path + ")");
        Resource foundResource = null;

        Matcher formCrudMatcher = FORM_URL_PATTERN.matcher(path);
        if (formCrudMatcher.matches()) {
            String xformsApplication = formCrudMatcher.group(1);
            String xformsType = formCrudMatcher.group(2);
            String resourceName = formCrudMatcher.group(3);

            log.debug("Possible form resource...");
            log.debug("application: " + xformsApplication);
            log.debug("type: " + xformsType);
            log.debug("resource name: " + resourceName);

            // URI = http://gatherdata.org/<xformsApplication>/<xformsType>
            URI formNamespace;
            try {
                formNamespace = new URI("http://gatherdata.org/" + xformsApplication + "/" + xformsType);
                foundResource = getFormResource(resourceResolver, path, formNamespace);
                
                if (foundResource == null) {
                    foundResource = new OrbeonFormResource(resourceResolver, path, formNamespace);
                }
            } catch (URISyntaxException e) {
                log.error("Failed to create form namespace URI", e);
            }

        }
        if (foundResource == null) {
            Matcher dataCrudMatcher = DATA_URL_PATTERN.matcher(path);
            if (dataCrudMatcher.matches()) {
                String xformsApplication = dataCrudMatcher.group(1);
                String xformsType = dataCrudMatcher.group(2);
                String instanceId = dataCrudMatcher.group(3);
                String resourceName = dataCrudMatcher.group(4);

                log.debug("Possible data resource...");
                log.debug("application: " + xformsApplication);
                log.debug("type: " + xformsType);
                log.debug("data id: " + instanceId);
                log.debug("resource name: " + resourceName);

                try {
                    // URI = http://gatherdata.org/<xformsApplication>/<xformsType>/<instanceId>
                    URI dataUid = new URI("http://gatherdata.org/" + xformsApplication + "/" + xformsType + "/" + instanceId);
                    foundResource = getDataResource(resourceResolver, path, dataUid);
                    
                    if (foundResource == null) {
                        foundResource = new OrbeonFormResource(resourceResolver, path, dataUid);
                    }
                } catch (URISyntaxException e) {
                    log.error("Failed to generate data URI", e);
                }

            }

        }
        if (foundResource == null) {
            Matcher searchMatcher = SEARCH_URL_PATTERN.matcher(path);
            if (searchMatcher.matches()) {
                String xformsApplication = searchMatcher.group(1);
                String xformsType = searchMatcher.group(2);

                log.debug("application: " + xformsApplication);
                log.debug("type: " + xformsType);

                try {
                    URI formNamespace = new URI("http://gatherdata.org/" + xformsApplication + "/" + xformsType);
                    // create a search resource object, which the servlet will
                    // use to search the formService.
                    // awkward? yeah.
                    foundResource = new OrbeonSearchResource(resourceResolver, path, formNamespace);

                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        log.debug("returning: " + foundResource);
        return foundResource;
    }

    private Resource getFormResource(ResourceResolver resourceResolver, String fullPath, URI requestedUid) {
        Resource foundResource = null;
        if (requestedUid != null) {
            log.debug("looking for form template with uid: " + requestedUid.toASCIIString());
            FormTemplate requestedForm = formCatalog.get(requestedUid);
            if (requestedForm != null) {
                foundResource = new OrbeonFormResource(resourceResolver, fullPath, requestedForm);
                log.debug("found resource: " + foundResource);
            } else {
                log.debug("form not found");
            }
        }

        return foundResource;
    }

    private Resource getDataResource(ResourceResolver resourceResolver, String path, URI dataUid) {
        // TODO Auto-generated method stub
        return null;
    }


    public Iterator<Resource> listChildren(Resource parent) {
        log.debug("listChildren(" + parent.getPath() + ")");
        return null;
    }

}
