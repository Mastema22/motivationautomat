package com.evbelcompany.motivationautomat;

public class Motivator {

    private String motivator;
    private Integer points;

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

    public Motivator(String motivator, Integer points) {
        this.motivator = motivator;
        this.points = points;
    }
}
