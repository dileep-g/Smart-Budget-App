package com.iui.smartbudget.ui.budget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.iui.smartbudget.R;

public class BudgetFragment extends Fragment {

    private BudgetViewModel budgetViewModel;
    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        budgetViewModel =
                ViewModelProviders.of(this).get(BudgetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);
        //final TextView textView = root.findViewById(R.id.text_budget);
        //TextView textView1 = root.findViewById(R.id.textViewProgressBar1);
        //progressBar = root.findViewById(R.id.progressBar);
//        budgetViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public void prograssBar(){
        progressBar.setSecondaryProgress(100);
        progressBar.setProgress(10);
        progressBar.setMax(100);
    }
}
