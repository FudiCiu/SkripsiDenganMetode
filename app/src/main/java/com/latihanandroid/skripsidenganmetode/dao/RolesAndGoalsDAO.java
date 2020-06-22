package com.latihanandroid.skripsidenganmetode.dao;

import java.util.ArrayList;

public interface RolesAndGoalsDAO {
    public long insertRolesAndGoals(RolesAndGoals rolesAndGoals);
    public ArrayList<RolesAndGoals> readAllRolesAndGoals();
    public RolesAndGoals readRolesAndGoalsById(int id);
    public ArrayList<RolesAndGoals> findRolesAndGoals(String keyword);
    public long upadateRolesAndGoals(RolesAndGoals rolesAndGoals);
    public long deleteRolesAndGoals(RolesAndGoals rolesAndGoals);
}
