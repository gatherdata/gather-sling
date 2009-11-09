package org.gatherdata.app.sling.gwt.forms.client;


import com.google.gwt.core.client.JavaScriptObject;

public class FormTemplateOverlay extends JavaScriptObject {

    protected FormTemplateOverlay() { }
    
    public final native String getUid() /*-{ return this.uid; }-*/;
    public final native String getDateCreated()  /*-{ return this.dateCreated; }-*/;
    public final native String getDateModified()  /*-{ return this.dateModified; }-*/;
    public final native String getFormType()  /*-{ return this.Type; }-*/;
    public final native String getFormStyle()  /*-{ return this.style; }-*/;
    public final native String getName()    /*-{ return this.name; }-*/;
    public final native String getApplication()    /*-{ return this.application; }-*/;
    public final native String getTitle()    /*-{ return title; }-*/;
    
}
