package com.clicknect.facebook;

import java.util.Enumeration;
import java.util.Hashtable;

//import org.w3c.dom.Document;

import com.clicknect.facebook.dao.FacebookAlbum;
import com.clicknect.facebook.dao.FacebookCheckin;
import com.clicknect.facebook.dao.FacebookComment;
import com.clicknect.facebook.dao.FacebookEducation;
import com.clicknect.facebook.dao.FacebookFriendList;
import com.clicknect.facebook.dao.FacebookInterest;
import com.clicknect.facebook.dao.FacebookLocation;
import com.clicknect.facebook.dao.FacebookPage;
import com.clicknect.facebook.dao.FacebookPhoto;
import com.clicknect.facebook.dao.FacebookPost;
import com.clicknect.facebook.dao.FacebookProfile;
import com.clicknect.facebook.dao.FacebookStatus;
import com.clicknect.facebook.dao.FacebookTag;
import com.clicknect.facebook.dao.FacebookUser;
import com.clicknect.facebook.dao.FacebookVideo;
import com.clicknect.facebook.dao.FacebookWork;
import com.clicknect.facebook.inf.Album;
import com.clicknect.facebook.inf.Checkin;
import com.clicknect.facebook.inf.Comment;
import com.clicknect.facebook.inf.Education;
import com.clicknect.facebook.inf.FriendList;
import com.clicknect.facebook.inf.Interest;
import com.clicknect.facebook.inf.Location;
import com.clicknect.facebook.inf.Page;
import com.clicknect.facebook.inf.Photo;
import com.clicknect.facebook.inf.Post;
import com.clicknect.facebook.inf.Profile;
import com.clicknect.facebook.inf.Status;
import com.clicknect.facebook.inf.Tag;
import com.clicknect.facebook.inf.User;
import com.clicknect.facebook.inf.Video;
import com.clicknect.facebook.inf.Work;
import com.clicknect.tvs.json.JSONException;
import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.json.JSONTokener;
import com.clicknect.tvs.util.HttpClient;
import javax.microedition.midlet.MIDlet;

public class Facebook {

    protected static final String GRAPH_URL = "https://graph.facebook.com";
    protected static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
    protected static final String LOADING = "Connecting to Facebook";
    //protected Logger log = Logger.getLogger(getClass());
    //protected HttpClient http;
    //protected ConnectionFactory cf;
    //protected LoggableConnectionFactory lcf;
    protected ApplicationSettings appSettings;
    protected String accessToken;
    protected Object ACCESS_TOKEN_LOCK = new Object();
    protected String id = "";
    protected String pwd = "";
    protected boolean autoMode = false;
    private MIDlet midlet;

    public static class Permissions {

        // FacebookUser Data Permissions
        public static final String USER_ABOUT_ME = "user_about_me";
        public static final String USER_ACTIVITIES = "user_activities";
        public static final String USER_BIRTHDAY = "user_birthday";
        public static final String USER_EDUCATION_HISTORY = "user_education_history";
        public static final String USER_EVENTS = "user_events";
        public static final String USER_GROUPS = "user_groups";
        public static final String USER_HOMETOWN = "user_hometown";
        public static final String USER_INTERESTS = "user_interests";
        public static final String USER_LIKES = "user_likes";
        public static final String USER_LOCATION = "user_location";
        public static final String USER_NOTES = "user_notes";
        public static final String USER_ONLINE_PRESENCE = "user_online_presence";
        public static final String USER_PHOTO_VIDEO_TAGS = "user_photo_video_tags";
        public static final String USER_PHOTOS = "user_photos";
        public static final String USER_RELATIONSHIPS = "user_relationships";
        public static final String USER_RELATIONSHIP_DETAILS = "user_relationship_details";
        public static final String USER_RELIGION_POLITICS = "user_religion_politics";
        public static final String USER_STATUS = "user_status";
        public static final String USER_VIDEOS = "user_videos";
        public static final String USER_WEBSITE = "user_website";
        public static final String USER_WORK_HISTORY = "user_work_history";
        public static final String EMAIL = "email";
        public static final String READ_FRIENDLISTS = "read_friendlists";
        public static final String READ_INSIGHTS = "read_insights";
        public static final String READ_MAILBOX = "read_mailbox";
        public static final String READ_REQUESTS = "read_requests";
        public static final String READ_STREAM = "read_stream";
        public static final String XMPP_LOGIN = "xmpp_login";
        public static final String ADS_MANAGEMENT = "ads_management";
        public static final String USER_CHECKINS = "user_checkins";
        // Friends Data Permissions
        public static final String FRIENDS_ABOUT_ME = "friends_about_me";
        public static final String FRIENDS_ACTIVITIES = "friends_activities";
        public static final String FRIENDS_BIRTHDAY = "friends_birthday";
        public static final String FRIENDS_EDUCATION_HISTORY = "friends_education_history";
        public static final String FRIENDS_EVENTS = "friends_events";
        public static final String FRIENDS_GROUPS = "friends_groups";
        public static final String FRIENDS_HOMETOWN = "friends_hometown";
        public static final String FRIENDS_INTERESTS = "friends_interests";
        public static final String FRIENDS_LIKES = "friends_likes";
        public static final String FRIENDS_LOCATION = "friends_location";
        public static final String FRIENDS_NOTES = "friends_notes";
        public static final String FRIENDS_ONLINE_PRESENCE = "friends_online_presence";
        public static final String FRIENDS_PHOTO_VIDEO_TAGS = "friends_photo_video_tags";
        public static final String FRIENDS_PHOTOS = "friends_photos";
        public static final String FRIENDS_RELATIONSHIPS = "friends_relationships";
        public static final String FRIENDS_RELATIONSHIP_DETAILS = "friends_relationship_details";
        public static final String FRIENDS_RELIGION_POLITICS = "friends_religion_politics";
        public static final String FRIENDS_STATUS = "friends_status";
        public static final String FRIENDS_VIDEOS = "friends_videos";
        public static final String FRIENDS_WEBSITE = "friends_website";
        public static final String FRIENDS_WORK_HISTORY = "friends_work_history";
        public static final String MANAGE_FRIENDLISTS = "manage_friendlists";
        public static final String FRIENDS_CHECKINS = "friends_checkins";
        // Publishing Permissions
        public static final String PUBLISH_STREAM = "publish_stream";
        public static final String CREATE_EVENT = "create_event";
        public static final String RSVP_EVENT = "rsvp_event";
        // public static final String SMS = "sms";
        public static final String OFFLINE_ACCESS = "offline_access";
        public static final String PUBLISH_CHECKINS = "publish_checkins";
        // Page Permissions
        public static final String MANAGE_PAGES = "manage_pages";
        // Some canned permissions bundles
        public static final String[] USER_DATA_PERMISSIONS = new String[]{USER_ABOUT_ME, USER_ACTIVITIES, USER_BIRTHDAY, USER_EDUCATION_HISTORY, USER_EVENTS, USER_GROUPS, USER_HOMETOWN, USER_INTERESTS, USER_LIKES, USER_LOCATION, USER_NOTES, USER_ONLINE_PRESENCE, USER_PHOTO_VIDEO_TAGS, USER_PHOTOS, USER_RELATIONSHIPS, USER_RELATIONSHIP_DETAILS, USER_RELIGION_POLITICS, USER_STATUS, USER_VIDEOS, USER_WEBSITE, USER_WORK_HISTORY, EMAIL, READ_FRIENDLISTS, READ_INSIGHTS, READ_MAILBOX, READ_REQUESTS, READ_STREAM, XMPP_LOGIN, ADS_MANAGEMENT, USER_CHECKINS};
        public static final String[] FRIENDS_DATA_PERMISSIONS = new String[]{FRIENDS_ABOUT_ME, FRIENDS_ACTIVITIES, FRIENDS_BIRTHDAY, FRIENDS_EDUCATION_HISTORY, FRIENDS_EVENTS, FRIENDS_GROUPS, FRIENDS_HOMETOWN, FRIENDS_INTERESTS, FRIENDS_LIKES, FRIENDS_LOCATION, FRIENDS_NOTES, FRIENDS_ONLINE_PRESENCE, FRIENDS_PHOTO_VIDEO_TAGS, FRIENDS_PHOTOS, FRIENDS_RELATIONSHIPS, FRIENDS_RELATIONSHIP_DETAILS, FRIENDS_RELIGION_POLITICS, FRIENDS_STATUS, FRIENDS_VIDEOS, FRIENDS_WEBSITE, FRIENDS_WORK_HISTORY, MANAGE_FRIENDLISTS, FRIENDS_CHECKINS};
        // public static final String[] PUBLISHING_PERMISSIONS = new String[] { PUBLISH_STREAM, CREATE_EVENT, RSVP_EVENT, SMS, OFFLINE_ACCESS, PUBLISH_CHECKINS };
        public static final String[] PUBLISHING_PERMISSIONS = new String[]{PUBLISH_STREAM, CREATE_EVENT, RSVP_EVENT, OFFLINE_ACCESS, PUBLISH_CHECKINS};
        public static final String[] PAGE_PERMISSIONS = new String[]{MANAGE_PAGES};
        // public static final String[] ALL_PERMISSIONS = new String[] { USER_ABOUT_ME, USER_ACTIVITIES, USER_BIRTHDAY, USER_EDUCATION_HISTORY, USER_EVENTS, USER_GROUPS, USER_HOMETOWN, USER_INTERESTS, USER_LIKES, USER_LOCATION, USER_NOTES, USER_ONLINE_PRESENCE, USER_PHOTO_VIDEO_TAGS, USER_PHOTOS, USER_RELATIONSHIPS, USER_RELATIONSHIP_DETAILS, USER_RELIGION_POLITICS, USER_STATUS, USER_VIDEOS, USER_WEBSITE, USER_WORK_HISTORY, EMAIL, READ_FRIENDLISTS, READ_INSIGHTS, READ_MAILBOX, READ_REQUESTS, READ_STREAM, XMPP_LOGIN, ADS_MANAGEMENT, USER_CHECKINS, FRIENDS_ABOUT_ME, FRIENDS_ACTIVITIES, FRIENDS_BIRTHDAY, FRIENDS_EDUCATION_HISTORY, FRIENDS_EVENTS, FRIENDS_GROUPS, FRIENDS_HOMETOWN, FRIENDS_INTERESTS, FRIENDS_LIKES, FRIENDS_LOCATION, FRIENDS_NOTES, FRIENDS_ONLINE_PRESENCE, FRIENDS_PHOTO_VIDEO_TAGS, FRIENDS_PHOTOS, FRIENDS_RELATIONSHIPS, FRIENDS_RELATIONSHIP_DETAILS, FRIENDS_RELIGION_POLITICS, FRIENDS_STATUS, FRIENDS_VIDEOS, FRIENDS_WEBSITE, FRIENDS_WORK_HISTORY, MANAGE_FRIENDLISTS, FRIENDS_CHECKINS, PUBLISH_STREAM, CREATE_EVENT, RSVP_EVENT, SMS, OFFLINE_ACCESS, PUBLISH_CHECKINS, MANAGE_PAGES };
        public static final String[] ALL_PERMISSIONS = new String[]{USER_ABOUT_ME, USER_ACTIVITIES, USER_BIRTHDAY, USER_EDUCATION_HISTORY, USER_EVENTS, USER_GROUPS, USER_HOMETOWN, USER_INTERESTS, USER_LIKES, USER_LOCATION, USER_NOTES, USER_ONLINE_PRESENCE, USER_PHOTO_VIDEO_TAGS, USER_PHOTOS, USER_RELATIONSHIPS, USER_RELATIONSHIP_DETAILS, USER_RELIGION_POLITICS, USER_STATUS, USER_VIDEOS, USER_WEBSITE, USER_WORK_HISTORY, EMAIL, READ_FRIENDLISTS, READ_INSIGHTS, READ_MAILBOX, READ_REQUESTS, READ_STREAM, XMPP_LOGIN, ADS_MANAGEMENT, USER_CHECKINS, FRIENDS_ABOUT_ME, FRIENDS_ACTIVITIES, FRIENDS_BIRTHDAY, FRIENDS_EDUCATION_HISTORY, FRIENDS_EVENTS, FRIENDS_GROUPS, FRIENDS_HOMETOWN, FRIENDS_INTERESTS, FRIENDS_LIKES, FRIENDS_LOCATION, FRIENDS_NOTES, FRIENDS_ONLINE_PRESENCE, FRIENDS_PHOTO_VIDEO_TAGS, FRIENDS_PHOTOS, FRIENDS_RELATIONSHIPS, FRIENDS_RELATIONSHIP_DETAILS, FRIENDS_RELIGION_POLITICS, FRIENDS_STATUS, FRIENDS_VIDEOS, FRIENDS_WEBSITE, FRIENDS_WORK_HISTORY, MANAGE_FRIENDLISTS, FRIENDS_CHECKINS, PUBLISH_STREAM, CREATE_EVENT, RSVP_EVENT, OFFLINE_ACCESS, PUBLISH_CHECKINS, MANAGE_PAGES};
    }

    public static class PictureTypes {

        public static final int SMALL = 1;
        public static final int NORMAL = 2;
        public static final int LARGE = 3;
        public static final int SQUARE = 4;
        public static final int THUMBNAIL = 5;
        public static final int ALBUM = 6;
    }

    public static class PostTypes {

        public static final String PHOTO = "photo";
        public static final String VIDEO = "video";
        public static final String STATUS = "status";
        public static final String LINK = "link";
        public static final String SWF = "swf";
    }

    public static class ProfileTypes {

        public static final int USER = 1;
        public static final int PAGE = 2;
    }

    private static Facebook instance=null;
    public static Facebook getInstance(MIDlet midlet, ApplicationSettings pAppSettings) {
//        return new Facebook(midlet, pAppSettings);
        if(instance==null){
            instance=new Facebook(midlet, pAppSettings);
        }
        return instance;
    }

    protected Facebook(MIDlet midlet, ApplicationSettings pAppSettings) {
        this.midlet = midlet;
        appSettings = pAppSettings;
    }

    public void setAutoMode(boolean pAutoMode, String pId, String pPwd) {
        autoMode = pAutoMode;
        id = pId;
        pwd = pPwd;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    public void setPermissions(String[] pPerms) {
        appSettings.setPermissions(pPerms);
        setAccessToken(null);
    }

    public String[] getPermissions() {
        return appSettings.getPermissions();
    }

    public void addPermission(String pPerm) {
        String[] oldPerms = getPermissions();
        if ((oldPerms != null) && (oldPerms.length > 0)) {
            String[] newPerms = new String[oldPerms.length + 1];
            for (int i = 0; i < oldPerms.length; i++) {
                newPerms[i] = oldPerms[i];
            }
            newPerms[oldPerms.length] = pPerm;
            setPermissions(newPerms);
        } else {
            String[] newPerms = new String[]{pPerm};
            setPermissions(newPerms);
        }
    }

    public StringBuffer checkResponse(StringBuffer res) throws FacebookException {
        StringBuffer result = null;
        try {
            if ((res != null) && (res.length() > 0) && (res.length() < 500)) {
                if ((res.charAt(0) == '{') && (res.charAt(res.length() - 1) == '}')) {
                    JSONObject jo = new JSONObject(new JSONTokener(res.toString()));
                    if ((jo != null) && jo.has("error")) {
                        JSONObject error = jo.getJSONObject("error");
                        String errorType = error.optString("type");
                        String errorMessage = error.optString("message");
                        if ((errorType != null) && errorType.trim().equals("OAuthException")) {
                            throw new OAuthException(errorMessage);
                        } else {
                            throw new UnknownException(errorMessage);
                        }
                    }
                }
            }
            result = res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject read(String path) throws FacebookException {
        return read(path, null, true);
    }

    public JSONObject read(String path, Hashtable params, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        JSONObject result = null;

        Hashtable args = new Hashtable();
        args.put("access_token", accessToken);
        args.put("format", "JSON");

        if ((params != null) && (params.size() > 0)) {
            Enumeration paramNamesEnum = params.keys();
            while (paramNamesEnum.hasMoreElements()) {
                String paramName = (String) paramNamesEnum.nextElement();
                String paramValue = (String) params.get(paramName);
                args.put(paramName, paramValue);
            }
        }

        try {
            StringBuffer responseBuffer = checkResponse(HttpClient.doGet(GRAPH_URL + '/' + path, args));

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                result = null;
                throw new FacebookException("Empty Response");
            }

            result = new JSONObject(new JSONTokener(responseBuffer.toString()));

        } catch (OAuthException e) {
            if (retry) {
                refreshAccessToken(true);
                result = read(path, params, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }

    public byte[] readRaw(String path, boolean relative) throws FacebookException {
        return readRaw(path, null, relative, true);
    }

    public byte[] readRaw(String path, Hashtable params, boolean relative, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        byte[] result = null;

        Hashtable args = new Hashtable();
        args.put("access_token", accessToken);

        if ((params != null) && (params.size() > 0)) {
            Enumeration paramNamesEnum = params.keys();
            while (paramNamesEnum.hasMoreElements()) {
                String paramName = (String) paramNamesEnum.nextElement();
                String paramValue = (String) params.get(paramName);
                args.put(paramName, paramValue);
            }
        }

        try {
            StringBuffer responseBuffer;
            if (relative) {
                responseBuffer = checkResponse(HttpClient.doGet(GRAPH_URL + '/' + path, args));
            } else {
                responseBuffer = checkResponse(HttpClient.doGet(path, args));
            }

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                throw new FacebookException("Empty Response");
            }

            result = responseBuffer.toString().getBytes();

        } catch (OAuthException e) {
            if (retry) {
                refreshAccessToken(true);
                result = readRaw(path, params, relative, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }

    public JSONObject write(String path, JSONObject object, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        JSONObject result = null;

        Hashtable data = new Hashtable();
        data.put("access_token", accessToken);
        data.put("format", "JSON");

        try {
            JSONObject jo = object;
            Enumeration keysEnum = jo.keys();

            while (keysEnum.hasMoreElements()) {
                String key = (String) keysEnum.nextElement();
                Object val = jo.get(key);
                if (!(val instanceof JSONObject)) {
                    data.put(key, val.toString());
                }
            }

            StringBuffer responseBuffer = checkResponse(HttpClient.doPost(GRAPH_URL + '/' + path, data));

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                //return null;
                throw new FacebookException("Empty Response");
            }

            result = new JSONObject(new JSONTokener(responseBuffer.toString()));

        } catch (OAuthException e) {
            
            if (retry) {
                refreshAccessToken(true);
                result = write(path, object, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }

    public JSONObject write(String path, JSONObject object, String contentType, byte[] payload, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        JSONObject result = null;

        Hashtable data = new Hashtable();
        data.put("access_token", accessToken);
        data.put("format", "JSON");

        try {
            JSONObject jo = object;
            Enumeration keysEnum = jo.keys();

            while (keysEnum.hasMoreElements()) {
                String key = (String) keysEnum.nextElement();
                Object val = jo.get(key);
                if (!(val instanceof JSONObject)) {
                    data.put(key, val.toString());
                }
            }


            //StringBuffer responseBuffer = checkResponse(http.doPostMultipart(GRAPH_URL + '/' + path, data, "name", "filename", contentType, payload));
            StringBuffer responseBuffer = checkResponse(HttpClient.doPost(GRAPH_URL + '/' + path, data));

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                //return null;
                throw new FacebookException("Empty Response");
            }

            result = new JSONObject(new JSONTokener(responseBuffer.toString()));

        } catch (OAuthException e) {
            if (retry) {
                refreshAccessToken(true);
                result = write(path, object, contentType, payload, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }
    
    public String write2(String path, JSONObject object, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        String result = null;

        Hashtable data = new Hashtable();
        data.put("access_token", accessToken);
        data.put("format", "JSON");

        try {
            JSONObject jo = object;
            Enumeration keysEnum = jo.keys();

            while (keysEnum.hasMoreElements()) {
                String key = (String) keysEnum.nextElement();
                Object val = jo.get(key);
                if (!(val instanceof JSONObject)) {
                    data.put(key, val.toString());
                }
            }

            StringBuffer responseBuffer = checkResponse(HttpClient.doPost(GRAPH_URL + '/' + path, data));

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                //return null;
                throw new FacebookException("Empty Response");
            }

            result = responseBuffer.toString();

        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }
    
    public JSONObject delete(String path, boolean retry) throws FacebookException {
        if (!hasAccessToken()) {
            refreshAccessToken(false);
        }

        JSONObject result = null;

        Hashtable data = new Hashtable();
        data.put("access_token", accessToken);
        data.put("format", "JSON");
        data.put("method", "delete");

        try {
            StringBuffer responseBuffer = checkResponse(HttpClient.doPost(GRAPH_URL + '/' + path, data));

            if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                //return null;
                throw new FacebookException("Empty Response");
            }

            result = new JSONObject(new JSONTokener(responseBuffer.toString()));

        } catch (OAuthException e) {
            if (retry) {
                refreshAccessToken(true);
                result = delete(path, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FacebookException(e.getMessage());

        } catch (Throwable t) {
            t.printStackTrace();
            throw new FacebookException(t.getMessage());
        }
        return result;
    }

    public User getCurrentUser(final AsyncCallback listener) throws FacebookException {
        return getUser("me", listener, null);
    }

    public User getCurrentUser() throws FacebookException {
        return getUser("me", null, null);
    }

    public User getUser(final Profile pProfile, final AsyncCallback listener) throws FacebookException {
        return getUser(pProfile.getId(), listener, null);
    }

    public User getUser(Profile pProfile) throws FacebookException {
        return getUser(pProfile.getId());
    }

    public User getUser(String pId) throws FacebookException {
        return getUser(pId, null, null);
    }

    public User getUser(final String pId, final AsyncCallback listener, final java.lang.Object state) throws FacebookException {
        if (listener != null) {
            new java.lang.Thread() {

                public void run() {
                    try {
                        User[] result = new User[1];
                        result[0] = getUser(pId);
                        listener.onComplete(result, null);

                    } catch (Exception e) {
                        listener.onException(e, null);
                    }
                }
            }.start();
            return null;

        } else {
            if ((pId == null) || pId.trim().equals("")) {
                return null;
            }

            User result = null;

            JSONObject jsonObject = read(pId);
            if ((jsonObject == null) || (jsonObject.length() <= 0)) {
                result = null;
            } else {
                result = getUser(jsonObject);
            }
            return result;
        }
    }

    public User getUser(JSONObject jo) throws FacebookException {
        return new FacebookUser(this, jo);
    }

    public Tag getTag(JSONObject jo) throws FacebookException {
        return new FacebookTag(this, jo);
    }

    public FriendList getFriendList(JSONObject jo) throws FacebookException {
        return new FacebookFriendList(this, jo);
    }

    public Comment getComment(JSONObject jo) throws FacebookException {
        return new FacebookComment(this, jo);
    }

    public Album getAlbum(JSONObject jo) throws FacebookException {
        return new FacebookAlbum(this, jo);
    }

    public Location getLocation(JSONObject jo) throws FacebookException {
        return new FacebookLocation(this, jo);
    }

    public Work getWork(JSONObject jo) throws FacebookException {
        return new FacebookWork(this, jo);
    }

    public Education getEducation(JSONObject jo) throws FacebookException {
        return new FacebookEducation(this, jo);
    }

    public Profile getProfile(JSONObject jo) throws FacebookException {
        return new FacebookProfile(this, jo);
    }

    public Interest getInterest(JSONObject jo) throws FacebookException {
        return new FacebookInterest(this, jo);
    }

    public Video getVideo(JSONObject jo) throws FacebookException {
        return new FacebookVideo(this, jo);
    }

    public Page getPage(JSONObject jo) throws FacebookException {
        return new FacebookPage(this, jo);
    }

    public void getPage(final Profile pProfile, final AsyncCallback listener) throws FacebookException {
        getPage(pProfile.getId(), listener, null);
    }

    public Page getPage(Profile pProfile) throws FacebookException {
        return getPage(pProfile.getId());
    }

    public Page getPage(String pId) throws FacebookException {
        return getPage(pId, null, null);
    }

    public Page getPage(final String pId, final AsyncCallback listener, final java.lang.Object state) throws FacebookException {

        if (listener != null) {
            new Thread() {

                public void run() {
                    try {
                        Page[] result = new Page[1];
                        result[0] = getPage(pId);
                        try {
                            listener.onComplete(result, state);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }

                    } catch (Exception e) {
                        listener.onException(e, state);
                    }
                }
            }.start();
            return null;

        } else {

            if ((pId == null) || pId.trim().equals("")) {
                return null;
            }

            Page result = null;

            JSONObject jsonObject = read(pId);
            if ((jsonObject == null) || (jsonObject.length() <= 0)) {
                result = null;
            } else {
                result = getPage(jsonObject);
            }
            return result;
        }

    }

    public Checkin getCheckin(JSONObject jo) throws FacebookException {
        return new FacebookCheckin(this, jo);
    }

    public Status getStatus(JSONObject jo) throws FacebookException {
        return new FacebookStatus(this, jo);
    }

    public Photo getPhoto(JSONObject jo) throws FacebookException {
        return new FacebookPhoto(this, jo);
    }

    public Post getPost(JSONObject jo) throws FacebookException {
        return new FacebookPost(this, jo);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String pAccessToken) {
        accessToken = pAccessToken;
    }

    public boolean hasAccessToken() {
        String at = getAccessToken();
        if ((at == null) || at.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isAccessTokenValid() {
        boolean result = false;
        if (!hasAccessToken()) {
            result = false;
        } else {
            try {
                JSONObject jsonObject = read("me", null, false);
                if ((jsonObject == null) || (jsonObject.length() <= 0)) {
                    result = false;
                } else {
                    result = true;
                }

            } catch (FacebookException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }
    
    public boolean likePost(final String postId, final AsyncCallback listener, final java.lang.Object state) {
        if (listener != null) {
            new java.lang.Thread() {

                public void run() {
                    try {
                        boolean[] result = new boolean[1];
                        result[0] = likePost(postId, null, null);
                        try {
                            listener.onComplete(result, state);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    } catch (Exception e) {
                        listener.onException(e, state);
                    }
                }
            }.start();
            return false;

        } else {
            boolean result = false;
            try {
                JSONObject requestObject = new JSONObject();
                requestObject.put("dummy", "dummy");

                String responseObject = write2(postId + "/likes", requestObject, true);
                if ((responseObject == null) || (responseObject.length() <= 0) || responseObject.equalsIgnoreCase("false")) {
                    result = false;
                } else {
                    if(responseObject.equalsIgnoreCase("true")){
                        result =  true;
                    }
                    else{
                        result = false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (FacebookException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    
    public String commentPost(final String postId, final String message, final AsyncCallback listener, final java.lang.Object state) {
        if (listener != null) {
            new java.lang.Thread() {

                public void run() {
                    try {
                        String[] result = new String[1];
                        result[0] = commentPost(postId, message, null, null);
                        try {
                            listener.onComplete(result, state);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    } catch (Exception e) {
                        listener.onException(e, state);
                    }
                }
            }.start();
            return null;

        } else {
            String result = "";
            try {
                JSONObject requestObject = new JSONObject();
                requestObject.put("message", message);

                JSONObject responseObject = write(postId + "/comments", requestObject, true);
                if ((responseObject == null) || (responseObject.length() <= 0) || responseObject.has("error")) {
                    result = null;
                } else {
                    result = responseObject.optString("id");
                }
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (FacebookException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void refreshAccessToken(boolean force) throws FacebookException {
        
    }
    
    private void oAuth2_0_device_token(){
        
    }
    
    private void getVerifiedCode(final AsyncCallback listener) {
        Thread t = new Thread() {

            public void run() {
                try {
                    String DEVICE_TOKEN_URL = "http://graph.facebook.com/oauth/device";
                    Hashtable args = new Hashtable();
                    args.put("type", "device_code");
                    args.put("client_id", appSettings.applicationId);
                    args.put("scope", appSettings.getPermissionsString());
                    StringBuffer responseBuffer = HttpClient.doGet(DEVICE_TOKEN_URL, args);
                    if ((responseBuffer == null) || (responseBuffer.length() <= 0)) {
                        listener.onException(null, null);
                    } else {
                        listener.onComplete(new String[]{responseBuffer.toString()}, null);
                    }
                } catch (Exception ex) {
                    listener.onException(ex, null);
                }
            }
        };
        t.start();
    }
    
    public boolean logout() {
        return logout(true);
    }

    public boolean logout(boolean clearAccessToken) {
        synchronized (ACCESS_TOKEN_LOCK) {
            String at = getAccessToken();

            if ((at != null) && !at.equals("")) {

                if (clearAccessToken) {
                    setAccessToken(null);
                }

                return true;

            } else {
                if (clearAccessToken) {
                    setAccessToken(null);
                }
                return true;
            }
        }
    }

}
