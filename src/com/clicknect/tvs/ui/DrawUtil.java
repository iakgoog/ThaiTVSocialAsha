/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.sun.lwuit.Font;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class DrawUtil {
    
    public static void drawButton(Graphics g, int x, int y, int w, int h, int bgColor, int fgColor, String text, Font f, boolean fill, Image img) {
        g.setColor(bgColor);
        //g.drawRoundRect(x, y, w, h, h/2, h/2);
        if(fill) {
            g.fillRoundRect(x, y, w, h, h, h);
        } else {
            g.drawRoundRect(x, y, w, h, h, h);
        }
        
        g.setColor(fgColor);
        g.setFont(f);
        if(img == null) {
            g.drawString(text, ((w - f.stringWidth(text)) / 2) + x, ((h - f.getHeight()) / 2) + y);
        } else {
            int xOrigin = (w - (f.stringWidth(text) + img.getWidth() + 2)) / 2;
            g.drawString(text, x + xOrigin, ((h - f.getHeight()) / 2) + y);
            g.drawImage(img, x + xOrigin + f.stringWidth(text) + 2, ((h - img.getHeight()) / 2) + y);
        }
    }
    
}
