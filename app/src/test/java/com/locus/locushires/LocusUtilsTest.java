package com.locus.locushires;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Base64;

import static org.junit.Assert.*;

public class LocusUtilsTest {

    @Mock
    private MainActivity mainActivity;
    @Mock
    private Bitmap bm1; //ToDo Mocking bitmap to make the test work


    @Test
    public void getBase64String() {
        int input = R.drawable.ic_launcher_background;
        String stringToBeChecked = LocusUtils.getBase64String(mainActivity, input);
        Base64.Decoder decoder = Base64.getDecoder();
        boolean isValid = false; //default
        try {
            decoder.decode(stringToBeChecked);
            isValid = true;
        } catch(IllegalArgumentException iae) {
            // That string wasn't valid.
            isValid = false;
        }
        assertTrue(isValid);
    }

    @Test
    public void getBase64String1() {
        //int input = R.drawable.ic_launcher_background;
        //Bitmap bitmap = BitmapFactory.decodeResource(mainActivity.getResources(), input);

        String stringToBeChecked = LocusUtils.getBase64String(mainActivity, bm1);
        Base64.Decoder decoder = Base64.getDecoder();
        boolean isValid = false; //default
        try {
            decoder.decode(stringToBeChecked);
            isValid = true;
        } catch(IllegalArgumentException iae) {
            // That string wasn't valid.
            isValid = false;
        }
        assertTrue(isValid);
    }

    @Test
    public void decodeBase64AndSetImage() {
    }

    @Test
    public void readXMLinString() {
    }
}