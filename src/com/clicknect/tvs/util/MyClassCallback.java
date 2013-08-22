package com.clicknect.tvs.util;


import java.util.Vector;
import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONObject;

public class MyClassCallback implements MyCallback {
    
    public void onComplete(String[] values, Object state) {}
    
    public void onComplete(JSONObject json, Object state) {}

    public void onComplete(JSONArray jsonArray, Object state) {}

    public void onComplete(Vector xmlVector, Object state) {}
    
    public void onComplete() {}
    
    public void onException(Exception e, Object state) {}

}