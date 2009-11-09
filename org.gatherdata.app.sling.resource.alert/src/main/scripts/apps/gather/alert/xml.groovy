import org.gatherdata.alert.core.model.ActionPlan;

response.setContentType("text/xml");
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' on ${resource}"
log.info "gonna represent ActionPlan as XML"

ActionPlan actionPlan = (ActionPlan)resource;

// create the XML
out.println("<?xml version='1.0' encoding='utf-8'?>");

def mb = new groovy.xml.MarkupBuilder(out) 
mb.archive() {
    uid(actionPlan.getUid())
    dateCreated(actionPlan.getDateCreated())
    name(actionPlan.getName())
    description(actionPlan.getDescription())
}