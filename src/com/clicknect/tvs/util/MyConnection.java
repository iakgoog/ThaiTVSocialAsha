/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

import com.clicknect.tvs.ui.ConnectionErrorDialog;
import com.clicknect.tvs.ui.Main;
import com.sun.lwuit.io.ConnectionRequest;
import java.io.IOException;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class MyConnection extends ConnectionRequest {

    protected void handleErrorResponseCode(int code, String message) {
        //Main.getInstance().showWelcomeScreen();
        ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"), Main.getMIDlet());
        errorDialog.showThis();
    }
    
    protected void handleIOException(IOException err) {
        //Main.getInstance().showWelcomeScreen();
        ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"), Main.getMIDlet());
        errorDialog.showThis();
    }

    protected void handleRuntimeException(RuntimeException err) {
        //Main.getInstance().showWelcomeScreen();
        ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"), Main.getMIDlet());
        errorDialog.showThis();
    }
    
}
