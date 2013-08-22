package com.clicknect.tvs.ui;

import com.clicknect.tvs.util.MyFont;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;

public class LoginForm extends Form {
    
    private Main main;
    
    private Image bgImg;
    
    private String buttonText = Main.getLocal().getMessage("login_with_fb");
    private Command cmdLoginFacebook;
    
    public LoginForm(Main m) {
        this.main = m;
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollVisible(false);
        setScrollable(false);
        setFocusable(false);
        setVisible(true);
        
        Main.setScreenHeight(Display.getInstance().getDisplayHeight());
        Main.setScreenWidth(Display.getInstance().getDisplayWidth());
        System.out.println("deviceScreenWidth = <"+Main.getScreenWidth()+">");
        System.out.println("deviceScreenHeight = <"+Main.getScreenHeight()+">");
        
        try {
            bgImg = Image.createImage("/loginBG.png");
            getStyle().setBgImage(bgImg);
            getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_ALIGNED_TOP_LEFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        cmdLoginFacebook = new Command(buttonText);
        
        setDefaultCommand(cmdLoginFacebook);
        addCommand(cmdLoginFacebook);
        addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(ae.getCommand() == cmdLoginFacebook) {
                    main.checkFacebookLogin();
                }
            }
        });
    }
    /*
    public void paint(Graphics g) {
        int x = getX();
        int y = getY();
        //System.out.println("x = "+x);
        //System.out.println("y = "+y);
        g.setColor(0xe2e3e5);
        g.fillRect(x, y + Main.getScreenHeight() - componentH, componentW, componentH);
        DrawUtil.drawButton(g, x + buttonX, y + buttonY, buttonW, buttonH, 0x000000, 0xFFFFFF, buttonText, MyFont.fontMedium, true, null);
    }
    */
    
    /*
    public void pointerReleased(int x, int y) {
        System.out.println("x = "+ x + " || y = "+y);
        if(((x > buttonX) && (x < (buttonX + componentW))) && (y > buttonY) && (y < (buttonY + buttonH))) {
            main.checkFacebookLogin();
        }
        repaint();
    }
    */
    
}