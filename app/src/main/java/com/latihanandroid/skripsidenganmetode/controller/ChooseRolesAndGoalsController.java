package com.latihanandroid.skripsidenganmetode.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.adapter.RolesAndGoalsAdapter;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.model.RolesAndGoalsModel;
import com.latihanandroid.skripsidenganmetode.view.ChooseRolesAndGoalsView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChooseRolesAndGoalsController extends DialogFragment implements
        RolesAndGoalsAdapter.OnItemRolesAndGoalsClickListener,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener,
        View.OnClickListener
{
    private ChooseRolesAndGoalsView chooseRolesAndGoalsView;
    private RolesAndGoalsModel rolesAndGoalsModel;
    private OnRolesAndGoalsSelectedListener rolesAndGoalsCallback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_choose_roles_and_goals, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showListRolesAndGoals();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        showSearchView(R.menu.main);
    }

    @Override
    public boolean onQueryTextSubmit(String keyword) {
        findRolesAndGoals(keyword);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onClose() {
        showListRolesAndGoals();
        return false;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment= getParentFragment();
        if (fragment instanceof AddDailyScheduleController){
            AddDailyScheduleController addDailyScheduleFragment= (AddDailyScheduleController) fragment;
            this.rolesAndGoalsCallback= addDailyScheduleFragment;

        }else if (fragment instanceof UpdateDeleteDailyScheduleController){
            UpdateDeleteDailyScheduleController updateDeleteDailyScheduleFragment= (UpdateDeleteDailyScheduleController) fragment;
            this.rolesAndGoalsCallback=updateDeleteDailyScheduleFragment;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.rolesAndGoalsCallback=null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goBack();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(RolesAndGoals rolesAndGoals) {
        if (rolesAndGoalsCallback!=null){
            rolesAndGoalsCallback.onRolesAndGoalsSelected(rolesAndGoals);
        }else {
            Toast.makeText(getContext(), "Tess", Toast.LENGTH_SHORT).show();
        }
        goBack();
    }
    @Override
    public void onClick(View view) {
        this.dismiss();
    }

    private void inisiasi() {
        chooseRolesAndGoalsView=new ChooseRolesAndGoalsView(this);
        rolesAndGoalsModel=new RolesAndGoalsModel(getContext());
    }
    private void showListRolesAndGoals(){

        if (getArguments()!=null){
            this.rolesAndGoalsCallback= (ChooseRolesAndGoalsController.OnRolesAndGoalsSelectedListener) getArguments().getSerializable("EXTRA_INTERFACE");
        }

        ArrayList<RolesAndGoals> datas=rolesAndGoalsModel.read();
        chooseRolesAndGoalsView.showListRolesAndGoals(datas);
    }
    private void showSearchView(int menu){
        chooseRolesAndGoalsView.prepareSearchView(menu,this);
    }
    private void findRolesAndGoals(String keyword){
        ArrayList<RolesAndGoals> rolesAndGoals=rolesAndGoalsModel.read(keyword);
        chooseRolesAndGoalsView.showListRolesAndGoals(rolesAndGoals);
    }
    private void goBack(){
        this.dismiss();
    }
    public interface OnRolesAndGoalsSelectedListener extends Serializable {
        public void onRolesAndGoalsSelected(RolesAndGoals rolesAndGoals);
    }
}
