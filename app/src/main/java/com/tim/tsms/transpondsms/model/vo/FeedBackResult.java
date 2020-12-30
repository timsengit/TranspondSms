package com.tim.tsms.transpondsms.model.vo;

import java.io.Serializable;

public class FeedBackResult implements Serializable {
    Integer code;
    String message;
    Object result;

    public FeedBackResult(){

    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess(){
        return 1==code;
    }
}
