package com.franckpano.devoir2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Franck on 23/03/2015.
 */
public class WriteActivity extends Activity {

    private DataDAO datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_layout);

        datasource = new DataDAO(this);
        datasource.open();
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        Data comment = null;
        final EditText text = (EditText) findViewById(R.id.texte);
        switch (view.getId()) {
            case R.id.btnSave:
                comment = datasource.createData(text.getText().toString(),MySQLiteHelper.TABLE_TEXTS);

                Toast SavedText = Toast.makeText(getApplicationContext(),
                        "Note Saved!", Toast.LENGTH_SHORT);
                break;
            case R.id.btnNew:
                text.setText("");
                break;
        }
    }


}