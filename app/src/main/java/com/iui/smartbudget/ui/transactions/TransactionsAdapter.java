package com.iui.smartbudget.ui.transactions;

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
import com.iui.smartbudget.utilities.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter <TransactionsAdapter.ViewHolder> implements Filterable
{
    private Context context;
    private List<Record> dataList;
    private List<Record> dataListFiltered;
    private String datePattern;
    private SimpleDateFormat dateFromatter;

    public TransactionsAdapter(List<Record> data, Context context)
    {
        this.dataList = data;
        this.context = context;
        this.dataListFiltered = data;
        this.datePattern = "EEE, MMM dd yyyy";
        this.dateFromatter = new SimpleDateFormat(datePattern);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewDate;
        TextView textViewVendor;
        TextView textViewCategory;
        TextView textViewExpense;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.textViewDate = (TextView) itemView.findViewById(R.id.record_date );
            this.textViewVendor = (TextView) itemView.findViewById(R.id.record_vendor);
            this.textViewCategory = (TextView) itemView.findViewById(R.id.record_category);
            this.textViewExpense = (TextView) itemView.findViewById(R.id.record_expense);
        }
    }

    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater. from (parent.getContext()).inflate(R.layout.recyclerview_record_item , parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionsAdapter.ViewHolder holder, final int position)
    {
        holder.textViewDate.setText(dateFromatter.format(dataListFiltered.get(position).getDateTime()));
        holder.textViewVendor.setText(dataListFiltered.get(position). getVendor());
        holder.textViewCategory.setText(dataListFiltered.get(position).getCategory());
        holder.textViewExpense.setText("$ " + String.valueOf(dataListFiltered.get(position).getExpense()));

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
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<Record> filteredList = new ArrayList<>();
                    for (Record row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for category or vendor name match
                        if (row.getCategory().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getVendor().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                filterResults.count = dataListFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<Record>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
