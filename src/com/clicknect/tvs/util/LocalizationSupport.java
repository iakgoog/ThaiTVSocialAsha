/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clicknect.tvs.util;

/**
 *
 * @author iakgoog
 */
public class LocalizationSupport {
    
    private int langNum;
    
    private final String[] EXIT = {"exit", "ออก"};
    private final String[] BACK = {"back", "ย้อนกลับ"};
    private final String[] OK = {"ok", "ตกลง"};
    private final String[] EN_LANG = {"English", "อังกฤษ"};
    private final String[] TH_LANG = {"Thai", "ไทย"};
    private final String[] LANGUAGE = {"Language", "ตัวเลือกภาษา"};
    private final String[] APP_NAME = {"Thai TV Social", "Thai TV Social"};
    private final String[] CONNECTION_ERROR_H = {"Connect", "เชื่อมต่อ"};
    private final String[] CONNECTION_ERROR_0 = {"Please check", "กรุณาตรวจสอบ"};
    private final String[] CONNECTION_ERROR_1 = {"your connection.", "การเชื่อมต่อของคุณ"};
    private final String[] CONNECTION_ERROR_2 = {"Try again later.", "ลองอีกครั้งภายหลัง"};
    private final String[] CONNECTION_ERROR_B = {"Please check you internet connection and restart the Application", "กรุณาตรวจสอบการเชื่อมต่อและเริ่มต้นแอพพลิเคชันใหม่"};
    private final String[] FACEBOOK = {"facebook", "เฟซบุ๊ค"};
    private final String[] PUBLISH_COMPLETED_H = {"Successful", "สำเร็จ"};
    private final String[] PUBLISH_COMPLETED = {"Your message has been sent", "ข้อความของคุณถูกส่งแล้ว"};
    private final String[] PUBLISH_FAILED_H = {"Error", "ผิดพลาด"};
    private final String[] PUBLISH_FAILED = {"Publish failed!", "โพสต์ข้อความไม่สำเร็จ"};
    private final String[] FACEBOOK_LOGOUT = {"Facebook logout", "ล็อคเอาท์เฟซบุ๊ค"};
    private final String[] LOGOUT_FROM_FACEBOOK_H = {"Logged out", "ล็อคเอาท์"};
    private final String[] LOGOUT_FROM_FACEBOOK = {"logout from facebook", "ล็อคเอาท์จากเฟซบุ๊ค"};
    private final String[] LOGIN_FAILED_H = {"Error", "ผิดพลาด"};
    private final String[] LOGIN_FAILED = {"Login failed!", "ล็อคอินไม่สำเร็จ"};
    private final String[] LOGOUT_FAILED_H = {"Error", "ผิดพลาด"};
    private final String[] LOGOUT_FAILED = {"Please try again", "กรุณาลองใหม่อีกครั้ง"};
    private final String[] LOGOUT_DONE_H = {"Success", "สำเร็จ"};
    private final String[] LOGOUT_DONE = {"You have logged out from Facebook", "คุณได้ลอกเอาท์จากเฟซบุ๊ค"};
    private final String[] PROGRESS_H = {"In Progress", "กำลังดำเนินการ"};
    private final String[] PROGRESS_B = {"Please wait while loading", "โปรดรอสักครู่"};
    private final String[] PROGRESS_CONNECT_TO_FACEBOOK = {"Connecting to Facebook", "กำลังเชื่อมต่อเฟซบุ๊ค"};
    private final String[] PROGRESS_CHECK_LOGIN = {"Checking login status", "กำลังตรวจสอบสถานะลอกอิน"};
    private final String[] PROGRESS_LOGOUT = {"Logging out", "กำลังลอกเอาท์"};
    private final String[] PROGRESS_LOAD_TVLIST = {"Loading TV List", "กำลังโหลดรายการทีวี"};
    private final String[] ABOUT_US = {"about us", "เกี่ยวกับเรา"};
    private final String[] PRIVACY_POLICY = {"Privacy policy", "ความเป็นส่วนตัว"};
    private final String[] TERM_OF_USE = {"term of use", "ข้อกำหนดการใช้งาน"};
    private final String[] HELP = {"help", "ความช่วยเหลือ"};
    private final String[] HELP_MESSAGE = { 
        "Start DooTV application, tab at TV button to view list of TV program by channel or at eyes button for popular TV program. Select that TV program you preferred to view the episode list. The selected VDO will shown by full screen side of landscape lay out.",
        "ในการเริ่มใช้ DOOTV ให้เลือกรายการทีวีที่คุณต้องการชม คุณสามารถเลือกการแสดงผลรายการทีวีให้จัดเรียงตามช่องหรือตามรายการทีวียอดนิยมได้ โดยการกดปุ่มรูปทีวีหรือปุ่มรูปตาด้านมุมบนขวา เมื่อคุณเลือกรายการทีวีนั้นแล้วจากนั้นทำการเลือกตอนที่คุณต้องการ วิดีฌอตินนั้นที่ถูกเลือกจะแสดงผลแบบเต็มจอแนวนอน เพื่อดูวิดีโอตามรายการทีวีและตอนที่คุณได้เลือก"
    };
    private final String[] HELP_LINK = { "http://help.ccnconsole.info/dootv/", "http://help.ccnconsole.info/dootv/" };
    private final String[] DEVELOPER_NAME = {"Developer name", "ผู้พัฒนาแอพพลิเคชั่น"};
    private final String[] APPLICATION_NAME = {"Application name", "ชื่อแอพพลิเคชั่น"};
    private final String[] VERSION_NUMBER = {"The version number", "เวอร์ชั่น"};
    private final String[] CONNECTING = {"connecting...", "กำลังเชื่อมต่อ..."};
    private final String[] LOADING = {"Loading...", "กำลังโหลด..."};
    private final String[] CHECKING_STATUS = {"Checking status...", "กำลังตรวจสอบสถานะ..."};
    private final String[] SENDING = {"Sending...", "กำลังส่งข้อความ..."};
    private final String[] LOGGING_OUT = {"Logging out...", "กำลังล็อคเอาท์..."};
    private final String[] RETRIEVE_FAIL= {"Fail to retrieve information", "ไม่สามารถรับข้อมูลได้"};
    
    private final String[] MAIN_TITLE = {"channel view", "สถานี"};
    private final String[] TV_PROGRAM = {"TV program", "รายการทีวี"};
    private final String[] ERROR = {"Error", "ผิดพลาด"};
    private final String[] PLEASE_FILL = {"Please fill in the form", "กรุณากรอกข้อความ"};
    private final String[] EPISODE = {"episode view", "ตอน"};
    private final String[] SHARE = {"Share", "แชร์"};
    private final String[] LOAD = {"Load", "โหลด"};
    private final String[] TOP_VIEW = {"top view", "top view"};
    private final String[] CONNECTION_ERROR_H2 = {"Error", "ผิดพลาด"};
    private final String[] CONNECTION_ERROR = {"Connection Error", "เชื่อมต่อไม่สำเร็จ"};
    private final String[] WELCOME_1 = {"Welcome to DOOTV.", "ยินดีต้อนรับสู่ DOOTV."};
    private final String[] WELCOME_2 = {"We hope you enjoy the service.", "ขอให้สนุกกับบริการของเรา"};
    
    private final String[] LOGIN_WITH_FB = {"Login with facebook", "ลอกอินด้วยเฟซบุ๊ค"};
    private final String[] LOADING_COMMENT = {"Loading comment...", "กำลังโหลด..."};
    private final String[] COMMENT_EMPTY = {"Comment is empty", "ไม่มีความคิดเห็น"};
    private final String[] REFRESH = {"Refresh", "รีโหลด"};
    private final String[] LOGOUT = {"Log out", "ลอกเอาท์"};
    private final String[] GRAPH = {"Graph", "กราฟ"};
    private final String[] NOW_SHOWING = {"Now showing", "รายการปัจจุบัน"};
    private final String[] DETAIL = {"Detail", "รายละเอียด"};
    private final String[] CHECK_IN = {"Check-in", "เช็กอิน"};
    private final String[] CHECK_IN_DONE = {"Check-in is successful", "เช็กอินสำเร็จ"};
    private final String[] CHECK_IN_FAIL = {"Check-in failed", "เช็กอินไม่สำเร็จ"};
    private final String[] COMMENT = {"Comment", "แสดงความเห็น"};
    private final String[] COMMENT_DONE = {"Comment is successful", "แสดงความเห็นสำเร็จ"};
    private final String[] COMMENT_FAIL = {"Comment failed", "แสดงความเห็นไม่สำเร็จ"};
    private final String[] LIKE = {"Like", "ไลค์"};
    private final String[] ALREADY_LIKE = {"You've already liked", "คุณได้ถูกใจสิ่งนี้แล้ว"};
    private final String[] RATE = {"Rate", "ให้คะแนน"};
    private final String[] RATE_DONE = {"Rate is successful", "ให้คะแนนสำเร็จ"};
    private final String[] RATE_FAIL = {"Rate failed", "ให้คะแนนไม่สำเร็จ"};
    private final String[] VOTE = {"Vote", "โหวต"};
    
    public LocalizationSupport() {
        String toLowerCase = System.getProperty("microedition.locale").toLowerCase();
        if(toLowerCase.indexOf("th") >= 0) {
            this.langNum = 1;
        } else {
            this.langNum = 0;
        }
    }
    
    public String getMessage(String msg) {
        if(msg.equals("exit")) { return EXIT[langNum]; }
        else if(msg.equals("back")) { return BACK[langNum]; }
        else if(msg.equals("ok")) { return OK[langNum]; }
        else if(msg.equals("english")) { return EN_LANG[langNum]; }
        else if(msg.equals("thai")) { return TH_LANG[langNum]; }
        else if(msg.equals("language")) { return LANGUAGE[langNum]; }
        else if(msg.equals("app_name")) { return APP_NAME[langNum]; }
        else if(msg.equals("connection_error_h")) { return CONNECTION_ERROR_H[langNum]; }
        else if(msg.equals("connection_error_0")) { return CONNECTION_ERROR_0[langNum]; }
        else if(msg.equals("connection_error_1")) { return CONNECTION_ERROR_1[langNum]; }
        else if(msg.equals("connection_error_2")) { return CONNECTION_ERROR_2[langNum]; }
        else if(msg.equals("connection_error_b")) { return CONNECTION_ERROR_B[langNum]; }
        else if(msg.equals("facebook")) { return FACEBOOK[langNum]; }
        else if(msg.equals("publish_completed_h")) { return PUBLISH_COMPLETED_H[langNum]; }
        else if(msg.equals("publish_completed")) { return PUBLISH_COMPLETED[langNum]; }
        else if(msg.equals("publish_failed_h")) { return PUBLISH_FAILED_H[langNum]; }
        else if(msg.equals("publish_failed")) { return PUBLISH_FAILED[langNum]; }
        else if(msg.equals("facebook_logout")) { return FACEBOOK_LOGOUT[langNum]; }
        else if(msg.equals("logout_from_facebook_h")) { return LOGOUT_FROM_FACEBOOK_H[langNum]; }
        else if(msg.equals("logout_from_facebook")) { return LOGOUT_FROM_FACEBOOK[langNum]; }
        else if(msg.equals("login_failed_h")) { return LOGIN_FAILED_H[langNum]; }
        else if(msg.equals("login_failed")) { return LOGIN_FAILED[langNum]; }
        else if(msg.equals("logout_failed_h")) { return LOGOUT_FAILED_H[langNum]; }
        else if(msg.equals("logout_failed")) { return LOGOUT_FAILED[langNum]; }
        else if(msg.equals("logout_done_h")) { return LOGOUT_DONE_H[langNum]; }
        else if(msg.equals("logout_done")) { return LOGOUT_DONE[langNum]; }
        else if(msg.equals("progress_h")) { return PROGRESS_H[langNum]; }
        else if(msg.equals("progress_b")) { return PROGRESS_B[langNum]; }
        else if(msg.equals("progress_connect_to_facebook")) { return PROGRESS_CONNECT_TO_FACEBOOK[langNum]; }
        else if(msg.equals("progress_check_login")) { return PROGRESS_CHECK_LOGIN[langNum]; }
        else if(msg.equals("progress_logout")) { return PROGRESS_LOGOUT[langNum]; }
        else if(msg.equals("progress_load_tvlist")) { return PROGRESS_LOAD_TVLIST[langNum]; }
        else if(msg.equals("about_us")) { return ABOUT_US[langNum]; }
        else if(msg.equals("privacy_policy")) { return PRIVACY_POLICY[langNum]; }
        else if(msg.equals("term_of_use")) { return TERM_OF_USE[langNum]; }
        else if(msg.equals("help")) { return HELP[langNum]; }
        else if(msg.equals("help_message")) { return HELP_MESSAGE[langNum]; }
        else if(msg.equals("help_link")) { return HELP_LINK[langNum]; }
        else if(msg.equals("developer_name")) { return DEVELOPER_NAME[langNum]; }
        else if(msg.equals("application_name")) { return APPLICATION_NAME[langNum]; }
        else if(msg.equals("version_number")) { return VERSION_NUMBER[langNum]; }
        else if(msg.equals("connecting")) { return CONNECTING[langNum]; }
        else if(msg.equals("loading")) { return LOADING[langNum]; }
        else if(msg.equals("checking_status")) { return CHECKING_STATUS[langNum]; }
        else if(msg.equals("sending")) { return SENDING[langNum]; }
        else if(msg.equals("logging_out")) { return LOGGING_OUT[langNum]; }
        else if(msg.equals("retrieve_fail")) { return RETRIEVE_FAIL[langNum]; }
        
        else if(msg.equals("main_title")) { return MAIN_TITLE[langNum]; }
        else if(msg.equals("tv_program")) { return TV_PROGRAM[langNum]; }
        
        else if(msg.equals("error")) { return ERROR[langNum]; }
        else if(msg.equals("please_fill")) { return PLEASE_FILL[langNum]; }
        else if(msg.equals("episode")) { return EPISODE[langNum]; }
        else if(msg.equals("share")) { return SHARE[langNum]; }
        else if(msg.equals("load")) { return LOAD[langNum]; }
        else if(msg.equals("top_view")) { return TOP_VIEW[langNum]; }
        else if(msg.equals("connection_error_h2")) { return CONNECTION_ERROR_H2[langNum]; }
        else if(msg.equals("connection_error")) { return CONNECTION_ERROR[langNum]; }
        else if(msg.equals("welcome_1")) { return WELCOME_1[langNum]; }
        else if(msg.equals("welcome_2")) { return WELCOME_2[langNum]; }
        
        else if(msg.equals("login_with_fb")) { return LOGIN_WITH_FB[langNum]; }
        else if(msg.equals("loading_comment")) { return LOADING_COMMENT[langNum]; }
        else if(msg.equals("comment_empty")) { return COMMENT_EMPTY[langNum]; }
        else if(msg.equals("refresh")) { return REFRESH[langNum]; }
        else if(msg.equals("logout")) { return LOGOUT[langNum]; }
        else if(msg.equals("graph")) { return GRAPH[langNum]; }
        else if(msg.equals("now_showing")) { return NOW_SHOWING[langNum]; }
        else if(msg.equals("detail")) { return DETAIL[langNum]; }
        else if(msg.equals("check_in")) { return CHECK_IN[langNum]; }
        else if(msg.equals("check_in_done")) { return CHECK_IN_DONE[langNum]; }
        else if(msg.equals("check_in_fail")) { return CHECK_IN_FAIL[langNum]; }
        else if(msg.equals("comment")) { return COMMENT[langNum]; }
        else if(msg.equals("comment_done")) { return COMMENT_DONE[langNum]; }
        else if(msg.equals("comment_fail")) { return COMMENT_FAIL[langNum]; }
        else if(msg.equals("like")) { return LIKE[langNum]; }
        else if(msg.equals("already_like")) { return ALREADY_LIKE[langNum]; }
        else if(msg.equals("rate")) { return RATE[langNum]; }
        else if(msg.equals("rate_done")) { return RATE_DONE[langNum]; }
        else if(msg.equals("rate_fail")) { return RATE_FAIL[langNum]; }
        else if(msg.equals("vote")) { return VOTE[langNum]; }
        
        return "";
    }
}