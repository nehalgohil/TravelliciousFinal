package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 18/9/18.
 */

public class SliderPackage_name_model {
    String location_image,location_title;

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

    public SliderPackage_name_model(String location_image, String location_title) {

        this.location_image = location_image;
        this.location_title = location_title;
    }
}
