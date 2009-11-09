package org.gatherdata.app.sling.resource.forms.internal;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.gatherdata.archiver.core.model.GatherArchive;
import org.gatherdata.forms.core.model.FormTemplate;

public class FormTemplateAdapterFactory implements AdapterFactory {
    
    public static final Log log = LogFactory.getLog(FormTemplateAdapterFactory.class);
    
    /**
     * ABKTODO: this should be part of FormTemplate (or somewhere else in the gather-forms feature)
     */
    public static final Pattern FORM_NAMESPACE_PATTERN = Pattern.compile("http://([\\S&&[^/]]+)/([\\S&&[^/]]+)/([\\S&&[^/]]+)");

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object fromInstance, Class<AdapterType> toInstanceOfType) {
        AdapterType adaptedInstance = null;

        if (fromInstance instanceof FormTemplate) {
            FormTemplate formInstance = (FormTemplate)fromInstance;
            if (toInstanceOfType.isAssignableFrom(ValueMap.class)) {
                Map<String, Object> objectMap = new HashMap<String, Object>();
                URI formUid = formInstance.getUid();
                objectMap.put("uid", formUid);
                String path = formUid.getPath();
                objectMap.put("path", formUid.getPath());
                
                String application = "unknown";
                String name = "unnamed";
                Matcher namespaceMatcher = FORM_NAMESPACE_PATTERN.matcher(formUid.toASCIIString());
                if (namespaceMatcher.matches()) {
                    application = namespaceMatcher.group(2);
                    name = namespaceMatcher.group(3);
                }
                objectMap.put("application", application);
                objectMap.put("name", name);
                
                // direct property conversions
                objectMap.put("dateCreated", formInstance.getDateCreated());
                objectMap.put("dateModified", formInstance.getDateModified());
                objectMap.put("style", formInstance.getFormStyle());
                objectMap.put("type", formInstance.getFormType());
                objectMap.put("title", StringUtils.capitalize(application) + 
                        " - " + StringUtils.capitalize(name)); // ABKTODO: consider adding getTitle() or extracting from form
                
                adaptedInstance = (AdapterType) new ValueMapDecorator(objectMap);
            } else if (toInstanceOfType.isAssignableFrom(GatherArchive.class)) {
                adaptedInstance = (AdapterType)fromInstance;
            }
        }
        return adaptedInstance;
    }

}
