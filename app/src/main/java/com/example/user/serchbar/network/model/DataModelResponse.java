package com.example.user.serchbar.network.model;

public class DataModelResponse {
    String x;
    String y;
    String header;
    String display;

    public DataModelResponse(String x, String y, String header, String display) {
        this.x=x;
        this.y=y;
        this.header=header;
        this.display=display;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
