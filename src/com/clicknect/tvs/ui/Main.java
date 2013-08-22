package com.clicknect.tvs.ui;

import com.clicknect.facebook.Facebook;
import com.clicknect.tvs.TVSMIDlet;
import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.social.FacebookAPI;
import com.clicknect.tvs.util.LocalizationSupport;
import com.clicknect.tvs.util.MyClassCallback;
import com.clicknect.tvs.util.MyConstant;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.io.CacheMap;
import com.sun.lwuit.io.NetworkManager;
import java.io.IOException;
import java.util.Vector;

public class Main {

    private static Main instance;
    private static TVSMIDlet midlet;
    private static LocalizationSupport local;
    private static int deviceScreenHeight = 0;
    private static int deviceScreenWidth = 0;
    private static AdsContainer[] adsContainer;
    private boolean isAdsLoad;
    private static String accessToken;
    private TVListData activeTVData;
    
    private NowShowingForm nowShowingForm;
    private DetailForm detailForm;
    private LoginForm loginForm;
    private RotationForm rotationForm;
    private Form loadingForm;
    
    private CacheMap imageCache = new CacheMap();
    private Vector fetchingUrls = new Vector();
    
    private Button buttonOK;
    
    private Facebook fb;
    private FacebookAPI facebookAPI;
    
    private MyDialog dialog;
    
    public static Main getInstance() {
        return instance;
    }
    
    public static TVSMIDlet getMIDlet() {
        return midlet;
    }
    
    public static LocalizationSupport getLocal() {
        return local;
    }
    
    public static void setScreenHeight(int screenH) {
        if(screenH <= 320) {
            deviceScreenHeight = MyConstant.DEVICE_SCREEN_HEIGHT;
        } else {
            deviceScreenHeight = screenH;
        }
    }
    
    public static void setScreenWidth(int screenW) {
        deviceScreenWidth = screenW;
    }
    
    public static int getScreenHeight() {
        return deviceScreenHeight;
    }
    
    public static int getScreenWidth() {
        return deviceScreenWidth;
    }
    
    public static AdsContainer getAds(int index) {
        return adsContainer[index];
    }
    
    public static String getToken() {
        return accessToken;
    }
    
    public void setActiveTVData(TVListData data) {
        this.activeTVData = data;
    }
    
    public TVListData getActiveTVData() {
        return this.activeTVData;
    }
    
    public void startApp(TVSMIDlet m) {
        instance = this;
        midlet = m;
        local = new LocalizationSupport();
        //dialog = new MyDialog();
        fb = Facebook.getInstance(midlet, null);
        
        NetworkManager.getInstance().start();
        
        loadingForm = new Form(getLocal().getMessage("loading"));
        loadingForm.show();
        
        dialog = new MyDialog();
        //Dialog.show("Title", "Body", "Ok Butoon", "Cancel Button");
        
        deviceScreenWidth = Display.getInstance().getDisplayWidth();
        deviceScreenHeight = Display.getInstance().getDisplayHeight();
        
        com.sun.lwuit.io.Storage.init("ThaiTVSocial");
        if(com.sun.lwuit.io.Storage.isInitialized()) {
            com.sun.lwuit.io.Storage.getInstance().writeObject("ThaiTVSocial", "[ ThaiTVSocial ] Storage has been initialized");
            String myStr = (String)com.sun.lwuit.io.Storage.getInstance().readObject("ThaiTVSocial");
            System.out.println(myStr);
        } else {
            System.out.println("Storage has NOT not been initialized!!!");
        }
        
        facebookAPI = new FacebookAPI();
        
        loginForm = new LoginForm(this);
        rotationForm = new RotationForm(this);
        
        buttonOK = new Button(new Command("ok") {
            public void actionPerformed(ActionEvent evt) {
                disposeDialog();
                createDetailForm(getActiveTVData());
            }
        });
        
        displayLoginForm();
    }
    
    public void pauseApp() {
    }

    public void destroyApp(boolean arg0) {
    }
    
    public void restartApp() {
   
    }
    
    public void cacheImage(String text, Object object) throws IOException {
        imageCache.put(text, object);
    }
    
    public Image getImage(String text) {
        return (Image) imageCache.get(text);
    }
    
    public void addFetchingURLs(String text) {
        fetchingUrls.addElement(text);
    }
    
    public void removeFetchingURLs(String text) {
        fetchingUrls.removeElement(text);
    }
    
    public boolean isFetchingURLContain(String text) {
        return fetchingUrls.contains(text);
    }
    
    public void showLoadingForm() {
        loadingForm.show();
    }
    
    public void showDialog(String title, String body, Button button, boolean complete) {
        try {
            dialog.showDialog(title, body, button, complete);
        } catch (Exception e) {
        }
    }
    
    public void disposeDialog() {
        dialog.doDispose();
    }
    
    public void displayLoginForm() {
        loadAds();
        loginForm.show();
    }
    
    public void loadAds() {
        if(!isAdsLoad) {
            MyProgress progress = new MyProgress(Main.getLocal().getMessage("progress_h"), Main.getLocal().getMessage("progress_b"), null);
            new LoadAdsThread(progress).start();
            progress.show();
        }
    }
    
    class LoadAdsThread extends Thread {
        
        MyProgress progress;

        public LoadAdsThread(MyProgress progress) {
            this.progress = progress;
        }
        
        public void run() {
            synchronized(LoadAdsThread.class) {
                adsContainer = new AdsContainer[3];
                adsContainer[0] = new AdsContainer();
                adsContainer[1] = new AdsContainer();
                adsContainer[2] = new AdsContainer();
                //adsContainer[3] = new AdsContainer();
                isAdsLoad = true;
                progress.dispose();
            }
        }
    }
    
//------------------------------------------------------------------------------//
    
    public static void showBrowser(String link) {
        try {
            Main.getMIDlet().platformRequest(link);
        } catch (Exception e) {
        }
    }
    
    public void showNowShowingForm() {
        nowShowingForm.show();
    }
    
    public void setRateNumInNowShowing(int num, String val) {
        nowShowingForm.setRateNum(num, val);
    }
    
    public void setCheckinNumInNowShowing(int num, String val) {
        nowShowingForm.setCheckinNum(num, val);
    }
    
    public void createDetailForm(TVListData data) {
        if(detailForm != null) {
            detailForm.removeComponents();
        }
        detailForm = null;
        detailForm = new DetailForm(instance, data);
    }
    
    public void showDetailForm() {
        detailForm.show();
    }
    
    public void setRateNumInDetail(String val) {
        detailForm.setRateNum(val);
    }
    
    public void setCheckinNumInDetail(String val) {
        detailForm.setCheckinNum(val);
    }
    
    public void setTVLists(Vector tvLists) {
        rotationForm.setTVLists(tvLists);
    }
    
    public void showGraph() {
        rotationForm.clearForm();
        rotationForm.showGraph();
    }
    
    public void showVote() {
        rotationForm.clearForm();
        rotationForm.showVote();
    }
    
    public void showCheckin(String msg) {
        rotationForm.clearForm();
        rotationForm.showCheckin(msg);
    }
    
    public void showComment() {
        rotationForm.clearForm();
        rotationForm.showComment();
    }
    
//------------------------------------ FACEBOOK FUNCTION -----------------------------------------
    public void checkFacebookLogin() {
        showLoadingForm();
        System.out.println("checkFacebookLogin() is triggered");
        /*
        if(detailsForm.isFBAttemptLogin()) {
            showLoadingForm(local.getMessage("loading"));
        }
        */
        facebookAPI.getAccessTokenForFacebook(new MyClassCallback() {
            public void onComplete(final String[] values, Object state) {
                try {
                    //Thread.sleep(250);
                    showLoadingForm();
                    System.out.println("checkFacebookLoginCallback value.length = "+values.length);
                    if(values.length==0 || values[0]=="null"){
                        facebookAPI.registerForFacebook(getMIDlet());
                    } else {
                        System.out.println("access token received >>>> " + values[0]);
                        facebookAPI.FBUser(values[0], new MyClassCallback(){
                            public void onComplete(JSONObject json, Object state) {
                                try {
                                    //Thread.sleep(250);
                                    Facebook fb = Facebook.getInstance(midlet, null);
                                    fb.setAccessToken(values[0]);
                                    accessToken = values[0];
                                    facebookAPI.setFacebookID(json.getString("id")); //json.getString("name")
                                    nowShowingForm = new NowShowingForm(instance);
                                } catch(Exception ex) {
                                    //ex.printStackTrace();
                                    System.out.println("FAIL to get FB USER, go to REGISTER");
                                    facebookAPI.registerForFacebook(getMIDlet());
                                }
                            }
                            
                            public void onException(Exception e, Object state) {
                                //e.printStackTrace();
                                System.out.println("FAIL to get FB USER, go to REGISTER");
                                facebookAPI.registerForFacebook(getMIDlet());
                            }
                        });
                        
                    }
                } catch (Exception e) {
                    
                }
            }

            public void onException(Exception e, Object state) {
                e.printStackTrace();
                facebookAPI.registerForFacebook(getMIDlet());
            }
        });
    }
    
    public void checkin(final String programID, final String msg) {
        facebookAPI.checkin(fb, programID, msg, new MyClassCallback() {
            public void onComplete(JSONObject json, Object state) {
                try {
                    //Thread.sleep(250);
                    setCheckinNumInNowShowing(rotationForm.getDataNumTemp(), rotationForm.getCheckinNumTemp());
                    setCheckinNumInDetail(rotationForm.getCheckinNumTemp());
                    Display.getInstance().getCurrent().repaint();
                    //createDetailForm(getActiveTVData());
                    //Thread.sleep(800);
                    showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("check_in_done"), buttonOK, false);
                } catch (Exception ex) {}
                /*
                new Thread() {
                    public void run() {
                        try {
                            sleep(200);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        //showDetailForm(); //Added 2013-07-26
                        createDetailForm(getActiveTVData());
                        showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("check_in_done"), false);
                    }
                }.start();
                */
            }

            public void onException(Exception e, Object state) {
                showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("check_in_fail"), null, false);
            }
        });
    }
    
    public void logout() {
        facebookAPI.FBUnregister(fb ,new MyClassCallback() {
            public void onComplete(JSONObject json, Object state) {
                try {
                    //Thread.sleep(250);
                } catch (Exception e) {
                }
                showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("logout_from_facebook"), null, false);
                midlet.notifyDestroyed();
            }

            public void onException(Exception e, Object state) {
                showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("logout_fail"), null, false);
            }
        });
    }
    
    public void comment(final String programID, final String msg) {
        facebookAPI.FBComment(programID, true, msg, new MyClassCallback() {
            public void onComplete(JSONObject json, Object state) {
                try {
                    //Thread.sleep(250);
                    Display.getInstance().getCurrent().repaint();
                    //createDetailForm(getActiveTVData());
                    //Thread.sleep(800);
                    showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("comment_done"), buttonOK, false);
                } catch (Exception ex) {}
                
                /*
                new Thread() {
                    public void run() {
                        try {
                            sleep(200);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        //showDetailForm(); //Added 2013-07-26
                        createDetailForm(getActiveTVData());
                        showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("comment_done"), false);
                    }
                }.start();
                */
            }

            public void onException(Exception e, Object state) {
                showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("comment_fail"), null, false);
            }
        });
    }
    
    public void rate(final String programID, final int rate) {
        facebookAPI.rate(programID, rate, new MyClassCallback() {
            public void onComplete(JSONObject json, Object state) {
                try {
                    //Thread.sleep(250);
                    setRateNumInNowShowing(rotationForm.getDataNumTemp(), rotationForm.getRateTemp());
                    setRateNumInDetail(rotationForm.getRateTemp());
                    Display.getInstance().getCurrent().repaint();
                    //createDetailForm(getActiveTVData());
                    //Thread.sleep(800);
                    showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("rate_done"), buttonOK, false);
                } catch (Exception ex) {}
            }

            public void onException(Exception e, Object state) {
                showDialog(Main.getLocal().getMessage("app_name"), Main.getLocal().getMessage("rate_fail"), null, false);
            }
        });
    }
    
    public void facebookButtonPressed() {
        /*
        if(!facebookAPI.isTokenAlive()) {
            if(detailsForm.isFBAttemptLogin()) {
                detailsForm.setFBAttemptLogin(false);
                facebookAPI.getAccessTokenForFacebook(checkFacebookBeforePulish);
            } else {
                detailsForm.setFBAttemptLogin(true);
                facebookAPI.facebookLoginBrowser(facebookLoginCallback, instance);
            }
        } else {
            publishToFacebook();
        }
        */
    }
    
    public void publishToFacebook() {
        //showLoadingForm(local.getMessage("sending"));
        //RSSItem rssItem = (RSSItem) horoItems.elementAt(itemIndex);
        //facebookAPI.publishToFacebook(facebookPublishCallback, news);
    }
    
    public void logoutFromFacebook() {
        //showLoadingForm(local.getMessage("logging_out"));
        //facebookAPI.logoutFromFacebook(facebookLogoutCallback);
    }
    
}