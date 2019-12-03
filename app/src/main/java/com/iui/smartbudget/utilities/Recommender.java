package com.iui.smartbudget.utilities;

import   java.util.*;

public class Recommender {

    public  HashMap<String,Bucket> categoryBucketMap;
    public  List<Alert> alerts;
    public  List<Bucket> buckets;

    public  Recommender(){
        categoryBucketMap=new HashMap<>();
        alerts=DataHolder.getInstance().alerts;
        buckets=DataHolder.getInstance().buckets;

    }

    public void createBuckets(List<Bucket> buckets){

        Bucket newBucket = new Bucket("Shopping", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("shopping"));
        newBucket.setCurrent(20);
        buckets.add(newBucket);
        newBucket = new Bucket("Entertainment", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("entertainment"));
        newBucket.setCurrent(30);
        buckets.add(newBucket);
        newBucket = new Bucket("Dining", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("dining"));
        newBucket.setCurrent(300);
        buckets.add(newBucket);
        newBucket = new Bucket("Personal", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("personal"));
        newBucket.setCurrent(120);
        buckets.add(newBucket);
        newBucket = new Bucket("Travel", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("travel"));
        newBucket.setCurrent(100);
        buckets.add(newBucket);
        newBucket = new Bucket("Groceries", 1.2f*DataHolder.getInstance().categoryToAvgExpenseMap.get("groceries"));
        newBucket.setCurrent(180);
        buckets.add(newBucket);

        for(Bucket bucket : buckets){
            String category=bucket.getName();
            categoryBucketMap.put(category, bucket);
        }

        for(Bucket bucket : buckets) {
            // check how full the bucket is!
            // check over budget
            // dummy data

            if(bucket.getCurrent()>=0.8*bucket.getCapacity() && bucket.getCurrent()<bucket.getCapacity())
                DataHolder.getInstance().alerts.add(new Alert(bucket.getName(), new Date(), "Your budget for "+bucket.getName()+" is almost about to reach its limit."));
            else if(bucket.getCurrent()>=bucket.getCapacity()){
                Alert alert=new Alert(bucket.getName(), new Date(), "You have exceeded your budget limit of $"+(Math.round(bucket.getCapacity()*10.0f)/10.0f) +" for "+bucket.getName()+"!");
                String recommendation = generateRecommendation(bucket, bucket.getCurrent()-bucket.getCapacity(), alert);
                alert.setText(recommendation);
                DataHolder.getInstance().alerts.add(alert);
            }
        }
    }

    public  String generateRecommendation(Bucket bucket, float diff, Alert alert){
        StringBuilder recommendation=new StringBuilder();
        recommendation.append("You have exceeded your budget limit of $"+(Math.round(bucket.getCapacity()*10.0f)/10.0f) +" for "+bucket.getName()+"!");
        recommendation.append("\n You can remove $");
        String category=bucket.getName();
        alert.setMainBucket(bucket);
        HashMap<Bucket, Float> bucketMap=new HashMap<>();
        for(int i=DataHolder.categoriesPriorityList.size()-1;i>=0;i--){
            if(DataHolder.categoriesPriorityList.get(i).equals(category)) continue;
            // now check which categories to cut costs. If 20% of a category (within limit) can offset the diff ->
            // if current <= 0.75*capacity, take 20% from capacity
            if(diff<0) break;
            String currCategory=DataHolder.categoriesPriorityList.get(i);
            Bucket currBucket=categoryBucketMap.get(currCategory);
            if(currBucket.getCurrent()<=0.75f*currBucket.getCapacity()){
                float offset=0.2f*currBucket.getCapacity();
                float amount=Math.min(offset, diff);
                bucketMap.put(currBucket, amount);
                //recommendation.append("\n You can remove $"+(Math.round(amount*10.0f)/10.0f)+" from your "+currCategory+" budget to stay on track for the rest of the month");
                recommendation.append((Math.round(amount*10.0f)/10.0f)+" from your "+currCategory+" budget ");
                if(offset>=diff) break;
                recommendation.append("and $");
                diff-=offset;
            }
        }
        alert.setRecoBuckets(bucketMap);
        recommendation.append("to stay on track for the rest of the month.");
        return recommendation.toString();
    }
}
