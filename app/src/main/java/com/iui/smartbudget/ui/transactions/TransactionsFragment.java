package com.iui.smartbudget.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.util.List;

public class TransactionsFragment extends Fragment {

    private TextView mTextViewEmpty;
    private ProgressBar mProgressBarLoading;
    private ImageView mImageViewEmpty;
    private RecyclerView mRecyclerView;
    private TransactionsAdapter mListadapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transactions , container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView );
        searchView = (SearchView) view.findViewById(R.id.search_bar);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager. VERTICAL );
        mRecyclerView.setLayoutManager(layoutManager);

        List<Record> data = DataHolder.getInstance().records;

        mListadapter = new TransactionsAdapter(data, getContext());
        mRecyclerView.setAdapter(mListadapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.sk_line_divider));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mListadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mListadapter.getFilter().filter(query);
                return false;
            }
        });
        return view;
    }
}
