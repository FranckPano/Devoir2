package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewActivity extends Activity {

    public final static String MODE = "mode";

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
                goToDataView(MySQLiteHelper.TABLE_TEXTS);
                break;
            case R.id.croquis:
                goToDataView(MySQLiteHelper.TABLE_CROQUIS);
                break;
            case R.id.voix:
                goToDataView(MySQLiteHelper.TABLE_VOIX);
                break;
            case R.id.photos:
                goToDataView(MySQLiteHelper.TABLE_PHOTOS);
                break;
            case R.id.videos:
                goToDataView(MySQLiteHelper.TABLE_VIDEOS);
                break;
        }
    }

    private void goToDataView(String dataType) {
        Intent newActivity = new Intent( this, DataViewActivity.class );
        newActivity.putExtra(MODE, dataType);
        startActivityForResult(newActivity, 0);
    }

}


