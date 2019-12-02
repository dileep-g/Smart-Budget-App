package com.iui.smartbudget.ui.budget;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.iui.smartbudget.utilities.Bucket;
import com.iui.smartbudget.utilities.DataHolder;

public class BudgetFragment extends Fragment {

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
    private Bucket entertainment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        budgetViewModel =
                ViewModelProviders.of(this).get(BudgetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);
        //final TextView textView = root.findViewById(R.id.text_budget);
        //TextView textView1 = root.findViewById(R.id.textViewProgressBar1);
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

        Bucket temp = new Bucket("Entertainment", DataHolder.categoryToAvgExpenseMap.get("entertainment"));
        temp.setName("Entertainment Test");
        temp.setCapacity(1.2 * DataHolder.categoryToAvgExpenseMap.get("entertainment"));
        temp.setCurrent(100);
        entertainmentSetters(temp);
        //entertainmentPB.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5CB85C")));



//        Bucket entertainment = new Bucket("Entertainment", 100);
//        entertainment.setCurrent();
//        entertainment.setCapacity();
        //entertainment.setName(String.valueOf(entertainmentName.getText()));

//        entertainmentName.setText(entertainment.getName());
//        entertainmentCurrent.setText(String.valueOf(entertainment.getCurrent()));
//        entertainmentTotal.setText(String.valueOf(entertainment.getCapacity()));
        /*entertaintTV;
        entertainPB;
        entertainmentTotal;
        Bucket entertain = new Bucket("Entertainment", 300);
        entertain.setCurrent(angshul.give.me.value);
        entertainmentTotal.setText(entertain.getCurrent() / entertain.getCapacity())

*/


//        entertainmentProgressBar = root.findViewById(R.id.entertainmentProgressBar);
//        entertainmentProgressBar.setMax(150);
//        entertainmentProgressBar.setProgress(100);
//        budgetViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    public void create(){

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

}
