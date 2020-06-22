package com.latihanandroid.skripsidenganmetode.view;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.adapter.RolesAndGoalsAdapter;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;

import java.util.ArrayList;

public class RolesAndGoalsListView extends BaseView {
    private FloatingActionButton btnAddRolesAndGoals;
    private RecyclerView rvRolesAndGoalsList;
    private RolesAndGoalsAdapter rolesAndGoalsAdapter;
    private SearchView searchView;

    public RolesAndGoalsListView(BaseController controller) {
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
    }

    @Override
    public void hubungkanViewDenganXML(BaseController baseController) {
        View view=baseController.getView();
        btnAddRolesAndGoals=view.findViewById(R.id.btnAddRolesAndGoals);
        rvRolesAndGoalsList=view.findViewById(R.id.rvRolesGoalsList);
        rolesAndGoalsAdapter=new RolesAndGoalsAdapter();
    }

//    @Override
//    protected void hubungkanViewDenganListener(ViewListener viewListener) {
//        viewListener.onClick(btnAddRolesAndGoals.getId(),rvRolesAndGoalsList.getId());
//    }

    @Override
    public void hubungkanViewDenganListener(BaseController controller){
        if (controller instanceof View.OnClickListener){
            btnAddRolesAndGoals.setOnClickListener((View.OnClickListener) controller);
        }
        if (controller instanceof RolesAndGoalsAdapter.OnItemRolesAndGoalsClickListener){
            rolesAndGoalsAdapter.setOnItemRolesAndGoalsClickCallback(
                    (RolesAndGoalsAdapter.OnItemRolesAndGoalsClickListener) controller);
        }

    }
    public void showListRolesAndGoals(ArrayList<RolesAndGoals> rolesAndGoals){
        rolesAndGoalsAdapter.setDatas(rolesAndGoals);
        rvRolesAndGoalsList.setAdapter(rolesAndGoalsAdapter);
        rvRolesAndGoalsList.setLayoutManager(new LinearLayoutManager(rvRolesAndGoalsList.getContext()));
        rvRolesAndGoalsList.setHasFixedSize(false);
        rolesAndGoalsAdapter.notifyDataSetChanged();
    }

    public void prepareSearchView(Menu menu,BaseController baseController){
        SearchManager searchManager=(SearchManager) baseController.getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager!=null){
            searchView= (SearchView) (menu.findItem(R.id.search)).getActionView();
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
