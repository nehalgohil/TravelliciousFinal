package com.dolphinwebsolution.travellcious.Model;

/**
 * Created by ap6 on 24/9/18.
 */

public class FAQ_Model {
    String FAQs_title,FAQs_containts;

    public FAQ_Model(String FAQs_title, String FAQs_containts) {
        this.FAQs_title = FAQs_title;
        this.FAQs_containts = FAQs_containts;
    }

    public String getFAQs_title() {

        return FAQs_title;
    }

    public void setFAQs_title(String FAQs_title) {
        this.FAQs_title = FAQs_title;
    }

    public String getFAQs_containts() {
        return FAQs_containts;
    }

    public void setFAQs_containts(String FAQs_containts) {
        this.FAQs_containts = FAQs_containts;
    }
}
