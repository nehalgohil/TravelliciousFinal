package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 24/9/18.
 */

public class Package_images_grid_model {
    String img_id,images;

    public Package_images_grid_model(String img_id, String images) {
        this.img_id = img_id;
        this.images = images;
    }

    public String getImg_id() {

        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
