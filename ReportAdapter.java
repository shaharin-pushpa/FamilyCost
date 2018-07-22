package com.example.kowshick.bazarcost;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<FamilyCost> costList;
    Context c;
    private RecyclerView mRecyclerV;

    public ReportAdapter(List<FamilyCost> costList, Context c, RecyclerView mRecyclerV) {
        this.costList = costList;
        this.c = c;
        this.mRecyclerV = mRecyclerV;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.report_row,parent,false);
        return new ReportAdapter.ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
            holder.date.setText(costList.get(position).getDate());
        holder.date.setText(costList.get(position).getDate());
        holder.name.setText(costList.get(position).getProduct());
        holder.quantity.setText(costList.get(position).getQuantity());
        holder.price.setText(Double.toString(costList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return costList.size();
    }


    public class ReportViewHolder extends RecyclerView.ViewHolder{
        TextView date,name,price,quantity;

        public View layout;
        public ReportViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            date=itemView.findViewById(R.id.date);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.quantity);
            price=itemView.findViewById(R.id.price);



        }
    }
}
