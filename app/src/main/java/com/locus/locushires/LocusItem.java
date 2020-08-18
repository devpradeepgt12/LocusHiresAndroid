package com.locus.locushires;

import java.util.ArrayList;

public class LocusItem {
    private String item_type;
    private String item_title;
    private String item_comment;
    private String photoSting;
    private int item_id;
    private String item_options;


    public LocusItem(String item_type, String item_title, String item_comment, String photoSting, int item_id, String item_options) {
        this.item_type = item_type;
        this.item_title = item_title;
        this.item_comment = item_comment;
        this.photoSting = photoSting;
        this.item_id = item_id;
        this.item_options = item_options;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getItem_title() {
        return item_title;
    }

    public String getItem_comment() {
        return item_comment;
    }

    public String getPhotoSting() {
        return photoSting;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_options() {
        return item_options;
    }
}
