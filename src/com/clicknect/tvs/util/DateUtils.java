/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

import java.util.Date;

/**
 *
 * @author Sutthinart Khunvadhana <iakgoog@gmail.com>
 */
public final class DateUtils {
    
    public static final String FACEBOOK_LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FACEBOOK_SHORT_DATE_FORMAT = "MM/dd/yyyy";
    public static final String FACEBOOK_MONTH_YEAR_DATE_FORMAT = "yyyy-MM";
    
    private DateUtils() {
    }

    public static Date parse(final String date) {
        if (date == null) {
                return null;
        }

        Date d = new Date(){
            public String toString(){
                return date;
            }
        };

       return d;
    }

}
