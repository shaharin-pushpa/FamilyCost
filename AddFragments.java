package com.example.kowshick.bazarcost;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragments extends Fragment {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Calendar calendar;
    private Spinner categorySp;
    private int year,month,day;
    private EditText dateET,productET,quantityET,priceET;
    private RadioGroup rg;
    private int rowId = 0;
    private Button btn;
    private CostDatabase costDatabase;
    private String category;
    private Fragment fragment;
    private ViewPager viewPager;

    public AddFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_add_fragments, container, false);
        dateET = view.findViewById(R.id.dateEnter);
        productET = view.findViewById(R.id.productEnter);
        quantityET=view.findViewById(R.id.quantityEnter);
        categorySp=view.findViewById(R.id.addCategorySp);
        priceET = view.findViewById(R.id.priceEnter);
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        btn=view.findViewById(R.id.add);
        //viewPager=view.findViewById(R.id.viewPager);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
        costDatabase=new CostDatabase(getActivity());

        dateET.setOnClickListener(new View.OnClickListener() {
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
                        dateET.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        ArrayAdapter<String> addadapterSp = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,getCategory());
        categorySp.setAdapter(addadapterSp);
        categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String date = dateET.getText().toString();
                        String product = productET.getText().toString();
                        String quantity = quantityET.getText().toString();
                        double price = Double.parseDouble(priceET.getText().toString());
                        //fc.addCostTOList(fc);
                        if (date.equals(null) || product.equals(null) || price == 0) {
                            Toast.makeText(getActivity(), "Please fill the fields", Toast.LENGTH_SHORT).show();
                        } else {
                            FamilyCost fc = new FamilyCost(date, category, product, quantity, price);
                            boolean status = costDatabase.insertCost(fc);
                            if (status) {
                                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Could Not Saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                     catch (Exception e){
                        //productET.setText(e.getMessage());
                         Toast.makeText(getActivity(), "Please fill the fields", Toast.LENGTH_SHORT).show();
                    }

                    /*fragment=new ListFragment();
                    getFragmentManager().beginTransaction().replace(R.id.viewPager,fragment).commit();*/
                    //tab change

                    dateET.setText("");
                    productET.setText("");
                    quantityET.setText("");
                    priceET.setText("");
                    //getFragmentManager().beginTransaction().detach(AddFragments.this).attach(AddFragments.this).commit();
                   // MainActivity m=new MainActivity();
                   // m.tabLoad();
                    //viewPager.setCurrentItem(0);

                }

            });

        return view;
    }
    private final List<String> getCategory(){
        List<String>areas = new ArrayList<>();
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
