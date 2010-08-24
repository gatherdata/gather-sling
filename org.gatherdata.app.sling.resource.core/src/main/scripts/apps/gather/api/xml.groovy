response.setContentType("text/xml"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' an api operation on ${resource}"
requestParameterMap = request.getRequestParameterMap();
requestParameterMap.each { key, value ->
		log.info "${key}: ${value}"
}

adapter = sling.getService(org.apache.sling.api.adapter.AdapterManager.class);

resourceValueMap = resource.adaptTo(org.apache.sling.api.resource.ValueMap.class);

log.info "valueMap: ${resourceValueMap}"
serviceClass = resourceValueMap['serviceClass']
log.info "service class from map: ${serviceClass}"
entityClass = resourceValueMap['entityClass']
log.info "entity class from map: ${entityClass}"
operation = "fetch"

foundService = null;

if (serviceClass) {
    foundService = sling.getService(Class.forName(serviceClass));
} 

log.info "found service: ${foundService}"

resultSet = [];

if (foundService) {
    resultSet = foundService.getAll();
}

// create the XML
out.println("<?xml version='1.0' encoding='utf-8'?>");

def mb = new groovy.xml.MarkupBuilder(out) 
rowsCreated = 0;
mb.response() {
    status(0)
    startRow(0)
    data() {    	
	    resultSet.eachWithIndex { resultEntity, i ->
        try {
            resultMap = adapter.getAdapter(resultEntity, org.apache.sling.api.resource.ValueMap.class);
            record() {
                resultMap.each { key, value ->
                   mkp.yieldUnescaped "<${key}>"
                   mkp.yield "${value}"
                   mkp.yieldUnescaped "</${key}>"
                }
            }
            rowsCreated++;
          } catch (Exception e) {
            log.error("skipping bad record, which caused: " + e)
          }
        }
    }
    endRow(rowsCreated)
    totalRows(rowsCreated)
}

log.info "sending ${rowsCreated} records"
