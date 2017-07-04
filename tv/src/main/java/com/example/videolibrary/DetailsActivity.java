package com.example.videolibrary;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by usuwi on 04/07/2017.
 */

public class DetailsActivity extends Activity {
    public static final String MOVIE = "Movie";
    public static final String SHARED_ELEMENT_NAME = "hero";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
    }

}
