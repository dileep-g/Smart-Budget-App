package com.iui.smartbudget.ui.budget;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.iui.smartbudget.R;
import com.iui.smartbudget.ui.transactions.TransactionsAdapter;
import com.iui.smartbudget.utilities.Bucket;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class BudgetFragment extends Fragment {
    private static final String TAG="SB:BudgetFragment";

    private BudgetViewModel budgetViewModel;
    private TextView title;

    private ProgressBar entertainmentPB;
    private TextView entertainmentName;
    private TextView entertainmentCurrent;
    private TextView entertainmentTotal;

    private ProgressBar diningPB;
    private TextView diningName;
    private TextView diningCurrent;
    private TextView diningTotal;


    private ProgressBar personalPB;
    private TextView personalName;
    private TextView personalCurrent;
    private TextView personalTotal;

    private ProgressBar travelPB;
    private TextView travelName;
    private TextView travelCurrent;
    private TextView travelTotal;

    private ProgressBar groceriesPB;
    private TextView groceriesName;
    private TextView groceriesCurrent;
    private TextView groceriesTotal;

    private ProgressBar shoppingPB;
    private TextView shoppingName;
    private TextView shoppingCurrent;
    private TextView shoppingTotal;

    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private TransactionsAdapter mListadapter;
    private String category = "";
    private double capacity = 0.0;
    private ImageButton addBtn;


//    private Bucket entertainmentBucket;
//    private Bucket diningBucket;
//    private Bucket personalBucket;
//    private Bucket travelBucket;
//    private Bucket groceriesBucket;
//    private Bucket shoppingBucket;

    private Map<String, Bucket> bucketMapper = new HashMap<>();


    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;
    private FragmentManager fragmentManager;
    private Fragment fragmentInstance;

    public View onCreateView(final LayoutInflater inflater1,
                             ViewGroup container1, Bundle savedInstanceState1) {
        inflater = inflater1;
        container = container1;
        savedInstanceState = savedInstanceState1;
        this.fragmentInstance = this;
        this.fragmentManager = this.getFragmentManager();
        budgetViewModel =
                ViewModelProviders.of(this).get(BudgetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);
        //final TextView textView = root.findViewById(R.id.text_budget);
        //TextView textView1 = root.findViewById(R.id.textViewProgressBar1);
        mRelativeLayout = (RelativeLayout) root.findViewById(R.id.budget_home);
        title = root.findViewById(R.id.title);
        title.setText("The Budget For: " + "December");

        //Entertainment
        entertainmentPB = root.findViewById(R.id.entertainmentProgressBar);
        entertainmentName = root.findViewById(R.id.entertainment);
        entertainmentCurrent = root.findViewById(R.id.entertainmentCurrent);
        entertainmentTotal = root.findViewById(R.id.entertainmentTotal);

        //Dining
        diningPB = root.findViewById(R.id.diningProgressBar);
        diningName = root.findViewById(R.id.dining);
        diningCurrent = root.findViewById(R.id.diningCurrent);
        diningTotal = root.findViewById(R.id.diningTotal);

        //Personal
        personalPB = root.findViewById(R.id.personalProgressBar);
        personalName = root.findViewById(R.id.personal);
        personalCurrent = root.findViewById(R.id.personalCurrent);
        personalTotal = root.findViewById(R.id.personalTotal);

        //Travel
        travelPB = root.findViewById(R.id.travelProgressBar);
        travelName = root.findViewById(R.id.travel);
        travelCurrent = root.findViewById(R.id.travelCurrent);
        travelTotal = root.findViewById(R.id.travelTotal);

        //Groceries
        groceriesPB = root.findViewById(R.id.groceriesProgressBar);
        groceriesName = root.findViewById(R.id.groceries);
        groceriesCurrent = root.findViewById(R.id.groceriesCurrent);
        groceriesTotal = root.findViewById(R.id.groceriesTotal);

        //Shopping
        shoppingPB = root.findViewById(R.id.shoppingProgressBar);
        shoppingName = root.findViewById(R.id.shopping);
        shoppingCurrent = root.findViewById(R.id.shoppingCurrent);
        shoppingTotal = root.findViewById(R.id.shoppingTotal);

        if(DataHolder.buckets.size() == 0) {
            Bucket entertainmentBucket = new Bucket("Entertainment", DataHolder.categoryToAvgExpenseMap.get("entertainment"));
            entertainmentBucket.setName("Entertainment");
            entertainmentBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("entertainment"));
            entertainmentBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("entertainment"));

            DataHolder.buckets.add(entertainmentBucket);
            bucketMapper.put("entertainment", entertainmentBucket);

            Bucket diningBucket = new Bucket("Dining", DataHolder.categoryToAvgExpenseMap.get("dining"));
            diningBucket.setName("Dining");
            diningBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("dining"));
            diningBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("dining"));

            DataHolder.buckets.add(diningBucket);
            bucketMapper.put("dining", diningBucket);

            Bucket personalBucket = new Bucket("Personal", DataHolder.categoryToAvgExpenseMap.get("personal"));
            personalBucket.setName("Personal");
            personalBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("personal"));
            personalBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("personal"));

            DataHolder.buckets.add(personalBucket);
            bucketMapper.put("personal", personalBucket);

            Bucket travelBucket = new Bucket("Travel", DataHolder.categoryToAvgExpenseMap.get("travel"));
            travelBucket.setName("Travel");
            travelBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("travel"));
            travelBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("travel"));

            DataHolder.buckets.add(travelBucket);
            bucketMapper.put("travel", travelBucket);

            Bucket groceriesBucket = new Bucket("Groceries", DataHolder.categoryToAvgExpenseMap.get("groceries"));
            groceriesBucket.setName("Groceries");
            groceriesBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("groceries"));
            groceriesBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("groceries"));

            DataHolder.buckets.add(groceriesBucket);
            bucketMapper.put("groceries", groceriesBucket);

            Bucket shoppingBucket = new Bucket("Shopping", DataHolder.categoryToAvgExpenseMap.get("shopping"));
            shoppingBucket.setName("Shopping");
            shoppingBucket.setCapacity((float)2 * DataHolder.categoryToAvgExpenseMap.get("shopping"));
            shoppingBucket.setCurrent(DataHolder.monthToCategoryMap.get(Month.DECEMBER).get("shopping"));

            DataHolder.buckets.add(shoppingBucket);
            bucketMapper.put("shopping", shoppingBucket);

        } else {
            for (Bucket bucket : DataHolder.buckets){
                bucketMapper.put(bucket.getName().toLowerCase(), bucket);
            }
        }

        entertainmentSetters(bucketMapper.get("entertainment"));
        diningSetters(bucketMapper.get("dining"));
        personalSetters(bucketMapper.get("personal"));
        travelSetters(bucketMapper.get("travel"));
        groceriesSetters(bucketMapper.get("groceries"));
        shoppingSetters(bucketMapper.get("shopping"));

        addBtn = (ImageButton) root.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setupPopup(inflater);
            }
        });
        //entertainmentPB.setMax(1000);
        return root;
    }

    public void entertainmentSetters(Bucket b){
        entertainmentName.setText(b.getName());
        entertainmentCurrent.setText(String.valueOf((int)b.getCurrent()));
        entertainmentTotal.setText(String.valueOf((int)b.getCapacity()));
        entertainmentPB.setMax((int)b.getCapacity());
        entertainmentPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            entertainmentPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            entertainmentPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else{ //green
            entertainmentPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    public void diningSetters(Bucket b){
        diningName.setText((b.getName()));
        diningCurrent.setText(String.valueOf((int)b.getCurrent()));
        diningTotal.setText(String.valueOf((int)b.getCapacity()));
        diningPB.setMax((int)b.getCapacity());
        diningPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            diningPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            diningPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else { //green
            diningPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    public void personalSetters(Bucket b){
        personalName.setText(b.getName());
        personalCurrent.setText(String.valueOf((int)b.getCurrent()));
        personalTotal.setText(String.valueOf((int)b.getCapacity()));
        personalPB.setMax((int)b.getCapacity());
        personalPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            personalPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            personalPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else{ //green
            personalPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    public void travelSetters(Bucket b){
        travelName.setText(b.getName());
        travelCurrent.setText(String.valueOf((int)b.getCurrent()));
        travelTotal.setText(String.valueOf((int)b.getCapacity()));
        travelPB.setMax((int)b.getCapacity());
        travelPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            travelPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            travelPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else{ //green
            travelPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    public void groceriesSetters(Bucket b){
        groceriesName.setText(b.getName());
        groceriesCurrent.setText(String.valueOf((int)b.getCurrent()));
        groceriesTotal.setText(String.valueOf((int)b.getCapacity()));
        groceriesPB.setMax((int)b.getCapacity());
        groceriesPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            groceriesPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            groceriesPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else{ //green
            groceriesPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    public void shoppingSetters(Bucket b){
        shoppingName.setText(b.getName());
        shoppingCurrent.setText(String.valueOf((int)b.getCurrent()));
        shoppingTotal.setText(String.valueOf((int)b.getCapacity()));
        shoppingPB.setMax((int)b.getCapacity());
        shoppingPB.setProgress((int)b.getCurrent());
        if(b.getCurrent()/b.getCapacity() >= 0.75) { //red
            shoppingPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D9534F")));
        }
        else if(b.getCurrent()/b.getCapacity() < 0.75 && b.getCurrent()/b.getCapacity() >= 0.50){ //yellow
            shoppingPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F0AD4E")));
        }
        else{ //green
            shoppingPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));
        }
    }

    private void setupPopup(LayoutInflater infalter) {
        // Inflate the custom layout/view
        View customView = infalter.inflate(R.layout.budget_add,null);
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
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.budget_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        //final TextInputEditText vendorInput = (TextInputEditText) customView.findViewById(R.id.vendor_input);
        final TextInputEditText capacityInput = (TextInputEditText) customView.findViewById(R.id.capacity_input);

        Spinner categorySpinner = (Spinner) customView.findViewById(R.id.add_budget_category);
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

        Button addButton = (Button) customView.findViewById(R.id.add_budget_btn);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                capacity = Double.valueOf(capacityInput.getText().toString());
//                List<Bucket> bucket = new ArrayList<Bucket>();
//                bucket.add(entertainmentBucket);
//                bucket.add(diningBucket);
//                bucket.add(personalBucket);
//                bucket.add(travelBucket);
//                bucket.add(groceriesBucket);
//                bucket.add(shoppingBucket);
//
//                for (Bucket temp : bucket) {
//                    System.out.println("Temp : " + temp.getName().toLowerCase());
//                    System.out.println("category: " + category.toLowerCase());
//                    if (temp.getName().toString().toLowerCase().equals(category.toString().toLowerCase())) {
//                        temp.setCapacity((float) capacity);
//                        entertainmentSetters(temp);
//                        //entertainmentPB.setMax(1000);
//                        //Log.d("budget fragment", "INSIDE LOOP");
//                    }
//                }
                if(category.toLowerCase().equals("entertainment")){
                    Bucket entertainmentBucket = bucketMapper.get("entertainment");
                    entertainmentBucket.setCapacity((float)capacity);
                    entertainmentSetters(entertainmentBucket);
                }
                else if(category.toLowerCase().equals("dining")){
                    Bucket diningBucket = bucketMapper.get("dining");
                    diningBucket.setCapacity((float)capacity);
                    diningSetters(diningBucket);
                }
                else if(category.toLowerCase().equals("personal")){
                    Bucket personalBucket = bucketMapper.get("personal");
                    personalBucket.setCapacity((float)capacity);
                    personalSetters(personalBucket);
                }
                else if(category.toLowerCase().equals("travel")){
                    Bucket travelBucket = bucketMapper.get("travel");
                    travelBucket.setCapacity((float)capacity);
                    travelSetters(travelBucket);
                }
                else if(category.toLowerCase().equals("groceries")){
                    Bucket groceriesBucket = bucketMapper.get("groceries");
                    groceriesBucket.setCapacity((float)capacity);
                    groceriesSetters(groceriesBucket);
                }
                else if(category.toLowerCase().equals("shopping")){
                    Bucket shoppingBucket = bucketMapper.get("shopping");
                    shoppingBucket.setCapacity((float)capacity);
                    shoppingSetters(shoppingBucket);
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
