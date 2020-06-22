package com.latihanandroid.skripsidenganmetode;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private static final String DEFAULT_HOUR = "hour";
    private static final String DEFAULT_MINUTE = "minute";
    private DialogTimeListener mDialogTimeListener;

    private String mTime;
    private String mMinute;

    private TimePickerFragment() {

    }

    public static TimePickerFragment newInstance(int defaultHH, int defaultMM,DialogTimeListener timeSetCallback) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.mDialogTimeListener=timeSetCallback;
        Bundle args = new Bundle();
        args.putInt(DEFAULT_HOUR, defaultHH);
        args.putInt(DEFAULT_MINUTE, defaultMM);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTime = getArguments().getString(DEFAULT_HOUR);
            mMinute = getArguments().getString(DEFAULT_MINUTE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int defaultHour= getArguments().getInt(DEFAULT_HOUR,calendar.get(Calendar.HOUR_OF_DAY));
        int defaultMinutes= getArguments().getInt(DEFAULT_MINUTE,calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY,defaultHour);
        calendar.set(Calendar.MINUTE,defaultMinutes);
        return new TimePickerDialog(getContext(),this,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        mDialogTimeListener.onDialogTimeSet(hours,minutes);
    }

    public interface DialogTimeListener{
        void onDialogTimeSet(int hours, int minutes);
    }
}
