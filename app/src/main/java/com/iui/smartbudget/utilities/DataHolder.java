package com.iui.smartbudget.utilities;



import java.time.Month;
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

    public static List<String> categoriesPriorityList;
    public static HashMap<Month, HashMap<String, Float>> monthToCategoryMap;

    private DataHolder(){
            records=new ArrayList<>();
            buckets=new ArrayList<>();
            alerts=new ArrayList<>();
            checkAlerts();
            categoriesPriorityList=Arrays.asList(new String[]{"Groceries", "Personal", "Travel", "Dining", "Shopping", "Entertainment"});
            categoryToAvgExpenseMap = new HashMap<>();
            monthToCategoryMap = new HashMap<>();

    }

    public static DataHolder getInstance(){
        return dataHolder;
    }

    public void checkAlerts() {
        // dummy buckets
        Bucket dummy = new Bucket("Shopping", 300.0);
        dummy.setCurrent(280);
        buckets.add(dummy);
        dummy = new Bucket("Entertainment", 300.0);
        dummy.setCurrent(220);
        buckets.add(dummy);

        for(Bucket bucket : buckets) {
            // check how full the bucket is!
            // check over budget
            // dummy data
            alerts.add(new Alert(bucket.getName(), new Date(), "Text goes here"));
        }
    }

}
