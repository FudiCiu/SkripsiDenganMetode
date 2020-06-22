package com.latihanandroid.skripsidenganmetode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihanandroid.skripsidenganmetode.R;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;

import java.util.ArrayList;

public class RolesAndGoalsAdapter extends RecyclerView.Adapter<RolesAndGoalsAdapter.RoalsAndGoalsViewHolder> {
    private ArrayList<RolesAndGoals> datas= new ArrayList<>();
    private OnItemRolesAndGoalsClickListener onItemRolesAndGoalsClickCallback;
    public RolesAndGoalsAdapter(ArrayList<RolesAndGoals> datas) {
        this.datas = datas;
    }

    public RolesAndGoalsAdapter() {
    }

    public ArrayList<RolesAndGoals> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<RolesAndGoals> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public OnItemRolesAndGoalsClickListener getOnItemRolesAndGoalsClickCallback() {
        return onItemRolesAndGoalsClickCallback;
    }

    public void setOnItemRolesAndGoalsClickCallback(OnItemRolesAndGoalsClickListener onItemRolesAndGoalsClickCallback) {
        this.onItemRolesAndGoalsClickCallback = onItemRolesAndGoalsClickCallback;
    }

    @NonNull
    @Override
    public RoalsAndGoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles_goals,parent,false);
        return new RoalsAndGoalsViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RoalsAndGoalsViewHolder holder, int position) {
        final RolesAndGoals rolesAndGoals=datas.get(position);
        holder.txtGoals.setText(rolesAndGoals.getGoals());
        holder.txtRoles.setText(rolesAndGoals.getRoles());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemRolesAndGoalsClickCallback.onItemClick(rolesAndGoals);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class RoalsAndGoalsViewHolder extends RecyclerView.ViewHolder{
        private TextView txtRoles,txtGoals;
        public RoalsAndGoalsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoles=itemView.findViewById(R.id.txtRoles);
            txtGoals=itemView.findViewById(R.id.txtGoals);
        }
    }
    public interface OnItemRolesAndGoalsClickListener{
        public void onItemClick(RolesAndGoals rolesAndGoals);
    }
}
