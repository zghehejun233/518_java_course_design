package org.fatmansoft.teach.payload.request;

import java.util.*;

public class DataRequest {
    private Map data;

    public DataRequest() {
        data = new HashMap();
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public void add(String key, Object obj){
        data.put(key,obj);
    }
    public Object get(String key){
        return data.get(key);
    }
    public String getString(String key){
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof String)
            return (String)obj;
        return obj.toString();
    }
    public Boolean getBoolean(String key){
        Object obj = data.get(key);
        if(obj == null)
            return false;
        if(obj instanceof Boolean)
            return (Boolean)obj;
        if("true".equals(obj.toString()))
            return true;
        else
            return false;
    }

    public List getList(String key){
        Object obj = data.get(key);
        if(obj == null)
            return new ArrayList();
        if(obj instanceof List)
            return (List)obj;
        else
            return new ArrayList();
    }
    public Map getMap(String key){
        Object obj = data.get(key);
        if(obj == null)
            return new HashMap();
        if(obj instanceof Map)
            return (Map)obj;
        else
            return new HashMap();
    }

    public Integer getInteger(String key) {
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Integer)
            return (Integer)obj;
        String str = obj.toString();
        try {
            return new Integer(str);
        }catch(Exception e) {
            return null;
        }
    }
    public Long getLong(String key) {
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Long)
            return (Long)obj;
        String str = obj.toString();
        try {
            return new Long(str);
        }catch(Exception e) {
            return null;
        }
    }

    public Double getDouble(String key) {
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Double)
            return (Double)obj;
        String str = obj.toString();
        try {
            return new Double(str);
        }catch(Exception e) {
            return null;
        }
    }
    public Date getDate(String key) {
        return null;
    }
    public Date getTime(String key) {
        return null;
    }

}
