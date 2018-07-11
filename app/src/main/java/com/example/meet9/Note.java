package com.example.meet9;

/**
 * Created by Игорь on 05.07.2018.
 */

public class Note {

    private long ID;
    private String Name;
    private String Date;
    private String Info;

    public Note() {
    }

    public Note(long id, String name, String date, String info) {
        this.ID = id;
        this.Name = name;
        this.Date = date;
        this.Info = info;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
