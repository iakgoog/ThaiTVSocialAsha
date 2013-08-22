/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.json.JSONObject;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class TVListData {
    static final String DEFAULT_VALUE = "";
    private int number;
    String id, channel, title, detail, date, time, likeStatus, likeNum, checkinStatus, checkinNum, rateStatus, rateNum, type, create, refid;
    String image_url, iconURL;

    public boolean isCheckedin() {
        return checkinStatus.equalsIgnoreCase("OK");
    }

    public boolean isRated() {
        return rateStatus.equalsIgnoreCase("OK");
    }

    public boolean isLiked() {
        if(likeStatus==null) {
            return false;
        } else {
            return likeStatus.equalsIgnoreCase("OK");
        }
    }
    
    public void setNumber(int i) {
        this.number = i;
    }
    
    public int getNumber() {
        return number;
    }

    public TVListData(JSONObject obj){
        id = obj.optString("id", DEFAULT_VALUE);
        channel = obj.optString("chanel", DEFAULT_VALUE);
        title = obj.optString("title", DEFAULT_VALUE);
        detail = obj.optString("detail", DEFAULT_VALUE);
        date = obj.optString("date", DEFAULT_VALUE);
        time = obj.optString("time", DEFAULT_VALUE);
        likeStatus = null;//obj.optString("like_status", DEFAULT_VALUE);
        likeNum = obj.optString("like_num", DEFAULT_VALUE);
        checkinStatus = obj.optString("checkin_status", DEFAULT_VALUE);
        checkinNum = obj.optString("checkin_num", DEFAULT_VALUE);
        rateStatus = obj.optString("rate_status", DEFAULT_VALUE);
        rateNum = obj.optString("rate_num", DEFAULT_VALUE);
        type = obj.optString("type", DEFAULT_VALUE);
        create = obj.optString("create", DEFAULT_VALUE);
        refid = obj.optString("refid", DEFAULT_VALUE);

        iconURL = obj.optString("image5", DEFAULT_VALUE);
        image_url = obj.optString("image6", DEFAULT_VALUE);

        
        System.out.println("id : "+id);
        System.out.println("channel : "+channel);
        System.out.println("title : "+title);
        System.out.println("detail : "+detail);
        System.out.println("date : "+date);
        System.out.println("time : "+time);
        System.out.println("likeNum : "+likeNum);
        System.out.println("checkinStatus : "+checkinStatus);
        System.out.println("checkinNum : "+checkinNum);
        System.out.println("rateStatus : "+rateStatus);
        System.out.println("rateNum : "+rateNum);
        System.out.println("type : "+type);
        System.out.println("create : "+create);
        System.out.println("refid : "+refid);
        System.out.println("iconURL : "+iconURL);
        System.out.println("image_url : "+image_url);
        
    }

}