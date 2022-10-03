package com.evbelcompany.motivationautomat;

public class Task {
    private long number;
    private String task;
    private int point;

    public Task() {
        this(0,null,0);
    }

    public Task(int number, String task, int point) {
        this.number = number;
        this.task = task;
        this.point = point;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
}


