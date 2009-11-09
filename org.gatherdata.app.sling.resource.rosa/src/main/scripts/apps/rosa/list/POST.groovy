response.setContentType("text/xml"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' a java rosa list on ${resource}"
requestParameterMap = request.getRequestParameterMap();
requestParameterMap.each { key, value ->
		log.info "${key}: ${value}"
}

log.info "Requested URL: ${request.getRequestURL()}"
log.info "Requested URI: ${request.getRequestURI()}"
log.info "Requested servlet: ${request.getServletPath()}"

String baseUrl = request.getRequestURL().toString()
baseUrl = baseUrl.minus(request.getRequestURI())
log.info "Base URL: ${baseUrl}"

adapter = sling.getService(org.apache.sling.api.adapter.AdapterManager.class);

resourceValueMap = resource.adaptTo(org.apache.sling.api.resource.ValueMap.class);

log.info "valueMap: ${resourceValueMap}"
serviceClass = resourceValueMap['serviceClass']
basePath = resourceValueMap['basePath']

foundService = null;

if (serviceClass) {
    foundService = sling.getService(Class.forName(serviceClass));
} 

log.info "found service: ${foundService}"

resultSet = [];

if (foundService) {
    resultSet = foundService.getAll();

    // create the XML
    out.println("<?xml version='1.0' encoding='utf-8'?>");

    def mb = new groovy.xml.MarkupBuilder(out) 
    mb.forms {
        resultSet.eachWithIndex { resultEntity, i ->
            formMap = adapter.getAdapter(resultEntity, org.apache.sling.api.resource.ValueMap.class);
//            log.info "form..."
//            formMap.each { key, value ->
//                log.info "\t${key} = ${value}"
//            }
            form(url:"${baseUrl}${basePath}forms${formMap.path}", formMap.title)
        }
    }
}
