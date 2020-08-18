package com.locus.locushires;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocusItemTest {

    LocusItem locusItem = new LocusItem("Photo", "New Photo", "Dummy", "Dummy photo string", 560, "New, Nice< Best");

    @Test
    public void getItem_type() {
        String input = "Photo";
        String output = locusItem.getItem_type();
        assertEquals(input,output);

    }

    @Test
    public void getItem_title() {
        String input = "New Photo";
        String output = locusItem.getItem_title();
        assertEquals(input,output);
    }

    @Test
    public void getItem_comment() {
        String input = "Dummy";
        String output = locusItem.getItem_comment();
        assertEquals(input,output);
    }

    @Test
    public void getPhotoSting() {
        String input = "Dummy photo string";
        String output = locusItem.getPhotoSting();
        assertEquals(input,output);
    }

    @Test
    public void getItem_id() {
        Integer input = 560;
        Integer output = locusItem.getItem_id();
        assertEquals(input,output);
    }

    @Test
    public void getItem_options() {
        String input = "New, Nice< Best";
        String output = locusItem.getItem_options();
        assertEquals(input,output);
    }
}