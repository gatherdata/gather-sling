
URI createUriFrom(String uriString) {
    URI createdURI = null;
    try {
        createdURI = new URI(uriString)
    } catch (e) {
        log.error "Unparsable UID: ${uriString}"
    }
    return createdURI;
}

Map detectChanges(def xmlRequest) {
    changedValues = [:]
    
    oldValues = xmlRequest.oldValues[0]
    newValues = xmlRequest.data[0].value()
    
    log.info "old: ${oldValues}"
    log.info "new: ${newValues}"
    oldValues.each { oldValue ->
        newValue = newValues[oldValue.name()][0]
        log.info "old ${oldValue} -> new ${newValue}"
        if ((newValue.value() != oldValue.value()) || (oldValue.name() ==~ /.*[uU]id/)) {
            changedValues[oldValue.name()] = newValue.value().text()
        }
    }
    //changedValues['uid'] = oldValues['uid'].text()
    
    return changedValues
}

Map extractNewFields(def xmlRequest, String dataType) {
    newFields = [:]

    newValues = xmlRequest.data[dataType]
    
    log.info "new: ${newValues}"
    newValues[0].each { newValue ->
        newFields[newValue.name()] = newValue.value().text()
    }
    
    return newFields
}

def sendUpdateResponse(out, updatedRecord) {
    out.println("<?xml version='1.0' encoding='utf-8'?>");
        
    def mb = new groovy.xml.MarkupBuilder(out)
    mb.response() {
        status(0)
        startRow(0)
        endRow(1)
        totalRows(1)
        data() {   
            resultMap = adapter.getAdapter(updatedRecord, org.apache.sling.api.resource.ValueMap.class);
            record() {
                resultMap.each { key, value ->
                    mb.yieldUnescaped "<${key}>"
                    mb.yield "${value}"
                    mb.yieldUnescaped "</${key}>"
                }
            }
        }
    }
}

def sendRemoveResponse(out, removedUid) {
    out.println("<?xml version='1.0' encoding='utf-8'?>");
        
    def mb = new groovy.xml.MarkupBuilder(out)
    mb.response() {
        status(0)
        startRow(0)
        endRow(1)
        totalRows(1)
        data() {   
            record() {
                uid(removedUid)
            }
        }
    }
}

response.setContentType("text/xml"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' a resource api operation on ${resource}"
requestParameterMap = request.getRequestParameterMap();
requestParameterMap.each { key, value ->
		log.info "${key}: ${value}"
}

adapter = sling.getService(org.apache.sling.api.adapter.AdapterManager.class);

resourceValueMap = resource.adaptTo(org.apache.sling.api.resource.ValueMap.class);

log.info "valueMap: ${resourceValueMap}"
serviceClassname = resourceValueMap['serviceClass']
log.info "service class: ${serviceClassname}"
entityClassname = resourceValueMap['entityClass']
log.info "entity class: ${entityClassname}"

foundService = null;

if (serviceClassname) {
    foundService = sling.getService(Class.forName(serviceClassname));
} 

log.info "found service: ${foundService}"

if (foundService) {
    // parse the XML request
    def xmlRequest = new XmlParser().parse(reader)
    
    log.info "API request: ${xmlRequest}"
    
    operationType = xmlRequest.operationType.text()
    dataSource = xmlRequest.dataSource.text()
    entityUid = xmlRequest.data[dataSource].uid.text()
    entityClass = Class.forName(entityClassname);
    
    log.info "nodes: ${operationType} ${entityUid} from ${dataSource}"

    // perform the operation
    entityUidAsUri = createUriFrom(entityUid);
    
    if (entityUidAsUri != null) {
        switch (operationType) {
            case "remove":
                log.info "removing..."
                foundService.remove(entityUidAsUri)
                sendRemoveResponse out, entityUid
                break;
            case "update":
                log.info "updating..."
                changedFields = detectChanges(xmlRequest)
                log.info "changed fields: ${changedFields}" 
                updatedEntity = adapter.getAdapter(changedFields, entityClass)
                log.info "updated entity: ${updatedEntity}" 
                foundService.update(updatedEntity)
                retrievedEntity = foundService.get(entityUidAsUri);
                sendUpdateResponse out, retrievedEntity
                break;
            case "add":
                addedFields = extractNewFields(xmlRequest, dataSource)
                log.info "fields for new instance: ${addedFields}"
                newEntity = adapter.getAdapter(addedFields, entityClass)
                log.info "new instance: ${newEntity}"
                foundService.save(newEntity);
                break;
            default:
                log.warn "unsupported API operation: ${operationType}"
        }
    }
}
