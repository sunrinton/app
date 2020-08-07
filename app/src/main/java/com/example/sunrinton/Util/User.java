package com.example.sunrinton.Util;


import java.util.Date;

public class User {

    private String name;
    private long birth;

    public String getName() {
        return name;
    }

    public long getBirth() {
        return birth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public Date getBirthDate() {
        Date date = new Date();
        date.setTime(getBirth());
        return date;
    }
}
