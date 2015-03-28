package com.franckpano.devoir2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Franck on 26/03/2015.
 */
public class DataViewActivity  extends ListActivity {

    private DataDAO datasource;

    private final int ID_OUVRIR = 0, ID_SUPPR = 1;
    private final int GROUP_DEFAULT = 0;

    public final static String DATA = "data";

    private Data dataSelected;
    private String mode;

    ArrayAdapter<Data> adapter;
    List<Data> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view_layout);

        final Intent intent = getIntent();
        mode = intent.getStringExtra(ViewActivity.MODE);

        datasource = new DataDAO(this);
        datasource.open();

        values = null;
        String string = "string";

        switch(mode){
            case MySQLiteHelper.TABLE_TEXTS:
                values = datasource.getAllContents(mode);
                break;
            case MySQLiteHelper.TABLE_CROQUIS:
                values = datasource.getAllContents(mode);
                break;
            case MySQLiteHelper.TABLE_VOIX:
                values = datasource.getAllContents(mode);
                break;
            case MySQLiteHelper.TABLE_PHOTOS:
                values = datasource.getAllContents(mode);
                break;
            case MySQLiteHelper.TABLE_VIDEOS:
                values = datasource.getAllContents(mode);
                break;
        }

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Data>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        ListView lv = (ListView) findViewById(android.R.id.list);
        registerForContextMenu(lv);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == android.R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            dataSelected = (Data) lv.getItemAtPosition(acmi.position);

            menu.add(GROUP_DEFAULT, ID_OUVRIR, 0, "Ouvrir");
            menu.add(GROUP_DEFAULT, ID_SUPPR, 0, "Supprimer");
        }
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_OUVRIR:
                ouvrirSelected();
                return true;
            case ID_SUPPR:
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Supprimer Note");
                newDialog.setMessage("Voulez-vous supprimer cette note?");
                newDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        values.remove(dataSelected);
                        datasource.deleteElement(dataSelected, mode);
                        adapter.remove(dataSelected);
                        setListAdapter(adapter);
                        Toast.makeText(getApplicationContext(), "Note supprimée", Toast.LENGTH_SHORT).show();
                    }
                });
                newDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                newDialog.show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void ouvrirSelected(){
        Intent nextActivity;
            switch(mode){
                case MySQLiteHelper.TABLE_TEXTS:
                    Toast.makeText(getApplicationContext(), "Pas encore possible", Toast.LENGTH_SHORT).show();
                    break;
                case MySQLiteHelper.TABLE_CROQUIS:
                    nextActivity = new Intent( this, ImageViewActivity.class );
                    nextActivity.putExtra(DATA, ((DataPicture)dataSelected).getData());
                    startActivityForResult(nextActivity, 0);
                    break;
                case MySQLiteHelper.TABLE_VOIX:
                    Toast.makeText(getApplicationContext(), "Pas encore possible", Toast.LENGTH_SHORT).show();
                    break;
                case MySQLiteHelper.TABLE_PHOTOS:
                    nextActivity = new Intent( this, ImageViewActivity.class );
                    nextActivity.putExtra(DATA, ((DataPicture)dataSelected).getData());
                    startActivityForResult(nextActivity, 0);
                    break;
                case MySQLiteHelper.TABLE_VIDEOS:
                    Toast.makeText(getApplicationContext(), "Pas encore possible", Toast.LENGTH_SHORT).show();
                    break;
            }
    }
}