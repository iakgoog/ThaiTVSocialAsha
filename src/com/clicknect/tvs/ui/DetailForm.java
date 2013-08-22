/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.facebook.Facebook;
import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONException;
import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.social.FacebookAPI;
import com.clicknect.tvs.util.MyClassCallback;
import com.clicknect.tvs.util.MyConstant;
import com.clicknect.tvs.util.MyFont;
import com.clicknect.tvs.util.MyUtil;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import com.sun.lwuit.io.services.ImageDownloadService;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;
import java.util.Vector;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class DetailForm extends Form implements ActionListener {
    
    private Main main; 
    private TVListData detailFormData;
    private DetailComponent detailComponent;
    private FirstRowButtons firstRowButtons;
    private SecondRowButtons secondRowButtons;
    private CommentContainer commmentContainer;
    private Container bodyWrapper, bodyContainer;
    private TextArea textArea;
    private ShowTextComponent stc;
    private JSONArray jsonArr;
    
    private Image rateSelected, rateNormal;
    
    private Facebook fb;
    
    private Command cmdBack, cmdComment/*, cmdRate*/;
    
    private int componentW = 240;
    private int componentH = 64;
    
    private boolean commentLoadComplete;
    private boolean preventCommentRefresh;
    
    private int checkInt = 0;

    public DetailForm(Main main, TVListData data) {
        this.main = main;
        this.detailFormData = data;
        
        fb = Facebook.getInstance(Main.getMIDlet(), null);
        
        cmdBack = new Command(Main.getLocal().getMessage("back"));
        cmdComment = new Command(Main.getLocal().getMessage("comment"));
        //cmdRate = new Command(Main.getLocal().getMessage("rate"));
        
        try {
            rateSelected = Image.createImage("/heart_hit.png");
            rateNormal = Image.createImage("/heart_normal.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle(Main.getLocal().getMessage("detail"));
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollVisible(false);
        setScrollable(false);
        setFocusable(false);
        setVisible(true);
        getStyle().setBgColor(0xF2F2F2);
        
        bodyWrapper = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bodyWrapper.setScrollVisible(false);
        bodyWrapper.setScrollableY(false);
        //bodyWrapper.setFocusable(true);
        //bodyWrapper.setVisible(true);
        
        bodyContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bodyContainer.setScrollVisible(true);
        bodyContainer.setScrollableY(true);
        
        detailComponent = new DetailComponent();
        firstRowButtons = new FirstRowButtons();
        secondRowButtons = new SecondRowButtons();
        commmentContainer = new CommentContainer();
        
        textArea = commentBox(data.detail);
        stc = new ShowTextComponent(data.detail);
        
        setBackCommand(cmdBack);
        //addCommand(cmdRate);
        //addCommand(cmdComment);
        addCommandListener(this);
        
        bodyWrapper.setPreferredH((this.getCommandCount()>1) ? MyConstant.CONTENT_HEIGHT_WITH_COMMAND: MyConstant.CONTENT_HEIGHT); //285
        
        addDetailComponents();
        show();
        check_like();
    }
    
    public void setRateNum(String num) {
        detailFormData.rateNum = num;
    }
    
    public void setCheckinNum(String val) {
        detailFormData.checkinNum = val;
    }
    
    private void addDetailComponents() {
        checkInt++;
        
        System.out.println("show checkInt (loop check) : ------------------------------------> ["+checkInt+"]");
        bodyContainer.addComponent(detailComponent);
        //bodyContainer.addComponent(textArea);
        bodyContainer.addComponent(stc);
        bodyContainer.addComponent(firstRowButtons);
        bodyContainer.addComponent(secondRowButtons);
        if(!commentLoadComplete) {
            bodyContainer.addComponent(commmentContainer);
        } else {
            if(jsonArr.length()!=0) { 
                try {
                    JSONObject json_item, json_from;
                    for(int i=0; i<jsonArr.length(); i++){
                        json_item = jsonArr.getJSONObject(i);
                        json_from = json_item.getJSONObject("from");
                        //System.out.println("json_from.getString(\"name\")" + json_from.getString("name"));
                        //System.out.println("json_item.getString(\"message\")" + json_item.getString("message"));

                        bodyContainer.addComponent(new Label(json_from.getString("name")+": "));
                        bodyContainer.addComponent(commentBox(json_item.getString("message")));
                        //bodyContainer.addComponent(spaceLabel());
                    }
                } catch (JSONException e) {
                }
            } else {
                bodyContainer.addComponent(new Label(Main.getLocal().getMessage("comment_empty")));
            }
        }
        bodyWrapper.addComponent(bodyContainer);
        addComponent(bodyWrapper);
        addComponent(Main.getAds(1));
        
    }
    
    public void removeComponents() {
        bodyContainer.removeAll();
        bodyWrapper.removeAll();
        this.removeAll();
    }
    
    private void check_like() { //IAKGOOG 19-04-2013
        if(detailFormData.likeStatus==null) {
            System.out.println("<<<<<<<< data.likeStatus = null >>>>>>>>");
            FacebookAPI.getInstance().FBLike(fb, detailFormData.refid, null, true, new MyClassCallback() {
                public void onComplete(JSONObject json, Object state) {
                    System.out.println("<<<<<<<< check_like onComplete >>>>>>>>");
                    firstRowButtons.chkLike = false;
                    firstRowButtons.repaint();
                    try {
                        System.out.println("check_like() = ["+json.getString("data")+"]");
                        if((json.has("data")) && (!json.getString("data").equals(""))  && (!json.getString("data").equals("[]"))) {
                            JSONArray data_json = json.getJSONArray("data");
                            //if(data_json.getJSONObject(0).getJSONObject("likes").getBoolean("user_likes")) {
                            if(data_json.getJSONObject(0).getBoolean("user_likes")) {
                                detailFormData.likeStatus = "OK";
                            } else {
                                detailFormData.likeStatus = "NO";
                            }
                        }
                        //loadComments();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                        //main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("retrieve_fail"), false);
                    } finally{
                        loadComments();
                    }
                }

                public void onException(Exception e, Object state) {
                    System.out.println("<<<<<<<< check_like onException >>>>>>>>");
                    firstRowButtons.chkLike = false;
                    firstRowButtons.repaint();
                    //main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("retrieve_fail"), false);
                    loadComments();
                }
            });
        } else {
            System.out.println("<<<<<<<< data.likeStatus is not NULL >>>>>>>>");
            
            firstRowButtons.chkLike = false;
            firstRowButtons.repaint();
            loadComments();
            
        }
    }
    
    private void like(){
        if(!detailFormData.isLiked() && !firstRowButtons.checkLiked()){
            FacebookAPI.getInstance().FBLike(fb, detailFormData.refid, "", false, new MyClassCallback(){
                public void onComplete(JSONObject json, Object state) {
                    firstRowButtons.setLike(true);
                    firstRowButtons.chkLike = false;
                    detailFormData.likeStatus = "OK";
                    firstRowButtons.repaint();
                }

                public void onException(Exception e, Object state) {
                    firstRowButtons.chkLike = false;
                    firstRowButtons.repaint();
                    main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("retrieve_fail"), null, false);
                }
            });
        } else {
            main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("already_liked"), null, false);
        }
    }
    
    private void loadComments() {
        System.out.println("<<<<<<<< loadComments has been triggered >>>>>>>>");
        FacebookAPI.getInstance().FBComment(detailFormData.refid, false, null, new MyClassCallback() {

            public void onException(Exception e, Object state) {
                commentLoadComplete = true;
                loadCommentComplete();
            }

            public void onComplete(JSONObject json, Object state) {
                commentLoadComplete = true;
                loadCommentComplete();
                try {
                    jsonArr = json.getJSONArray("data");
                    if(jsonArr.length()==0){
                        //notfound_idx = append(mylocal.getMessage("not_found_comment"));
                        System.out.println("JSONArray == [null]");
                        removeComponents();
                        addDetailComponents();
                        if(!preventCommentRefresh) {
                            show();
                        }
                        //addComponent(new Label("Comment is empty"));
                    } else {
                        removeComponents();
                        addDetailComponents();
                        if(!preventCommentRefresh) {
                            show();
                        }
                    }
                } catch (JSONException ex) {
                }
            }

        });
    }
    
    private void loadCommentComplete() {
        addCommand(cmdComment);
        bodyWrapper.setPreferredH((this.getCommandCount()>1) ? MyConstant.CONTENT_HEIGHT_WITH_COMMAND: MyConstant.CONTENT_HEIGHT); //285
        show();
    }
    
    private TextArea commentBox(String text) {
        TextArea ta = new TextArea(text, 20);
        ta.setUIID("Label");
        ta.setGrowByContent(true);
        ta.setEditable(false);
        ta.setFocusable(false);
        ta.setSingleLineTextArea(false);
        ta.getUnselectedStyle().setFont(MyFont.fontSmall); 
        ta.getSelectedStyle().setFont(MyFont.fontSmall); 
        ta.getStyle().setFgColor(0x333333);
        ta.getStyle().setBgColor(this.getStyle().getBgColor());
        //ta.setText(text);
        
        return ta;
    }
    
    private Label spaceLabel() {
        Label l = new Label("");
        l.setPreferredSize(new Dimension(Main.getScreenWidth(), 2)); //10
        return l;
    }
    
    private void doRepaint() {
        repaint();
    }

    public void actionPerformed(ActionEvent ae) {
        if(commentLoadComplete) {
            if(ae.getCommand() == cmdBack) {
                this.removeAll();
                main.showNowShowingForm();
                /*
                if(Main.getInstance().isFetchingURLContain(data.image_url)) {
                    System.out.println("<DetailForm> It's still in the cachemap");
                } else {
                    System.out.println("<DetailForm> It's gone from the cachemap");
                }
                */
            }/* else if(ae.getCommand() == cmdRate) {
                main.setActiveTVData(detailFormData);
                main.showVote();
            }*/ else if(ae.getCommand() == cmdComment) {
                main.setActiveTVData(detailFormData);
                main.showComment();
            }
        }
    }
    
    class DetailComponent extends Container {
        
        private Image image;
        private String channelName, label, checkinNum, imageURL, loading;
        private boolean chkFirstLoad = true;
        
        public DetailComponent() {
            System.out.println("DetailComponent has been constructed!!!!!!!!");
            this.imageURL = detailFormData.image_url;
            this.channelName = detailFormData.channel;
            this.label = MyUtil.cutText(detailFormData.title, MyFont.fontSmall, 124) + " : " + detailFormData.time;
            this.checkinNum = detailFormData.checkinNum;
            this.image = null;
            this.loading = Main.getLocal().getMessage("loading");
            //System.out.println("label >>>> "+label);
            this.setPreferredSize(new Dimension(componentW, componentH));
            //System.out.println("setPreferredSize!!!!!!!!");
        }
        
        private void loadImageProcess() {
            try {
                if(imageURL.startsWith("http")) {
                    //System.out.println("<DetailForm> imageURL starts with http : "+imageURL);
                    if(chkFirstLoad) {
                        chkFirstLoad = false;
                    } else {
                        Image i = main.getImage(imageURL);
                        //image = i.scaled(componentW, componentH);
                        image = i.subImage(0, 48, componentW, componentH, false);
                    }
                    if(image == null) {
                        if(!main.isFetchingURLContain(imageURL)) {
                            System.out.println("<DetailForm> Loading Image has been started");
                            System.out.println("<DetailForm> fetching URL doesn't contain : "+imageURL);
                            main.addFetchingURLs(imageURL);
                            if(main.isFetchingURLContain(imageURL)) {
                                System.out.println("<DetailForm> Image has been added to cachemap");
                            }
                            ImageDownloadService d = new ImageDownloadService(imageURL, new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    NetworkEvent n = (NetworkEvent) ae;
                                    try {
                                        System.out.println("<DetailForm> Download service completed");
                                        main.cacheImage(n.getConnectionRequest().getUrl(), n.getMetaData());
                                        main.removeFetchingURLs(n.getConnectionRequest().getUrl());
                                        Image i = main.getImage(n.getConnectionRequest().getUrl());
                                        image = i.subImage(0, 48, componentW, componentH, false);
                                        repaint();
                                    } catch (Exception e) {
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue(d);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
            }
        }

        public void paint(Graphics g) {
            int x = getX();
            int y = getY();
            if(image == null) {
                g.setColor(0);
                g.fillRect(x, y, componentW, componentH);
                g.setFont(MyFont.fontMedium);
                g.setColor(0xFFFFFF);
                g.drawString(loading, (componentW - MyFont.fontMedium.stringWidth(loading)) / 2, (componentH - MyFont.fontMedium.getHeight()) / 2);
                loadImageProcess();
            } else {
                g.drawImage(image, x, y);
                g.setColor(0x000000);
                g.setAlpha(128);
                g.fillRect(x, y + componentH - 20, componentW, 20);

                g.setAlpha(255);
                
                g.setFont(MyFont.fontSmall);
                g.setColor(0xed2120);
                g.fillRect(Main.getScreenWidth() - (MyFont.fontSmall.stringWidth(channelName) + 8), y, MyFont.fontSmall.stringWidth(channelName) + 8, MyFont.fontSmall.getHeight() + 4);
                g.setColor(0xFFFFFF);
                
                g.drawString(channelName, Main.getScreenWidth() - (MyFont.fontSmall.stringWidth(channelName) + 4), 2);
                g.drawString(label, (componentW - MyFont.fontSmall.stringWidth(label)) / 2, y + componentH - 19);
            }
        }
        
    }
    
    class ShowTextComponent extends Component {
        
        String text;
        Vector lines;
        
        ShowTextComponent(String text) {
            this.text = text;
            lines = MyUtil.wrapToLines(text.trim(), MyFont.fontSmall, 150, 224);
            int componentW = 236;
            int componentH = (MyFont.fontSmall.getHeight() * lines.size()) + 4;
            
            Style conponentStyle = UIManager.getInstance().getComponentStyle("Label");
            conponentStyle.setBgTransparency(0);
            
            this.setPreferredSize(new Dimension(componentW, componentH));
            this.getStyle().setBgTransparency(0);
            this.setPressedStyle(conponentStyle);
            this.setFocusable(false);
        }

        public void paint(Graphics g) {
            g.setFont(MyFont.fontSmall);
            g.setColor(0x000000);
            
            int x = getX();
            int y = getY();
            for(int i=0; i<lines.size(); i++) {
                String toPrint = (String) lines.elementAt(i);
                g.drawString((String) lines.elementAt(i), ((i == 0)? x+12:x), y);
                y += MyFont.fontSmall.getHeight();
            }
        }
        
    }
    
    class FirstRowButtons extends Container {
        
        public final int containerW = Main.getScreenWidth();
        public final int containerH = 50;
        public final int buttonW = 106;
        public final int buttonH = 34;
        public final int firstX = ((containerW / 2) - buttonW) / 2;
        public final int firstY = (containerH - buttonH) / 2;
        public final int secondX = (containerW / 2) + firstX;
        
        private Image liked;
        private boolean chkLike = true;
        private boolean alreadyLiked = false;
        
        public FirstRowButtons() {
            this.setPreferredSize(new Dimension(containerW, containerH));
            try {
                liked = Image.createImage("/like.png");
            } catch (Exception e) {
            }
        }
        
        public void setLike(boolean val) {
            this.alreadyLiked = val;
        }
        
        public boolean checkLiked() {
            return alreadyLiked;
        }
        
        public void pointerReleased(int x, int y) {
            if(commentLoadComplete) {
                if((x > firstX) && (x < (firstX + buttonW))) {
                    System.out.println("<<<<---- isCheckinPressed ---->>>>");
                    if(!detailFormData.isCheckedin()) {
                        preventCommentRefresh = true;
                        main.setActiveTVData(detailFormData);
                        main.showCheckin("DetailForm");
                    }
                } else if((x > secondX) && (x < (secondX + buttonW))) {
                    if(!firstRowButtons.checkLiked()) {
                        preventCommentRefresh = true;
                        System.out.println("<<<<---- isLikePressed ---->>>>");
                        chkLike = true;
                        like();
                    }
                }
            }
        }
        
        public void paint(Graphics g) {
            int x = getX();
            int y = getY();
            DrawUtil.drawButton(g, x + firstX, y + firstY, buttonW, buttonH, (commentLoadComplete) ? 0xed2120 : 0x555555, (commentLoadComplete) ? 0xFFFFFF : 0x000000, Main.getLocal().getMessage("check_in"), MyFont.fontMedium, true, null);
            if(chkLike) {
                DrawUtil.drawButton(g, x + secondX, y + firstY, buttonW, buttonH, 0x3e3e40, 0xFFFFFF, Main.getLocal().getMessage("loading"), MyFont.fontMedium, true, null);
            } else {
                DrawUtil.drawButton(g, x + secondX, y + firstY, buttonW, buttonH, (commentLoadComplete) ? 0x3e3e40 : 0x555555, (commentLoadComplete) ? 0xFFFFFF : 0x000000, Main.getLocal().getMessage("like"), MyFont.fontMedium, true, (detailFormData.isLiked()) ? liked:null);
            }
        }
        
    }
    
    class SecondRowButtons extends Container {
        
        public final int containerW = Main.getScreenWidth();
        public final int containerH = 50;
        public final int buttonW = 106;
        public final int buttonH = 34;
        public final int firstX = ((containerW / 2) - buttonW) / 2;
        public final int firstY = (containerH - buttonH) / 2;
        public final int secondX = (containerW / 2) + firstX;
        
        public SecondRowButtons() {
            this.setPreferredSize(new Dimension(containerW, containerH));
        }
        
        public void pointerReleased(int x, int y) {
            if(commentLoadComplete) {
                if((x > firstX) && (x < (firstX + buttonW))) {
                    preventCommentRefresh = true;
                    main.setActiveTVData(detailFormData);
                    main.showVote();
                }
            }
        }
        
        public void paint(Graphics g) {
            int x = getX();
            int y = getY();
            DrawUtil.drawButton(g, x + firstX, y + firstY, buttonW, buttonH, (commentLoadComplete) ? 0xbbbbbb : 0x555555, 0x000000, Main.getLocal().getMessage("rate"), MyFont.fontMedium, (commentLoadComplete) ? false : true, null);
            
            g.drawImage(getRatingImg(detailFormData.rateNum), x + 136, y + firstY);
        }
        
        Image getRatingImg(String rateNum) {
            Image img = Image.createImage(84, 34);
            Graphics g = img.getGraphics();
            
            int num = Integer.parseInt(rateNum);
            String numPercent = Integer.toString(num * 20) + " %";
            int textBoxWidth = 48;
            int rateGap = 18;
            g.setColor(0xF2F2F2);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
            g.setColor(0xd4d4d4);
            g.fillRect(img.getWidth() - textBoxWidth, 0, textBoxWidth, 2);
            g.setColor(0xffffff);
            g.fillRect(img.getWidth() - textBoxWidth, 2, textBoxWidth, 16);
            g.setFont(MyFont.fontSmall);
            g.setColor(0xc05662);
            g.drawString(numPercent, (img.getWidth() - MyFont.fontSmall.stringWidth(numPercent) - 4), 1);
            
            int i=0;
            for(; i<num; i++) {
                g.drawImage(rateSelected, i * rateGap, 22);
            }
            for(; i<5; i++) {
                g.drawImage(rateNormal, i * rateGap, 22);
            }
            
            return img;
        }
        
    }
    
    class CommentContainer extends Container {
        
        public CommentContainer() {
            setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            setScrollVisible(true);
            setScrollable(true);
            setFocusable(false);
            setVisible(true);
            setPreferredSize(new Dimension(Main.getScreenWidth(), 24));
        }

        public void paint(Graphics g) {
            int x = getX();
            int y = getY();
            String load = Main.getLocal().getMessage("loading");
            g.setColor(0x000000);
            g.fillRect(x, y, Main.getScreenWidth(), 36);
            g.setColor(0xFFFFFF);
            g.setFont(MyFont.fontMedium);
            g.drawString(load, x + ((Main.getScreenWidth() - MyFont.fontMedium.stringWidth(load)) / 2), y + ((36 - MyFont.fontMedium.getHeight()) / 2));
        }
        
    }
    
}