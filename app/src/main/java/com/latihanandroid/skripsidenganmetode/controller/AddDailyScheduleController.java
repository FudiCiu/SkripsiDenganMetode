package com.latihanandroid.skripsidenganmetode.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.latihanandroid.skripsidenganmetode.AlarmReceiver;
import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.TimePickerFragment;
import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;
import com.latihanandroid.skripsidenganmetode.model.DailyScheduleModel;
import com.latihanandroid.skripsidenganmetode.view.AddDailyScheduleView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDailyScheduleController extends BaseController
        implements TimePickerFragment.DialogTimeListener,
        View.OnClickListener,
        ChooseRolesAndGoalsController.OnRolesAndGoalsSelectedListener
{
    private AddDailyScheduleView addDailyScheduleView;
    private DailyScheduleModel dailyScheduleModel;
    private RolesAndGoals selectedRolesGoals;
    public static final String ADD_EXTRA = "EXTRA_DAY";

    @Override
    protected void inisiasi() {
        addDailyScheduleView = new AddDailyScheduleView(this);
        dailyScheduleModel = new DailyScheduleModel(getContext());
    }

    @Override
    public void onDialogTimeSet(int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        addDailyScheduleView.setTime(simpleDateFormat.format(calendar.getTime()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_daily_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showDefaultFromNowDate();
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId==addDailyScheduleView.getBtnAddId()){
            addProcess();
        }else if (viewId==addDailyScheduleView.getBtnSetTimeId()){
            openTimeDialog();
        }else if (viewId==addDailyScheduleView.getBtnChooseActivityId()){
            openChooseActivityDialog();
        }
    }




    @Override
    public void onRolesAndGoalsSelected(RolesAndGoals rolesAndGoals) {
        selectedRolesGoals=rolesAndGoals;
        addDailyScheduleView.setActivityValue(rolesAndGoals.getRoles()+" : "+rolesAndGoals.getGoals());
    }

    private void addProcess(){
        if (getDailyScheduleFromView()==null){
            Toast.makeText(getContext(), "Empty Field detected", Toast.LENGTH_SHORT).show();
        }else {
            DailySchedule dailySchedule=getDailyScheduleFromView();
            long insertedId= dailyScheduleModel.insert(dailySchedule);
            if (insertedId>0){
                Toast.makeText(getContext(), "Daily Schedule with id "+ insertedId+" added", Toast.LENGTH_SHORT).show();
                dailySchedule.setId((int) insertedId);
                if (dailySchedule.getJenisAlarm()>0){
                    if (getContext()!=null){
                        AlarmReceiver.setDailyScheduleAlarm(getContext(),dailySchedule);
                    }
                }else{
                    if (getContext()!=null){
                        AlarmReceiver.cancelAlarm(getContext(),dailySchedule);
                    }
                }
                goBack();
            }else {
                Toast.makeText(getContext(), "Failed to insert daily activity", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openTimeDialog(){
        if (getFragmentManager()!=null){
            String[] waktu=addDailyScheduleView.getTimeValue().split(":");
            TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(Integer.parseInt(waktu[0]),
                    Integer.parseInt(waktu[1]),AddDailyScheduleController.this);
            timePickerFragment.show(getFragmentManager(),TimePickerFragment.class.getSimpleName());
        }
    }
    private void openChooseActivityDialog(){
        ChooseRolesAndGoalsController chooseRolesAndGoalsFragment= new ChooseRolesAndGoalsController();
        FragmentManager fragmentManager=getChildFragmentManager();
        chooseRolesAndGoalsFragment.show(fragmentManager,ChooseRolesAndGoalsController.class.getSimpleName());
    }
    private DailySchedule getDailyScheduleFromView(){
        DailySchedule dailySchedule=null;
        if (addDailyScheduleView.getPlaceValue().toString().equals("")||this.selectedRolesGoals==null) return dailySchedule;
        short hari=-1;
        if (getArguments()!=null){
            hari= (short) getArguments().getInt(AddDailyScheduleController.ADD_EXTRA,-1);
        }
        dailySchedule=new DailySchedule();
        dailySchedule.setHari(hari);
        dailySchedule.setWaktuFromString(addDailyScheduleView.getTimeValue());
        dailySchedule.setTempat(addDailyScheduleView.getPlaceValue());
        dailySchedule.setAktivitas(selectedRolesGoals);
        dailySchedule.setJenisAlarm(addDailyScheduleView.getJenisAlarmValue());
        return dailySchedule;
    }
    private void goBack(){
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
    private void showDefaultFromNowDate(){
        String hari = "";
        String waktu = "";
        if (getArguments() != null) {
            int hariVal = getArguments().getInt(ADD_EXTRA, 0);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, hariVal);
            SimpleDateFormat sf = new SimpleDateFormat("EEEE", Locale.US);
            hari = sf.format(c.getTime());
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        waktu = simpleDateFormat.format(calendar.getTime());
        addDailyScheduleView.isiNilaiDefault(hari, waktu);
    }
}
