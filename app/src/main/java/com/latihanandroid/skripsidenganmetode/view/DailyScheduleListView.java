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
import com.latihanandroid.skripsidenganmetode.adapter.DailyScheduleAdapter;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;
import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;

import java.util.ArrayList;

public class DailyScheduleListView extends BaseView {
    private RecyclerView rvDailyScheduleList;
    private DailyScheduleAdapter dailyScheduleAdapter;
    private FloatingActionButton btnAddDailyScheduleFragment;
    private SearchView searchView;

    public DailyScheduleListView(BaseController controller) {
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
    }

    @Override
    public void hubungkanViewDenganXML(BaseController baseController) {
        View view=baseController.getView();
        btnAddDailyScheduleFragment= view.findViewById(R.id.btnAddDailySchedule);
        rvDailyScheduleList= view.findViewById(R.id.rvDailyScheduleList);
        dailyScheduleAdapter=new DailyScheduleAdapter();
    }

    @Override
    public void hubungkanViewDenganListener(BaseController controller) {
        if (controller instanceof View.OnClickListener){
            btnAddDailyScheduleFragment.setOnClickListener((View.OnClickListener) controller);
        }
        if (controller instanceof DailyScheduleAdapter.OnItemDailyScheduleClickListener){
            dailyScheduleAdapter.setOnItemDailyScheduleClickCallback((DailyScheduleAdapter.OnItemDailyScheduleClickListener) controller);
        }
    }
    public void prepareSearchView(Menu menu, BaseController baseController){
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
    public void showListDailySchedule(ArrayList<DailySchedule> dailySchedules){
        dailyScheduleAdapter.setDatas(dailySchedules);
        rvDailyScheduleList.setHasFixedSize(false);
        rvDailyScheduleList.setLayoutManager(new LinearLayoutManager(rvDailyScheduleList.getContext()));
        rvDailyScheduleList.setAdapter(dailyScheduleAdapter);
    }
}
