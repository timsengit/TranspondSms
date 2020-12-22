package com.tim.tsms.transpondsms.model;

import com.tim.tsms.transpondsms.R;

import java.util.HashMap;
import java.util.Map;

public class RuleModel {
    private Long id;
    public static final String FILED_PHONE_NUM="phone_num";
    public static final String FILED_MSG_CONTENT="msg_content";
    public static final Map<String,String> FILED_MAP=new HashMap<String, String>();
    static{
        FILED_MAP.put("phone_num", "手机号");
        FILED_MAP.put("msg_content", "内容");
    }
    private String filed;

    public static final String CHECK_IS="is";
    public static final String CHECK_CONTAIN="contain";
    public static final String CHECK_START_WITH="startwith";
    public static final String CHECK_END_WITH="endwith";
    public static final String CHECK_NOT_IS="notis";
    public static final Map<String,String> CHECK_MAP=new HashMap<String, String>();
    static{
        CHECK_MAP.put("is", "是");
        CHECK_MAP.put("contain", "包含");
        CHECK_MAP.put("startwith", "开头是");
        CHECK_MAP.put("endwith", "结尾是");
        CHECK_MAP.put("notis", "不是");
    }
    private String check;

    private String value;

    private Long senderId;
    private Long time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getRuleMatch() {
        return "当 "+FILED_MAP.get(filed)+" "+CHECK_MAP.get(check)+" "+value+" 转发到 ";
    }

    public static String getRuleMatch(String filed,String check,String value) {
        return "当 "+FILED_MAP.get(filed)+" "+CHECK_MAP.get(check)+" "+value;
    }

    public Long getRuleSenderId() {
        return senderId;
    }

    public int getRuleFiledCheckId(){
        switch (filed){
            case FILED_MSG_CONTENT:
                return R.id.btnContent;
            default:
                return R.id.btnPhone;
        }
    }

    public static String getRuleFiledFromCheckId(int id){
        switch (id){
            case R.id.btnContent:
                return FILED_MSG_CONTENT;
            default:
                return FILED_PHONE_NUM;
        }
    }

    public int getRuleCheckCheckId(){
        switch (check){
            case CHECK_CONTAIN:
                return R.id.btnContain;
            case CHECK_START_WITH:
                return R.id.btnStartWith;
            case CHECK_END_WITH:
                return R.id.btnEndWith;
            case CHECK_NOT_IS:
                return R.id.btnNotIs;
            default:
                return R.id.btnIs;
        }
    }

    public static String getRuleCheckFromCheckId(int id){
        switch (id){
            case R.id.btnContain:
                return CHECK_CONTAIN;
            case R.id.btnStartWith:
                return CHECK_START_WITH;
            case R.id.btnEndWith:
                return CHECK_END_WITH;
            case R.id.btnNotIs:
                return CHECK_NOT_IS;
            default:
                return CHECK_IS;
        }
    }

}
