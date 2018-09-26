package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 10/9/18.
 */

public class Honeymoon_model {
    String location_id,location_image,location_title,budget;

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
    }

    public String getLocation_title() {
        return location_title;
    }

    public void setLocation_title(String location_title) {
        this.location_title = location_title;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Honeymoon_model(String location_id, String location_image, String location_title, String budget) {

        this.location_id = location_id;
        this.location_image = location_image;
        this.location_title = location_title;
        this.budget = budget;
    }
}
