package com.iui.smartbudget.utilities;



import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DataHolder {
    public static List<Record> records;
    public static List<Bucket> buckets;
    public static List<Alert> alerts;
    public static DataHolder dataHolder = new DataHolder();
    public static HashMap<String, Float> categoryToAvgExpenseMap;

    public static List<String> categoriesPriorityList;
    public static HashMap<Month, HashMap<String, Float>> monthToCategoryMap;

    private DataHolder(){
            records=new ArrayList<>();
            buckets=new ArrayList<>();
            alerts=new ArrayList<>();
            categoriesPriorityList=Arrays.asList(new String[]{"Groceries", "Personal", "Travel", "Dining", "Shopping", "Entertainment"});
            categoryToAvgExpenseMap = new HashMap<>();
            monthToCategoryMap = new HashMap<>();

    }

    public static DataHolder getInstance(){
        return dataHolder;
    }


}
