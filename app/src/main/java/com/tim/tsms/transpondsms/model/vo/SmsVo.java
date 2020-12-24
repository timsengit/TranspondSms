package com.tim.tsms.transpondsms.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsVo implements Serializable{
    String mobile;
    String content;
    Date date;

    public SmsVo() {
    }

    public SmsVo(String mobile, String content, Date date) {
        this.mobile = mobile;
        this.content = content;
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSmsVoForSend(){
        return mobile + "\n" +
               content + "\n" +
               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    @Override
    public String toString() {
        return "SmsVo{" +
                "mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
