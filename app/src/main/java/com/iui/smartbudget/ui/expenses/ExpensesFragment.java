package com.iui.smartbudget.ui.expenses;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iui.smartbudget.MainActivity;
import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.time.LocalDate;
import java.time.ZoneId;
import  java.time.*;
import java.util.*;


public class ExpensesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ExpensesViewModel expensesViewModel;
    private PieChart pieChart;
    private BarChart barChart;
    final int[] MY_COLORS = {Color.rgb(75,136,235), Color.rgb(18,176,55), Color.rgb(214,66,43),
            Color.rgb(232,187,23), Color.rgb(204,4,4), Color.rgb(176,16,204), Color.rgb(66,214,162)};
    ArrayList<Integer> colors = new ArrayList<Integer>();

    // dropdown attributes
    private int selectedYear;




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
        selectedYear=2019;
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.years,
                android.R.layout.simple_spinner_item) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
            Date date=record.getDateTime();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(localDate.getYear()!=selectedYear) continue;
            categoryMap.put(record.getCategory(), categoryMap.getOrDefault(record.getCategory(),0.0f)+record.getExpense());
            categories.add(record.getCategory());
        }
        for(String category : categories){
            pieEntries.add(new PieEntry(categoryMap.get(category),category));
        }
        PieDataSet dataSet=new PieDataSet(pieEntries, "");
        pieChart.setVisibility(View.VISIBLE);
        dataSet.setColors(ColorTemplate.createColors(MY_COLORS));
        PieData pieData=new PieData(dataSet);
        pieChart.setData(pieData);
        Description desc=new Description();
        desc.setText("Expense Categories");
        pieChart.setDescription(desc);
        pieChart.getDescription().setTextSize(20f);
        pieChart.getDescription().setTextAlign(Paint.Align.CENTER);
        pieChart.getDescription().setPosition(250f, 40f);
        Legend legend=pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.PIECHART_CENTER);
        pieData.setValueTextSize(15f);
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
        for(Month month : monthToExpenseMap.get(selectedYear).keySet()){
            barEntries.add(new BarEntry( month.getValue(), monthToExpenseMap.get(selectedYear).get(month) ));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setVisibility(View.VISIBLE);
        BarData barData=new BarData(dataSet);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(monthLabels));
        barChart.setData(barData);
        barChart.getXAxis().setLabelCount(12);
        Description desc=new Description();
        desc.setText("Monthly Expenses");
        barChart.setDescription(desc);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getDescription().setTextSize(20f);
        barChart.getDescription().setPosition(250f, 40f);
        barChart.getDescription().setTextAlign(Paint.Align.CENTER);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.invalidate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String year=parent.getItemAtPosition(position).toString();
        selectedYear = year.equals("Current Year") ? 2019 : 2018;
        getExpensePieChart();
        getMonthBarChart();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedYear = 2019;
    }
}
