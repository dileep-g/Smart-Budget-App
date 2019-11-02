package com.iui.smartbudget;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
//                String csvfileString = this.getApplicationInfo().dataDir + File.separatorChar + "data.csv";
//                File csvfile = new File(csvfileString);
//                CSVReader reader = new CSVReader(new FileReader(csvfile),',','\'',1);
//                CSVReader reader = new CSVReader(new FileReader("data.csv"),',','\'',1);
//                String[] nextLine;
//                SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//                while ((nextLine = reader.readNext()) != null) {
//                    // nextLine[] is an array of values from the line
//                    Record record=new Record();
//                    record.setId(Integer.valueOf(nextLine[0]));
//                    record.setDateTime(format.parse(nextLine[1]));
//                    record.setVendor(nextLine[2]);
//                    record.setCategory(nextLine[3]);
//                    record.setExpense(Double.valueOf(nextLine[4]));
//                    DataHolder.records.add(record);
//                }
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
                    record.setExpense(Double.valueOf(nextLine[4]));
                    DataHolder.records.add(record);
                }
            } catch (Exception e) {
             Log.d(TAG,e.getLocalizedMessage(), e);
            }

        }


}
