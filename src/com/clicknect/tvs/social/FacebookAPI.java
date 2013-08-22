/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.social;

import com.clicknect.facebook.Facebook;
import com.clicknect.tvs.TVSMIDlet;
import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.ui.Main;
import com.clicknect.tvs.util.DeviceInfo;
import com.clicknect.tvs.util.HttpClient;
import com.clicknect.tvs.util.MyCallback;
import com.clicknect.tvs.util.MyClassCallback;
import com.clicknect.tvs.util.MyConstant;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class FacebookAPI {
    
    private static FacebookAPI instance;
    
    private static final String BASE_URL = "http://socialshare.ccnconsole.info/services/v2/";
    private static final String GET_SERVICE_URL = BASE_URL + "getservices/";
    private static final String REGISTER_URL = BASE_URL + "register/?rt=dialog&rttype=xml";
    private static final String CONFIG_ID = "45376";
    private static final String FACEBOOK_SERVICE_ID = "facebook";
    
    private static final String GET_TV_LIST_URL = "http://tv.clicknect.com/tvsocialapis/getlist/";
    private static final String TV_CHECKIN_URL  = "http://tv.clicknect.com/tvsocialapis/checkin/";
    private static final String TV_RATE_URL     = "http://tv.clicknect.com/tvsocialapis/rate/";
    private static final String FB_USER = "http://tv.clicknect.com/tvsocialapis/fbuser/";
    private static final String FB_COMMENT_URL  = "http://tv.clicknect.com/tvsocialapis/fbcomments/";
    private static final String FB_LIKE_URL     = "http://tv.clicknect.com/tvsocialapis/fblike/";
    private static final String FB_UNREGISTER     = "http://tv.clicknect.com/tvsocialapis/unregister/";

    private final String imei = DeviceInfo.getIMEI(); //test device IMEI = 359330040023150 004402133710006
    //private final String imei = "004402133710006";
    private String facebookToken = "";
    private boolean tokenIsAlive = false;
    
    private String facebookID;
    private JSONObject fbuser;

    public FacebookAPI() {
        instance = this;
    }
    
    public static FacebookAPI getInstance(){
        return instance;
    }
    
    public String getServiceURL() {
        return GET_SERVICE_URL;
    }
    
    public String getRegisterURL() {
        return REGISTER_URL;
    }
    
    public String getIMEI() {
        return imei;
    }
    
    public String getFacebookToken() {
        return facebookToken;
    }
    
    public boolean isTokenAlive() {
        return tokenIsAlive;
    }
    
    public void setFacebookID(String fbid){
        facebookID = fbid;
    }
    
    public void getAccessTokenForFacebook(final MyCallback listener){
        try {
            JSONObject sc = new JSONObject();
            sc.put("im", imei);
            sc.put("confid", CONFIG_ID);

            Hashtable params = new Hashtable();
            params.put("rttype", "json");
            params.put("sc", sc.toString());
            params.put("extends", "token");
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);

            String writeURL = HttpClient.manageURL(GET_SERVICE_URL, params);
            
            /*
            Hashtable params = new Hashtable();
            params.put("imei", imei);
            params.put("app", "tsnFacebook");

            String writeURL = HttpClient.manageURL("http://clicknect.com/tsn/facebookgetservices/", params);
            */
            /*-----------------------------------------------------------------*/
            
            System.out.println("FacebookAPI.getAccessTokenForFacebook URL >> " + writeURL);
            //StringBuffer buffer = HttpClient.doGet(writeURL);
            HttpClient.doGet(writeURL, Main.getLocal().getMessage("progress_check_login"), true, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("getAccessTokenForFacebook.doGet response = "+response);
                        StringBuffer buf = new StringBuffer(response);
                        buf = buf.deleteCharAt(0);
                        buf = buf.deleteCharAt(buf.length() - 1);
                        JSONObject json = new JSONObject(buf.toString());
                        if(json.length()>0) {
                            if(json.has("token")) {
                                tokenIsAlive = true;
                                facebookToken = json.getString("token");
                                System.out.println("<<<< facebook token : " + facebookToken + " >>>>");
                                //Log.l.log("FB has token >>"+token, "");
                                listener.onComplete(new String[] {facebookToken}, null);
                            } else {
                                System.out.println("<<<< facebook has no token >>>>");
                                listener.onComplete(new String[] {}, null);
                            }
                        } else {
                            System.out.println("<<<< json.length = 0 >>>>");
                            facebookToken = "";
                            listener.onException(new Exception() {
                                public String toString(){
                                    return "Exception in getServicesAvailable() json object length=0";
                                }
                            } , null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onException(e , null);
                    }
                }
                
                public void onException(Exception e, Object state) {
                    listener.onException(e , null);
                }
            });
            
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
    public void registerForFacebook(MIDlet midlet) {
        try {
            JSONObject sc = new JSONObject();
            sc.put("im", imei);
            sc.put("confid", CONFIG_ID);
            sc.put("service", FACEBOOK_SERVICE_ID);
            String postURL = REGISTER_URL + "&sc=" + sc.toString()+"&url_success=http://socialshare.ccnconsole.info/nokia/reg/success/&url_error=http://socialshare.ccnconsole.info/nokia/reg/error/";
            System.out.println("registerForFacebookURL >>>> " + postURL);
            midlet.platformRequest(HttpClient.urlEncode(postURL));
            //midlet.platformRequest(postURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void FBUnregister(final Facebook fb, final MyCallback listener){
        try {
            System.out.println("fb.getAccessToken() ==== "+fb.getAccessToken());
            Hashtable params = new Hashtable();
            params.put("token", fb.getAccessToken());
            HttpClient.doPost(FB_UNREGISTER, Main.getLocal().getMessage("progress_logout"), true, params, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("FBUnregister response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e , null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e , null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
    public void FBUser(final String access_token, final MyCallback listener){
        try {
            System.out.println("FBUser started working");
            Hashtable params = new Hashtable();
            params.put("token", access_token);
            HttpClient.doPost(FB_USER, Main.getLocal().getMessage("progress_connect_to_facebook"), true, params, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("FBUser response >>>> "+response);
                        if(!response.equals("")) {
                            JSONObject fbuser = new JSONObject(response);
                            listener.onComplete(fbuser, null);
                        } else {
                            listener.onException(new Exception() {
                                public String toString(){
                                    return "Exception in FBUser() no response";
                                }
                            } , null);
                        }
                    } catch (Exception e) {
                        listener.onException(e, null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e, null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    };
    
    public void FBLike(final Facebook fb, final String fb_refid, final String msg, final boolean check_like, final MyCallback listener){
        try {
            Hashtable params = new Hashtable();
            params.put("refid", fb_refid);
            //params.put("userfb", fb.getCurrentUser().getId());
            params.put("token", fb.getAccessToken());
            if(!check_like) {
                params.put("like", "1");
            }
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);
            if(msg!=null && !msg.equals("")) {
                params.put("msg", msg);
            }
            HttpClient.doPost(FB_LIKE_URL, Main.getLocal().getMessage("progress_connect_to_facebook"), false, params, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("FBLike response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e, null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e, null);
                }
            });
            /*
            StringBuffer buffer = client.doPost(FB_LIKE_URL, params);
            String response = buffer.toString();
            JSONObject json = new JSONObject(response);
            listener.onComplete(json, null);
            */
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    };
    
    public void FBComment(final String fb_refid, boolean showProgress, final String msg, final MyCallback listener){
        try {
            Hashtable params = new Hashtable();
            params.put("refid", fb_refid);
            //params.put("userfb", fb.getCurrentUser().getId());
            //params.put("token", fb.getAccessToken());
            params.put("token", Main.getToken());
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);
            if(msg!=null && !msg.equals("")) {
                params.put("msg", msg);
            }
            Enumeration keysEnum = params.keys();
            System.out.println("FBComment >> "+FB_COMMENT_URL);
            while (keysEnum.hasMoreElements()) {
                String key = (String) keysEnum.nextElement();
                String val = (String) params.get(key);
                System.out.println("\""+key+"\" : \""+val+"\"");
            }
            System.out.println("FBComment >> ");
            HttpClient.doPost(FB_COMMENT_URL, Main.getLocal().getMessage("progress_connect_to_facebook"), showProgress, params, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("FBComment response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e , null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e , null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
    public void rate(final String programID, final int rate, final MyCallback listener){
        try {
            Hashtable params = new Hashtable();
            params.put("userfb", facebookID);
            params.put("tv_id", programID);
            params.put("rate", rate+"");
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);
            
            String writeURL = HttpClient.manageURL(TV_RATE_URL, params);
            System.out.println("FacebookAPI.rate URL >> " + writeURL);
            
            HttpClient.doGet(writeURL, Main.getLocal().getMessage("progress_connect_to_facebook"), true, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("Rate response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e , null);
                    }
                }
                public void onException(Exception e, Object state) {
                    listener.onException(e , null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
    public void checkin(final Facebook fb, final String programID, final String msg, final MyCallback listener){
        try {
            Hashtable params = new Hashtable();
            params.put("userfb", facebookID);
            params.put("tv_id", programID);
            params.put("token", fb.getAccessToken());
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);
            params.put("msg", msg);
            /*
            StringBuffer buffer = client.doPost(TV_CHECKIN_URL, params);
            String response = buffer.toString();
            JSONObject json = new JSONObject(response);
            listener.onComplete(json, null);
            */
            HttpClient.doPost(TV_CHECKIN_URL, Main.getLocal().getMessage("progress_connect_to_facebook"), true, params, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("checkin response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e, null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e , null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
    public void getTVList(final MyCallback listener){
        try {
            Hashtable params = new Hashtable();
            params.put("userfb", facebookID);
            params.put("agent", MyConstant.AGENT);
            params.put("appver", MyConstant.APP_VER);
            params.put("tm", new Date().getTime()+"");

            String writeURL = HttpClient.manageURL(GET_TV_LIST_URL, params);
            System.out.println("FacebookAPI.getTVList URL >> " + writeURL);
            HttpClient.doGet(writeURL, Main.getLocal().getMessage("progress_load_tvlist"), true, new MyClassCallback() {
                public void onComplete(String[] values, Object state) {
                    try {
                        String response = values[0];
                        System.out.println("getTVList response >>>> "+response);
                        JSONObject json = new JSONObject(response);
                        listener.onComplete(json, null);
                    } catch (Exception e) {
                        listener.onException(e, null);
                    }
                }

                public void onException(Exception e, Object state) {
                    listener.onException(e, null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            listener.onException(ex, null);
        }
    }
    
}