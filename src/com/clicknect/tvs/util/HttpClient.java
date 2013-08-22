package com.clicknect.tvs.util;

import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.ui.Main;
import com.clicknect.tvs.ui.MyProgress;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.io.ConnectionRequest;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HttpClient {
    
    private static byte[] b;
    
    public static void doGet(String url, String waitingText, final boolean isShowProgress, final MyCallback listener) throws Exception {
        System.out.println("doGet readable URL:  " + url);
        url = urlEncode(url);
        try {
            final MyConnection myRequest = new MyConnection();
            MyClassCallback mccb = new MyClassCallback() {
                public void onComplete() {
                    if(b != null) {
                        String value = new String(b);
                        System.out.println("doGet >> "+value);
                        listener.onComplete(new String[] {value}, null);
                    } else {
                        listener.onException(new Exception() {
                            public String toString() {
                                return "Exception in reading response";
                            }
                        }, null);
                    }
                }
            };
            final MyProgress progress = new MyProgress(Main.getLocal().getMessage("progress_h"), waitingText, null, myRequest, (isShowProgress)? mccb: null);
            myRequest.setUrl(url);
            myRequest.setPost(false);
            myRequest.setTimeout(6000);
            myRequest.addResponseCodeListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    myRequest.kill();
                    Dialog d = new Dialog("Connection Error");
                    d.setTimeout(6000);
                    d.show();
                }
            });
            myRequest.addResponseListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    try {
                        NetworkEvent n = (NetworkEvent) ev;
                        b = (byte[]) n.getMetaData();
                        if(!isShowProgress) {
                            if(b != null) {
                                String value = new String(b);
                                System.out.println("doGet >> "+value);
                                listener.onComplete(new String[] {value}, null);
                            } else {
                                listener.onException(new Exception() {
                                    public String toString() {
                                        return "Exception in reading response";
                                    }
                                }, null);
                            }
                        }
                    } catch(Exception e) {
                        
                    }
                }
            });
            if(isShowProgress) {
                progress.setDisposeOnCompletion(true);
                progress.setAutoShow(true);
                NetworkManager.getInstance().addToQueue(myRequest);
                Display.getInstance().getCurrent().repaint();
                progress.show();
            } else {
                NetworkManager.getInstance().addToQueue(myRequest);
            }
        } catch(Exception e) {
            
        }
    }
    
    public static void doPost(String url, String waitingText, final boolean isShowProgress, Hashtable postData, final MyCallback listener) throws Exception {
        System.out.println("doPost readable URL:  " + url);
        url = urlEncode(url);
        StringBuffer postBuffer = new StringBuffer();
        try {
            final MyConnection myRequest = new MyConnection();
            MyClassCallback mccb = new MyClassCallback() {
                public void onComplete() {
                    if(b != null) {
                        String value = new String(b);
                        System.out.println("doPost >> "+value);
                        listener.onComplete(new String[] {value}, null);
                    } else {
                        listener.onException(new Exception() {
                            public String toString() {
                                return "Exception in reading response";
                            }
                        }, null);
                    }
                }
            };
            final MyProgress progress = new MyProgress(Main.getLocal().getMessage("progress_h"), waitingText, null, myRequest, (isShowProgress) ? mccb : null);
            myRequest.setPriority(ConnectionRequest.PRIORITY_HIGH);
            myRequest.setPost(true);

            if((postData != null) && (postData.size() > 0)) {
                Enumeration keysEnum = postData.keys();
                while (keysEnum.hasMoreElements()) {
                    String key = (String) keysEnum.nextElement();
                    String val = (String) postData.get(key);
                    postBuffer.append(val);
                    myRequest.addArgument(key, val);
                }
            }
            
            myRequest.setUrl(url);
            myRequest.addRequestHeader("User-Agent","Profile/MIDP-2.0 Configuration/CLDC-1.1");
            myRequest.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
            myRequest.addRequestHeader("Content-Length",String.valueOf(postBuffer.toString().getBytes("UTF-8").length));
            myRequest.addRequestHeader("Accept-Charset","UTF-8;q=0.7,*;q=0.7");
            myRequest.addRequestHeader("Accept","text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
            myRequest.setWriteRequest(true);
            myRequest.setReadRequest(true);
            myRequest.addResponseCodeListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    myRequest.kill();
                    Dialog d = new Dialog("Connection Error");
                    d.setTimeout(6000);
                    d.show();
                }
            });
            myRequest.addResponseListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    try {
                        NetworkEvent n = (NetworkEvent) ev;
                        b = (byte[]) n.getMetaData();
                        if(!isShowProgress) {
                            if(b != null) {
                                String value = new String(b);
                                System.out.println("doPost >> "+value);
                                listener.onComplete(new String[] {value}, null);
                            } else {
                                listener.onException(new Exception() {
                                    public String toString() {
                                        return "Exception in reading response";
                                    }
                                }, null);
                            }
                        }
                    } catch(Exception e) {
                        
                    }
                }
            });
            if(isShowProgress) {
                progress.setDisposeOnCompletion(true);
                progress.setAutoShow(true);
                NetworkManager.getInstance().addToQueue(myRequest);
                Display.getInstance().getCurrent().repaint();
                progress.show();
            } else {
                NetworkManager.getInstance().addToQueue(myRequest);
            }
        } catch (Exception e) {
        }
    }
    
    public static StringBuffer doGet(String url, Hashtable args) throws Exception {
        StringBuffer urlBuffer = new StringBuffer(url);

        if ((args != null) && (args.size() > 0)) {
                urlBuffer.append('?');
                Enumeration keysEnum = args.keys();

                while (keysEnum.hasMoreElements()) {
                        String key = (String) keysEnum.nextElement();
                        String val = (String) args.get(key);
                        urlBuffer.append(key).append('=').append(val);

                        if (keysEnum.hasMoreElements()) {
                                urlBuffer.append('&');
                        }
                }
        }

        return doGet(urlBuffer.toString());
    }
    
    public static StringBuffer doGet(String url) throws Exception {
        String rURL = url;
        System.out.println("StringBuffer doGet readable URL:  " + rURL);
        url = urlEncode(url);
        HttpConnection conn = null;
        StringBuffer buffer = new StringBuffer();

        try {
            if ((url == null) || url.equalsIgnoreCase("")) {
                return null;
            }
            conn = (HttpConnection) Connector.open(url);
//                        conn.setRequestMethod(HttpConnection.GET);
//			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            
            System.out.println("HTTP-GET:  " + conn.getURL());
            int resCode = conn.getResponseCode();
            String resMessage = conn.getResponseMessage();
            System.out.println("HTTP-GET Response:  " + resCode + " " + resMessage);

            switch (resCode) {

                case HttpConnection.HTTP_OK: {
                    InputStream inputStream = conn.openInputStream();
                    int c;

                    while ((c = inputStream.read()) != -1) {
                        buffer.append((char) c);
                    }

                    inputStream.close();

                    break;
                }

                case HttpConnection.HTTP_BAD_REQUEST: {
                    InputStream inputStream = conn.openInputStream();
                    int c;

                    while ((c = inputStream.read()) != -1) {
                        buffer.append((char) c);
                    }

                    inputStream.close();
                    break;
                }

                case HttpConnection.HTTP_TEMP_REDIRECT:
                case HttpConnection.HTTP_MOVED_TEMP:
                case HttpConnection.HTTP_MOVED_PERM: {
                    url = conn.getHeaderField("Location");
                    buffer = doGet(url);
                    break;
                }

                default:
                    break;
            }
            System.out.println("HTTP-GET Body:  " + conn.getType() + "(" + buffer.length() + ")");
            if ((conn.getType() != null) && conn.getType().trim().startsWith("text/javascript")) {
                System.out.println(buffer.toString());
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                }
            }
        }
        System.out.println("buffer >> "+buffer.toString());
        return buffer;
    }
    
    public static StringBuffer doPost(String url, Hashtable postData) throws Exception {
        HttpConnection conn = null;
        OutputStream os = null;
        StringBuffer buffer = new StringBuffer();

        try {
            if ((url == null) || url.equalsIgnoreCase("")) {
                return null;
            }

            //StringBuffer postBuffer = new StringBuffer(url);
            StringBuffer postBuffer = new StringBuffer();

            if ((postData != null) && (postData.size() > 0)) {
                //postBuffer.append('?');
                Enumeration keysEnum = postData.keys();

                while (keysEnum.hasMoreElements()) {
                    String key = (String) keysEnum.nextElement();
                    String val = (String) postData.get(key);
                    postBuffer.append(key).append('=').append(val);

                    if (keysEnum.hasMoreElements()) {
                        postBuffer.append('&');
                    }
                }
            }

            conn = (HttpConnection) Connector.open(url);

            if (conn != null) {
                try {
                    // post data
                    if (postData != null) {
                        conn.setRequestMethod(HttpConnection.POST);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(postBuffer.toString().getBytes("UTF-8").length));
                        conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
                        conn.setRequestProperty("Accept-Charset", "UTF-8;q=0.7,*;q=0.7");
                        //conn.setRequestProperty("Accept-Encoding","gzip, deflate");
                        conn.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

                        os = conn.openOutputStream();
                        OutputStreamWriter out = new OutputStreamWriter(os, "UTF-8");
                        out.write(postBuffer.toString());
                        out.close();

                    } else {
                        conn.setRequestMethod(HttpConnection.GET);
                    }

                } catch (Throwable t) {
                    t.printStackTrace();
                    System.out.println("Throwable: " + t.getMessage());
                }

                System.out.println("HTTP-POST :  " + conn.getURL());
                int resCode = conn.getResponseCode();
                String resMessage = conn.getResponseMessage();
                System.out.println("HTTP-POST Response:  " + resCode + " " + resMessage);

                switch (resCode) {

                    case HttpConnection.HTTP_OK: {
                        InputStream inputStream = conn.openInputStream();
                        int c;

                        while ((c = inputStream.read()) != -1) {
                            buffer.append((char) c);

                            //System.err.println("buffer="+buffer+", c="+((char) c));
                        }
                        System.err.println("buffer=" + buffer);
                        inputStream.close();
                        break;
                    }

                    case HttpConnection.HTTP_BAD_REQUEST: {
                        InputStream inputStream = conn.openInputStream();
                        int c;

                        while ((c = inputStream.read()) != -1) {
                            buffer.append((char) c);
                        }

                        inputStream.close();
                        break;
                    }

                    case HttpConnection.HTTP_TEMP_REDIRECT:
                    case HttpConnection.HTTP_MOVED_TEMP:
                    case HttpConnection.HTTP_MOVED_PERM: {
                        url = conn.getHeaderField("Location");
                        buffer = doPost(url, postData);
                        break;
                    }

                    default:
                        break;
                }
            }
            System.out.println("HTTP-POST Body:  " + conn.getType() + "(" + buffer.length() + ")");
            if ((conn.getType() != null) && conn.getType().trim().startsWith("text/javascript")) {
                System.out.println(buffer.toString());
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("Throwable: " + t.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                }
            }
        }

        return buffer;
    }
    
    public static StringBuffer doPost(String url, JSONObject postData) throws Exception {
        HttpConnection conn = null;
        OutputStream os = null;
        StringBuffer buffer = new StringBuffer();
        JSONObject jsonObject = postData;

        try {
            if ((url == null) || url.equalsIgnoreCase("")) {
                return null;
            }

            conn = (HttpConnection) Connector.open(url);

            if (conn != null) {
                try {
                    // post data
                    if (postData != null) {
                        conn.setRequestMethod(HttpConnection.POST);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(jsonObject.toString().getBytes("UTF-8").length));
                        conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
                        conn.setRequestProperty("Accept-Charset", "UTF-8;q=0.7,*;q=0.7");
                        //conn.setRequestProperty("Accept-Encoding","gzip, deflate");
                        conn.setRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

                        os = conn.openOutputStream();
                        OutputStreamWriter out = new OutputStreamWriter(os, "UTF-8");
                        out.write(jsonObject.toString());
                        out.close();


                    } else {
                        conn.setRequestMethod(HttpConnection.GET);
                    }

                } catch (Throwable t) {
                    t.printStackTrace();
                    System.out.println("Throwable: " + t.getMessage());
                }

                System.out.println("HTTP-POST :  " + conn.getURL());
                int resCode = conn.getResponseCode();
                String resMessage = conn.getResponseMessage();
                System.out.println("HTTP-POST Response:  " + resCode + " " + resMessage);

                switch (resCode) {

                    case HttpConnection.HTTP_OK: {
                        InputStream inputStream = conn.openInputStream();
                        int c;

                        while ((c = inputStream.read()) != -1) {
                            buffer.append((char) c);

                            //System.err.println("buffer="+buffer+", c="+((char) c));
                        }
                        System.err.println("buffer=" + buffer);
                        inputStream.close();
                        break;
                    }

                    case HttpConnection.HTTP_BAD_REQUEST: {
                        InputStream inputStream = conn.openInputStream();
                        int c;

                        while ((c = inputStream.read()) != -1) {
                            buffer.append((char) c);
                        }

                        inputStream.close();
                        break;
                    }

                    case HttpConnection.HTTP_TEMP_REDIRECT:
                    case HttpConnection.HTTP_MOVED_TEMP:
                    case HttpConnection.HTTP_MOVED_PERM: {
                        url = conn.getHeaderField("Location");
                        buffer = doPost(url, postData);
                        break;
                    }

                    default:
                        break;
                }
            }
            System.out.println("HTTP-POST Body:  " + conn.getType() + "(" + buffer.length() + ")");
            if ((conn.getType() != null) && conn.getType().trim().startsWith("text/javascript")) {
                System.out.println(buffer.toString());
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("Throwable: " + t.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                }
            }
        }

        return buffer;
    }
    
    public static String urlEncode(String url) {
        StringBuffer encoded = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char ch = url.charAt(i);
            if (ch == '<') {
                encoded.append("%3C");
            } else if (ch == '>') {
                encoded.append("%3E");
            } else if (ch == ' ') {
                encoded.append("%20");
            } else if (ch == '-') {
                encoded.append("%2D");
            } // json syntax
            else if (ch == '{') {
                encoded.append("%7B");
            } else if (ch == '"') {
                encoded.append("%22");
            } else if (ch == ':' && url.charAt(i + 1) != '/') {
                encoded.append("%3A");
            } else if (ch == ',') {
                encoded.append("%2C");
            } else if (ch == '}') {
                encoded.append("%7D");
            } else {
                encoded.append(ch);
            }
        }
        return encoded.toString();
    }
    
    public static String manageURL(String url, Hashtable args) throws Exception {
        System.out.println(url);
        StringBuffer urlBuffer = new StringBuffer(url);

        if ((args != null) && (args.size() > 0)) {
            urlBuffer.append('?');
            Enumeration keysEnum = args.keys();
            while (keysEnum.hasMoreElements()) {
                String key = (String) keysEnum.nextElement();
                String val = (String) args.get(key);
                urlBuffer.append(key).append('=').append(val);

                if (keysEnum.hasMoreElements()) {
                    urlBuffer.append('&');
                }
            }
        }
        return urlBuffer.toString();
    }
    
}