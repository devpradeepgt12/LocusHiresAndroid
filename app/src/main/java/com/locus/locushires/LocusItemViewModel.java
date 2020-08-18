package com.locus.locushires;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class LocusItemViewModel extends ViewModel {

    public String item_photo,item_title,item_comment,item_id,item_type;
    public String item_options;

    public LocusItemViewModel() {
    }

    public LocusItemViewModel(String item_photo, String item_title, String item_comment, String item_id, String item_options, String item_type) {
        this.item_photo = item_photo;
        this.item_title = item_title;
        this.item_comment = item_comment;
        this.item_id = item_id;
        this.item_options = item_options;
        this.item_type = item_type;
    }

    @BindingAdapter({"app:imageString"})
    public static void loadImage(ImageView view, String imageString) {
        Context context = view.getContext();
        LocusUtils.decodeBase64AndSetImage(imageString, view);
    }
}
