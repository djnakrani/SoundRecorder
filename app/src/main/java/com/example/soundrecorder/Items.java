package com.example.soundrecorder;

public class Items {
    private String file_name;
    private String name;
    private String desc;
    private String time;

    public Items(String file_name, String name, String desc, String time) {
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.file_name=file_name;
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
    public String getFile_name() {
        return file_name;
    }

}
