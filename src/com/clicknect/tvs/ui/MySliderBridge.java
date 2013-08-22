/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.sun.lwuit.Slider;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.io.ConnectionRequest;
import com.sun.lwuit.io.NetworkEvent;
import com.sun.lwuit.io.NetworkManager;
import java.util.Vector;

/**
 * Binds a slider to network progress events so it shows the pro
 *
 * @author Shai Almog
 */
public class MySliderBridge extends Slider {
    private ConnectionRequest[] sources;

    public MySliderBridge() {
        bindProgress((ConnectionRequest[])null, this);
    }

    /**
     * Allows binding progress to an arbitrary slider
     *
     * @param source the source connection request 
     * @param s the slider
     */
    public static void bindProgress(final ConnectionRequest source, final Slider s) {
        if(source == null) {
            bindProgress((ConnectionRequest[]) null, s);
        } else {
            bindProgress(new ConnectionRequest[] {source}, s);
        }
    }
    
    /**
     * Allows binding progress to an arbitrary slider
     * 
     * @param sources the source connection request (null for all network activity)
     * @param s the slider
     */
    public static void bindProgress(final ConnectionRequest[] sources, final Slider s) {
        Vector v = null;
        int portions = 100;
        if(sources != null) {
            v = new Vector();
            for(int iter = 0 ; iter < sources.length ; iter++) {
                v.addElement(sources[iter]);
            }
            portions = portions / sources.length;
        }
        final Vector sourceVec = v;
        final int portionPerSource = portions;
        NetworkManager.getInstance().addProgressListener(new ActionListener() {
            private float currentLength;
            private int soFar;

            /**
             * @inheritDoc
             */
            public void actionPerformed(ActionEvent evt) {
                if(sources != null) {
                    if(!sourceVec.contains(evt.getSource())) {
                        return;
                    }
                }
                NetworkEvent e = (NetworkEvent)evt;
                switch(e.getProgressType()) {
                    case NetworkEvent.PROGRESS_TYPE_COMPLETED:
                        s.setInfinite(false);
                        //s.setProgress(s.getMaxValue());
                        soFar += portionPerSource;
                        s.setProgress(soFar);
                        if(sources != null) {
                            NetworkManager.getInstance().removeProgressListener(this);
                        }
                        break;
                    case NetworkEvent.PROGRESS_TYPE_INITIALIZING:
                        s.setInfinite(true);
                        break;
                    case NetworkEvent.PROGRESS_TYPE_INPUT:
                    case NetworkEvent.PROGRESS_TYPE_OUTPUT:
                        if(e.getLength() > 0) {
                            currentLength = e.getLength();
                            s.setMaxValue(1000);
                            s.setInfinite(false);
                            float sentReceived = e.getSentReceived();
                            sentReceived = sentReceived / currentLength * portionPerSource;
                            s.setProgress((int)sentReceived + soFar);
                            //s.setProgress(e.getSentReceived());
                            //s.setMaxValue(e.getLength());
                        } else {
                            s.setInfinite(true);
                        }
                        break;
                }
            }
        });
    }

    /**
     * Displays progress only for the source object, every other object in the queue
     * before completion will produce an infinite progress. After 100% the progress will
     * no longer move.
     *
     * @param source the request whose progress should be followed
     */
    public MySliderBridge(ConnectionRequest source) {
        if(source != null) {
            sources = new ConnectionRequest[] {source};
        }
        bindProgress(sources, this);
    }

    /**
     * Allows displaying progress of multiple requests being sent
     *
     * @param sources the requests whose progress should be followed
     */
    public MySliderBridge(ConnectionRequest[] sources) {
        this.sources = sources;
        bindProgress(sources, this);
    }
}
