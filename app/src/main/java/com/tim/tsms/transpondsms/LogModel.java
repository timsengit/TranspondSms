package com.tim.tsms.transpondsms;

public class LogModel {
    private String name;
    private int imageId;

    public LogModel(String name, int imageId){
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
