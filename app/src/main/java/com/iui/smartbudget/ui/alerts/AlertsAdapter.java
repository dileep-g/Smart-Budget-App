package com.iui.smartbudget.ui.alerts;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.Alert;
import com.iui.smartbudget.utilities.Bucket;
import com.iui.smartbudget.utilities.DataHolder;
import com.iui.smartbudget.utilities.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertsAdapter extends RecyclerView.Adapter <AlertsAdapter.ViewHolder> {

    private Context context;
    private List<Alert> dataList;
    private String datePattern;
    private SimpleDateFormat dateFromatter;
    private PopupWindow mPopupWindow;
    private ViewGroup parentLayout;
    private Map<String, TextInputEditText>  inputMapper;
    private Map<String, TextInputLayout>  inputBoxMapper;
    private String[] bucketNames = {"entertainment", "travel", "groceries", "dining", "personal", "shopping"};
    private List<String> editBucketNames;

    public AlertsAdapter(List<Alert> data, Context context, ViewGroup layout)
    {
        this.dataList = data;
        this.context = context;
        this.datePattern = "EEE, MMM dd yyyy";
        this.dateFromatter = new SimpleDateFormat(datePattern);
        this.parentLayout = layout;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.alert_title);
            this.textViewDate = (TextView) itemView.findViewById(R.id.alert_date );
            this.textViewText = (TextView) itemView.findViewById(R.id.alert_text );
            //textViewText.setTextColor(Color.RED);
        }
    }

    @Override
    public AlertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater. from (parent.getContext()).inflate(R.layout.recyclerview_alert_item , parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlertsAdapter.ViewHolder holder, final int position)
    {
        holder.textViewTitle.setText(dataList.get(position). getTitle());
        holder.textViewDate.setText(dateFromatter.format(dataList.get(position).getDateTime()));
        holder.textViewText.setText(dataList.get(position).getText());

        if (position%2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ecf9ec"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText (context, "Item " + position + " is clicked.", Toast. LENGTH_SHORT ).show( );
                LayoutInflater inflater = LayoutInflater.from(context);
                if (DataHolder.alerts.get(position).getMainBucket() != null) {
                    setupPopup(inflater, position);
                } else {
                    Toast.makeText (context, ":)", Toast. LENGTH_SHORT ).show( );
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public void addAlert(Alert alert) {
        this.dataList.add(0, alert);
        notifyDataSetChanged();
    }

    private void setupPopup(LayoutInflater infalter, final int position) {
        // Inflate the custom layout/view
        View customView = infalter.inflate(R.layout.alert_budget_edit,null);
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
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_alerts_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        inputMapper = new HashMap<>();
        inputBoxMapper = new HashMap<>();
        final TextInputEditText entertainmentInput = (TextInputEditText) customView.findViewById(R.id.entertainment_input);
        inputMapper.put("entertainment", entertainmentInput);
        inputBoxMapper.put("entertainment-box", (TextInputLayout) customView.findViewById(R.id.edit_entertainment_layout));
        final TextInputEditText travelInput = (TextInputEditText) customView.findViewById(R.id.travel_input);
        inputMapper.put("travel", travelInput);
        inputBoxMapper.put("travel-box", (TextInputLayout) customView.findViewById(R.id.edit_travel_layout));
        final TextInputEditText groceriesInput = (TextInputEditText) customView.findViewById(R.id.groceries_input);
        inputMapper.put("groceries", groceriesInput);
        inputBoxMapper.put("groceries-box", (TextInputLayout) customView.findViewById(R.id.edit_groceries_layout));
        final TextInputEditText diningInput = (TextInputEditText) customView.findViewById(R.id.dining_input);
        inputMapper.put("dining", diningInput);
        inputBoxMapper.put("dining-box", (TextInputLayout) customView.findViewById(R.id.edit_dining_layout));
        final TextInputEditText personalInput = (TextInputEditText) customView.findViewById(R.id.personal_input);
        inputMapper.put("personal", personalInput);
        inputBoxMapper.put("personal-box", (TextInputLayout) customView.findViewById(R.id.edit_personal_layout));
        final TextInputEditText shoppingInput = (TextInputEditText) customView.findViewById(R.id.shopping_input);
        inputMapper.put("shopping", shoppingInput);
        inputBoxMapper.put("shopping-box", (TextInputLayout) customView.findViewById(R.id.edit_shopping_layout));

        Alert alert = DataHolder.getInstance().alerts.get(position);
        // Make edit boxes visible for alert specific buckets
        editBucketNames = new ArrayList<>();
        if (alert.getMainBucket() != null) {
            TextInputEditText mainBucketInput = inputMapper.get(alert.getMainBucket().getName().toLowerCase());
            inputBoxMapper.get(alert.getMainBucket().getName().toLowerCase()+"-box").setVisibility(View.VISIBLE);
            inputMapper.get(alert.getMainBucket().getName().toLowerCase()).setText(Float.toString(Math.round((alert.getMainBucket().getCapacity() + alert.getMainValue())*10.0f)/10.0f));
            editBucketNames.add(alert.getMainBucket().getName().toLowerCase());
            for (Bucket bucket: alert.getRecoBuckets().keySet()) {
                inputBoxMapper.get(bucket.getName().toLowerCase() + "-box").setVisibility(View.VISIBLE);
                inputMapper.get(bucket.getName().toLowerCase()).setText(Float.toString(Math.round((bucket.getCapacity() - alert.getRecoBuckets().get(bucket))*10.0f)/10.0f));
                editBucketNames.add(bucket.getName().toLowerCase());
            }
        }

        Button addButton = (Button) customView.findViewById(R.id.add_alerts_btn);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                vendor = vendorInput.getText().toString();
//                expense = Double.valueOf(expenseInput.getText().toString());
//                Log.d(TAG, vendor + ": " + expense);
//
//                Record record = new Record();
//                record.setDateTime(new Date());
//                record.setVendor(vendor);
//                record.setExpense((float)expense);
//                record.setCategory(category.toLowerCase());
//                mListadapter.addRecord(record);

                for (String bucketName : editBucketNames) {
                    for(Bucket bucket : DataHolder.getInstance().buckets) {
                        if (bucket.getName().toLowerCase().equals(bucketName)) {
                            bucket.setCapacity(Float.valueOf(inputMapper.get(bucketName).getText().toString()));
                        }
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
        mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER,0,0);
    }
}
