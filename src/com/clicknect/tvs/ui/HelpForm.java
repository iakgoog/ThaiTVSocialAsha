/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.TVSMIDlet;
import com.clicknect.tvs.util.MyFont;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class HelpForm extends Form implements ActionListener {
    
    private Command cmdBack;
    
    public HelpForm() {
        final TVSMIDlet thisMidlet = Main.getMIDlet();
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.setTitle(Main.getLocal().getMessage("help"));
        this.getStyle().setBgColor(0x000000);
        
        cmdBack = new Command(Main.getLocal().getMessage("back"));
        this.setBackCommand(cmdBack);
        this.addCommandListener(this);
        
        TextArea helpText = generateTextArea();
        helpText.setText(Main.getLocal().getMessage("help_message"));
        
        Button button = new Button(new Command(Main.getLocal().getMessage("help_link")) {
            public void actionPerformed(ActionEvent evt) {
                //thisMidlet.goToLink(Main.getLocal().getMessage("help_link"));
            }
        });
        button.getUnselectedStyle().setBorder(null);
        button.getSelectedStyle().setBorder(null);
        button.getUnselectedStyle().setFont(MyFont.fontSmall);
        button.getSelectedStyle().setFont(MyFont.fontSmall);
        button.getUnselectedStyle().setFgColor(0x0000ff);
        button.getSelectedStyle().setFgColor(0x0000ff);
        
        this.addComponent(helpText);
        this.addComponent(button);
    }
    
    private TextArea generateTextArea() {
        TextArea textArea = new TextArea(2, 12);
        textArea.setUIID("Label");
        textArea.setGrowByContent(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        //textArea.setUseStringWidth(true);
        textArea.getStyle().setBgTransparency(0);
        textArea.getStyle().setBorder(null);
        textArea.getStyle().setFgColor(0xFFFFFF);
        textArea.getStyle().setFont(MyFont.fontSmall);
        return textArea;
    }
    
    public void actionPerformed(ActionEvent ae) {
        //Main.getInstance().showChannelForm();
    }
    
}