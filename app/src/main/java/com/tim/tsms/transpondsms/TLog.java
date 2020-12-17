package com.tim.tsms.transpondsms;

public class TLog {
    private String name;
    private int imageId;

    public TLog(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return  imageId;
    }
}
