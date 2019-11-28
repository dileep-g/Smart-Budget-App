package com.iui.smartbudget.ui.alerts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.iui.smartbudget.R;
import com.iui.smartbudget.utilities.Alert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter <AlertsAdapter.ViewHolder> {

    private Context context;
    private List<Alert> dataList;
    private String datePattern;
    private SimpleDateFormat dateFromatter;

    public AlertsAdapter(List<Alert> data, Context context)
    {
        this.dataList = data;
        this.context = context;
        this.datePattern = "EEE, MMM dd yyyy";
        this.dateFromatter = new SimpleDateFormat(datePattern);
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
            textViewText.setTextColor(Color.RED);
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
                Toast.makeText (context, "Item " + position + " is clicked.", Toast. LENGTH_SHORT ).show( );
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
}