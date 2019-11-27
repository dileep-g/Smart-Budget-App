package com.iui.smartbudget.utilities;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataHolder {
    public List<Record> records;
    public List<Bucket> buckets;
    public List<Alert> alerts;
    public static DataHolder dataHolder = new DataHolder();
    public HashMap<String, Float> categoryToAvgExpenseMap;

    public double bucketThreshold = 0.8;

    private DataHolder(){
            records=new ArrayList<>();
            buckets=new ArrayList<>();
            alerts=new ArrayList<>();
            categoryToAvgExpenseMap = new HashMap<>();
            checkAlerts();

    }

    public static DataHolder getInstance(){
        return dataHolder;
    }

    public void checkAlerts() {
        for(String bucket : Arrays.asList(new String[]{"Travel", "Shopping"})) {
            // check how full the bucket is!
            // check over budget
            // dummy data
            alerts.add(new Alert(bucket, new Date(), "Text goes here"));
        }
    }

}
