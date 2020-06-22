package com.latihanandroid.skripsidenganmetode.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DailySchedule implements Parcelable {
    private int id;
    private short hari;
    private int jenisAlarm;
    private Date waktu;
    private String tempat;
    private RolesAndGoals aktivitas;
    public static final String TABLE_NAME="dailyschedule";
    public static final String ID_COLUMN="id";
    public static final String HARI_COLUMN="hari";
    public static final String JENIS_ALARM_COLUMN="jenisAlarm";
    public static final String WAKTU_COLUMN="waktu";
    public static final String TEMPAT_COLUMN="tempat";
    public static final String AKTIVITAS_COLUMN="aktivitas";
    protected DailySchedule(Parcel in) {
        id = in.readInt();
        hari = (short) in.readInt();
        jenisAlarm = in.readInt();
        waktu=new Date(in.readLong());
        tempat = in.readString();
        aktivitas = in.readParcelable(RolesAndGoals.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt((int) hari);
        dest.writeInt(jenisAlarm);
        dest.writeLong(waktu==null?0:waktu.getTime());
        dest.writeString(tempat);
        dest.writeParcelable(aktivitas, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailySchedule> CREATOR = new Creator<DailySchedule>() {
        @Override
        public DailySchedule createFromParcel(Parcel in) {
            return new DailySchedule(in);
        }

        @Override
        public DailySchedule[] newArray(int size) {
            return new DailySchedule[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getHari() {
        return hari;
    }

    public void setHari(short hari) {
        this.hari = hari;
    }

    public int getJenisAlarm() {
        return jenisAlarm;
    }

    public void setJenisAlarm(int jenisAlarm) {
        this.jenisAlarm = jenisAlarm;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public RolesAndGoals getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(RolesAndGoals aktivitas) {
        this.aktivitas = aktivitas;
    }

    public void setAktivitasFromString(String txtActivity){
        String[] aktivitas=txtActivity.split(":");
        RolesAndGoals rolesAndGoals=new RolesAndGoals(aktivitas[0],aktivitas[1]);
        this.aktivitas=rolesAndGoals;
    }
    public String getAktiviasAsString(){
        return this.aktivitas.getRoles()+" : "+this.aktivitas.getGoals();
    }
    public void setWaktuFromString(String txtWaktu){
        String[] waktu= txtWaktu.split(":");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(waktu[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(waktu[1]));
        this.waktu=calendar.getTime();
    }
    public String getWaktuAsString(){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm", Locale.getDefault());
        if (waktu!=null){
            return formatter.format(waktu);
        }else {
            return "Unkown";
        }
    }
    public int getWaktuJamAsInt(){
        SimpleDateFormat formatter= new SimpleDateFormat("HH",Locale.getDefault());
        if (waktu!=null){
            return Integer.parseInt(formatter.format(waktu));
        }else {
            return 0;
        }
    }
    public int getWaktuMenitAsInt(){
        SimpleDateFormat formatter= new SimpleDateFormat("mm",Locale.getDefault());
        if (waktu!=null){
            return Integer.parseInt(formatter.format(waktu));
        }else {
            return 0;
        }
    }
    public String getHariAsString(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,this.hari);
        SimpleDateFormat formatter= new SimpleDateFormat("EEEE", Locale.US);
        return formatter.format(calendar.getTime());
    }
    public void setJenisAlarmFromInt(int jenisAlarm){
        this.jenisAlarm=jenisAlarm;
    }
    public DailySchedule(int id, short hari, int jenisAlarm, Date waktu, String tempat, RolesAndGoals aktivitas) {
        this.id = id;
        this.hari = hari;
        this.jenisAlarm = jenisAlarm;
        this.waktu = waktu;
        this.tempat = tempat;
        this.aktivitas = aktivitas;
    }

    public DailySchedule(short hari, int jenisAlarm, Date waktu, String tempat, RolesAndGoals aktivitas) {
        this.hari = hari;
        this.jenisAlarm = jenisAlarm;
        this.waktu = waktu;
        this.tempat = tempat;
        this.aktivitas = aktivitas;
    }

    public DailySchedule() {
    }
}
