package com.latihanandroid.skripsidenganmetode.view;

import com.latihanandroid.skripsidenganmetode.controller.BaseController;

public abstract class BaseView {
    protected abstract void hubungkanViewDenganXML(BaseController baseController);
    protected abstract void hubungkanViewDenganListener(BaseController baseController);

}
