import org.apache.sling.commons.json.io.JSONWriter;
import org.gatherdata.alert.core.model.ActionPlan;

response.setContentType("application/json"); 
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' on ${resource}" 
log.info "gonna represent as JSON"

ActionPlan actionPlan = (ActionPlan)resource;

// create the JSON
def json = new JSONWriter(out) 

json.object();
    json.key("uid").value(actionPlan.getUid());
	json.key("dateCreated").value(actionPlan.getDateCreated());
	json.key("name").value(actionPlan.getName());
	json.key("description").value(actionPlan.getDescription());
json.endObject();