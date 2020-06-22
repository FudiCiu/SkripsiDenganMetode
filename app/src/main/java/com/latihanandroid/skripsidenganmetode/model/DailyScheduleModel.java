package com.latihanandroid.skripsidenganmetode.model;

import android.content.Context;

import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;
import com.latihanandroid.skripsidenganmetode.dao.DailyScheduleDAO;
import com.latihanandroid.skripsidenganmetode.dao.DailyScheduleDAO_Implementation;
import com.latihanandroid.skripsidenganmetode.dao.SQLiteHelper;

import java.util.ArrayList;

public class DailyScheduleModel extends BaseModel {
    private Context context;
    private DailyScheduleDAO dailyScheduleDAO;
    public DailyScheduleModel(Context context) {
        this.context = context;
        hubungkanDenganDatabase();
    }

    @Override
    protected void hubungkanDenganDatabase() {
        SQLiteHelper helper=SQLiteHelper.getInstance(context);
        dailyScheduleDAO=new DailyScheduleDAO_Implementation(helper);
    }

    public long insert(DailySchedule dailySchedule){
        return dailyScheduleDAO.insertDailySchedule(dailySchedule);
    }

    public long update(DailySchedule dailySchedule){
        return dailyScheduleDAO.updateDailySchedule(dailySchedule);
    }

    public ArrayList<DailySchedule> read(){
        return dailyScheduleDAO.readAllDailySchedule();
    }

    public ArrayList<DailySchedule> read(int hari){
        return dailyScheduleDAO.readAllDailySchedule(hari);
    }

    public  ArrayList<DailySchedule> read(int hari,String keyword){
        return dailyScheduleDAO.findDailySchedule(hari,keyword);
    }
    public long delete(DailySchedule dailySchedule){
        return dailyScheduleDAO.deleteDailySchedule(dailySchedule);
    }
}
