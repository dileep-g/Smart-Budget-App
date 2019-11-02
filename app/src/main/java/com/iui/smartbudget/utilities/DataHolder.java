package com.iui.smartbudget.utilities;



import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    public static List<Record> records;
    public static DataHolder dataHolder = new DataHolder();

    private DataHolder(){
            records=new ArrayList<>();
    }

    public static DataHolder getInstance(){
        return dataHolder;
    }
}
