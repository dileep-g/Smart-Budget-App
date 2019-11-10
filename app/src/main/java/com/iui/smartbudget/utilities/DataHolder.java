package com.iui.smartbudget.utilities;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHolder {
    public static List<Record> records;
    public static DataHolder dataHolder = new DataHolder();
    public static HashMap<String, Float> categoryToAvgExpenseMap;

    private DataHolder(){
            records=new ArrayList<>();
            categoryToAvgExpenseMap = new HashMap<>();
    }

    public static DataHolder getInstance(){
        return dataHolder;
    }
}
