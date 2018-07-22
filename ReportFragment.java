package com.example.kowshick.bazarcost;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
    private RecyclerView reportRecyclerview;
    private EditText frm,to,totalCost;
    private Button repBtn;
    private Spinner cateSp;
    private Calendar calendar;
    private int year,month,day;
    private ReportAdapter radapter;
    private CostDatabase costDatabase;
    private FamilyCost fc;
    private double value;
    private String category;
    private TextView total;


    private List<FamilyCost> costs=new ArrayList<>();


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_report, container, false);
        reportRecyclerview=view.findViewById(R.id.reportRecycler);
        frm=view.findViewById(R.id.fromDate);
        totalCost=view.findViewById(R.id.totalCost);
        to=view.findViewById(R.id.toDate);
        total=view.findViewById(R.id.totalTv);
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        cateSp=view.findViewById(R.id.categorySp);
        repBtn=view.findViewById(R.id.reportbtn);
        ArrayAdapter<String> adapterSp = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,getAllCategory());
        cateSp.setAdapter(adapterSp);
        cateSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        /*StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(dayOfMonth).append("/").append(month+1).append("/").append(year);
                        dateET.setText(stringBuilder.toString());*/
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

                        calendar.set(year,month,dayOfMonth);
                        String date=sdf.format(calendar.getTime());
                        frm.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }

        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        /*StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(dayOfMonth).append("/").append(month+1).append("/").append(year);
                        dateET.setText(stringBuilder.toString());*/
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

                        calendar.set(year,month,dayOfMonth);
                        String date=sdf.format(calendar.getTime());
                        to.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }


        });

        repBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costDatabase = new CostDatabase(getActivity());
                fc = new FamilyCost();
                costs = costDatabase.getSearchCost(category, frm.getText().toString(), to.getText().toString());
                total.setVisibility(View.VISIBLE);
                totalCost.setVisibility(View.VISIBLE);
                value = 0;
                for (int i = 0; i < costs.size(); ++i) {
                    value = value + (costs.get(i).getPrice());
                }
                totalCost.setText(String.valueOf(value));
                radapter = new ReportAdapter(costs, getActivity(), reportRecyclerview);
                GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
                reportRecyclerview.setLayoutManager(glm);
        /*LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);*/
                reportRecyclerview.setAdapter(radapter);
                if (costs.size()==0){
                    Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
                }
                frm.setText("");
                to.setText("");
            }

        });

        return view;
    }

    private final List<String> getAllCategory(){
        List<String>areas = new ArrayList<>();
        areas.add("All");
        areas.add("Residence");
        areas.add("Grossary");
        areas.add("Shopping");
        areas.add("Entertainment");
        areas.add("Utility");
        areas.add("Health");
        areas.add("Education");
        areas.add("Traveling");
        Collections.sort(areas);
        return areas;
    }



}
