package org.gatherdata.app.sling.resource.archiver.internal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.gatherdata.archiver.core.model.GatherArchive;

public class GatherArchiveAdapterFactory implements AdapterFactory {
    
    public static final Log log = LogFactory.getLog(GatherArchiveAdapterFactory.class);

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object fromInstance, Class<AdapterType> toInstanceOfType) {
        AdapterType adaptedInstance = null;
        
        if (fromInstance instanceof GatherArchive) {
            GatherArchive archiveInstance = (GatherArchive)fromInstance;
            if (toInstanceOfType.isAssignableFrom(ValueMap.class)) {
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("uid", archiveInstance.getUid());
                objectMap.put("dateCreated", archiveInstance.getDateCreated());
                objectMap.put("content", archiveInstance.getContent().toString());
                
                Map<String, Object> metaMap = new HashMap<String, Object>();
                metaMap.putAll(archiveInstance.getMetadata());
                objectMap.put("meta", metaMap);
                
                adaptedInstance = (AdapterType) new ValueMapDecorator(objectMap);
            } else if (toInstanceOfType.isAssignableFrom(GatherArchive.class)) {
                adaptedInstance = (AdapterType)fromInstance;
            }
        }
        return adaptedInstance;
    }

}
