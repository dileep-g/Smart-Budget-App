package com.iui.smartbudget.ui.alerts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.Alert;
import com.iui.smartbudget.utilities.Bucket;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Recommender;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AlertsFragment extends Fragment {

    private AlertsViewModel alertsViewModel;
    private RecyclerView mRecyclerView;
    private AlertsAdapter mListadapter;
    private RelativeLayout mRelativeLayout;
    public HashMap<String,Bucket> categoryBucketMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alertsViewModel =
                ViewModelProviders.of(this).get(AlertsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView );
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager. VERTICAL );
        mRecyclerView.setLayoutManager(layoutManager);

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.alerts_home);

        Recommender recommender=new Recommender();
        if(recommender.alerts.size()>0) recommender.alerts.clear();
        recommender.createBuckets(recommender.buckets);
        mListadapter = new AlertsAdapter(recommender.alerts, getContext(), mRelativeLayout);
        mRecyclerView.setAdapter(mListadapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.sk_line_divider));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        return view;
    }

}