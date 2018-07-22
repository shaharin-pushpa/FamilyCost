package com.example.kowshick.bazarcost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private CostDatabase costDatabase;
    private ArrayList<ShopList> arrayList=new ArrayList<>();
    private LayoutInflater inflater;
    private boolean isListView;
    private SparseBooleanArray mSelectedItemsIds;

    public ListAdapter(Context context, ArrayList<ShopList> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            //inflate the layout on basis of boolean
            view = inflater.inflate(R.layout.list_row, viewGroup, false);
            costDatabase=new CostDatabase(context);

            viewHolder.label = (TextView) view.findViewById(R.id.nameTv);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.label.setText(arrayList.get(i).getName());

        viewHolder.checkBox.setChecked(mSelectedItemsIds.get(i));
        //checkbox

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
                //checkCheckBox(i, mSelectedItemsIds.get(i));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose option");
                builder.setMessage("Delete Item?");
               /* builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are You sure?");
                        builder.setMessage("Yes or No?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean st = costDatabase.deleteList(arrayList.get(i).getId());
                                if (st) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Delete not  successful", Toast.LENGTH_SHORT).show();
                                }
                                arrayList.remove(i);
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


/// label


       /* viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(i, !mSelectedItemsIds.get(i));
            }
        });*/

        return view;
    }

    private class ViewHolder {
        private TextView label;
        private CheckBox checkBox;
    }


    /**
     * Remove all checkbox Selection
     **/
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, true);

        }
        else {
            mSelectedItemsIds.delete(position);

        }

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}



