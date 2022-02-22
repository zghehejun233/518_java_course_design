package org.fatmansoft.teach.util;


import org.json.JSONObject;

import java.util.Map;

public class UimsUtil {
    private static UimsUtil instance = new UimsUtil();
    private Map root = null;
    public static UimsUtil getInstance(){
        return instance;
    }

    public Map getRoot() {
        return root;
    }

    public void setRoot(Map root) {
        this.root = root;
    }
}
