package com.example.soundrecorder;

public class Items {
    private String name;
    private String date;
    private String duration;

    public Items(String name, String date, String duration) {
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getDuration() {
        return duration;
    }
}
