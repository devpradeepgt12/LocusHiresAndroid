package com.locus.locushires;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocusUtils {

    public static String getBase64String(Activity activity, int resource_id) {

        // give your image file url in mCurrentPhotoPath
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resource_id);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String getBase64String(Activity activity, Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static void decodeBase64AndSetImage(String completeImageData, ImageView imageView) {

        if (completeImageData!=null) {
            InputStream stream = new ByteArrayInputStream(Base64.decode(completeImageData.getBytes(), Base64.DEFAULT));

            Bitmap bitmap = BitmapFactory.decodeStream(stream);

            imageView.setImageBitmap(bitmap);
        }
    }

    public static String readXMLinString(Activity activity) {
        try {
            InputStream is = activity.getResources().openRawResource(R.raw.datajson);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            return text;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
