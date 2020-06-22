package com.latihanandroid.skripsidenganmetode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnRebootReceiver extends BroadcastReceiver {
    public static final String BOOT_COMPLETE_ACTION="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            ReminderService.startAllAlarm(context);
        }
    }
}
