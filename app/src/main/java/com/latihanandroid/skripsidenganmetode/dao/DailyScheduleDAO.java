package com.latihanandroid.skripsidenganmetode.dao;

import java.util.ArrayList;

public interface DailyScheduleDAO {
    public long insertDailySchedule(DailySchedule dailySchedule);
    public ArrayList<DailySchedule> readAllDailySchedule(int hari);
    public ArrayList<DailySchedule> readAllDailySchedule();
    public ArrayList<DailySchedule> findDailySchedule(int hari,String keyword);
    public long updateDailySchedule(DailySchedule dailySchedule);
    public long deleteDailySchedule(DailySchedule dailySchedule);
}
