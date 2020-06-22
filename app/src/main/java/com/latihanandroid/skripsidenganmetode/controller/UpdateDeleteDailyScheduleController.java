package com.latihanandroid.skripsidenganmetode.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.latihanandroid.skripsidenganmetode.view.UpdateDeleteDailyScheduleView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateDeleteDailyScheduleController extends BaseController
        implements TimePickerFragment.DialogTimeListener,
        View.OnClickListener,
        ChooseRolesAndGoalsController.OnRolesAndGoalsSelectedListener
{
    private UpdateDeleteDailyScheduleView updateDeleteDailyScheduleView;
    private DailyScheduleModel dailyScheduleModel;
    private RolesAndGoals selectedRolesGoals;
    public static final String ADD_EXTRA = "EXTRA_DAY";
    public static final String DAILY_SCHEDULE_EXTRA="EXTRA_DAILY_SCHEDULE";
    @Override
    protected void inisiasi() {
        updateDeleteDailyScheduleView = new UpdateDeleteDailyScheduleView(this);
        dailyScheduleModel = new DailyScheduleModel(getContext());
    }

    @Override
    public void onDialogTimeSet(int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        updateDeleteDailyScheduleView.setTime(simpleDateFormat.format(calendar.getTime()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               goBack();
            case R.id.delete:
                deleteProcess();
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_update_delete_daily_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showDefaultValue();
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId== updateDeleteDailyScheduleView.getBtnAddId()){
            updateProcess();
        }else if (viewId== updateDeleteDailyScheduleView.getBtnSetTimeId()){
            openTimeDialog();
        }else if (viewId== updateDeleteDailyScheduleView.getBtnChooseActivityId()){
            openChooseActivityDialog();
        }
    }




    @Override
    public void onRolesAndGoalsSelected(RolesAndGoals rolesAndGoals) {
        selectedRolesGoals=rolesAndGoals;
        updateDeleteDailyScheduleView.setActivityValue(rolesAndGoals.getRoles()+" : "+rolesAndGoals.getGoals());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.update_delete_menu,menu);
    }

    private void updateProcess(){
        DailySchedule dailySchedule=getDailyScheduleFromView();
//                AlarmReceiver.showNotification(getContext(),dailySchedule);
        if (dailySchedule==null){
            Toast.makeText(getContext(), "Empty field detected", Toast.LENGTH_SHORT).show();
        }else{
            if (getArguments()!=null){
                DailySchedule defaultDailySchedule=getArguments().getParcelable(UpdateDeleteDailyScheduleController.DAILY_SCHEDULE_EXTRA);
                dailySchedule.setId(defaultDailySchedule.getId());
                dailySchedule.setHari(defaultDailySchedule.getHari());
                long editedCount= dailyScheduleModel.update(dailySchedule);
                if (editedCount>0){
                    Toast.makeText(getContext(), String.valueOf(editedCount)+" data daily Schedule edited", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Failed to edit data Daily", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void deleteProcess(){
        if (getArguments()!=null){
            DailySchedule old= getArguments().getParcelable(UpdateDeleteDailyScheduleController.DAILY_SCHEDULE_EXTRA);
            if (getContext()!=null){
                AlarmReceiver.cancelAlarm(getContext(),old);
            }
            long deletedCount= dailyScheduleModel.delete(old);
            if (deletedCount>0){
                Toast.makeText(getContext(), String.valueOf(deletedCount)+" data daily schedule deleted", Toast.LENGTH_SHORT).show();
                goBack();
            }else {
                Toast.makeText(getContext(), " Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openTimeDialog(){
        if (getFragmentManager()!=null){
            String[] waktu= updateDeleteDailyScheduleView.getTimeValue().split(":");
            TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(Integer.parseInt(waktu[0]),
                    Integer.parseInt(waktu[1]),UpdateDeleteDailyScheduleController.this);
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
        if (updateDeleteDailyScheduleView.getPlaceValue().toString().equals("")
                ||this.selectedRolesGoals==null
        )
            return dailySchedule;
        short hari=-1;
        if (getArguments()!=null){
            hari= (short) getArguments().getInt(AddDailyScheduleController.ADD_EXTRA,-1);
        }
        dailySchedule=new DailySchedule();
        dailySchedule.setHari(hari);
        dailySchedule.setWaktuFromString(updateDeleteDailyScheduleView.getTimeValue());
        dailySchedule.setTempat(updateDeleteDailyScheduleView.getPlaceValue());
        dailySchedule.setAktivitas(selectedRolesGoals);
        dailySchedule.setJenisAlarm(updateDeleteDailyScheduleView.getJenisAlarmValue());
        return dailySchedule;
    }
    private void showDefaultValue(){
        if (getArguments()!=null){
            DailySchedule dailySchedule=getArguments().getParcelable(DAILY_SCHEDULE_EXTRA);
            if (dailySchedule!=null){
                updateDeleteDailyScheduleView.isiNilaiDefault(dailySchedule.getHariAsString(),dailySchedule.getWaktuAsString(),
                        dailySchedule.getTempat(),dailySchedule.getAktiviasAsString(),dailySchedule.getJenisAlarm());
                this.selectedRolesGoals=dailySchedule.getAktivitas();
            }else {
                Toast.makeText(getContext(), "Daily Schedule is null", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Argument is null", Toast.LENGTH_SHORT).show();
        }
    }
    private void goBack(){
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
