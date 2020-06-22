package com.latihanandroid.skripsidenganmetode.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.model.RolesAndGoalsModel;
import com.latihanandroid.skripsidenganmetode.view.UpdateDeleteRolesAndGoalsView;

public class UpdateDeleteRolesAndGoalsController extends BaseController
    implements View.OnClickListener
{
    private UpdateDeleteRolesAndGoalsView updateDeleteRolesAndGoalsView;
    private RolesAndGoalsModel rolesAndGoalsModel;
    public static final String EXTRA_ROLES_GOALS="EXTRA_ROLES_GOALS";

    public UpdateDeleteRolesAndGoalsController() {
    }

    @Override
    protected void inisiasi() {
        updateDeleteRolesAndGoalsView=new UpdateDeleteRolesAndGoalsView(this);
        rolesAndGoalsModel=new RolesAndGoalsModel(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.update_delete_menu,menu);
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_update_delete_roles_and_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showDefault();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                deleteProcess();
                break;
            case android.R.id.home:
                goBack();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        updateProcess();
    }

    private void updateProcess(){
        if (getArguments()!=null){
            RolesAndGoals rolesAndGoalsBefore= getArguments().getParcelable(EXTRA_ROLES_GOALS);
            if (rolesAndGoalsBefore!=null){
                RolesAndGoals rolesAndGoalsAfter= getFromView();
                if (rolesAndGoalsAfter==null){
                    Toast.makeText(getContext(), "Empty Field Detected", Toast.LENGTH_SHORT).show();
                }else {
                    rolesAndGoalsAfter.setId(rolesAndGoalsBefore.getId());
                    long updated=rolesAndGoalsModel.update(rolesAndGoalsAfter);
                    if (updated>0){
                        Toast.makeText(getContext(), "Roles and goals with id "+rolesAndGoalsAfter.getId()+" updated", Toast.LENGTH_SHORT).show();
                        goBack();
                    }else{
                        Toast.makeText(getContext(), "Failed to delete data", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }
    private void deleteProcess(){
        if (getArguments()!=null) {
            RolesAndGoals rolesAndGoalsBefore = getArguments().getParcelable(EXTRA_ROLES_GOALS);
            if (rolesAndGoalsBefore != null) {
                long deletedCount= rolesAndGoalsModel.delete(rolesAndGoalsBefore);
                if (deletedCount>0){
                    Toast.makeText(getContext(), "Roles and goals with id "+rolesAndGoalsBefore.getId()+" deleted", Toast.LENGTH_SHORT).show();
                    goBack();
                }else {
                    Toast.makeText(getContext(), "Failed to delete data", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private RolesAndGoals getFromView(){
        String roles= String.valueOf(updateDeleteRolesAndGoalsView.getRolesValue());
        String goals=String.valueOf(updateDeleteRolesAndGoalsView.getGoalsValue());
        if (roles.equals("")||goals.equals(""))
            return null;
        else
            return new RolesAndGoals(roles,goals);
    }
    private void showDefault(){
        Bundle arg=getArguments();
        if (arg!=null){
            RolesAndGoals rolesAndGoals=arg.getParcelable(EXTRA_ROLES_GOALS);
            if (rolesAndGoals!=null){
                updateDeleteRolesAndGoalsView.setNilaiDefault(rolesAndGoals.getRoles(),rolesAndGoals.getGoals());
            }
        }
    }
    private void goBack(){
        if (getActivity()!=null){
            getActivity().onBackPressed();
        }
    }
}
