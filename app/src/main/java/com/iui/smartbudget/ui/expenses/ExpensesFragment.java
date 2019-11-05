package com.iui.smartbudget.ui.expenses;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.time.LocalDate;
import java.time.ZoneId;
import  java.time.*;
import java.util.*;


public class ExpensesFragment extends Fragment {

    private ExpensesViewModel expensesViewModel;
    private PieChart pieChart;
    private BarChart barChart;
    final int[] MY_COLORS = {Color.rgb(180,0,0), Color.rgb(20,60,200), Color.rgb(255,192,0),
            Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
    ArrayList<Integer> colors = new ArrayList<Integer>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        expensesViewModel =
                ViewModelProviders.of(this).get(ExpensesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);
        final TextView textView = root.findViewById(R.id.text_expenses);
        expensesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        pieChart=root.findViewById(R.id.pie_chart);
        barChart=root.findViewById(R.id.bar_chart);
        getExpensePieChart();
        getMonthBarChart();
        return root;
    }

    public void getExpensePieChart(){
        List<PieEntry> pieEntries=new ArrayList<>();
        HashMap<String,Float>  categoryMap=new HashMap<>();
        HashSet<String> categories=new HashSet<>();
        for(Record record : DataHolder.records){
            categoryMap.put(record.getCategory(), categoryMap.getOrDefault(record.getCategory(),0.0f)+record.getExpense());
            categories.add(record.getCategory());
        }
        for(String category : categories){
            pieEntries.add(new PieEntry(categoryMap.get(category),category));
        }
        PieDataSet dataSet=new PieDataSet(pieEntries, "Category Expenses");
        pieChart.setVisibility(View.VISIBLE);
        dataSet.setColors(ColorTemplate.createColors(MY_COLORS));
        PieData pieData=new PieData(dataSet);
        pieChart.setData(pieData);
        Description desc=new Description();
        desc.setText("Expenses in each Category");
        pieChart.setDescription(desc);
        pieChart.invalidate();
    }

    public void getMonthBarChart(){
        List<BarEntry> barEntries=new ArrayList<>();
        HashMap<Integer,HashMap<Month,Float>> monthToExpenseMap = new HashMap<>();
        ArrayList<String> monthLabels=new ArrayList<>();
        for(Record record : DataHolder.records){
            Date date=record.getDateTime();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(!monthToExpenseMap.containsKey(localDate.getYear()))
                monthToExpenseMap.put(localDate.getYear(), new HashMap<Month, Float>());
            HashMap<Month, Float> currMap=monthToExpenseMap.get(localDate.getYear());
            currMap.put(localDate.getMonth(), currMap.getOrDefault(localDate.getMonth(),0.0F)+record.getExpense());
            if(!monthLabels.contains(localDate.getMonth().name().substring(0,3)))
                monthLabels.add(localDate.getMonth().name().substring(0,3));
        }
        for(Month month : monthToExpenseMap.get(2019).keySet()){
            barEntries.add(new BarEntry( month.getValue(), monthToExpenseMap.get(2019).get(month) ));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "Monthly Expenses");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setVisibility(View.VISIBLE);
        BarData barData=new BarData(dataSet);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(monthLabels));
        barChart.setData(barData);
        barChart.invalidate();
    }
}
