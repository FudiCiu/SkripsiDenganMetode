package com.latihanandroid.skripsidenganmetode.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="dbdailyreminderapp_db";
    private static SQLiteHelper INSTANCE;
    private static final int DATABASE_VERSION=1;

    private static final String SQL_CREATE_TABLE_ROLE= String.format("CREATE TABLE %s ("
                    +"%s INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"%s TEXT NOT NULL,"
                    +"%s TEXT NOT NULL)",
            RolesAndGoals.TABLE_NAME,
            RolesAndGoals.ID_COLUMN,
            RolesAndGoals.ROLES_COLUMN,
            RolesAndGoals.GOALS_COLUMN);
    private static final String SQL_CREATE_TABLE_DAILY_SCHEDULE= String.format(
            "CREATE TABLE %s "+//tablename
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+//id
                    "%s INTEGER NOT NULL,"+ //hari
                    "%s INTEGER NOT NULL,"+ //jenisAlarm
                    "%s TEXT NOT NULL,"+ //waktu
                    "%s TEXT NOT NULL,"+ //tempat
                    "%s INTEGER NOT NULL)", //Aktivitas
            DailySchedule.TABLE_NAME,
            DailySchedule.ID_COLUMN,
            DailySchedule.HARI_COLUMN,
            DailySchedule.JENIS_ALARM_COLUMN,
            DailySchedule.WAKTU_COLUMN,
            DailySchedule.TEMPAT_COLUMN,
            DailySchedule.AKTIVITAS_COLUMN
    );
    private SQLiteHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteHelper getInstance(Context context){
        if (INSTANCE==null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE==null){
                    INSTANCE=new SQLiteHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ROLE);
        db.execSQL(SQL_CREATE_TABLE_DAILY_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+RolesAndGoals.TABLE_NAME);
        onCreate(db);
    }

}
