package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 9/8/18.
 */

public class Sub_cat_model {
   // String icon,image,name;
    String location_id;
    String location_title;
    String description;
    String location_image;

    public Sub_cat_model(String location_id,
                         String location_title,
                         String description, String location_image,
                         String budget) {

        this.location_id = location_id;
        this.location_title = location_title;
        this.description = description;
        this.location_image = location_image;
        this.budget = budget;
    }

    public String getLocation_id() {

        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_title() {
        return location_title;
    }

    public void setLocation_title(String location_title) {
        this.location_title = location_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    String budget;
}
