package com.tim.tsms.transpondsms.model;

public class MatchModel {
    private Long id;
    private Long nextId;
    private String filed;
    //is 是；contain 包含；startwith 开头；endwith 结尾；notis 不是
    private String check;
    private String value;
    private Long time;

    public MatchModel(Long nextId, String filed, String check, String value, Long time) {
        this.nextId = nextId;
        this.filed = filed;
        this.check = check;
        this.value = value;
        this.time = time;
    }
}
