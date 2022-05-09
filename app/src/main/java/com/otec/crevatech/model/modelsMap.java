package com.otec.crevatech.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class modelsMap {

    @SerializedName(("message"))
    private Object message;

    public  Object getMessage() {
        return message;
    }
}
