/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.TVSMIDlet;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class ConnectionErrorDialog extends Dialog {
    
    private TVSMIDlet midlet;
    private Container outerContainer;
    private Container innerContainer;
    private Command cancel;

    public ConnectionErrorDialog(String t, final TVSMIDlet midlet) {
        //super(t);
        System.out.println("xxxxxxxx ErrorDialog has been constructed!!!! xxxxxxxx");
        //this.midlet = midlet;
        setLayout(new BorderLayout());
        
        outerContainer = new Container(new BorderLayout());
        innerContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container south = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        //Label error0 = new Label(TSNMIDlet.getLocal().getMessage("connection_error_0"));
        //error0.getStyle().setBgTransparency(0);
        //Label error1 = new Label(TSNMIDlet.getLocal().getMessage("connection_error_1"));
        //error1.getStyle().setBgTransparency(0);
        //Label error2 = new Label(TSNMIDlet.getLocal().getMessage("connection_error_2"));
        //error2.getStyle().setBgTransparency(0);
        
        TextArea textArea = new TextArea(2, 10);
        textArea.setText(Main.getLocal().getMessage("connection_error_b"));
        textArea.setUIID("Label");
        textArea.setGrowByContent(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.getStyle().setFgColor(0xffffff);
        
        byte bgTransparency = this.getDialogStyle().getBgTransparency();
        textArea.getStyle().setBgTransparency(bgTransparency);
        
        innerContainer.addComponent(textArea);
        
        cancel = new Command("OK") {
            public void actionPerformed(ActionEvent evt) {
                //midlet.setForceQuit(true);
                dispose();
                Display.getInstance().getCurrent().repaint();
                midlet.notifyDestroyed();
            }
        };
        Button cancelButton = new Button(cancel);
        Style buttonStyle = UIManager.getInstance().getComponentStyle("DialogButton");
        cancelButton.setUnselectedStyle(buttonStyle);
        cancelButton.setSelectedStyle(buttonStyle);
        //cancelButton.setPreferredW(180);
        cancelButton.getStyle().setAlignment(Button.CENTER);
        
        south.addComponent(cancelButton);
        
        outerContainer.addComponent(BorderLayout.CENTER, innerContainer);
        
        addComponent(BorderLayout.NORTH, outerContainer);
        addComponent(BorderLayout.SOUTH, south);
    }

    protected void actionCommand(Command cmd) {
        /*
        midlet.notifyDestroyed();
        dispose();
        System.out.println("Command was pressed!!!!");
        */
        //midlet.forceQuit();
    }
    
    
    public void showThis() {
        System.out.println("ErrorDialog has been Shown!!!!");
        show();
    }
    
}