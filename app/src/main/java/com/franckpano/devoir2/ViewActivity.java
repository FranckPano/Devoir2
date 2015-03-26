package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class ViewActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.ltm.ltmactionbar.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.texts:
                goToDataView("texts");
                break;
            case R.id.croquis:
                goToDataView("croquis");
                break;
            case R.id.voix:
                goToDataView("voix");
                break;
            case R.id.photos:
                goToDataView("photos");
                break;
            case R.id.videos:
                goToDataView("videos");
                break;
        }
    }

    private void goToDataView(String dataType) {
        Intent newActivity = new Intent( this, DataViewActivity.class );
        newActivity.putExtra(EXTRA_MESSAGE, dataType);
        startActivityForResult(newActivity, 0);
    }

}

