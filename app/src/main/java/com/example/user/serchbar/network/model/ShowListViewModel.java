package com.example.user.serchbar.network.model;

public class ShowListViewModel {
    String x,y,header,display,id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShowListViewModel(String x, String y, String header, String display, String id) {
        this.x = x;
        this.y = y;

        this.header = header;
        this.display = display;
        this.id = id;

    }

}
