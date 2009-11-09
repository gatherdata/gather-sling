package org.gatherdata.app.sling.resource.alert.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.gatherdata.alert.core.model.ActionPlan;
import org.gatherdata.alert.core.model.LanguageScript;
import org.gatherdata.alert.core.model.PlannedNotification;
import org.gatherdata.alert.core.model.RuleSet;
import org.gatherdata.alert.core.model.impl.MutableActionPlan;
import org.gatherdata.alert.core.model.impl.MutableLanguageScript;
import org.gatherdata.alert.core.model.impl.MutablePlannedNotification;
import org.gatherdata.alert.core.model.impl.MutableRuleSet;
import org.gatherdata.commons.model.impl.MutableEntity;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ActionPlanAdapterFactory implements AdapterFactory {
    
    public static final Log log = LogFactory.getLog(ActionPlanAdapterFactory.class);
    
    private static final String UID_KEY = "uid";

    private static final String DATE_CREATED_KEY = "dateCreated";

    private static final String PLAN_NAME_KEY = "name";

    private static final String PLAN_DESCRIPTION_KEY = "description";

    private static final String RULE_UID_KEY = "ruleUid";

    private static final String RULE_LANGUAGE_KEY = "ruleLanguage";

    private static final String RULE_SCRIPT_KEY = "ruleScript";

    private static final String NOTICE_UID_KEY = "noticeUid";

    private static final String NOTICE_DESTINATION_KEY = "noticeDestination";

    private static final String NOTICE_LANGUAGE_KEY = "noticeLanguage";

    private static final String NOTICE_TEMPLATE_KEY = "noticeTemplate";

    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.basicDate();

    @SuppressWarnings("unchecked")
    public <AdapterType> AdapterType getAdapter(Object fromInstance, Class<AdapterType> toInstanceOfType) {
        log.debug("adapting " + fromInstance + " to type " + toInstanceOfType);
        AdapterType adaptedInstance = null;
        
        if (fromInstance instanceof ActionPlan) {
            ActionPlan modelInstance = (ActionPlan)fromInstance;
            if (toInstanceOfType.isAssignableFrom(ValueMap.class)) {                
                adaptedInstance = (AdapterType) createMapFromActionPlan(modelInstance);
            } else if (toInstanceOfType.isAssignableFrom(ActionPlan.class)) {
                adaptedInstance = (AdapterType)fromInstance;
            }
        } else if (fromInstance instanceof Map) {
            Map planMap = (Map)fromInstance;
            if (toInstanceOfType.isAssignableFrom(ActionPlan.class)) {
                adaptedInstance = (AdapterType) createPlanFromMap(planMap);
            }            
        }
        return adaptedInstance;
    }

    private ActionPlan createPlanFromMap(Map<String, String> planMap) {
        MutableActionPlan recreatedPlan = new MutableActionPlan();
        
        if (planMap.containsKey(UID_KEY)) {
            String uidFromMap = planMap.get(UID_KEY);
            try {
                recreatedPlan.setUid(new URI(uidFromMap));
            } catch (URISyntaxException e) {
                log.warn("failed to parse UID: " + uidFromMap);
            }
        }
        if (planMap.containsKey(DATE_CREATED_KEY)) {
            recreatedPlan.setDateCreated(dateTimeFormatter.parseDateTime(planMap.get(DATE_CREATED_KEY)));
        }
        if (planMap.containsKey(PLAN_NAME_KEY)) {
            recreatedPlan.setName(planMap.get(PLAN_NAME_KEY));
        }
        if (planMap.containsKey(PLAN_DESCRIPTION_KEY)) {
            recreatedPlan.setDescription(planMap.get(PLAN_DESCRIPTION_KEY));
        }
        
        MutableRuleSet ruleset = new MutableRuleSet();
        MutableLanguageScript ruleScript = new MutableLanguageScript();
        boolean scriptChanged = false;
        if (planMap.containsKey(RULE_UID_KEY)) {
            String ruleuidFromMap = planMap.get(RULE_UID_KEY);
            try {
                ruleScript.setUid(new URI(ruleuidFromMap));
            } catch (URISyntaxException e) {
                log.warn("failed to parse rule UID: " + ruleuidFromMap);
            }
        }
        if (planMap.containsKey(RULE_LANGUAGE_KEY)) {
            ruleScript.setLanguage(planMap.get(RULE_LANGUAGE_KEY));
            scriptChanged = true;
        }
        if (planMap.containsKey(RULE_SCRIPT_KEY)) {
            ruleScript.setScript(planMap.get(RULE_SCRIPT_KEY));
            scriptChanged = true;
        }
        if (scriptChanged) {
            ruleset.add(ruleScript);
            recreatedPlan.setRuleSet(ruleset);
        }
        
        MutablePlannedNotification plannedNotification = new MutablePlannedNotification();
        boolean notificationChanged = false;
        if (planMap.containsKey(NOTICE_UID_KEY)) {
            String noticeUidFromMap = planMap.get(NOTICE_UID_KEY);
            try {
                plannedNotification.setUid(new URI(noticeUidFromMap));
            } catch (URISyntaxException e) {
                log.warn("failed to notice UID: " + noticeUidFromMap);
            }
        }
        if (planMap.containsKey(NOTICE_DESTINATION_KEY)) {
            String destinationFromMap = planMap.get(NOTICE_DESTINATION_KEY);
            try {
                plannedNotification.setDestination(new URI(destinationFromMap));
                notificationChanged = true;
            } catch (URISyntaxException e) {
                log.warn("failed to destination URL: " + destinationFromMap);
            }
        }
        MutableLanguageScript noticeTemplate = new MutableLanguageScript();
        boolean templateChanged = false;
        if (planMap.containsKey(NOTICE_LANGUAGE_KEY)) {
            noticeTemplate.setLanguage(planMap.get(NOTICE_LANGUAGE_KEY));
            templateChanged = true;
        }
        if (planMap.containsKey(NOTICE_TEMPLATE_KEY)) {
            noticeTemplate.setScript(planMap.get(NOTICE_TEMPLATE_KEY));
            templateChanged = true;
        }
        if (templateChanged) {
            plannedNotification.setTemplate(noticeTemplate);
            notificationChanged = true;
        }        
        if (notificationChanged) recreatedPlan.add(plannedNotification);
        
        return recreatedPlan;
    }

    private ValueMap createMapFromActionPlan(ActionPlan modelInstance) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put(UID_KEY, modelInstance.getUid());
        objectMap.put(DATE_CREATED_KEY, modelInstance.getDateCreated());
        objectMap.put(PLAN_NAME_KEY, modelInstance.getName());
        objectMap.put(PLAN_DESCRIPTION_KEY, modelInstance.getDescription());
        
        RuleSet rules = modelInstance.getRuleSet();
        // only get the first script
        LanguageScript script = null;
        if (rules.getPredicateCount() > 0) {
            script = rules.getPredicates().iterator().next();
            objectMap.put(RULE_UID_KEY, script.getUid());
            objectMap.put(RULE_LANGUAGE_KEY, script.getLanguage());
            objectMap.put(RULE_SCRIPT_KEY, script.getScript());
        } else {
            objectMap.put(RULE_LANGUAGE_KEY, "");
            objectMap.put(RULE_SCRIPT_KEY, "");
        }        
        
        Iterator<? extends PlannedNotification> notifications = modelInstance.getNotifications().iterator();
        if (notifications.hasNext()) {
            PlannedNotification notification = notifications.next();
            objectMap.put(NOTICE_DESTINATION_KEY, notification.getDestination());
            objectMap.put(NOTICE_UID_KEY, notification.getUid());
            LanguageScript template = notification.getTemplate();
            objectMap.put(NOTICE_LANGUAGE_KEY, template.getLanguage());
            objectMap.put(NOTICE_TEMPLATE_KEY, template.getScript());
        } else {
            objectMap.put(NOTICE_DESTINATION_KEY, "");
            objectMap.put(NOTICE_LANGUAGE_KEY, "");
            objectMap.put(NOTICE_TEMPLATE_KEY, "");

        }
                
        return new ValueMapDecorator(objectMap);
    }

}
