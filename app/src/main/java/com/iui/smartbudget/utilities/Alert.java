package com.iui.smartbudget.utilities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Alert {
    private String title;
    private Date dateTime;
    private String text;

    private Bucket mainBucket;
    private Float mainValue;
    private Map<Bucket, Float> recoBuckets;

    public Alert(String title, Date dateTime, String text) {
        this.title = title;
        this.dateTime = dateTime;
        this.text = text;
        this.recoBuckets = new HashMap<>();
        this.mainValue = 0.0f;
        this.mainBucket = null;
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

    public Bucket getMainBucket() {
        return mainBucket;
    }

    public void setMainBucket(Bucket mainBucket) {
        this.mainBucket = mainBucket;
    }

    public Float getMainValue() {
        return mainValue;
    }

    public void setMainValue(Float mainValue) {
        this.mainValue = mainValue;
    }

    public Map<Bucket, Float> getRecoBuckets() {
        return recoBuckets;
    }

    public void setRecoBuckets(Map<Bucket, Float> recoBuckets) {
        this.recoBuckets = recoBuckets;
    }
}