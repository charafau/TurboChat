package com.nullpointerbay.turbochat.model;

public class User {

    private long id;
    private String nick;
    private String name;

    public User(long id, String nick, String name) {
        this.id = id;
        this.nick = nick;
        this.name = name;
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
}
