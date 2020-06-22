package com.latihanandroid.skripsidenganmetode.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.model.RolesAndGoalsModel;
import com.latihanandroid.skripsidenganmetode.view.AddRolesAndGoalsView;

public class AddRolesAndGoalsController extends BaseController implements View.OnClickListener {
    private AddRolesAndGoalsView addRolesAndGoalsView;
    private RolesAndGoalsModel rolesAndGoalsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_roles_and_goals, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
    }

    @Override
    protected void inisiasi() {
        addRolesAndGoalsView=new AddRolesAndGoalsView(this);
        rolesAndGoalsModel=new RolesAndGoalsModel(getContext());
    }

    @Override
    public void onClick(View view) {
        addProcess();
    }

    private RolesAndGoals getFromView(){
        String roles= addRolesAndGoalsView.getRolesValue();
        String goals=addRolesAndGoalsView.getGoalsValue();
        if (roles.equals("")||goals.equals(""))
            return null;
        else
            return new RolesAndGoals(roles,goals);
    }
    private void addProcess(){
        if (getFromView()==null){
            Toast.makeText(getContext(), "Empty field detected", Toast.LENGTH_SHORT).show();
        }else {
            long idData= rolesAndGoalsModel.insert(getFromView());
            if (idData==-1)
                Toast.makeText(getContext(), "Failed to insert roles and goals", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(getContext(), "Roles and Goals added with id= "+ String.valueOf(idData), Toast.LENGTH_SHORT).show();
                goBack();
            }
        }
    }
    private void goBack(){
        if (getActivity()!=null){
            getActivity().onBackPressed();
        }
    }
}
