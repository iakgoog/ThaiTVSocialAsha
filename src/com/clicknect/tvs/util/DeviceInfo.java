/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

import java.util.Random;

/**
 *
 * @author Administrator
 */
public class DeviceInfo {
    public static String UNKNOW_IMEI;// = "JENK00000000001";
    
    public static String getIMEI(){
        //if(true) return UNKNOW_IMEI;
        
        String imei = null;
        imei = System.getProperty("imei");
        if(imei==null) imei = System.getProperty("IMEI");
        if(imei==null) imei = System.getProperty("imei");
        if(imei==null) imei = System.getProperty("mid.imei");
        if(imei==null) imei = System.getProperty("mid.IMEI");
        if(imei==null) imei = System.getProperty("com.nokia.mid.imei");
        if(imei==null) imei = System.getProperty("com.nokia.mid.IMEI");
        if(imei==null) imei = System.getProperty("phone.imei");
        if(imei==null) imei = System.getProperty("com.nokia.IMEI");
        if(imei==null) imei = System.getProperty("com.nokia.imei");
        /*if(imei==null){
            try {
                imei = LocalDevice.getLocalDevice().getBluetoothAddress();
            } catch (BluetoothStateException ex) {
                ex.printStackTrace();
            }
        }*/
        //Random IMEI
        if(UNKNOW_IMEI==null){
            //Random r = new Random(5000);
            //UNKNOW_IMEI = r.nextLong()+"_"+r.nextLong();
            char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            String sb = new String();
            Random random = new Random();
            char[] c = new char[20];
            for (int i = 0; i < 20; i++) {
                c[i] = chars[random.nextInt(chars.length)];
                
            }
            sb = new String(c);
            UNKNOW_IMEI = sb;
            //UNKNOW_IMEI = "112233445566";
            //String output = sb.toString();
        }
        
        System.err.println("imei = "+imei);
        if(imei==null) imei = UNKNOW_IMEI;
        else if(imei.equals("004402133751100")) imei = "XX11222334455";
        return imei;
    }
    
    public static String getResourceRootPath(){
        return System.getProperty("fileconn.dir.memorycard");
    }
    
    public static String getResourcePath(String path){
        return getResourceRootPath() + path;
    }
    
    private static int w, h;
    public static int getWidth(){
        return w;
    }
    
    public static int getHeight(){
        return h;
    }
    
    public static void setDisplaySize(int w, int h){
        DeviceInfo.w = w;
        DeviceInfo.h = h;
    }
}
