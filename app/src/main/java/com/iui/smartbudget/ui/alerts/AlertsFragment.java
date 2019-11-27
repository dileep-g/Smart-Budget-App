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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.Alert;
import com.iui.smartbudget.utilities.DataHolder;

import java.util.List;

public class AlertsFragment extends Fragment {

    private AlertsViewModel alertsViewModel;
    private RecyclerView mRecyclerView;
    private AlertsAdapter mListadapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alertsViewModel =
                ViewModelProviders.of(this).get(AlertsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView );
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager. VERTICAL );
        mRecyclerView.setLayoutManager(layoutManager);

        List<Alert> alerts = DataHolder.getInstance().alerts;

        mListadapter = new AlertsAdapter(alerts, getContext());
        mRecyclerView.setAdapter(mListadapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.sk_line_divider));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        return view;
    }
}
