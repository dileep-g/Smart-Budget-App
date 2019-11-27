package com.iui.smartbudget;

import android.os.Bundle;
import android.util.Log;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
public class MainActivity extends AppCompatActivity {

    private static final String TAG="SB:MainActivity";
    public File data;
    public List<Record> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_expenses, R.id.navigation_budget, R.id.navigation_transactions, R.id.navigation_alerts, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        readData();
    }

        protected void readData(){
            try {
                HashMap<String, Float> map = new HashMap<>();
                HashMap<Month, HashMap<String, Float>> monthToCategoryMap = new HashMap<>();
                InputStream is = getAssets().open("data.csv");
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
                String csvLine;
                SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                reader.readLine();
                while ((csvLine = reader.readLine()) != null) {
                    String[] nextLine = csvLine.split(",");
                    Record record=new Record();
                    record.setId(Integer.valueOf(nextLine[0]));
                    record.setDateTime(format.parse(nextLine[1]));
                    record.setVendor(nextLine[2]);
                    record.setCategory(nextLine[3]);
                    record.setExpense(Float.valueOf(nextLine[4]));
                    DataHolder.getInstance().records.add(record);

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
                for(String category : map.keySet()){
                    map.put(category, map.get(category)/12.0F);
                }
                DataHolder.getInstance().categoryToAvgExpenseMap = map;
                DataHolder.monthToCategoryMap = monthToCategoryMap;
            } catch (Exception e) {
             Log.d(TAG,e.getLocalizedMessage(), e);
            }

        }


}
