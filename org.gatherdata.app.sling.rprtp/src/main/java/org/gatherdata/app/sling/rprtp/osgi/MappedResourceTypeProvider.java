package org.gatherdata.app.sling.rprtp.osgi;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceDecorator;
import org.apache.sling.api.resource.ResourceWrapper;

public class MappedResourceTypeProvider implements ResourceDecorator {
    Log log = LogFactory.getLog(MappedResourceTypeProvider.class);

    Map<String, String> pathToTypeMap = new HashMap<String, String>();
    
    public String getResourceTypeForNode(Node n) throws RepositoryException {
        final String nodePath = n.getPath();
        log.debug("Asked for type of node: " + n.getName() + " at path " + nodePath);
        if (pathToTypeMap.containsKey(nodePath)) {
            return pathToTypeMap.get(nodePath);
        }
        return null;
    }

    public void mapPathToType(String path, String defaultType) {
        log.debug("mapPathToType(" + path + ", " + defaultType + ")");
        pathToTypeMap.put(path, defaultType);
    }

    public void unmapPath(String pathToUnmap) {
        log.debug("umapPath(" + pathToUnmap + ")");
        pathToTypeMap.remove(pathToUnmap);
    }

	public Resource decorate(Resource r) {
        final String nodePath = r.getPath();
        log.debug("Asked for type of resource at path " + nodePath);
        if (pathToTypeMap.containsKey(nodePath)) {
            final String resourceType = pathToTypeMap.get(nodePath);
            return new ResourceWrapper(r) {
                @Override
                public String getResourceType() {
                    return resourceType;
                }
            };
        }
        return r;
	}

	public Resource decorate(Resource r, HttpServletRequest arg1) {
		return this.decorate(r);
	}

}
