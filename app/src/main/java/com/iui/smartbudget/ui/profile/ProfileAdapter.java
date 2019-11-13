package com.iui.smartbudget.ui.profile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iui.smartbudget.R;

import org.w3c.dom.Text;

import java.util.List;


public class ProfileAdapter  extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    public Context context;
    public List<String> categories;

    public ProfileAdapter(Context context, List<String> categories){
        this.context=context;
        this.categories=categories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewItem;


        public ViewHolder(View itemView)
        {
            super(itemView);
            this.textViewItem = (TextView) itemView.findViewById(R.id.category_item );

        }
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.recyclerview_category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        holder.textViewItem.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }




}

