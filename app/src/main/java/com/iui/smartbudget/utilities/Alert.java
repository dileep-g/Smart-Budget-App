package com.iui.smartbudget.utilities;

import java.util.Date;

public class Alert {
    private String title;
    private Date dateTime;
    private String text;

    Alert(String title, Date dateTime, String text) {
        this.title = title;
        this.dateTime = dateTime;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
