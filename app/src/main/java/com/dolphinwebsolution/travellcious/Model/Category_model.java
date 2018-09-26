package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 8/8/18.
 */

public class Category_model {
    private String category_id,img_cat,txtcatnm;

    public Category_model(String category_id, String img_cat, String txtcatnm) {
        this.category_id = category_id;
        this.img_cat = img_cat;
        this.txtcatnm = txtcatnm;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImg_cat() {
        return img_cat;
    }

    public void setImg_cat(String img_cat) {
        this.img_cat = img_cat;
    }

    public String getTxtcatnm() {
        return txtcatnm;
    }

    public void setTxtcatnm(String txtcatnm) {
        this.txtcatnm = txtcatnm;
    }
}
