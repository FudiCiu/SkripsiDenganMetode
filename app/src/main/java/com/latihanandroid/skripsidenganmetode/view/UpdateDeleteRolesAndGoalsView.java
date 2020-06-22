package com.latihanandroid.skripsidenganmetode.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;

public class UpdateDeleteRolesAndGoalsView extends BaseView {
    private EditText edtRoles,edtGoals;
    private Button btnUpdate;


    public UpdateDeleteRolesAndGoalsView(BaseController controller){
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
    }
    @Override
    public void hubungkanViewDenganXML(BaseController baseController) {
        View view=baseController.getView();
        edtRoles=view.findViewById(R.id.edtRoles);
        edtGoals=view.findViewById(R.id.edtGoals);
        btnUpdate=view.findViewById(R.id.btnUpdate);
    }

    @Override
    public void hubungkanViewDenganListener(BaseController baseController) {
        if (baseController instanceof View.OnClickListener){
            btnUpdate.setOnClickListener((View.OnClickListener) baseController);
        }
    }

    public void setNilaiDefault(String roles,String goals){
        edtRoles.setText(roles);
        edtGoals.setText(goals);
    }

    public String getRolesValue(){
        return edtRoles.getText().toString();
    }
    public String getGoalsValue(){
        return edtGoals.getText().toString();
    }
}
