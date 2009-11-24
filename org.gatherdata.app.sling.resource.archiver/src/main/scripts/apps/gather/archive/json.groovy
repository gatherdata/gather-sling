import org.apache.sling.commons.json.io.JSONWriter;
import org.gatherdata.archiver.core.model.GatherArchive;

response.setContentType("application/json"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' on ${resource}" 
log.info "gonna represent as JSON"

GatherArchive archive = (GatherArchive)resource;

// create the JSON
def json = new JSONWriter(out) 

json.object();
    json.key("uid").value(archive.getUid());
	json.key("dateCreated").value(archive.getDateCreated());
	json.key("content").value(archive.getContent().toString());
	json.key("metadata")
		.array();
		archive.getMetadata().each { key, value ->
        	json.object();
			json.key(key).value(value);
			json.endObject();
		}
	json.endArray();
json.endObject();
