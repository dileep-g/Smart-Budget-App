package com.iui.smartbudget.ui.expenses;

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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.util.*;


public class ExpensesFragment extends Fragment {

    private ExpensesViewModel expensesViewModel;
    private PieChart pieChart;
    private BarChart barChart;

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
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData=new PieData(dataSet);
        pieChart.setData(pieData);
        Description desc=new Description();
        desc.setText("Expenses in each Category");
        pieChart.setDescription(desc);
        pieChart.invalidate();
    }
}
