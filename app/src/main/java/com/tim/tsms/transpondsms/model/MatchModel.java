package com.tim.tsms.transpondsms.model;

public class MatchModel {
    private Long id;
    private Long nextId;
    public static final String FILED_PHONE_NUM="phone_num";
    public static final String FILED_MSG_CONTENT="msg_CONTENT";
    private String filed;

    public static final String CHECK_IS="is";
    public static final String CHECK_CONTAIN="contain";
    public static final String CHECK_START_WITH="startwith";
    public static final String CHECK_END_WITH="endwith";
    public static final String CHECK_NOT_IS="notis";
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
