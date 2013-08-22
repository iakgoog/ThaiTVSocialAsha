/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.util.MyConstant;
import com.clicknect.tvs.util.MyFont;
import com.clicknect.tvs.util.MyUtil;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Slider;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Vector;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class RotationForm extends Form implements ActionListener {
    
    private Main main;
    private Vector tvLists;
    private Slider slider;
    private Container bodyWrapper, bodyContainer;
    private Command cmdGraphBack, cmdRateBack, cmdRate, cmdCheckinBack, cmdCheckin, cmdCommentBack, cmdComment;
    private TextArea textField;
    private String msg_comment = null;
    
    private String backTo = "";
    
    private int dataNumTemp;
    private String rateTemp;
    private String checkinNumTemp;

    public RotationForm(Main main) {
        this.main = main;
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        //setScrollVisible(false);
        //setScrollable(false);
        //setFocusable(false);
        //setVisible(true);
        
        bodyWrapper = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bodyWrapper.setScrollVisible(false);
        bodyWrapper.setScrollableY(false);
        //bodyWrapper.setPreferredH(237);
        
        bodyContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        bodyContainer.setScrollVisible(false);
        bodyContainer.setScrollableY(true);
        
        cmdGraphBack = new Command(Main.getLocal().getMessage("back"));
        cmdRateBack = new Command(Main.getLocal().getMessage("back"));
        cmdRate = new Command(Main.getLocal().getMessage("rate"));
        cmdCheckinBack = new Command(Main.getLocal().getMessage("back"));
        cmdCheckin = new Command(Main.getLocal().getMessage("check_in"));
        cmdCommentBack = new Command(Main.getLocal().getMessage("back"));
        cmdComment = new Command(Main.getLocal().getMessage("comment"));
    }
    
    public void clearForm() {
        bodyContainer.removeAll();
        bodyWrapper.removeAll();
        removeAll();
        removeAllCommands();
    }
    
    public String getRateTemp() {
        return rateTemp;
    }
    
    public String getCheckinNumTemp() {
        return checkinNumTemp;
    }
    
    public int getDataNumTemp() {
        return dataNumTemp;
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getCommand() == cmdGraphBack) {
            main.showNowShowingForm();
        } else if(ae.getCommand() == cmdRateBack) {
            //main.showNowShowingForm();
            main.showDetailForm();
        } else if(ae.getCommand() == cmdRate) {
            //System.out.println("slider.getSliderValue() >>>> "+slider.getSliderValue());
            if(slider.getSliderValue() > 0) {
                rateTemp = Integer.toString(slider.getSliderValue());
                dataNumTemp = main.getActiveTVData().getNumber();
                main.rate(main.getActiveTVData().id, slider.getSliderValue());
            }
        } else if(ae.getCommand() == cmdCheckinBack) {
            /*
            if(backTo.equals("NowShowingForm")) {
                main.showNowShowingForm();
            } else {
                main.showDetailForm();
            }
            */
            main.showDetailForm();
        } else if(ae.getCommand() == cmdCheckin) {
            msg_comment = textField.getText().trim();
            if(msg_comment.equals("")) {
                System.out.println("MESSAGE IS BLANK");
                main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("please_fill"), null, false);
                return;
            }
            System.out.println("MESSAGE IS \""+msg_comment+"\"");
            int i = Integer.parseInt(main.getActiveTVData().checkinNum);
            i++;
            checkinNumTemp = Integer.toString(i);
            dataNumTemp = main.getActiveTVData().getNumber();
            main.checkin(main.getActiveTVData().id, msg_comment);
        } else if(ae.getCommand() == cmdCommentBack) {
            //main.showNowShowingForm();
            main.showDetailForm();
        } else if(ae.getCommand() == cmdComment) {
            msg_comment = textField.getText().trim();
            if(msg_comment.equals("")){
                main.showDialog(Main.getLocal().getMessage("error"), Main.getLocal().getMessage("please_fill"), null, false);
                return;
            }
            main.comment(main.getActiveTVData().refid, msg_comment);
        }
    }
    
    public void setTVLists(Vector tvLists) {
        this.tvLists = tvLists;
    }
    
    public void showGraph() {
        setTitle("Graph");
        int size = tvLists.size();
        int max = -1;

        for(int i=0; i<size; i++){
            TVListData tvdata = (TVListData) tvLists.elementAt(i);
            max = Math.max(max, Integer.parseInt(tvdata.checkinNum));
        }
        for(int i=0; i<size; i++){
            TVListData tvdata = (TVListData) tvLists.elementAt(i);
            bodyContainer.addComponent(new ItemStat(tvdata, max));
        }
        bodyWrapper.addComponent(bodyContainer);
        addComponent(bodyWrapper);
        addComponent(Main.getAds(2));
        
        setBackCommand(cmdGraphBack);
        addCommand(cmdGraphBack);
        addCommandListener(this);
        
        showRotationForm();
    }
    
    public void showVote() {
        setTitle(Main.getLocal().getMessage("rate"));
        
        slider = new Slider();
        slider.setEditable(true);
        slider.setRenderValueOnTop(true);
        slider.setMaxValue(5);
        
        addComponent(slider);
        
        setBackCommand(cmdRateBack);
        addCommand(cmdRateBack);
        addCommand(cmdRate);
        addCommandListener(this);
        
        showRotationForm();
    }
    
    public void showCheckin(String fromMsg) {
        backTo = fromMsg;
        setTitle(Main.getLocal().getMessage("check_in"));
        textField = new TextArea("", 10);
        textField.setSingleLineTextArea(false);
        textField.setMaxSize(255);
        //textField.setRows(3);
        
        addComponent(textField);
        
        setBackCommand(cmdCheckinBack);
        addCommand(cmdCheckinBack);
        addCommand(cmdCheckin);
        addCommandListener(this);
        
        showRotationForm();
    }
    
    public void showComment() {
        setTitle(Main.getLocal().getMessage("comment"));
        textField = new TextArea("", 10);
        textField.setSingleLineTextArea(false);
        textField.setMaxSize(255);
        //textField.setRows(3);
        
        addComponent(textField);
        
        setBackCommand(cmdCommentBack);
        addCommand(cmdCommentBack);
        addCommand(cmdComment);
        addCommandListener(this);
        
        showRotationForm();
    }
    
    public void showRotationForm() {
        bodyWrapper.setPreferredH((this.getCommandCount()>1) ? MyConstant.CONTENT_HEIGHT_WITH_COMMAND: MyConstant.CONTENT_HEIGHT); //285
        show();
    }
    
    class ItemStat extends Container {
        
        private TVListData data;
        private Font fSmall = MyFont.fontSmall;
        private Font fMedium = MyFont.fontMedium;
        private int containerH = 52;
        private int graphW = 156;
        private int graphH = 26;
        private int digitW = 56;
        private String cutTitle;
        private int channelW;
        private int availTitleW;
        private int graphMax;
        private int cNum;

        public ItemStat(TVListData tvdata, int max) {
            setPreferredSize(new Dimension(Main.getScreenWidth(), containerH));
            this.data = tvdata;
            this.graphMax = max;
            
            channelW = fSmall.stringWidth(data.channel);
            availTitleW = Main.getScreenWidth() - channelW - 28;
            cutTitle = MyUtil.cutText(data.title, fSmall, availTitleW);
            cNum = Integer.parseInt(data.checkinNum);
            //System.out.println("titleW >>>> "+titleW);
            //System.out.println("channelW >>>> "+channelW);
            //System.out.println("availTitleW >>>> "+availTitleW);
            
        }

        public void paint(Graphics g) {
            int x = getX();
            int y = getY();
            g.setFont(fSmall);
            g.setColor(0);
            g.drawString(cutTitle, x + 4, y);
            g.setColor(0x656565);
            g.drawString("("+data.channel+")", x + (fSmall.stringWidth(cutTitle)) + 6, y);
            
            if((cNum == graphMax) && (cNum != 0)) {
                g.setColor(0xe82e33);
            } else {
                g.setColor(0x333333);
            }
            g.fillRect(x + 4, y + 18, digitW, graphH);
            g.setColor(0xe8575c);
            g.drawRect(x + 4 + digitW, y + 18, graphW, graphH - 1);
            g.fillRect(x + 4 + digitW, y + 18, (int) (graphW * ((float)cNum/(float)graphMax)), graphH);
            
            g.setFont(fMedium);
            g.setColor(0xFFFFFF);
            g.drawString(data.checkinNum, x + 8, y + 22);
        }
        
    }
    
}