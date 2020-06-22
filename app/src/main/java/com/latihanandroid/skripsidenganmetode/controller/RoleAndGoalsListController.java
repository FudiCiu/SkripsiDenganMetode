package com.latihanandroid.skripsidenganmetode.controller;

import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;

import com.latihanandroid.skripsidenganmetode.GeneratePdfHelper;
import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.adapter.RolesAndGoalsAdapter;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.model.RolesAndGoalsModel;
import com.latihanandroid.skripsidenganmetode.view.RolesAndGoalsListView;

import java.util.ArrayList;

public class RoleAndGoalsListController extends BaseController implements
        RolesAndGoalsAdapter.OnItemRolesAndGoalsClickListener,
        View.OnClickListener,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener
{
    private RolesAndGoalsListView rolesAndGoalsListView;
    private RolesAndGoalsModel rolesAndGoalsModel;
    @Override
    protected void inisiasi() {
        rolesAndGoalsListView=new RolesAndGoalsListView(this);
        rolesAndGoalsModel=new RolesAndGoalsModel(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_roles_and_goals_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showAllRolesAndGoalsList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        showSearchView(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.print:
                printPdf(rolesAndGoalsModel.read());
                break;
        }
        return false;
    }
    @Override
    public void onItemClick(RolesAndGoals rolesAndGoals) {
        openUpdateDeleteMenu(rolesAndGoals);
    }

    @Override
    public void onClick(View view) {
        openAddMenu();
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
        showAllRolesAndGoalsList();
        return false;
    }

    private void openAddMenu(){
        if (getActivity()!=null){
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_to_add_roals_goals);
        }
    }
    private void openUpdateDeleteMenu(RolesAndGoals rolesAndGoals){
        Bundle bundle=new Bundle();
        bundle.putParcelable(UpdateDeleteRolesAndGoalsController.EXTRA_ROLES_GOALS,rolesAndGoals);
        if (getActivity()!=null){
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_update_delete_roles_and_goals,bundle);
        }else {
            Toast.makeText(getContext(), "Activity is null", Toast.LENGTH_SHORT).show();
        }
    }
    private void showSearchView(Menu menu){
        rolesAndGoalsListView.prepareSearchView(menu,this);
    }
    private void showAllRolesAndGoalsList(){
        ArrayList<RolesAndGoals> datas=rolesAndGoalsModel.read();
        rolesAndGoalsListView.showListRolesAndGoals(datas);
    }
    private void findRolesAndGoals(String keyword){
        ArrayList<RolesAndGoals> rolesAndGoals=rolesAndGoalsModel.read(keyword);
        rolesAndGoalsListView.showListRolesAndGoals(rolesAndGoals);
    }
    private void printPdf(ArrayList<RolesAndGoals> rolesAndGoals){
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        if (rolesAndGoals.size()>0){
            GeneratePdfHelper pdfHelper=new GeneratePdfHelper(getContext());
            pdfHelper.createPdfRolesGoals(rolesAndGoals);
        }else {
            Toast.makeText(getContext(), "There is no data to print", Toast.LENGTH_SHORT).show();
        }
    }
}
