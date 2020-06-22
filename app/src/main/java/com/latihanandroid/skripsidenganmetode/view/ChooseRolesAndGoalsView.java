package com.latihanandroid.skripsidenganmetode.view;

import android.app.SearchManager;
import android.content.Context;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.adapter.RolesAndGoalsAdapter;
import com.latihanandroid.skripsidenganmetode.controller.ChooseRolesAndGoalsController;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;

import java.util.ArrayList;

public class ChooseRolesAndGoalsView {
    private RecyclerView rvRolesAndGoalsList;
    private RolesAndGoalsAdapter rolesAndGoalsAdapter;
    private SearchView searchView;
    private Toolbar toolbar;

    public ChooseRolesAndGoalsView(ChooseRolesAndGoalsController controller) {
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
    }

    public void hubungkanViewDenganXML(ChooseRolesAndGoalsController baseController) {
        View view=baseController.getView();
        rvRolesAndGoalsList=view.findViewById(R.id.rvRolesGoalsList);
        toolbar=view.findViewById(R.id.toolbar);
        rolesAndGoalsAdapter=new RolesAndGoalsAdapter();
    }

    public void hubungkanViewDenganListener(ChooseRolesAndGoalsController controller) {
        if (controller instanceof RolesAndGoalsAdapter.OnItemRolesAndGoalsClickListener){
            rolesAndGoalsAdapter.setOnItemRolesAndGoalsClickCallback(
                    controller);
        }
        if (controller instanceof View.OnClickListener){
            toolbar.setNavigationOnClickListener(controller);
        }
    }
    public void showListRolesAndGoals(ArrayList<RolesAndGoals> rolesAndGoals){
        rolesAndGoalsAdapter.setDatas(rolesAndGoals);
        rvRolesAndGoalsList.setAdapter(rolesAndGoalsAdapter);
        rvRolesAndGoalsList.setLayoutManager(new LinearLayoutManager(rvRolesAndGoalsList.getContext()));
        rvRolesAndGoalsList.setHasFixedSize(false);
        rolesAndGoalsAdapter.notifyDataSetChanged();
    }

    public void prepareSearchView(int menuId, ChooseRolesAndGoalsController baseController){
        toolbar.setTitle("Choose Roles And Goals");
        toolbar.setNavigationIcon(R.drawable.ic_back_24);
        toolbar.inflateMenu(menuId);
        SearchManager searchManager=(SearchManager) baseController.getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager!=null){
            searchView= (SearchView) toolbar.getMenu().findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(baseController.getActivity().getComponentName()));
            searchView.setQueryHint(baseController.getResources().getString(R.string.search_keyword));
        }
        if (baseController instanceof SearchView.OnQueryTextListener){
            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) baseController);
        }
        if (baseController instanceof SearchView.OnCloseListener){
            searchView.setOnCloseListener((SearchView.OnCloseListener) baseController);
        }
    }
}
