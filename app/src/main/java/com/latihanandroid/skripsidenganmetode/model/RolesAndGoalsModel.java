package com.latihanandroid.skripsidenganmetode.model;

import android.content.Context;

import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoalsDAO;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoalsDAO_Implementation;
import com.latihanandroid.skripsidenganmetode.dao.SQLiteHelper;

import java.util.ArrayList;

public class RolesAndGoalsModel extends BaseModel {
    private Context context;
    private RolesAndGoals rolesAndGoals;
    private RolesAndGoalsDAO rolesAndGoalsDAO;
    public RolesAndGoalsModel(Context context) {
        this.context=context;
        hubungkanDenganDatabase();
    }
    public void setRolesAndGoals(int id, String roles, String goals){
        this.rolesAndGoals= new RolesAndGoals(id,roles,goals);
    }
    public void setRolesAndGoas(String roles,String goals){
        this.rolesAndGoals=new RolesAndGoals(roles,goals);
    }

    @Override
    protected void hubungkanDenganDatabase() {
        SQLiteHelper helper=SQLiteHelper.getInstance(context);
        rolesAndGoalsDAO=new RolesAndGoalsDAO_Implementation(helper);
    }

    public long insert(RolesAndGoals rolesAndGoals){
        return rolesAndGoalsDAO.insertRolesAndGoals(rolesAndGoals);
    }
    public long insert(){
        return rolesAndGoalsDAO.insertRolesAndGoals(this.rolesAndGoals);
    }
    public ArrayList<RolesAndGoals> read(){
        return rolesAndGoalsDAO.readAllRolesAndGoals();
    }
    public RolesAndGoals read(int id){
        return rolesAndGoalsDAO.readRolesAndGoalsById(id);
    }
    public ArrayList<RolesAndGoals> read(String keyword){
        return rolesAndGoalsDAO.findRolesAndGoals(keyword);
    }
    public long update(RolesAndGoals rolesAndGoals){
        return rolesAndGoalsDAO.upadateRolesAndGoals(rolesAndGoals);
    }
    public long delete(RolesAndGoals rolesAndGoals){
        return rolesAndGoalsDAO.deleteRolesAndGoals(rolesAndGoals);
    }
}
