/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

//import com.clicknect.dtv.ui.ConnectionErrorDialog;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.io.services.ImageDownloadService;
import java.io.IOException;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class MyImageDownload extends ImageDownloadService {

    public MyImageDownload(String url, ActionListener l) {
        super(url, l);
    }

    protected void handleErrorResponseCode(int code, String message) {
        //ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"));
        //errorDialog.showThis();
    }
    
    protected void handleIOException(IOException err) {
        //ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"));
        //errorDialog.showThis();
    }

    protected void handleRuntimeException(RuntimeException err) {
        //ConnectionErrorDialog errorDialog = new ConnectionErrorDialog(Main.getLocal().getMessage("connection_error_h"));
        //errorDialog.showThis();
    }
    
}
