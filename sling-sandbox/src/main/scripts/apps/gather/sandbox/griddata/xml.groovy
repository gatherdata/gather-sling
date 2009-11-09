response.setContentType("text/xml"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' on ${resource}" 
requestParameterMap = request.getRequestParameterMap();
requestParameterMap.each { key, value ->
		log.info "${key}: ${value}"
	} 

children = [];

requestedPage = 1;
startingIndex = 0;
shouldSearch = false;
orderByClause = "";

totalRecords = currentNode.nodes.size;
requestedRows = totalRecords;

if (requestParameterMap.size() != 0) {
	shouldSearch = requestParameterMap['_search'][0].string.toBoolean(); // presume non-null
	requestedPage = requestParameterMap['page'][0].string.toInteger(); // presume non-null
	requestedRows = requestParameterMap['rows'][0].string.toInteger(); // presume non-null
	requestedRowToSortBy = requestParameterMap['sidx'][0].string;
	requestedSortOrder = requestParameterMap['sord'][0].string;
	startingIndex = requestedPage*requestedRows - requestedRows;
	sortOrder = "descending";
	
	if (requestedSortOrder!=null) {
		if (requestedSortOrder == "asc") {
			sortOrder = "ascending";
		}
	}
	orderByClause = (requestedRowToSortBy!=null) ? " order by @${requestedRowToSortBy} ${sortOrder}" : "";
}

if (shouldSearch) {
	totalRecordsQueryResult = request.resourceResolver.queryResources("fn:count(/jcr:root"+resource.path+
		"/*[id='7'])", "xpath");
	log.info ("totalRecordsQueryResult: " + totalRecordsQueryResult)
	children = request.resourceResolver.findResources("/jcr:root"+resource.path+
		"/*[id='7']"+
		orderByClause, "xpath");
} else {	
	children = request.resourceResolver.findResources("/jcr:root"+resource.path+
		"/*"+
		orderByClause, "xpath");
}

totalPages = (int)((totalRecords == 0) ? 0 : Math.ceil(totalRecords/requestedRows));

// create the XML
out.println("<?xml version='1.0' encoding='utf-8'?>");

def mb = new groovy.xml.MarkupBuilder(out) 
rowsCreated = 0;
mb.rows() {
	page(requestedPage)
	total(totalPages)
	records(totalRecords)
	// eachWithIndex is wasteful because it iterates over the entire list
	// but, it is convenient. optimize later
	children.eachWithIndex { child, i ->
		if ((i >= startingIndex) && (rowsCreated < requestedRows)) {
			childMap = child.adaptTo(org.apache.sling.api.resource.ValueMap.class);
			row(id:childMap['id']) {
				cell(childMap['id']) 
				cell(childMap['invdate'])
				cell(childMap['name'])
				cell(childMap['amount'])
				cell(childMap['tax'])
				cell(childMap['total'])
				cell(childMap['note'])
			}
			rowsCreated++;
		}
	}
}