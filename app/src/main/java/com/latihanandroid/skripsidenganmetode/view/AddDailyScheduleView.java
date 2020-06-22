package com.latihanandroid.skripsidenganmetode.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;

public class AddDailyScheduleView extends BaseView
{
    private Spinner spnJenisAlarm;
    private Button btnSetTime;
    private Button btnAdd;
    private TextView txtTime;
    private TextView txtDay;
    private EditText edtPlace;
    private TextView txtActivity;
    private Button btnChooseActivity;

    public AddDailyScheduleView(BaseController controller) {
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
        btnAdd=view.findViewById(R.id.btnAdd);
    }

    @Override
    public void hubungkanViewDenganListener(BaseController baseController) {
        if (baseController instanceof View.OnClickListener){
            btnAdd.setOnClickListener((View.OnClickListener) baseController);
            btnChooseActivity.setOnClickListener((View.OnClickListener) baseController);
            btnSetTime.setOnClickListener((View.OnClickListener) baseController);
        }

    }

    public int getBtnAddId(){
        return btnAdd.getId();
    }
    public int getBtnChooseActivityId(){
        return btnChooseActivity.getId();
    }
    public int getBtnSetTimeId(){
        return btnSetTime.getId();
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
    public void isiNilaiDefault(String hari,String waktu){
        txtDay.setText(hari);
        txtTime.setText(waktu);
        edtPlace.setText("");
        txtActivity.setText("Roles : Goals");
        spnJenisAlarm.setSelection(0);
    }
}
