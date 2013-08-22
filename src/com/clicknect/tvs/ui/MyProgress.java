/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */

import com.clicknect.tvs.util.MyCallback;
import com.nokia.lwuit.components.InfiniteProgressIndicator;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.io.ConnectionRequest;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.sun.lwuit.plaf.UIManager;

/**
 * Displays a progress dialog with the ability to cancel an ongoing operation
 *
 * @author Shai Almog
 */
public class MyProgress extends Dialog implements ActionListener {
    private ConnectionRequest request = null;
    private boolean disposeOnCompletion;
    private boolean autoShow;
    private Command cmdCancel;
    private MyCallback listener;
    private String body;

    public MyProgress(String title, ConnectionRequest request) {
        //this(title, request, false);
    }

    public MyProgress(String title, String body, Image image, ConnectionRequest request, MyCallback listener) {
        super(title);
        this.request = request;
        this.body = body;
        this.listener = listener;
        System.out.println("MyProgress with ConnectionRequest was constructed!!!");
        
        setupProgress(body, image);
        
        setDisposeWhenPointerOutOfBounds(false);
        setAutoDispose(false);
        NetworkManager.getInstance().addProgressListener(this);
    }
    
    public MyProgress(String title, String body, Image image) {
        super(title);
        System.out.println("MyProgress was constructed!!!");
        setupProgress(body, image);
    }
    
    private void setupProgress(String body, Image image) {
        setLayout(new BorderLayout());
        InfiniteProgressIndicator progress = new InfiniteProgressIndicator(InfiniteProgressIndicator.SPINNER_MED);
        progress.setPreferredSize(new Dimension(38, 38));
        progress.setFocusable(false);
        Container progressCon = new Container(new FlowLayout(Component.CENTER));
        progressCon.addComponent(progress);
        
        Label pictureLabel;
        if(image!=null) {
            Image icon = image;
            pictureLabel = new Label(icon);
        } else {
            pictureLabel = new Label("[Icon Missing]");
        }
        TextArea textArea = new TextArea(2, 12);
        //textArea.setText("In-determinate progress indicator");
        textArea.setText(body);
        textArea.setUIID("Label");
        textArea.setGrowByContent(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.getStyle().setFgColor(0xffffff);
        textArea.getStyle().setAlignment(TextArea.CENTER);
        
        cmdCancel = new Command("Cancel");
        Style buttonStyle = UIManager.getInstance().getComponentStyle("ButtonBlack");
        Button cancelButton = new Button(cmdCancel);
        cancelButton.setUnselectedStyle(buttonStyle);
        cancelButton.setSelectedStyle(buttonStyle);
        cancelButton.setPreferredW(180);
        cancelButton.getStyle().setAlignment(Button.CENTER);
        
        byte bgTransparency = this.getDialogStyle().getBgTransparency();
        pictureLabel.getStyle().setBgTransparency(bgTransparency);
        textArea.getStyle().setBgTransparency(bgTransparency);
        
        Container north = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //north.addComponent(pictureLabel);
        north.addComponent(progressCon);
        north.addComponent(textArea);
        
        addComponent(BorderLayout.NORTH, north);
        addComponent(BorderLayout.SOUTH, cancelButton);
    }

    protected void actionCommand(Command cmd) {
        /*
        if(Display.getInstance().isTouchScreenDevice() || getSoftButtonCount() < 2) {
            for(int iter = 0 ; iter < getComponentCount() ; iter++) {
                Component c = getComponentAt(iter);
                if(c instanceof Button) {
                    c.setEnabled(false);
                }
            }
        } else {
            removeAllCommands();
        }
        */
        // cancel was pressed
        if(request!=null) {
            //request.kill();
            NetworkManager.getInstance().killAndWait(request);
        }
        dispose();
        //((Dialog) Display.getInstance().getCurrent()).dispose();
        Display.getInstance().getCurrent().repaint();
    }

    public boolean isDisposeOnCompletion() {
        return disposeOnCompletion;
    }

    public void setDisposeOnCompletion(boolean disposeOnCompletion) {
        this.disposeOnCompletion = disposeOnCompletion;
    }

    public void actionPerformed(ActionEvent evt) {
        //System.out.println(">>>> Progress is showing this "+Display.getInstance().getCurrent()+" form");
        if(evt.getCommand() == cmdCancel) {
            System.out.println("Cancel was pressed!!!");
        }
        NetworkEvent ev = (NetworkEvent) evt;
        if(ev.getConnectionRequest() == request) {
            if(disposeOnCompletion && ev.getProgressType() == NetworkEvent.PROGRESS_TYPE_COMPLETED) {
                dispose();
                Display.getInstance().getCurrent().repaint();
                this.listener.onComplete();
                System.out.println("==== Connection \""+this.body+"\" is completed and Progress was disposed!!! ====");
                return;
            }
            if(autoShow && Display.getInstance().getCurrent() != this) {
                show();
            }
        }
    }

    public boolean isAutoShow() {
        return autoShow;
    }

    public void setAutoShow(boolean autoShow) {
        this.autoShow = autoShow;
    }
    
}