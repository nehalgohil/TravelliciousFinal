package com.dolphinwebsolution.travellcious.Model;

import java.util.ArrayList;

/**
 * Created by ap6 on 30/8/18.
 */

public class Hotel_model {
    String hotel_title;
    String description;
    String star;
    String hotel_image;
    String check_in;
    String check_out;
    String room_type;
    String meals,facilities,Days,Nights;


    public ArrayList<Facilities_model> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Facilities_model> items) {
        Items = items;
    }

    private ArrayList<Facilities_model> Items;
    public Hotel_model(String hotel_title, String description,
                       String star, String hotel_image, String check_in,
                       String check_out, String room_type, String meals,
                       String facilities, String Days, String Nights
    ) {
        this.hotel_title = hotel_title;
        this.description = description;
        this.star = star;
        this.hotel_image = hotel_image;
        this.check_in = check_in;
        this.check_out = check_out;
        this.room_type = room_type;
        this.meals = meals;
        this.facilities = facilities;
        this.Days = Days;
        this.Nights = Nights;
    }

    public Hotel_model() {

    }


    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getNights() {
        return Nights;
    }

    public void setNights(String nights) {
        Nights = nights;
    }


    public String getHotel_title() {

        return hotel_title;
    }

    public void setHotel_title(String hotel_title) {
        this.hotel_title = hotel_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getHotel_image() {
        return hotel_image;
    }

    public void setHotel_image(String hotel_image) {
        this.hotel_image = hotel_image;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }






}




