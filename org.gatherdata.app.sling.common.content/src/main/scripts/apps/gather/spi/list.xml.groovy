response.setContentType("text/xml"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' an spi list for ${resource}"
log.info "node: ${currentNode}" 
requestParameterMap = request.getRequestParameterMap();
requestParameterMap.each { key, value ->
		log.info "${key}: ${value}"
}

adapter = sling.getService(org.apache.sling.api.adapter.AdapterManager.class);

currentValueMap = resource.adaptTo(org.apache.sling.api.resource.ValueMap.class);

log.info "valueMap: ${currentValueMap}"
serviceClassFromMap = currentValueMap['serviceClass']
log.info "service class from map: ${serviceClassFromMap}"

serviceClassFromNode = currentNode.getProperty('serviceClass').getString(); 
log.info "service class from node: ${serviceClassFromNode}"

serviceClass = serviceClassFromNode;

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
mb.xml() {
    rows() {
    	page(1)
    	total(1)
    	records(1)
    	
	    resultSet.eachWithIndex { resultEntity, i ->
            resultMap = adapter.getAdapter(resultEntity, org.apache.sling.api.resource.ValueMap.class);
            row(id:resultMap['uid']) {
                cell(resultMap['uid'])
                cell(resultMap['dateCreated'])
                //cell(resultMap['description'])
            }
            rowsCreated++;
        }
    }
}