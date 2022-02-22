package org.fatmansoft.teach.payload.response;

import java.util.Map;

public class DataResponse {
    private String code;
    private Map data;

    public DataResponse(String code, Map data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
