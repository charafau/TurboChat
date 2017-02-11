package com.nullpointerbay.turbochat.model;


import java.util.List;

public class Team {
    private String uuid;
    private String name;
    private String photoUrl;
    private List<User> teamUsers;

    public Team(String uuid, String name, String photoUrl) {
        this.uuid = uuid;
        this.name = name;
        this.photoUrl = photoUrl;
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
}
