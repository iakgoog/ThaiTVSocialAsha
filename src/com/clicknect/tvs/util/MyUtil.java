/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

import com.sun.lwuit.Button;
import com.sun.lwuit.Font;
import com.sun.lwuit.plaf.Style;
import java.util.Vector;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class MyUtil {
    
    public static Vector wrapToLines(String text, Font f, int firstWidth, int maxWidth) {
        Vector myLines = new Vector ();
        boolean paragraphFormat = false;
        if(text == null) {
            return myLines;
        }
        if(f.stringWidth(text) < firstWidth) {
            myLines.addElement(text);
            return myLines;
        } else {
            char chars[] = text.toCharArray();
            int len = chars.length;
            int count = 0;
            int charWidth = 0;
            int curLinePosStart = 0;
            char bar = ' ';
            while(count < len) {
                if((charWidth += f.charWidth(chars[count])) > (maxWidth - 4) || count == len - 1) { // wrap to next line
                    if(count == len - 1) {
                        count++;
                    }
                    String line = new String(chars, curLinePosStart, count - curLinePosStart);
                    if(paragraphFormat) {
                        int lastSpacePosition = line.lastIndexOf((int) bar);
                        String l = new String(chars, curLinePosStart, (lastSpacePosition != -1) ? lastSpacePosition + 1 : (count == len - 1) ? count - curLinePosStart + 1 : count - curLinePosStart);
                        myLines.addElement(l);
                        curLinePosStart = (lastSpacePosition != -1) ? lastSpacePosition + 1 : count;
                    } else {
                        myLines.addElement(line);
                        curLinePosStart = count;
                    }
                    charWidth = 0;
                }
                count++;
            }
            return myLines;
        }
    }
    
    public static String cutText(String s, Font f, int widthLimit) {
        //80 maximum string width
        String returnText = s;
        //System.out.println("f.stringWidth(returnText) ======== "+f.stringWidth(returnText));
        if(returnText == null) {
            return returnText;
        } else if(f.stringWidth(returnText) < widthLimit) {
            return returnText;
        }
        int len = returnText.length();
        int count = 0;
        do {            
            if(f.stringWidth(returnText) < widthLimit) {
                returnText += "...";
                return returnText;
            } else {
                int length = returnText.length();
                returnText = returnText.substring(0, length - 1);
                //System.out.println("cutText >>>> "+returnText);
            }
            count++;
        } while (count < len);
        return null;
    }
    
    public static Vector divideParagraph(String str) {
        Vector paragraph = new Vector ();
        int s = 0;
        int e = 0;
        while ((e = str.indexOf("<p>", s)) >= 0 ) {
            paragraph.addElement(replace(str.substring(e + 3, str.indexOf("</p>", e)), 0));
            //result.append(str.substring(s, e) );
            //result.append( rep[index] );
            s = str.indexOf("</p>", e);
        }
        return paragraph;
    }
    
    public static String replace(String str, int pattern) {
        String[] tag = {"&nbsp;", "&ldquo;", "&rdquo;", "′"};
        String[] rep = {" ", "�?", "�?", "'"};
        int s = 0;
        int e = 0;
        int index = pattern;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(tag[index], s)) >= 0 ) {
            result.append(str.substring(s, e) );
            result.append( rep[index] );
            s = e + tag[index].length();
        }
        result.append(str.substring(s));
        index++;
        //System.out.println("result.toString() >> "+result.toString());
        if(index < tag.length) {
            return replace(result.toString(), index);
        } else {
            return result.toString();
        }
    }
    
    public static void SetButtonColor(Button btn, int bgColor, int fgColor){
        Style btnstyle = btn.getStyle();
        btnstyle.setBgColor(bgColor);
        Style selectedStyle = btn.getSelectedStyle();
        selectedStyle.setBgColor(bgColor);
        selectedStyle.setFgColor(fgColor, false);
    }
    
}