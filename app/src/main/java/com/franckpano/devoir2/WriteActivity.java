package com.franckpano.devoir2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
        final EditText text = (EditText) findViewById(R.id.texte);
        switch (view.getId()) {
            case R.id.btnSave:

                LayoutInflater factory = LayoutInflater.from(this);
                final View alertDialogView = factory.inflate(R.layout.save_layout, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setView(alertDialogView);
                adb.setTitle("Sauvegarde de la note");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
                        Data data = datasource.createData(text.getText().toString(), et.getText().toString(), MySQLiteHelper.TABLE_TEXTS);
                        Toast savedText = Toast.makeText(getApplicationContext(),
                                "Text Note Saved!", Toast.LENGTH_SHORT);
                        savedText.show();
                    } });

                adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();


                break;
            case R.id.btnNew:
                text.setText("");
                break;
        }
    }
}



