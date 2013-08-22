package com.clicknect.tvs.util;


import java.util.Vector;
import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONObject;

public interface MyCallback {
    
    public void onComplete(String[] values, java.lang.Object state);
    
    public void onComplete(JSONObject json, java.lang.Object state);

    public void onComplete(JSONArray jsonArray, java.lang.Object state);
    
    public void onComplete(Vector xmlVector, java.lang.Object state);
    
    public void onComplete();

    public void onException(Exception e, java.lang.Object state);
    
}