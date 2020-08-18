package com.locus.locushires;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.locus.locushires.databinding.LocusItemBinding;
import com.locus.locushires.databinding.LocusItemBindingComment;
import com.locus.locushires.databinding.LocusItemBindingOptions;
import com.locus.locushires.databinding.LocusItemBindingPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocusItemAdapter.ItemClickListener, LocusItemAdapter.ItemTouchListener {

    public static final String PHOTO_TYPE = "PHOTO";
    public static final String COMMENT_TYPE = "COMMENT";
    public static final String OPTIONS_TYPE = "SINGLE_CHOICE";
    private static final int CAMERA_REQUEST = 1888;
    private ImageView userInputImageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private RecyclerView locus_item_list;
    private List<LocusItemViewModel> locusItemViewModels = new ArrayList<>();
    private JSONArray jsonArray;
    private ArrayList<Integer> photoSetPositions = new ArrayList<>();
    private int currentPosition = -1;
    private LocusItemAdapter locusItemAdapter;
    private Dialog imagePopupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.ic_home_24dp);
        }
        setContentView(R.layout.activity_main);
        locus_item_list = findViewById(R.id.locus_item_list);
        //locus_item_list.setLayoutManager(new LinearLayoutManager(this));
        locus_item_list.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
                return false;
            }
        });

        try {
            jsonArray = new JSONArray(LocusUtils.readXMLinString(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null && jsonArray.length() > 0) {

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String type = jsonObject.getString("type");
                    if (type != null && !type.isEmpty()) {
                        switch (type) {
                            case PHOTO_TYPE:
                                LocusItemViewModel locusItemViewModel = new LocusItemViewModel();
                                locusItemViewModel.item_photo = null;
                                locusItemViewModel.item_title = jsonObject.getString("title");
                                locusItemViewModel.item_type = PHOTO_TYPE;
                                locus_item_list.scrollToPosition(0);
                                locusItemViewModels.add(locusItemViewModel);
                                break;


                            case COMMENT_TYPE:
                                LocusItemViewModel locusItemViewModel1 = new LocusItemViewModel();
                                locusItemViewModel1.item_photo = LocusUtils.getBase64String(this, R.mipmap.ht);
                                locusItemViewModel1.item_type = COMMENT_TYPE;
                                locusItemViewModel1.item_title = jsonObject.has("title") ? jsonObject.getString("title") : getString(R.string.app_name);
                                locusItemViewModels.add(locusItemViewModel1);
                                break;

                            case OPTIONS_TYPE:
                                LocusItemViewModel locusItemViewModel2 = new LocusItemViewModel();
                                locusItemViewModel2.item_photo = LocusUtils.getBase64String(this, R.mipmap.ht);
                                locusItemViewModel2.item_type = OPTIONS_TYPE;
                                locusItemViewModel2.item_title = jsonObject.has("title") ? jsonObject.getString("title") : getString(R.string.app_name);
                                JSONObject jsonoptions = jsonObject.getJSONObject("dataMap");
                                JSONArray jsonoptionsarray = jsonoptions.has("options") ? jsonoptions.getJSONArray("options") : new JSONArray();
                                ArrayList<String> list_options = new ArrayList<>();
                                for (int j = 0; j < jsonoptionsarray.length(); j++) {
                                    list_options.add(jsonoptionsarray.getString(j));
                                }
                                locusItemViewModel2.item_options = list_options.toString().replaceAll("\\[|\\]", "").replaceAll(", ", "\t");
                                locusItemViewModels.add(locusItemViewModel2);
                                break;

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        locusItemAdapter = new LocusItemAdapter(locusItemViewModels, this);
        locus_item_list.setAdapter(locusItemAdapter);
        locusItemAdapter.setClickListener(this);
        locusItemAdapter.setTouchListener(this);
        locus_item_list.requestFocus();
        locus_item_list.scrollTo(0,0);

    }


    @Override
    public void onClick(View view, LocusItemBinding locusItemBinding, int position) {

    }

    @Override
    public void onClick(View view, LocusItemBindingPhoto locusItemBindingPhoto, int position) {
        if(locusItemBindingPhoto.itemPhoto.equals(view)){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            { requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE); }
            else
            { userInputImageView = locusItemBindingPhoto.itemPhoto;
                currentPosition = position;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST); }
        }
    }

    @Override
    public void onClick(View view, LocusItemBindingComment locusItemBindingComment, int position) {

    }

    @Override
    public void onClick(View view, LocusItemBindingOptions locusItemBindingOptions, int position) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userInputImageView.setImageBitmap(photo);
            userInputImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            photoSetPositions.add(currentPosition);
            locusItemViewModels.get(currentPosition).item_photo = LocusUtils.getBase64String(this, photo);
            locusItemAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userInputImageView = (ImageView) v;
        if (imagePopupDialog == null) {
            imagePopupDialog = new Dialog(this);
            imagePopupDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View popup_view = getLayoutInflater().inflate(R.layout.popup_image_view, null);
            ImageView popup_imageview = popup_view.findViewById(R.id.popup_imageview);
            if (userInputImageView != null)
                popup_imageview.setImageDrawable(userInputImageView.getDrawable());
            imagePopupDialog.setContentView(popup_view);
            Button ok = imagePopupDialog.findViewById(R.id.ok_btn);
            ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    imagePopupDialog.dismiss();
                    imagePopupDialog.cancel();
                    imagePopupDialog = null;
                }
            });
            imagePopupDialog.show();
        }

        return false;
    }
}
