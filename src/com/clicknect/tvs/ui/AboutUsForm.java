/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.clicknect.tvs.util.LocalizationSupport;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class AboutUsForm extends Form implements ActionListener {
    
    private Command cmdBack;
    private LocalizationSupport local;

    public AboutUsForm() {
        local = new LocalizationSupport();
        
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.setTitle(local.getMessage("about_us"));
        this.getStyle().setBgColor(0x000000);
        this.getStyle().setPadding(24, 0, 0, 0);
        
        cmdBack = new Command(local.getMessage("back"));
        this.setBackCommand(cmdBack);
        this.addCommandListener(this);
        
        Label devName = tagLabel(local.getMessage("developer_name"));
        Label devNameValue = valueLabel(Main.getMIDlet().getAppProperty("MIDlet-Vendor"));
        Label appName = tagLabel(local.getMessage("application_name"));
        Label appNameValue = valueLabel(Main.getMIDlet().getAppProperty("MIDlet-Name"));
        Label verName = tagLabel(local.getMessage("version_number"));
        Label verNameValue = valueLabel(Main.getMIDlet().getAppProperty("MIDlet-Version"));
        
        this.addComponent(devName);
        this.addComponent(devNameValue);
        this.addComponent(appName);
        this.addComponent(appNameValue);
        this.addComponent(verName);
        this.addComponent(verNameValue);
    }
    
    private Label tagLabel(String text) {
        Style labelStyle = UIManager.getInstance().getComponentStyle("Label");
        labelStyle.setBgTransparency(0);
        labelStyle.setFgColor(0xFFFFFF);
        labelStyle.setMargin(12, 0, 0, 0);
        labelStyle.setAlignment(Component.CENTER);
        
        Label label = new Label(text);
        label.setPressedStyle(labelStyle);
        label.setUnselectedStyle(labelStyle);
        return label;
    }
    
    private Label valueLabel(String text) {
        Style labelStyle = UIManager.getInstance().getComponentStyle("Label");
        labelStyle.setBgTransparency(0);
        labelStyle.setFgColor(0x0080FF);
        labelStyle.setAlignment(Component.CENTER);
        
        Label label = new Label(text);
        label.setPressedStyle(labelStyle);
        label.setUnselectedStyle(labelStyle);
        return label;
    }
    
    public void actionPerformed(ActionEvent ae) {
        //midlet.showMainPage();
        //TVSMIDlet.getInstance().showChannelForm();
    }
    
}