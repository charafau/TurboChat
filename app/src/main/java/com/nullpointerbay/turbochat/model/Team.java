package com.nullpointerbay.turbochat.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import paperparcel.PaperParcel;

@PaperParcel
public class Team implements Parcelable {
    public static final Creator<Team> CREATOR = PaperParcelTeam.CREATOR;

    String uuid;
    String name;
    String photoUrl;
    List<User> teamUsers;

    public Team(String uuid, String name, String photoUrl) {
        this.uuid = uuid;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    protected Team(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        photoUrl = in.readString();
        teamUsers = in.createTypedArrayList(User.CREATOR);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return uuid != null ? uuid.equals(team.uuid) : team.uuid == null;

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Team{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        PaperParcelTeam.writeToParcel(this, parcel, flags);
    }
}
