package com.iui.smartbudget.utilities;



import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class DataHolder {
    public static List<Record> records;

    public static List<String> categoriesPriorityList;

    public static DataHolder dataHolder = new DataHolder();
    public static HashMap<String, Float> categoryToAvgExpenseMap;
    public static HashMap<Month, HashMap<String, Float>> monthToCategoryMap;

    private DataHolder(){
            records=new ArrayList<>();

            categoriesPriorityList=Arrays.asList(new String[]{"Groceries", "Personal", "Travel", "Dining", "Shopping", "Entertainment"});
            categoryToAvgExpenseMap = new HashMap<>();
            monthToCategoryMap = new HashMap<>();
            categoryToAvgExpenseMap = new HashMap<>();


    }

    public static DataHolder getInstance(){
        return dataHolder;
    }
}
