package com.latihanandroid.skripsidenganmetode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;

import java.util.ArrayList;

public class DailyScheduleAdapter extends RecyclerView.Adapter<DailyScheduleAdapter.DailyScheduleViewHolder> {
    private ArrayList<DailySchedule> datas=new ArrayList<>();
    private OnItemDailyScheduleClickListener onItemDailyScheduleClickCallback;

    public ArrayList<DailySchedule> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<DailySchedule> datas) {
        this.datas = datas;
    }

    public OnItemDailyScheduleClickListener getOnItemDailyScheduleClickCallback() {
        return onItemDailyScheduleClickCallback;
    }

    public void setOnItemDailyScheduleClickCallback(OnItemDailyScheduleClickListener onItemDailyScheduleClickCallback) {
        this.onItemDailyScheduleClickCallback = onItemDailyScheduleClickCallback;
    }

    public DailyScheduleAdapter() {
    }

    public DailyScheduleAdapter(ArrayList<DailySchedule> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public DailyScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_schedule,parent,false);
        return new DailyScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyScheduleViewHolder holder, int position) {
        final DailySchedule dailySchedule=datas.get(position);
        holder.txtTime.setText(dailySchedule.getWaktuAsString());
        holder.txtPlace.setText(dailySchedule.getTempat());
        holder.txtActivity.setText(dailySchedule.getAktiviasAsString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDailyScheduleClickCallback.onClick(dailySchedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class DailyScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView txtActivity,txtTime,txtPlace;

        public DailyScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime=itemView.findViewById(R.id.txtTime);
            txtPlace=itemView.findViewById(R.id.txtPlace);
            txtActivity=itemView.findViewById(R.id.txtActivity);
        }
    }
    public interface OnItemDailyScheduleClickListener{
        public void onClick(DailySchedule dailySchedule);
    }
}
