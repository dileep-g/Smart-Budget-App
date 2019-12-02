package com.iui.smartbudget.utilities;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class Budget {

    private HashMap<String, Float> map = new HashMap<>();
    private HashMap<Month, HashMap<String, Float>> monthToCategoryMap = new HashMap<>();

    public Budget(){
        monthToCategoryMap=new HashMap<>();
        }

    public void updateMonthExpenseMap(){
        for(Record record : DataHolder.records){
            Date date=record.getDateTime();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(localDate.getYear()== 2019){
                map.put(record.getCategory(), map.getOrDefault(record.getCategory(), 0.0F) + record.getExpense());
                if(!monthToCategoryMap.containsKey(localDate.getMonth()))
                    monthToCategoryMap.put(localDate.getMonth(), new HashMap<String,Float>());
                HashMap<String,Float> temp = monthToCategoryMap.get(localDate.getMonth());
                temp.put(record.getCategory(), temp.getOrDefault(record.getCategory(),0.0F)+record.getExpense());

            }


        }
       DataHolder.monthToCategoryMap=monthToCategoryMap;

        }
    }

