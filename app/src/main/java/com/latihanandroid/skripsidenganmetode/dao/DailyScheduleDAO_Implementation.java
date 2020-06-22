package com.latihanandroid.skripsidenganmetode.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DailyScheduleDAO_Implementation implements DailyScheduleDAO {
    private SQLiteHelper helper;

    public DailyScheduleDAO_Implementation(SQLiteHelper helper) {
        this.helper = helper;
    }

    @Override
    public long insertDailySchedule(DailySchedule dailySchedule){
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(DailySchedule.HARI_COLUMN,dailySchedule.getHari());
        contentValues.put(DailySchedule.JENIS_ALARM_COLUMN,dailySchedule.getJenisAlarm());
        contentValues.put(DailySchedule.WAKTU_COLUMN,dailySchedule.getWaktuAsString());
        contentValues.put(DailySchedule.TEMPAT_COLUMN,dailySchedule.getTempat());
        contentValues.put(DailySchedule.AKTIVITAS_COLUMN,dailySchedule.getAktivitas().getId());
        long id= database.insert(DailySchedule.TABLE_NAME,null,contentValues);
        database.close();
        return id;
    }

    @Override
    public ArrayList<DailySchedule> readAllDailySchedule(int hari){
        ArrayList<DailySchedule> dailySchedules=new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        RolesAndGoalsDAO rolesAndGoalsDAO=new RolesAndGoalsDAO_Implementation(helper);
        Cursor cursor=database.query(DailySchedule.TABLE_NAME,null,DailySchedule.HARI_COLUMN+"=? ",new String[]{String.valueOf(hari)},null,
                null,DailySchedule.ID_COLUMN);
        if (cursor.moveToNext()){
            do {
                DailySchedule dailySchedule=new DailySchedule();
                dailySchedule.setId(cursor.getInt(cursor.getColumnIndex(DailySchedule.ID_COLUMN)));
                dailySchedule.setHari((short) cursor.getInt(cursor.getColumnIndex(DailySchedule.HARI_COLUMN)));
                dailySchedule.setJenisAlarmFromInt(cursor.getInt(cursor.getColumnIndex(DailySchedule.JENIS_ALARM_COLUMN)));
                dailySchedule.setWaktuFromString(cursor.getString(cursor.getColumnIndex(DailySchedule.WAKTU_COLUMN)));
                dailySchedule.setTempat(cursor.getString(cursor.getColumnIndex(DailySchedule.TEMPAT_COLUMN)));
                dailySchedule.setAktivitas(rolesAndGoalsDAO.readRolesAndGoalsById(cursor.getInt(cursor.getColumnIndex(DailySchedule.AKTIVITAS_COLUMN))));
                dailySchedules.add(dailySchedule);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dailySchedules;
    }

    @Override
    public ArrayList<DailySchedule> readAllDailySchedule(){
        ArrayList<DailySchedule> dailySchedules=new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        RolesAndGoalsDAO rolesAndGoalsDAO=new RolesAndGoalsDAO_Implementation(helper);
        Cursor cursor=database.query(DailySchedule.TABLE_NAME,null,null,
                null,null,
                null,DailySchedule.ID_COLUMN);
        if (cursor.moveToNext()){
            do {
                DailySchedule dailySchedule=new DailySchedule();
                dailySchedule.setId(cursor.getInt(cursor.getColumnIndex(DailySchedule.ID_COLUMN)));
                dailySchedule.setHari((short) cursor.getInt(cursor.getColumnIndex(DailySchedule.HARI_COLUMN)));
                dailySchedule.setJenisAlarmFromInt(cursor.getInt(cursor.getColumnIndex(DailySchedule.JENIS_ALARM_COLUMN)));
                dailySchedule.setWaktuFromString(cursor.getString(cursor.getColumnIndex(DailySchedule.WAKTU_COLUMN)));
                dailySchedule.setTempat(cursor.getString(cursor.getColumnIndex(DailySchedule.TEMPAT_COLUMN)));
                dailySchedule.setAktivitas(rolesAndGoalsDAO.readRolesAndGoalsById(cursor.getInt(cursor.getColumnIndex(DailySchedule.AKTIVITAS_COLUMN))));

                dailySchedules.add(dailySchedule);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dailySchedules;
    }

    @Override
    public ArrayList<DailySchedule> findDailySchedule(int hari,String keyword){
        ArrayList<DailySchedule> dailySchedules=new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        String sqlSelectTable= "SELECT "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.ID_COLUMN+", "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.HARI_COLUMN+", "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.JENIS_ALARM_COLUMN+", "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.WAKTU_COLUMN+", "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.TEMPAT_COLUMN+", "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.AKTIVITAS_COLUMN+", "+
                RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ID_COLUMN+", "+
                RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ROLES_COLUMN+", "+
                RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.GOALS_COLUMN+
                " FROM "+DailySchedule.TABLE_NAME +
                " INNER JOIN "+ RolesAndGoals.TABLE_NAME+
                " ON "+DailySchedule.TABLE_NAME+"."+DailySchedule.AKTIVITAS_COLUMN+"="+RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ID_COLUMN
                +" WHERE "+DailySchedule.TABLE_NAME+"."+DailySchedule.HARI_COLUMN+"="+String.valueOf(hari)+" AND ( "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.WAKTU_COLUMN+" LIKE '"+keyword+"%' OR "+
                DailySchedule.TABLE_NAME+"."+DailySchedule.TEMPAT_COLUMN+" LIKE '"+keyword+"%' OR "+
                RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ROLES_COLUMN+" LIKE '"+keyword+"%' OR "+
                RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.GOALS_COLUMN+" LIKE '"+keyword+"%'"+
                " ) "
                ;
        Cursor cursor=database.rawQuery(sqlSelectTable,null);
        if (cursor.moveToFirst()){
            do {
                DailySchedule dailySchedule=new DailySchedule();
                dailySchedule.setId(cursor.getInt(cursor.getColumnIndex(DailySchedule.TABLE_NAME+"."+DailySchedule.ID_COLUMN)));
                dailySchedule.setHari((short) cursor.getInt(cursor.getColumnIndex(DailySchedule.TABLE_NAME+"."+DailySchedule.HARI_COLUMN)));
                dailySchedule.setJenisAlarmFromInt(cursor.getInt(cursor.getColumnIndex(DailySchedule.TABLE_NAME+"."+DailySchedule.JENIS_ALARM_COLUMN)));
                dailySchedule.setWaktuFromString(cursor.getString(cursor.getColumnIndex(DailySchedule.TABLE_NAME+"."+DailySchedule.WAKTU_COLUMN)));
                dailySchedule.setTempat(cursor.getString(cursor.getColumnIndex(DailySchedule.TABLE_NAME+"."+DailySchedule.TEMPAT_COLUMN)));
                dailySchedule.setAktivitas(new RolesAndGoals(
                        cursor.getInt(cursor.getColumnIndex(RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.ROLES_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(RolesAndGoals.TABLE_NAME+"."+RolesAndGoals.GOALS_COLUMN))
                ));
                dailySchedules.add(dailySchedule);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dailySchedules;
    }

    @Override
    public long updateDailySchedule(DailySchedule dailySchedule){
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(DailySchedule.HARI_COLUMN,dailySchedule.getHari());
        contentValues.put(DailySchedule.JENIS_ALARM_COLUMN,dailySchedule.getJenisAlarm());
        contentValues.put(DailySchedule.WAKTU_COLUMN,dailySchedule.getWaktuAsString());
        contentValues.put(DailySchedule.TEMPAT_COLUMN,dailySchedule.getTempat());
        contentValues.put(DailySchedule.AKTIVITAS_COLUMN,dailySchedule.getAktivitas().getId());
        long editedCount= database.update(DailySchedule.TABLE_NAME,contentValues,DailySchedule.ID_COLUMN+"=? ",
                new String[]{String.valueOf(dailySchedule.getId())});
        database.close();
        return editedCount;
    }

    @Override
    public long deleteDailySchedule(DailySchedule dailySchedule){
        SQLiteDatabase database=helper.getWritableDatabase();
        long deletedCount=database.delete(DailySchedule.TABLE_NAME,DailySchedule.ID_COLUMN+"=? ",
                new String[]{String.valueOf(dailySchedule.getId())});
        database.close();
        return deletedCount;
    }
}
