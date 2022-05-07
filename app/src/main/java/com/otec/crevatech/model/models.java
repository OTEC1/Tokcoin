package com.otec.crevatech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class models {

    @SerializedName(("message"))
    private List<Map<String,Object>> message;

    public List<Map<String, Object>> getMessage() {
        return message;
    }
}
