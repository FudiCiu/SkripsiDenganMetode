package com.latihanandroid.skripsidenganmetode.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;

public class UpdateDeleteDailyScheduleView extends BaseView {
    private Spinner spnJenisAlarm;
    private Button btnSetTime;
    private Button btnUpdate;
    private TextView txtTime;
    private TextView txtDay;
    private EditText edtPlace;
    private TextView txtActivity;
    private Button btnChooseActivity;

    public UpdateDeleteDailyScheduleView(BaseController controller) {
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
    }

    @Override
    public void hubungkanViewDenganXML(BaseController baseController) {
        View view=baseController.getView();
        btnSetTime=view.findViewById(R.id.btnSetTime);
        spnJenisAlarm =view.findViewById(R.id.spnJenisAlarm);
        txtDay=view.findViewById(R.id.txtDay);
        txtActivity=view.findViewById(R.id.txtActivity);
        txtTime=view.findViewById(R.id.txtTime);
        edtPlace=view.findViewById(R.id.edtPlace);
        btnChooseActivity=view.findViewById(R.id.btnSetActivity);
        btnUpdate =view.findViewById(R.id.btnUpdate);
    }

    @Override
    public void hubungkanViewDenganListener(BaseController baseController) {
        if (baseController instanceof View.OnClickListener){
            btnUpdate.setOnClickListener((View.OnClickListener) baseController);
            btnChooseActivity.setOnClickListener((View.OnClickListener) baseController);
            btnSetTime.setOnClickListener((View.OnClickListener) baseController);
        }
    }

    public int getBtnAddId(){
        return btnUpdate.getId();
    }
    public int getBtnChooseActivityId(){
        return btnChooseActivity.getId();
    }
    public int getBtnSetTimeId(){
        return btnSetTime.getId();
    }
    public String getDayValue(){
        return txtDay.getText().toString();
    }
    public String getActivityValue(){
        return txtActivity.getText().toString();
    }
    public String getTimeValue(){
        return txtTime.getText().toString();
    }
    public String getPlaceValue(){
        return edtPlace.getText().toString();
    }
    public void setTime(String time){
        txtTime.setText(time);
    }
    public void setActivityValue(String activityValue){
        txtActivity.setText(activityValue);
    }
    public int getJenisAlarmValue(){
        return spnJenisAlarm.getSelectedItemPosition();
    }
    public void isiNilaiDefault(String hari, String waktu, String tempat, String aktivitas, int jenisAlarm){
        txtDay.setText(hari);
        txtTime.setText(waktu);
        edtPlace.setText(tempat);
        txtActivity.setText(aktivitas);
        spnJenisAlarm.setSelection(jenisAlarm);
    }
}
