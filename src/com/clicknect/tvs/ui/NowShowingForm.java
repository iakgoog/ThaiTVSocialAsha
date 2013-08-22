/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONException;
import com.clicknect.tvs.json.JSONObject;
import com.clicknect.tvs.social.FacebookAPI;
import com.clicknect.tvs.util.DeviceInfo;
import com.clicknect.tvs.util.MyClassCallback;
import com.clicknect.tvs.util.MyConstant;
import com.clicknect.tvs.util.MyFont;
import com.clicknect.tvs.util.MyUtil;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import com.sun.lwuit.io.services.ImageDownloadService;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Vector;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class NowShowingForm extends Form implements ActionListener {
    
    private Main main;
    private Vector tvLists;
    private Container bodyWrapper, bodyContainer;
    private TVListComponent[] component;
    private TVListData[] data;
    private NowShowingForm instance;
    
    private int imgWidth = 184;
    private int imgHeight = 44;
    private int componentW = 240;
    private int componentH = 65;
    
    private Image channelTagImg, checkinNumImg;
    
    private Command cmdRefresh, cmdPrivacy, cmdLogout, cmdGraph;

    public NowShowingForm(Main m) {
        this.main = m;
        instance = this;
        tvLists = new Vector();
        
        try {
            channelTagImg = Image.createImage("/channelTag.png");
            checkinNumImg = Image.createImage("/checkin_num.png");
        } catch (Exception e) {
        }
        
        cmdRefresh = new Command(Main.getLocal().getMessage("refresh"));
        cmdPrivacy = new Command(Main.getLocal().getMessage("privacy_policy"));
        cmdLogout = new Command(Main.getLocal().getMessage("logout"));
        cmdGraph = new Command(Main.getLocal().getMessage("graph"));
        
        setTitle(Main.getLocal().getMessage("now_showing"));
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
        bodyContainer.setScrollVisible(false);
        bodyContainer.setScrollableY(true);
        
        
        //bodyWrapper.setPreferredH(isOneCommand() ? MyConstant.CONTENT_HEIGHT_WITH_COMMAND: MyConstant.CONTENT_HEIGHT); //285
        bodyWrapper.setPreferredH(MyConstant.CONTENT_HEIGHT_WITH_COMMAND); //285
        
        //show();
        getList();
    }
    
    private void formAddCommand() {
        addCommand(cmdPrivacy);
        addCommand(cmdGraph);
        addCommand(cmdLogout);
        addCommand(cmdRefresh);
        addCommandListener(this);
    }
    
    private boolean isOneCommand() {
        return this.getCommandCount() > 1;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getCommand() == cmdRefresh) {
            doRefresh();
        } else if(ae.getCommand() == cmdPrivacy) {
            System.out.println(">>>>>>>>  cmdPrivacy had been pressed  <<<<<<<<");
            try {
                Main.getMIDlet().platformRequest(MyConstant.PRIVACY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(ae.getCommand() == cmdLogout) {
            main.logout();
        } else if(ae.getCommand() == cmdGraph) {
            main.setTVLists(tvLists);
            main.showGraph();
        }
    }
    
    public void getList() {
        FacebookAPI api = FacebookAPI.getInstance();
        api.getTVList(new MyClassCallback() {

            public void onException(Exception e, Object state) {
                System.out.println("...........................NowShowingForm getList() is onException");
                show();
            }

            public void onComplete(JSONObject json, Object state) {
                System.out.println("...........................NowShowingForm getList() is onComplete");
                String status = json.optString("status");
                if(status.equalsIgnoreCase("OK")) {
                    JSONArray jsons = json.optJSONArray("list");
                    int length = jsons.length();
                    data = new TVListData[length];
                    component = new TVListComponent[length];
                    if(length>0) {
                        clearForm();
                        tvLists.removeAllElements();
                        //show();
                        
                        for(int i=0; i<length; i++) {
                            try {
                                JSONObject obj = jsons.getJSONObject(i);
                                data[i] = new TVListData(obj);
                                tvLists.addElement(data[i]);
                                TVListData data = (TVListData) tvLists.elementAt(i);
                                data.setNumber(i);
                                component[i] = new TVListComponent(data, instance);
                                bodyContainer.addComponent(component[i]);
                            } catch (JSONException ex) {
                                System.out.println(ex.toString());
                            }
                        }
                        bodyWrapper.addComponent(bodyContainer);
                        if(tvLists.size()==length) {
                            addComponent(bodyWrapper);
                            addComponent(Main.getAds(0));
                            formAddCommand();
                            show();
                        } else {
                            
                        }
                    } else {
                        
                    }
                } else {
                    
                }
            }
        });
    }
    
    private void clearForm() {
        bodyWrapper.removeAll();
        bodyContainer.removeAll();
        this.removeAll();
        this.removeAllCommands();
        //System.out.println("______________________________________ clearForm() There is(are) "+this.getCommandCount()+" command(s) left");
    }
    
    private void doRefresh() {
        /*
        clearForm();
        tvLists.removeAllElements();
        show();
        */
        getList();
    }
    
    public void setRateNum(int num, String val) {
        data[num].rateNum = val;
    }
    
    public void setCheckinNum(int num, String val) {
        data[num].checkinNum = val;
        component[num].setCheckinNum(val);
    }

    class TVListComponent extends Container {
        private NowShowingForm nowShowingForm;
        private Image image;
        private String channelName, label, checkinNum, imageURL;
        //boolean checkin;
        private int[] btn_state = new int[] {0,0};
        private TVListData data;
        
        private boolean chkFirstLoad = true;

        public TVListComponent(TVListData d, NowShowingForm n) {
            this.nowShowingForm = n;
            this.data = d;
            this.imageURL = d.iconURL;
            this.channelName = d.channel;
            this.label = MyUtil.cutText(d.title, MyFont.fontSmall, 80) + " : " + d.time;
            this.checkinNum = d.checkinNum;
            this.image = null;
            //System.out.println("label >>>> "+label);
            this.setPreferredSize(new Dimension(componentW, componentH));
            //System.out.println("MyFont.fontSmall.stringWidth(label) :::: "+MyFont.fontSmall.stringWidth(label));

        }

        public void checkin() {
            //if(!data.isCheckedin() && btn_state[0]!=2) {
            if(!data.isCheckedin()) {
                main.setActiveTVData(data);
                main.showCheckin("NowShowingForm");
            }
        }
        
        private void setCheckinNum(String data) {
            this.checkinNum = data;
        }

        public void openDetailForm() {
            main.createDetailForm(data);
        }

        public boolean isCheckedin(){
            return data.isCheckedin();
        }

        protected int getMinContentHeight() {
            return image.getHeight() +  MyFont.fontMedium.getHeight();
        }

        protected int getMinContentWidth() {
            return DeviceInfo.getWidth();
        }

        protected int getPrefContentHeight(int width) {
            return image.getHeight() + MyFont.fontMedium.getHeight();
        }

        protected int getPrefContentWidth(int height) {
            return DeviceInfo.getWidth();
        }

        public void pointerReleased(int x, int y) {
            openDetailForm();
        }

        private void loadImageProcess() {
            try {
                if(imageURL.startsWith("http")) {
                    //System.out.println("imageURL starts with http : "+imageURL);
                    if(chkFirstLoad) {
                        chkFirstLoad = false;
                    } else {
                        Image i = Main.getInstance().getImage(imageURL);
                        image = i.scaled(imgWidth, imgHeight);
                    }
                    if(image == null) {
                        if(!Main.getInstance().isFetchingURLContain(imageURL)) {
                            //System.out.println("<NowShowingForm> Loading Image has been started");
                            System.out.println("<NowShowingForm> fetching URL doesn't contain : "+imageURL);
                            Main.getInstance().addFetchingURLs(imageURL);
                            ImageDownloadService d = new ImageDownloadService(imageURL, new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    NetworkEvent n = (NetworkEvent) ae;
                                    try {
                                        System.out.println("<NowShowingForm> Download service completed");
                                        Main.getInstance().cacheImage(n.getConnectionRequest().getUrl(), n.getMetaData());
                                        Main.getInstance().removeFetchingURLs(n.getConnectionRequest().getUrl());
                                        Image i = Main.getInstance().getImage(n.getConnectionRequest().getUrl());
                                        image = i.scaled(imgWidth, imgHeight);
                                        //repaint();
                                        //nowShowingForm.repaint();
                                    } catch (Exception e) {
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueue(d);
                        }
                    }
                    //System.out.println("image != null");
                } else {

                }
            } catch (Exception e) {
            }
        }

        public void paint(Graphics g) {
            try {
                int x = getX();
                int y = getY();
                g.setAlpha(255);
                if(image == null) {
                    loadImageProcess();
                } else {
                    g.drawImage(image, x, y);
                }

                g.setColor(0x333333);
                g.fillRect(x, y + imgHeight, imgWidth, 18);
                g.setColor(0);
                g.fillRect(x, y + imgHeight + 18, imgWidth, 3);
                g.drawImage(channelTagImg, x, y);
                g.setColor(0);
                g.setFont(MyFont.fontSmall);
                g.drawString(channelName, x+3, y+3);
                g.setColor(0xFFFFFF);
                g.drawString(channelName, x+2, y+2);
                g.drawString(label, (imgWidth - MyFont.fontSmall.stringWidth(label)) / 2, y + imgHeight + 1);

                g.setColor(0xcccbcb);
                g.fillRect(x + imgWidth, y, 56, imgHeight);
                g.setColor(0x979797);
                g.fillRect(x + imgWidth, y + imgHeight, 56, 18);
                g.setColor(0x676767);
                g.fillRect(x + imgWidth, y + imgHeight + 18, 56, 3);
                g.drawImage(checkinNumImg, x + imgWidth + (((Main.getScreenWidth() - imgWidth) - checkinNumImg.getWidth()) / 2), y + 7);
                g.setFont(MyFont.fontSmall);
                g.setColor(0);
                g.drawString(checkinNum, x + imgWidth + (((Main.getScreenWidth() - imgWidth) - MyFont.fontSmall.stringWidth(checkinNum)) / 2), y + 12); //iakgoog
                g.setColor(0xFFFFFF);
                g.drawString(Main.getLocal().getMessage("check_in"), x + imgWidth, y + imgHeight);
            } catch (Exception e) {
            }
        }
    }
}