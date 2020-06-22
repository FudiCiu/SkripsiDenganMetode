package com.latihanandroid.skripsidenganmetode.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.controller.BaseController;

public class AddRolesAndGoalsView extends BaseView {
    private Button btnAdd;
    private EditText edtRoles,edtGoals;

    public AddRolesAndGoalsView() {

    }

    public AddRolesAndGoalsView(BaseController controller) {
        hubungkanViewDenganXML(controller);
        hubungkanViewDenganListener(controller);
        isiNilaiDefault();
    }

    @Override
    protected void hubungkanViewDenganXML(BaseController baseController) {
        View view = baseController.getView();
        edtRoles=view.findViewById(R.id.edtRoles);
        edtGoals=view.findViewById(R.id.edtGoals);
        btnAdd=view.findViewById(R.id.btnAdd);
    }

//    @Override
//    protected void hubungkanViewDenganListener(final ViewListener listener) {
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onClick(btnAdd.getId());
//            }
//        });
//    }
    @Override
    protected void hubungkanViewDenganListener(BaseController baseController) {
        if (baseController instanceof View.OnClickListener){
            btnAdd.setOnClickListener((View.OnClickListener) baseController);
        }

    }

    public String getRolesValue(){
        return edtRoles.getText().toString();
    }
    public String getGoalsValue(){
        return edtGoals.getText().toString();
    }
    private void isiNilaiDefault(){
        edtRoles.setText("");
        edtGoals.setText("");
    }
}
