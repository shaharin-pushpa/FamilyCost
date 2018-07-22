package com.example.kowshick.bazarcost;


import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kowshick.bazarcost.Database.CostDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SEARCH_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private RecyclerView recyclerview;
    private CostAdapter costadapter;
    private CostDatabase costDatabase;
    private FamilyCost fc;
    private List<FamilyCost>costs=new ArrayList<>();


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        recyclerview=view.findViewById(R.id.recyclerView);
        //viewPager=view.findViewById(R.id.viewPager);
        //tabLayout=view.findViewById(R.id.tabLayout);
        costDatabase=new CostDatabase(getActivity());
        fc=new FamilyCost();
        costs=costDatabase.getAllCost();
        if(costs.size()==0){
            ((TextView)view.findViewById(R.id.showEmptyListMsgTV)).setVisibility(View.VISIBLE);
        }
        costadapter = new CostAdapter(costs,getActivity(),recyclerview);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),1);
        recyclerview.setLayoutManager(glm);
        /*LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);*/
        recyclerview.setAdapter(costadapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager manager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                costadapter.getFilter().filter(query);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                costadapter.getFilter().filter(newText);
                return true;
            }
        });

    }


}
