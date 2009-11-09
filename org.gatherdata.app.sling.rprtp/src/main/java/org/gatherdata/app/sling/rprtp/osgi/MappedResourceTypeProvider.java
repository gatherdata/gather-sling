package org.gatherdata.app.sling.rprtp.osgi;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.jcr.resource.JcrResourceTypeProvider;

public class MappedResourceTypeProvider implements JcrResourceTypeProvider {
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

}
