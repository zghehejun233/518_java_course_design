package org.fatmansoft.teach.util;

import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.security.services.UserDetailsImpl;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class CommonMethod {
    public static DataResponse getReturnData(Object obj, String msg){
        Map data = new HashMap();
        data.put("data",obj);
        data.put("msg",msg);
        return new   DataResponse("0",data);
    }
    public static DataResponse getReturnMessage(String code, String msg){
        Map data = new HashMap();
        data.put("data",null);
        data.put("msg",msg);
        return new   DataResponse(code,data);
    }
    public static  DataResponse getReturnData(Object obj){
        return getReturnData(obj,null);
    }
    public static DataResponse getReturnMessageOK(String msg){
        return getReturnMessage("0", msg);
    }
    public static DataResponse getReturnMessageOK(){
        return getReturnMessage("0", null);
    }
    public static DataResponse getReturnMessageError(String msg){
        return getReturnMessage("1", msg);
    }

    public static Integer getUserId(){
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails != null)
            return userDetails.getId();
        else
            return null;
    }

    public static String getNextNum3( String num) {
        String str;
        String prefix;
        if(num.length() == 3) {
            str = num;
            prefix= "";
        }
        else {
            str = num.substring(num.length() - 3, num.length());
            prefix = num.substring(0,num.length() - 3);
        }
        int c;
        if(str.charAt(0)=='0') {
            if(str.charAt(1)=='0') {
                c = str.charAt(2)-'0';
            }else {
                c = (str.charAt(1)-'0')*10 + str.charAt(2)-'0';
            }
        }else {
            c = (str.charAt(0)-'0')*100  +(str.charAt(1)-'0')*10 + str.charAt(2)-'0';
        }
        c++;
        if(c < 10) {
            return prefix+"00" + c;
        }else if(c < 100) {
            return prefix+"0" + c;
        }else {
            return prefix+ c;
        }
    }
    public static String getNextNum4( String num) {
        String str;
        String prefix;
        if(num.length() == 4) {
            str = num;
            prefix= "";
        }
        else {
            str = num.substring(num.length() - 4, num.length());
            prefix = num.substring(0,num.length() - 4);
        }
        int c;
        if(str.charAt(0)=='0') {
            if (str.charAt(1) == '0') {
                if (str.charAt(2) == '0') {
                    c = str.charAt(3) - '0';
                } else {
                    c = (str.charAt(2) - '0') * 10 + str.charAt(3) - '0';
                }
            } else {
                c = (str.charAt(1) - '0') * 100 + (str.charAt(2) - '0') * 10 + str.charAt(3) - '0';
            }
        }else {
            c = (str.charAt(0) - '0') * 1000 + (str.charAt(1) - '0') * 100 + (str.charAt(2) - '0') * 10 + str.charAt(3) - '0';
        }
        c++;
        if(c < 10) {
            return prefix+"000" + c;
        }else if(c < 100) {
            return prefix+"00" + c;
        }else if(c < 1000){
            return prefix + "0" + c;
        }else {
            return prefix+ c;
        }
    }
    public static String[] getStrings(Map data,String key){
        Object obj = data.get(key);
        if(obj == null)
            return new String[]{};
        if(obj instanceof String[])
            return (String[])obj;
        return new String[]{};
    }

    public static String getString(Map data,String key){
        Object obj = data.get(key);
        if(obj == null)
            return "";
        if(obj instanceof String)
            return (String)obj;
        return obj.toString();
    }
    public static Boolean getBoolean(Map data,String key){
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
    public static List getList(Map data, String key){
        Object obj = data.get(key);
        if(obj == null)
            return new ArrayList();
        if(obj instanceof List)
            return (List)obj;
        else
            return new ArrayList();
    }
    public static Map getMap(Map data,String key){
        Object obj = data.get(key);
        if(obj == null)
            return new HashMap();
        if(obj instanceof Map)
            return (Map)obj;
        else
            return new HashMap();
    }

    public static Integer getInteger(Map data,String key) {
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
    public static Integer getInteger0(Map data,String key) {
        Object obj = data.get(key);
        if(obj == null)
            return 0;
        if(obj instanceof Integer)
            return (Integer)obj;
        String str = obj.toString();
        try {
            return new Integer(str);
        }catch(Exception e) {
            return 0;
        }
    }
    public static Long getLong(Map data,String key) {
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

    public static Double getDouble(Map data,String key) {
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
    public static Double getDouble0(Map data,String key) {
        Double d0 = new Double(0);
        Object obj = data.get(key);
        if(obj == null)
            return d0;
        if(obj instanceof Double)
            return (Double)obj;
        String str = obj.toString();
        try {
            return new Double(str);
        }catch(Exception e) {
            return d0;
        }
    }
    public static Date getDate(Map data, String key) {
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Date)
            return (Date)obj;
        String str = obj.toString();
        return DateTimeTool.formatDateTime(str,"yyyy-MM-dd");
    }
    public static Date getTime(Map data,String key) {
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Date)
            return (Date)obj;
        String str = obj.toString();
        return DateTimeTool.formatDateTime(str,"yyyy-MM-dd HH:mm:ss");
    }

}
