package com.example.kowshick.bazarcost;

import android.app.AlertDialog;
import android.os.Handler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.CostViewHolder> implements Filterable {
    private List<FamilyCost> costList;
    private List<FamilyCost> filteredList;
    Context c;
    private RecyclerView mRecyclerV;

    public CostAdapter(List<FamilyCost> costList,Context c, RecyclerView mRecyclerV) {
        this.costList = costList;
        this.c = c;
        filteredList = costList;
        this.mRecyclerV = mRecyclerV;
    }

    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cost_row,parent,false);
        return new CostViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, final int position) {
        holder.date.setText(filteredList.get(position).getDate());
        holder.name.setText(filteredList.get(position).getProduct());
        holder.price.setText(Double.toString(filteredList.get(position).getPrice()));
        holder.rowname.setText("Product Name: ");
        holder.rowdate.setText("Date: ");
        holder.rowprice.setText("Price: ");


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Choose option");
                    builder.setMessage("Update or delete Cost?");
                   builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           //go to update activity
                            if(filteredList.size()>0) {
                                int rowId = filteredList.get(position).getId();

                                goToUpdateActivity(rowId);
                            }

                        }
                    });
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(c);
                            builder.setTitle("Are You sure?");
                            builder.setMessage("Yes or No?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CostDatabase dbHelper = new CostDatabase(c);
                                    dbHelper.deleteCost(filteredList.get(position).getId());
                                    filteredList.remove(position);
                                    mRecyclerV.removeViewAt(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, filteredList.size());
                                    notifyDataSetChanged();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }

        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if(query.isEmpty()){
                    filteredList = costList;
                }else{
                    List<FamilyCost>tempList = new ArrayList<>();
                    for(FamilyCost m : costList){
                        if(m.getCategory().toLowerCase().contains(query.toLowerCase()) ||
                                m.getProduct().toLowerCase().contains(query.toLowerCase())){
                            tempList.add(m);
                        }
                    }
                    filteredList = tempList;

                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<FamilyCost>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class CostViewHolder extends RecyclerView.ViewHolder{
        TextView date,name,price,rowdate,rowprice,rowname;
        ViewPager viewPager;

        public View layout;
        public CostViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            date=itemView.findViewById(R.id.rowDate);
            name=itemView.findViewById(R.id.rowProduct);
            price=itemView.findViewById(R.id.rowPrice);
            rowdate=itemView.findViewById(R.id.rowDateTv);
            rowprice=itemView.findViewById(R.id.rowPriceTv);
            rowname=itemView.findViewById(R.id.rowProductTv);
            //viewPager = (ViewPager) c.findViewById(R.id.viewPager);
        }
    }
    public void add(int position, FamilyCost fc) {
        filteredList.add(position, fc);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        filteredList.remove(position);
        notifyItemRemoved(position);
    }
    private void goToUpdateActivity(int costId){
       /* Intent goToUpdate = new Intent(c,AddFragments.class);
        goToUpdate.putExtra("rowId", costId);
        c.startActivity(goToUpdate);*/

    }

}
