package org.gatherdata.app.sling.gwt.alert.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

public class GatherArchiveOverlay extends JavaScriptObject {

    protected GatherArchiveOverlay() { }
    
    public final native String getUid() /*-{ return this.uid; }-*/;
    public final native String getDateCreated()  /*-{ return this.dateCreated; }-*/;
    public final native String getContent()  /*-{ return this.content; }-*/;
    
}
