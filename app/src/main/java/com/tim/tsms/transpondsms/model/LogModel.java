package com.tim.tsms.transpondsms.model;

public class LogModel {
    private String from;
    private String content;
    private Long time;

    public LogModel(String from, String content, Long time) {
        this.from = from;
        this.content = content;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

}
