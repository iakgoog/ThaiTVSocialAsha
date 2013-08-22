package com.clicknect.tvs.ui;

import buzzcity.java.mobile.*;
import com.sun.lwuit.Container;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.layouts.BoxLayout;

public class AdsContainer extends Container {
    
    public static final int PID = 33960;
    private BCAdsClientBanner bannerAdClient;
    private Banner bannerBanner;
    private Image imgBanner;
    //private String adText;
    
    public AdsContainer() {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setPreferredH(36);
        this.setVisible(true);
        
        bannerAdClient = new BCAdsClientBanner(PID, 3, Main.getMIDlet());
        
        try {
            bannerBanner = bannerAdClient.getBanner();
        } catch(Exception e) {
            
        }
        
        if (bannerBanner != null) {
            //Display ad based on its type
            if (bannerBanner.getType().equals("image")) {
                imgBanner = Image.createImage(bannerBanner.getItem());
                System.out.println("(((( imgBanner is IMAGE ))))");
            }/* else {
                adText = (String) bannerBanner.getItem();
                System.out.println("(((( imgBanner is TEXT ))))");
            }*/
        } else {
            System.out.println("bannerBanner ================ null");
        }
        //repaint();
    }

    public void paint(Graphics g) { //draw ads here
        //g.setColor(0xF2F2F2);
        //g.fillRect(getX(), getY(), getWidth(), getHeight());
        if(imgBanner != null) {
            //g.drawImage(imgBanner, x + (getWidth()-imgBanner.getWidth())/2 , getY());
            g.drawImage(imgBanner, getX(), getY());
        }
    }

    public void pointerReleased(int x, int y) {
        //System.out.println("x : "+x+" | y : "+y);
        if(imgBanner != null) {
            bannerAdClient.clickAd();
        }
    }
    
}