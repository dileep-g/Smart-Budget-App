package com.iui.smartbudget.ui.alerts;

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

import com.iui.smartbudget.R;

public class AlertsFragment extends Fragment {

    private AlertsViewModel alertsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alertsViewModel =
                ViewModelProviders.of(this).get(AlertsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alerts, container, false);
        final TextView textView = root.findViewById(R.id.text_alerts);
        alertsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
