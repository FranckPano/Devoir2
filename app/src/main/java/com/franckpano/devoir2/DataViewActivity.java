package com.franckpano.devoir2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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

/*public class UsersAdapter extends ArrayAdapter<Data> {
    public UsersAdapter(Context context, ArrayList<Data> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Data data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.data_view_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        // Populate the data into the template view using the data object
        tvName.setText(data.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}*/
