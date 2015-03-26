package com.franckpano.devoir2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Franck on 26/03/2015.
 */
public class DataViewActivity  extends ListActivity {

    private DataDAO datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view_layout);

        final Intent intent = getIntent();
        String mode = intent.getStringExtra(ViewActivity.EXTRA_MESSAGE);

        datasource = new DataDAO(this);
        datasource.open();

        List<Data> values = null;

        switch(mode){
            case "texts":
                values = datasource.getAllContents(MySQLiteHelper.TABLE_TEXTS);
                break;
            case "croquis":
                values = datasource.getAllContents(MySQLiteHelper.TABLE_CROQUIS);
                break;
            case "voix":
                values = datasource.getAllContents(MySQLiteHelper.TABLE_VOIX);
                break;
            case "photos":
                values = datasource.getAllContents(MySQLiteHelper.TABLE_PHOTOS);
                break;
            case "videos":
                values = datasource.getAllContents(MySQLiteHelper.TABLE_VIDEOS);
                break;
        }

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Data> adapter = new ArrayAdapter<Data>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
