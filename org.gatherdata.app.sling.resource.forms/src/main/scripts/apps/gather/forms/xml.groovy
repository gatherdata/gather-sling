import org.gatherdata.archiver.core.model.GatherArchive;
import groovy.xml.MarkupBuilder 

response.setContentType("text/xml");
response.setCharacterEncoding("UTF-8"); 
 
// debug
log.info "Groovin' on ${resource}"
log.info "gonna represent as XML"

GatherArchive archive = (GatherArchive)resource;

// create the XML
out.println("<?xml version='1.0' encoding='utf-8'?>");

def mb = new groovy.xml.MarkupBuilder(out) 
mb.archive() {
    uid(archive.getUid())
    dateCreated(archive.getDateCreated())
    content('') {
        out.write("<![CDATA[${archive.getContent().toString()}]]>")
    }
    metadata() { 
        archive.getMetadata().each { metaKey, metaValue ->
            entry(key:metaKey, value:metaValue);
        }
    }
}