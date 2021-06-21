package com.example.soundrecorder;

public class Items {
    private String name;
    private String desc;
    private String time;

    public Items(String name, String desc, String time) {
        this.name = name;
        this.desc = desc;
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
    public String getTime() {
        return time;
    }
}
