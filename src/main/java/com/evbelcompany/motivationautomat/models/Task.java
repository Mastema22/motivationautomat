package com.evbelcompany.motivationautomat.models;

import java.io.Serializable;

public class Task implements Serializable {
    private long number;
    private String task;
    private int point;
    private String status;

    public Task() {
        this(0,null,0, null);
    }

    public Task(int number, String task, int point, String status) {
        this.number = number;
        this.task = task;
        this.point = point;
        this.status = status;

    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}


