package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 5/9/18.
 */

public class Review_model {
    String Start;
    String Review;
    String User_Id,User_image,Username;

    public String getUser_image() {
        return User_image;
    }

    public void setUser_image(String user_image) {
        User_image = user_image;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Review_model(String start, String review, String user_Id,
                        String package_id, String User_image, String Username) {
        this.Start = start;
        this.Review = review;
        this.User_Id = user_Id;
        this.Package_id = package_id;
        this.User_image = User_image;
        this.Username = Username;
    }

    public String getStart() {

        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getPackage_id() {
        return Package_id;
    }

    public void setPackage_id(String package_id) {
        Package_id = package_id;
    }

    String Package_id;
}
