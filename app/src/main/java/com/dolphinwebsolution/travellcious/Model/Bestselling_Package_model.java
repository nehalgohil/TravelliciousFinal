package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 5/9/18.
 */

public class Bestselling_Package_model {
    String Location_title;
    String duration;
    String budget;
    String PackageId;
    String features1;
    String features2;
    String features3;
    String features4;
    String features5;

    public Bestselling_Package_model(String Location_title,
                                     String duration, String budget,
                                     String PackageId,String features1,String features2,String features3,String features4,String features5) {
        this.Location_title = Location_title;
        this.duration = duration;
        this.budget = budget;
        this.PackageId = PackageId;
        this.features1 = features1;
        this.features2 = features2;
        this.features3 = features3;
        this.features4 = features4;
        this.features5 = features5;

    }

    public String getFeatures1() {
        return features1;
    }

    public void setFeatures1(String features1) {
        this.features1 = features1;
    }

    public String getFeatures2() {
        return features2;
    }

    public void setFeatures2(String features2) {
        this.features2 = features2;
    }

    public String getFeatures3() {
        return features3;
    }

    public void setFeatures3(String features3) {
        this.features3 = features3;
    }

    public String getFeatures4() {
        return features4;
    }

    public void setFeatures4(String features4) {
        this.features4 = features4;
    }

    public String getFeatures5() {
        return features5;
    }

    public void setFeatures5(String features5) {
        this.features5 = features5;
    }

    public String getLocation_title() {
        return Location_title;
    }
    public void setLocation_title(String location_title) {
        Location_title = location_title;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getBudget() {
        return budget;
    }
    public void setBudget(String budget) {
        this.budget = budget;
    }
    public String getPackageId() {
        return PackageId;
    }
    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

}
