package com.example.ibds.Do;

import java.io.Serializable;

public class TaskDo implements Serializable {
    boolean check = false;
    String task="";
    String Createdtiming="";
    String allocatedTime="";
    String accuracy="";
    String endTiming="";

    public String getCreatedtiming() {
        return Createdtiming;
    }

    public void setCreatedtiming(String createdtiming) {
        Createdtiming = createdtiming;
    }

    public String getEndTiming() {
        return endTiming;
    }

    public void setEndTiming(String endTiming) {
        this.endTiming = endTiming;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public String getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(String allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
