package com.clicknect.tvs.ui;


import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;

public class MyDialog extends Dialog {
    
    private Label labelComplete;
    private Label labelIncomplete;
    private TextArea textArea;
    private Button btnOK;
    
    public MyDialog() {
        super();
        setLayout(new BorderLayout());
        setScrollVisible(false);
        
        try {
            btnOK = new Button(new Command("ok") {
                public void actionPerformed(ActionEvent evt) {
                    dispose();
                    Display.getInstance().getCurrent().repaint();
                }
            });
            Style buttonStyle = UIManager.getInstance().getComponentStyle("ButtonBlack");
            btnOK.setUnselectedStyle(buttonStyle);
            btnOK.setSelectedStyle(buttonStyle);
            btnOK.setPreferredW(180);
            btnOK.getStyle().setAlignment(Button.CENTER);
            
            textArea = new TextArea(2, 12);
            textArea.setUIID("Label");
            textArea.setGrowByContent(true);
            textArea.setEditable(false);
            textArea.setFocusable(false);
            textArea.getStyle().setFgColor(0xffffff);
            textArea.getStyle().setAlignment(TextArea.CENTER);
            //textArea.getStyle().setBgColor(0x383838);
            
            byte bgTransparency = this.getDialogStyle().getBgTransparency();
            textArea.getStyle().setBgTransparency(bgTransparency);
        } catch (Exception e) {
        }
    }
    
    public void doDispose() {
        dispose();
        Display.getInstance().getCurrent().repaint();
    }
    
    public void showDialog(String title, String body, Button buttonOK, boolean complete) {
        this.removeAll();
        this.setTitle(title);
        textArea.setText(body);
        this.addComponent(BorderLayout.NORTH, textArea);
        //this.addComponent(BorderLayout.SOUTH, btnOK);
        this.addComponent(BorderLayout.SOUTH, (buttonOK != null) ? buttonOK : btnOK);
        try {
            System.out.println("-------- Dialog ia about to show in 0.1 second --------");
            //Thread.sleep(800);
            Display.getInstance().getCurrent().repaint();
            show();
        } catch (Exception e) {
        }
        /*
        new Thread() {
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                show();
            }
        }.start();
        */
    }

}