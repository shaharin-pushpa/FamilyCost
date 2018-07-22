package com.example.kowshick.bazarcost;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MakeListFragment extends Fragment {
    private Context context;
    private ListAdapter adapter;
   // private List<ShopList>arrayList = new ArrayList<>();
    private Button selectButton;
    private CostDatabase costDatabase;
    private ArrayList<String> itemIds;
    private EditText addEt;
    private Button addBtn;
    private ListView listView;
    private ArrayList<ShopList>arrayList=new ArrayList<>();
    public MakeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_make_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //selectButton = (Button) view.findViewById(R.id.select_button);
        addEt=view.findViewById(R.id.addEt);
        addBtn=view.findViewById(R.id.additem);
        costDatabase=new CostDatabase(context);
        listView=view.findViewById(R.id.listView);
        loadListView(view);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item=addEt.getText().toString();
                boolean status=costDatabase.insertList(item);
                if(status){
                    Toast.makeText(getActivity(), "Item added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Item added", Toast.LENGTH_SHORT).show();
                }
                //arrayList.add(item);
                Toast.makeText(getActivity(), "Item added", Toast.LENGTH_SHORT).show();
                loadListView(view);
                addEt.setText("");
            }
        });
        //onClickEvent(view);
        loadListView(view);
    }


    private void loadListView(View view) {
        arrayList=costDatabase.getAllList();
        adapter = new ListAdapter(context,arrayList,true);
        listView.setAdapter(adapter);
    }

  /* private void onClickEvent(View view) {
        view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();
                if (selectedRows.size() > 0) {
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {
                        try {
                        if (selectedRows.valueAt(i)) {
                            arrayList.remove(selectedRows.keyAt(i));
                            }
                        }
                            catch (Exception e){

                            }

                    }
                    adapter.removeSelection();
                    loadListView(view);
                }
            }
        });
    }*/
}


