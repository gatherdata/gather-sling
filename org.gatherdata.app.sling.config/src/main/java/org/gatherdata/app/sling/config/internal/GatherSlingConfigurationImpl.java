package org.gatherdata.app.sling.config.internal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.app.sling.config.GatherSlingConfiguration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

/**
 * Internal implementation of our example OSGi service
 */
public final class GatherSlingConfigurationImpl implements GatherSlingConfiguration, ManagedService {
    
    protected static Log log = LogFactory.getLog(GatherSlingConfigurationImpl.class);

    public static final String SERVICE_PID = "org.gatherdata.app.sling";

    private Map<String, String> internalConfiguration = new HashMap<String, String>();
    
    public void clear() {
        internalConfiguration.clear();
    }

    public boolean containsKey(Object key) {
        return internalConfiguration.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return internalConfiguration.containsValue(value);
    }

    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return internalConfiguration.entrySet();
    }

    public String get(Object key) {
        log.info("get(" + key + ") returns " + internalConfiguration.get(key));
        return internalConfiguration.get(key);
    }

    public boolean isEmpty() {
        return internalConfiguration.isEmpty();
    }

    public Set<String> keySet() {
        return internalConfiguration.keySet();
    }

    public String put(String key, String value) {
        log.info("put(" + key + "," + value + ")");
        return internalConfiguration.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends String> m) {
        internalConfiguration.putAll(m);

    }

    public String remove(Object key) {
        return internalConfiguration.remove(key);
    }

    public int size() {
        return internalConfiguration.size();
    }

    public Collection<String> values() {
        return internalConfiguration.values();
    }

    @SuppressWarnings("unchecked")
    public void updated(Dictionary configuration) throws ConfigurationException {
        log.info("updated(" + configuration + ")");
        if (configuration != null) {
            Enumeration e = configuration.keys();
            while (e.hasMoreElements()) {
                Object key = e.nextElement();
                put(key.toString(), configuration.get(key).toString());
            }
        }
    }

}
