/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs;

import com.clicknect.tvs.ui.Main;
import com.sun.lwuit.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class TVSMIDlet extends MIDlet {
    
    private Main main = new Main();
    
    protected void startApp() throws MIDletStateChangeException {
        Display.init(this);
        main.startApp(this);
    }

    protected void pauseApp() {
    	main.pauseApp();
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        System.out.println("destroyApp()");
        main.destroyApp(unconditional);
    }
    
}