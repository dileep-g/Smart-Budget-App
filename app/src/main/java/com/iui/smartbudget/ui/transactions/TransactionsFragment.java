package com.iui.smartbudget.ui.transactions;

import android.icu.text.LocaleDisplayNames;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.Bucket;
import com.iui.smartbudget.utilities.Budget;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Recommender;
import com.iui.smartbudget.utilities.Record;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private static final String TAG="SB:TransactionsFragment";

    private TextView mTextViewEmpty;
    private ProgressBar mProgressBarLoading;
    private ImageView mImageViewEmpty;
    private RecyclerView mRecyclerView;
    private TransactionsAdapter mListadapter;
    private SearchView searchView;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private ImageButton addBtn;

    private String vendor = "";
    private String category = "";
    private double expense = 0.0;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transactions , container, false);

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.transactions_home);
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

        addBtn = (ImageButton) view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setupPopup(inflater);
            }
        });

        return view;
    }


    private void setupPopup(LayoutInflater infalter) {
        // Inflate the custom layout/view
        View customView = infalter.inflate(R.layout.transaction_add,null);
          /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        mPopupWindow.setFocusable(true);

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        final TextInputEditText vendorInput = (TextInputEditText) customView.findViewById(R.id.vendor_input);
        final TextInputEditText expenseInput = (TextInputEditText) customView.findViewById(R.id.expense_input);

        Spinner categorySpinner = (Spinner) customView.findViewById(R.id.add_transaction_category);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "Category selected: " + parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button addButton = (Button) customView.findViewById(R.id.add_transaction_btn);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                vendor = vendorInput.getText().toString();
                expense = Double.valueOf(expenseInput.getText().toString());
                Log.d(TAG, vendor + ": " + expense);

                Record record = new Record();
                record.setDateTime(new Date());
                record.setVendor(vendor);
                record.setExpense((float)expense);
                record.setCategory(category.toLowerCase());
                mListadapter.addRecord(record);
                Budget.updateMonthExpenseMap();

                // update bucket
                for (Bucket bucket: DataHolder.buckets) {
                    if (bucket.getName().toLowerCase().equals(category.toLowerCase())) {
                        bucket.setCurrent(bucket.getCurrent()+(float)expense);
                    }
                }

                mPopupWindow.dismiss();
            }
        });


                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
    }
}
