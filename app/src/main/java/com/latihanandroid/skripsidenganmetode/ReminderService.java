package com.latihanandroid.skripsidenganmetode;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;
import com.latihanandroid.skripsidenganmetode.model.DailyScheduleModel;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ReminderService extends IntentService {


    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            triggerAllAlarm(this);
            AlarmReceiver.setEveryDayAdaptationAlarm(this);
        }
    }
    public static void startAllAlarm(Context context){
        Intent intent=new Intent(context,ReminderService.class);
        context.startService(intent);
    }
    private void triggerAllAlarm(final Context context){
        final DailyScheduleModel dailyScheduleModel=new DailyScheduleModel(context);
        Handler handler=new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<DailySchedule> dailySchedules=dailyScheduleModel.read();
                for (int i=0;i<dailySchedules.size();i++){
                    DailySchedule dailySchedule=dailySchedules.get(i);
                    if (dailySchedule.getJenisAlarm()>0){
                        triggerOneAlarm(context,dailySchedule);
                    }

                }
            }
        });
    }

    private void triggerOneAlarm(Context context, DailySchedule dailySchedule){
        if (context==null){
            Toast.makeText(context,"Error in set One Alarm",Toast.LENGTH_SHORT).show();
            return;
        }
        AlarmReceiver.setDailyScheduleAlarm(context,dailySchedule);
    }
}
