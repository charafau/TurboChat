package com.nullpointerbay.turbochat.model;

import android.os.Parcel;
import android.os.Parcelable;

import paperparcel.PaperParcel;

@PaperParcel
public class User implements Parcelable{

    public static final Creator<User> CREATOR = PaperParcelUser.CREATOR;

    long id;
    String nick;
    String name;
    String avatarUrl;

    public User(long id, String nick, String name, String avatarUrl) {
        this.id = id;
        this.nick = nick;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    protected User(Parcel in) {
        id = in.readLong();
        nick = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        PaperParcelUser.writeToParcel(this, parcel, flags);
    }
}
