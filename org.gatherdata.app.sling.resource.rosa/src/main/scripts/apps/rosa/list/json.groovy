import org.apache.sling.commons.json.io.JSONWriter;

response.setContentType("application/json"); 
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

// create the JSON
def json = new JSONWriter(out) 
rowsCreated = 0;

json.object();
	json.key("page").value(requestedPage);
	json.key("total").value(totalPages);
	json.key("records").value(totalRecords);
	json.key("rows")
		.array();
			// eachWithIndex is wasteful because it iterates over the entire list
			// but, it is convenient. optimize later
			children.eachWithIndex { child, i ->
				if ((i >= startingIndex) && (rowsCreated < requestedRows)) {
					childMap = child.adaptTo(org.apache.sling.api.resource.ValueMap.class);
					json.object()
						.key("id").value(childMap['id'])
						.key("cell").array()
							.value(childMap['id']) 
							.value(childMap['invdate'])
							.value(childMap['name'])
							.value(childMap['amount'])
							.value(childMap['tax'])
							.value(childMap['total'])
							.value(childMap['note'])
							.endArray()
						.endObject();
					rowsCreated++;
				}
			}
	json.endArray();
json.endObject();