package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 25/9/18.
 */

public class Sightseen_model {
    String days;
    String locations;
    String sightseen_title;

    public Sightseen_model(String days, String locations,
                           String sightseen_title, String description) {
        this.days = days;
        this.locations = locations;
        this.sightseen_title = sightseen_title;
        this.description = description;
    }

    public String getDays() {

        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getSightseen_title() {
        return sightseen_title;
    }

    public void setSightseen_title(String sightseen_title) {
        this.sightseen_title = sightseen_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;
}
