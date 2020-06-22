package com.latihanandroid.skripsidenganmetode.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;

import com.latihanandroid.skripsidenganmetode.GeneratePdfHelper;
import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.adapter.DailyScheduleAdapter;
import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;
import com.latihanandroid.skripsidenganmetode.model.DailyScheduleModel;
import com.latihanandroid.skripsidenganmetode.view.DailyScheduleListView;

import java.util.ArrayList;
import java.util.Calendar;

public class DailyScheduleListController extends BaseController
    implements View.OnClickListener,
        DailyScheduleAdapter.OnItemDailyScheduleClickListener,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener
{
    public static final String EXTRA_DAILY_SCHEDULE="EXTRA_DAILY_SCHEDULE";
    public static short hariValue=0;
    private DailyScheduleListView dailyScheduleListView;
    private DailyScheduleModel dailyScheduleModel;
    private ArrayList<DailySchedule> datas;
    @Override
    protected void inisiasi() {
        dailyScheduleListView=new DailyScheduleListView(this);
        dailyScheduleModel=new DailyScheduleModel(getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_daily_schedule_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inisiasi();
        showDailyScheduleList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);

        dailyScheduleListView.prepareSearchView(menu,this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.print:
                printPdf(datas);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        openAddMenu();
    }

    @Override
    public void onClick(DailySchedule dailySchedule) {
       openUpdateDeleteMenu(dailySchedule);
    }


    @Override
    public boolean onQueryTextSubmit(String keyword) {
       findListDailySchedule(keyword);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onClose() {
        showDailyScheduleList();
        return false;
    }

    private void showDailyScheduleList(){
        datas=new ArrayList<>();
        switch (DailyScheduleListController.hariValue){
            case Calendar.MONDAY :
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.MONDAY);
                break;
            case Calendar.TUESDAY:
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.TUESDAY);
                break;
            case Calendar.WEDNESDAY:
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.WEDNESDAY);
                break;
            case Calendar.THURSDAY:
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.THURSDAY);
                break;
            case Calendar.FRIDAY:
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.FRIDAY);
                break;
            case Calendar.SATURDAY:
                datas.clear();
                datas= dailyScheduleModel.read(Calendar.SATURDAY);
                break;
            case Calendar.SUNDAY:
                datas.clear();
                datas=dailyScheduleModel.read(Calendar.SUNDAY);
                break;
        }
        dailyScheduleListView.showListDailySchedule(datas);
    }
    private void findListDailySchedule(String keyword){
        ArrayList<DailySchedule> dailySchedules=dailyScheduleModel.read(hariValue,keyword);
        dailyScheduleListView.showListDailySchedule(dailySchedules);
    }
    private void openAddMenu(){
        Bundle bundle=new Bundle();
        bundle.putInt(AddDailyScheduleController.ADD_EXTRA,DailyScheduleListController.hariValue);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_add_daily_schedule_fragment,bundle);
    }
    private void openUpdateDeleteMenu(DailySchedule dailySchedule){
        Bundle bundle=new Bundle();
        bundle.putParcelable(UpdateDeleteDailyScheduleController.DAILY_SCHEDULE_EXTRA,dailySchedule);
        if (getActivity()!=null){
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_update_delete_daily_schedule,bundle);
        }else{
            Toast.makeText(getContext(), "Activity is null", Toast.LENGTH_SHORT).show();
        }
    }
    private void printPdf(ArrayList<DailySchedule> dailySchedules){
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        if (dailySchedules.size()>0){
            GeneratePdfHelper pdfHelper=new GeneratePdfHelper(getContext());
            pdfHelper.createPdfDailySchedule(dailySchedules);
        }else {
            Toast.makeText(getContext(), "There is no data to print", Toast.LENGTH_SHORT).show();
        }
    }
}
