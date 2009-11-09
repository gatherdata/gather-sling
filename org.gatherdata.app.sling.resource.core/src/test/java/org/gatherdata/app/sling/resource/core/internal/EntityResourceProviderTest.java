package org.gatherdata.app.sling.resource.core.internal;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.gatherdata.app.sling.resource.core.EntityResourceProvider;
import org.gatherdata.commons.model.UniqueEntity;
import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.commons.spi.StorageService;
import org.junit.Test;

public class EntityResourceProviderTest {

    @Test
    public void shouldMatchCbidAsUidForArchives() {
        EntityResourceProvider mockProvider = new EntityResourceProvider("/gather/test/") {            
            @Override
            public Resource createMemberResource(ResourceResolver resourceResolver, String fullPath, String memberType,
                    String memberUid) {
                return null;
            }
            @Override
            public Class<? extends UniqueEntity> getEntityClass(String alias) {
                // TODO Auto-generated method stub
                return null;
            }
            @Override
            public Class<? extends StorageService<?>> getServiceClass() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        
        String testPath = "/gather/test/archive/" +
            CbidFactory.createCbid("foo" + new Random().nextDouble());
        
        assertTrue(testPath, mockProvider.getResourceMemberPattern().matcher(testPath).matches());
    }

}
