package com.tim.tsms.transpondsms.model;

public class RuleModel {
    private Long id;
    private Long matchId;
    private Long senderId;
    private Long time;

    public RuleModel( Long matchId, Long senderId) {
        this.matchId = matchId;
        this.senderId = senderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
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
}
