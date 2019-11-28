package com.iui.smartbudget.utilities;



import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DataHolder {
    public static List<Record> records;
    public static  List<Bucket> buckets;
    public static List<String> categoriesPriorityList;
    public static DataHolder dataHolder = new DataHolder();
    public static HashMap<String, Float> categoryToAvgExpenseMap;
    public static HashMap<Month, HashMap<String, Float>> monthToCategoryMap;
    public static List<Alert> alerts;
    public static double bucketThreshold = 0.8;

    private DataHolder(){
            records=new ArrayList<>();
            buckets=new ArrayList<>();
            alerts=new ArrayList<>();
            //checkAlerts();
            categoriesPriorityList=Arrays.asList(new String[]{"Groceries", "Personal", "Travel", "Dining", "Shopping", "Entertainment"});
            categoryToAvgExpenseMap = new HashMap<>();
            monthToCategoryMap = new HashMap<>();

    }

    public static DataHolder getInstance(){
        return dataHolder;
    }

    public static void checkAlerts() {
        // dummy buckets
        Bucket newBucket = new Bucket("Shopping", 300);
        newBucket.setCurrent(200);
        buckets.add(newBucket);
        newBucket = new Bucket("Entertainment", 250);
        newBucket.setCurrent(205);
        buckets.add(newBucket);
        newBucket = new Bucket("Dining", 300);
        newBucket.setCurrent(250);
        buckets.add(newBucket);
        newBucket = new Bucket("Personal", 250);
        newBucket.setCurrent(175);
        buckets.add(newBucket);
        newBucket = new Bucket("Travel", 150);
        newBucket.setCurrent(155);
        buckets.add(newBucket);
        newBucket = new Bucket("Groceries", 200);
        newBucket.setCurrent(130);
        buckets.add(newBucket);

        for(Bucket bucket : buckets) {
            // check how full the bucket is!
            // check over budget
            // dummy data
            if(bucket.getCurrent()>=0.8*bucket.getCapacity() && bucket.getCurrent()<bucket.getCapacity())
                alerts.add(new Alert(bucket.getName(), new Date(), "Your budget for "+bucket.getName()+" is almost about to reach its limit."));
            else if(bucket.getCurrent()>=bucket.getCapacity()){
                Alert alert=new Alert(bucket.getName(), new Date(), "You have exceeded your budget limit for "+bucket.getName()+"!");
                //String recommendation = generateRecommendation(bucket, bucket.getCurrent()-bucket.getCapacity());
                //alert.setText(recommendation);
                alerts.add(alert);
            }
        }
    }

    public static String generateRecommendation(Bucket bucket, float diff){
        StringBuilder recommendation=new StringBuilder();
        String category=bucket.getName().toLowerCase();
        for(int i=categoriesPriorityList.size()-1;i>=0;i--){
            if(categoriesPriorityList.get(i).equals(category)) break;
            // now check which categories to cut costs. If 20% of a category (within limit) can offset the diff ->
            // if current <= 0.75*capacity, take 20% from capacity
        }
        return recommendation.toString();
    }
}
