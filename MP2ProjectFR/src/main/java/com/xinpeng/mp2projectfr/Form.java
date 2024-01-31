package com.xinpeng.mp2projectfr;

public class Form {

    private String id = "";
    private String content;

    public Form() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String toString(){
        return id + ": " + getContent();
    }

}
