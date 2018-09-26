package com.dolphinwebsolution.travellcious.Model;

public class sliderimage_model {

    String location_id,location_image;


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

    public sliderimage_model(String location_id, String location_image) {

        this.location_id = location_id;
        this.location_image = location_image;
    }
}
