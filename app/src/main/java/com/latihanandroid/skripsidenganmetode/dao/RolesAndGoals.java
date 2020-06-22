package com.latihanandroid.skripsidenganmetode.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class RolesAndGoals implements Parcelable {
    private int id;
    private String roles;
    private String goals;
    public static String TABLE_NAME="rolesandgoals";
    public static String ID_COLUMN="id";
    public static String ROLES_COLUMN="roles";
    public static String GOALS_COLUMN="goals";
    protected RolesAndGoals(Parcel in) {
        id = in.readInt();
        roles = in.readString();
        goals = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(roles);
        dest.writeString(goals);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RolesAndGoals> CREATOR = new Creator<RolesAndGoals>() {
        @Override
        public RolesAndGoals createFromParcel(Parcel in) {
            return new RolesAndGoals(in);
        }

        @Override
        public RolesAndGoals[] newArray(int size) {
            return new RolesAndGoals[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public RolesAndGoals(int id, String roles, String goals) {
        this.id = id;
        this.roles = roles;
        this.goals = goals;
    }

    public RolesAndGoals(String roles, String mGoals) {
        this.roles = roles;
        this.goals = mGoals;
    }
}
