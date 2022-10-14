package com.evbelcompany.motivationautomat.models;

import java.io.Serializable;

public class Motivator implements Serializable {

    private String motivator;
    private Integer points;
    private String status;

    public Motivator(String motivator, Integer points, String status) {
        this.motivator = motivator;
        this.points = points;
        this.status = status;

    }

    public String getMotivator() {
        return motivator;
    }

    public void setMotivator(String motivator) {
        this.motivator = motivator;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
