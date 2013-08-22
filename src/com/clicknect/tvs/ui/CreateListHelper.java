/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.ui;

import com.sun.lwuit.List;
import com.sun.lwuit.list.ListCellRenderer;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public class CreateListHelper {
    
    public static List createList(TVListData[] data, int orientation, ListCellRenderer renderer) {
        List list = new List(data);
        list.getStyle().setBgTransparency(0);
        list.setListCellRenderer(renderer);
        list.setOrientation(orientation);
        list.setPaintFocusBehindList(true);
        return list;
    }
    
}