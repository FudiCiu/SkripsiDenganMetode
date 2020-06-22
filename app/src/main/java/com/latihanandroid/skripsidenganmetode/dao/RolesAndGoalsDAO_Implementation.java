package com.latihanandroid.skripsidenganmetode.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RolesAndGoalsDAO_Implementation implements RolesAndGoalsDAO {
    private SQLiteHelper helper;

    public RolesAndGoalsDAO_Implementation(SQLiteHelper helper) {
        this.helper = helper;
    }

    public long insertRolesAndGoals(RolesAndGoals rolesAndGoals){
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(RolesAndGoals.ROLES_COLUMN,rolesAndGoals.getRoles());
        contentValues.put(RolesAndGoals.GOALS_COLUMN,rolesAndGoals.getGoals());
        long id=database.insert(RolesAndGoals.TABLE_NAME,null,contentValues);
        database.close();
        return id;
    }
    public ArrayList<RolesAndGoals> readAllRolesAndGoals(){
        ArrayList<RolesAndGoals> rolesAndGoalses=new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.query(RolesAndGoals.TABLE_NAME,null,null,
                null,null,null,RolesAndGoals.ID_COLUMN);
        if (cursor.moveToFirst()){
            do{
                RolesAndGoals rolesAndGoals=new RolesAndGoals(
                        cursor.getInt(cursor.getColumnIndex(RolesAndGoals.ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.ROLES_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.GOALS_COLUMN))
                );
                rolesAndGoalses.add(rolesAndGoals);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return rolesAndGoalses;
    }
    public RolesAndGoals readRolesAndGoalsById(int id){
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor=database.query(RolesAndGoals.TABLE_NAME,null,RolesAndGoals.ID_COLUMN+" =? ",new String[]{
                String.valueOf(id)
        },null,null,null);
        RolesAndGoals rolesAndGoals=null;
        if (cursor.moveToFirst()){
            rolesAndGoals=new RolesAndGoals(
                    cursor.getInt(cursor.getColumnIndex(RolesAndGoals.ID_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(RolesAndGoals.ROLES_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(RolesAndGoals.GOALS_COLUMN))
            );
            cursor.close();
            database.close();
        }
        return rolesAndGoals;
    }
    public ArrayList<RolesAndGoals> findRolesAndGoals(String keyword){
        ArrayList<RolesAndGoals> rolesAndGoalses=new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor= database.query(true,RolesAndGoals.TABLE_NAME,
                null,
                RolesAndGoals.ROLES_COLUMN+" LIKE ? OR "+ RolesAndGoals.GOALS_COLUMN+" LIKE ? ",
                new String[]{keyword+"%",keyword+"%"},
                null,
                null,
                RolesAndGoals.ID_COLUMN,
                null);
        if (cursor.moveToFirst()){
            do {
                RolesAndGoals rolesAndGoals=new RolesAndGoals(
                        cursor.getInt(cursor.getColumnIndex(RolesAndGoals.ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.ROLES_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.GOALS_COLUMN))
                );
                rolesAndGoalses.add(rolesAndGoals);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return rolesAndGoalses;
    }

    public long upadateRolesAndGoals(RolesAndGoals rolesAndGoals){
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(RolesAndGoals.ROLES_COLUMN,rolesAndGoals.getRoles());
        contentValues.put(RolesAndGoals.GOALS_COLUMN,rolesAndGoals.getGoals());
        long changed=database.update(RolesAndGoals.TABLE_NAME,contentValues,RolesAndGoals.ID_COLUMN+" =?",new String[]{
                String.valueOf(rolesAndGoals.getId())
        });
        database.close();
        return changed;
    }
    public long deleteRolesAndGoals(RolesAndGoals rolesAndGoals){
        SQLiteDatabase database=helper.getWritableDatabase();
        Cursor cursor=database.query(DailySchedule.TABLE_NAME,null,DailySchedule.AKTIVITAS_COLUMN+"=? ",new String[]{String.valueOf(rolesAndGoals.getId())},
                null,
                null,DailySchedule.ID_COLUMN);
        if (cursor.moveToFirst()){
            database.close();
            return -1;
        }else {
            long deletedCount= database.delete(RolesAndGoals.TABLE_NAME,RolesAndGoals.ID_COLUMN+" =?",
                    new String[]{String.valueOf(rolesAndGoals.getId())});
            database.close();
            return deletedCount;
        }
    }
}
