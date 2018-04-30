package com.addapp.izum;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Азат on 25.09.2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<Main> {

    public MainActivityTest() {
        super(Main.class);
    }

    public void testActivityExists(){
        Main activity = getActivity();
        assertNotNull(activity);
    }

    public void testGreet(){
        Main activity = getActivity();

    }
}
