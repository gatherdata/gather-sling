package org.gatherdata.app.sling.resource.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.gatherdata.archiver.core.model.GatherArchive;

/**
 * The SyntheticMapResource is a SynteticResource which does have data,
 * in the form of a Map.
 * 
 */
public class SyntheticMapResource extends SyntheticResource implements Resource, Map<String, Object> {

    protected Map<String, Object> syntheticMap = new HashMap<String, Object>();

    public SyntheticMapResource(ResourceResolver resourceResolver, String path, String resourceType) {
        super(resourceResolver, path, resourceType);
    }

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType adaptTo(Class<AdapterType> toInstanceOfType) {
        AdapterType adaptedInstance = null;
        if (toInstanceOfType.isAssignableFrom(ValueMap.class)) {
            adaptedInstance = (AdapterType) new ValueMapDecorator(this.syntheticMap);
        }
        return adaptedInstance;
    }

    public void clear() {
        syntheticMap.clear();
    }

    public boolean containsKey(Object key) {
        return syntheticMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return syntheticMap.containsValue(value);
    }

    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return syntheticMap.entrySet();
    }

    public Object get(Object key) {
        return syntheticMap.get(key);
    }

    public boolean isEmpty() {
        return syntheticMap.isEmpty();
    }

    public Set<String> keySet() {
        return syntheticMap.keySet();
    }

    public Object put(String key, Object value) {
        return syntheticMap.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        syntheticMap.putAll(m);
    }

    public Object remove(Object key) {
        return syntheticMap.remove(key);
    }

    public int size() {
        return syntheticMap.size();
    }

    public Collection<Object> values() {
        return syntheticMap.values();
    }

}
