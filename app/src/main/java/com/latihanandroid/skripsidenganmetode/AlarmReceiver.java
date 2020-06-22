package com.latihanandroid.skripsidenganmetode;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.latihanandroid.skripsidenganmetode.controller.UpdateDeleteDailyScheduleController;
import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_DAILY_SCHEDULE="com.latihanandroid.skripstitanpametode.EXTRA_DAILY_SCHEDULE";
    public static final String EXTRA_DAILY_ADAPTATION="com.latihanandroid.skripstitanpametode.EXTRA_DAILY_ADAPTATION";
    public static final String GROUP_NOTIFICATION="com.latihanandroid.skripstitanpametode.GROUP_NOTIFICATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getBundleExtra(EXTRA_DAILY_SCHEDULE);
        DailySchedule dailySchedule=bundle.getParcelable(EXTRA_DAILY_SCHEDULE);
        boolean daily= intent.getBooleanExtra(AlarmReceiver.EXTRA_DAILY_ADAPTATION,false);
        if (daily){
            showDailyAdaptationNotification(context);
        }
        if (dailySchedule==null){
            Log.d("TEST", "onReceive: Daily schedule is null");
        }else {
            showNotification(context.getApplicationContext(),dailySchedule);
            Log.d("TES", "setDailyScheduleAlarm: Alarm Triggered with id "+String.valueOf(dailySchedule.getId()));
        }
    }

    public static void setDailyScheduleAlarm(Context context,DailySchedule dailyScheduleAlarm){

        //Persiapan dasar
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Buat intent untuk memulai alarm kemudian simpan pada pending intent untuk memulai broadcast ketika waktu sudah tiba
        Intent intent= new Intent(context,AlarmReceiver.class);
        Bundle extras= new Bundle();
        extras.putParcelable(EXTRA_DAILY_SCHEDULE,dailyScheduleAlarm);
        intent.putExtra(AlarmReceiver.EXTRA_DAILY_SCHEDULE,extras);
        int requestCode=2000+dailyScheduleAlarm.getId();
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Tetapkan waktu
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,dailyScheduleAlarm.getHari());
        calendar.set(Calendar.HOUR_OF_DAY,dailyScheduleAlarm.getWaktuJamAsInt());
        calendar.set(Calendar.MINUTE,dailyScheduleAlarm.getWaktuMenitAsInt());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        int secondBefore=0;
        switch (dailyScheduleAlarm.getJenisAlarm()){
            case 1:
                secondBefore=3600*1000*0;
                break;
            case 2:
                secondBefore=3600*1000*1;
                break;
            case 3:
                secondBefore=3600*1000*6;
                break;
            case 4:
                secondBefore=3600*1000*12;
                break;
        }
        calendar.setTimeInMillis(calendar.getTimeInMillis()-secondBefore);

        Calendar now =Calendar.getInstance();
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        if (calendar.before(now)){
            calendar.add(Calendar.DATE,7);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),7*24*60*60*1000,pendingIntent);
        SimpleDateFormat logformatter=new SimpleDateFormat("ddMMYYYY HH:mm");
        Log.d("TES", "setDailyScheduleAlarm: Alarm set with id "+String.valueOf(requestCode)+" with time "+logformatter.format(calendar.getTime()));
//        boolean set=PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_NO_CREATE)!=null;
//        if (!set){
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),7*24*60*60*1000,pendingIntent);
//        }
    }

    public static void cancelAlarm(Context context,DailySchedule dailySchedule){
        //Siapkan manajer
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Buat intent untuk memulai alarm kemudian simpan pada pending intent untuk memulai broadcast ketika waktu sudah tiba
        Intent intent= new Intent(context,AlarmReceiver.class);
        Bundle extras= new Bundle();
        extras.putParcelable(EXTRA_DAILY_SCHEDULE,dailySchedule);
        intent.putExtra(AlarmReceiver.EXTRA_DAILY_SCHEDULE,extras);
        int requestCode=2000+dailySchedule.getId();
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCode,intent,0);
        pendingIntent.cancel();
        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
        Log.d("TES", "setDailyScheduleAlarm: canceled alarm showed with id "+String.valueOf(requestCode));
    }
    public static void showNotification(Context context, DailySchedule dailySchedule){
        //      Persiapan dasar yang dibutuhkan notifikasi
        int notifId= dailySchedule.getId();
        String channelID="channel_daily_schedule";
        String channelName="channel_name";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //      Buat intent untuk buka activity dan simpan ke pending intent supaya ketika notifikai ditap akan muncul activity
        Intent intent=new Intent(context, UpdateDeleteDailyScheduleController.class);
        intent.putExtra(UpdateDeleteDailyScheduleController.DAILY_SCHEDULE_EXTRA,dailySchedule);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,notifId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //      Siapkan resource yang dibutuhkan oleh notifikasi
        Bitmap largeIcon= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_event_note_black_24dp);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Persiapkan builder utnuk membuat notifikasi
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,channelID)
                .setContentTitle(dailySchedule.getAktiviasAsString())
                .setContentText(dailySchedule.getTempat()+" - "+dailySchedule.getWaktuAsString())
                .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_NOTIFICATION)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{500,500,500,500,500})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(channelID);
            if (notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        //Buat notifikasi dan tampilkan
        Notification notification=builder.build();
        if (notificationManager!=null){
            notificationManager.notify(notifId,notification);
        }
        Log.d("TES", "setDailyScheduleAlarm: Notification showed with id "+String.valueOf(notifId));
    }
    public static void showDailyAdaptationNotification(Context context){
        //      Persiapan dasar yang dibutuhkan notifikasi
        int notifId= 1024;
        String channelID="channel_daily_schedule";
        String channelName="channel_name";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //      Buat intent untuk buka activity dan simpan ke pending intent supaya ketika notifikai ditap akan muncul activity
        Intent intent=new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,notifId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //      Siapkan resource yang dibutuhkan oleh notifikasi
        Bitmap largeIcon= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_event_note_black_24dp);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Persiapkan builder utnuk membuat notifikasi
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,channelID)
                .setContentTitle("Daily Schedule Reminder Check")
                .setContentText("Remember to check your daily shcedule")
                .setSmallIcon(R.drawable.ic_event_note_black_24dp)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_NOTIFICATION)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{500,500,500,500,500})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(channelID);
            if (notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        //Buat notifikasi dan tampilkan
        Notification notification=builder.build();
        if (notificationManager!=null){
            notificationManager.notify(notifId,notification);
        }
        Log.d("TES", "setDailyScheduleAlarm: Notification showed with id "+String.valueOf(notifId));
    }
    public static void setEveryDayAdaptationAlarm(Context context){
        //Persiapan dasar
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Buat intent untuk memulai alarm kemudian simpan pada pending intent untuk memulai broadcast ketika waktu sudah tiba
        Intent intent= new Intent(context,AlarmReceiver.class);
        Bundle extras= new Bundle();
        extras.putBoolean(EXTRA_DAILY_ADAPTATION,true);
        int requestCode=200;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Tetapkan waktu
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        Calendar now =Calendar.getInstance();
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);

    }
}
