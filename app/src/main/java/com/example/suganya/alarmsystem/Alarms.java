package com.example.suganya.alarmsystem;

public class Alarms {

    private String time,name,tone;
    private Integer status;


    public Alarms(String time , String name,String tone,int status){
        this.setTime(time);
        this.setName(name);
        this.setTone(tone);
        this.setStatus(status);
    }
    public Alarms()
    {

    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
