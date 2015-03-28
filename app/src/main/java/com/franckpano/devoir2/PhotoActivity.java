package com.franckpano.devoir2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Franck on 26/03/2015.
 */
public class PhotoActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;

    private DataDAO datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_layout);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


        //Création et ouverture de la base de données, via la DAO
        datasource = new DataDAO(this);
        datasource.open();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                LayoutInflater factory = LayoutInflater.from(this);
                final View alertDialogView = factory.inflate(R.layout.save_layout, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setView(alertDialogView);
                adb.setTitle("Sauvegarde de la note");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText et = (EditText)alertDialogView.findViewById(R.id.EditText1);
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Data data = datasource.createCroquisData(byteArray, et.getText().toString() ,MySQLiteHelper.TABLE_PHOTOS);
                        Toast savedText = Toast.makeText(getApplicationContext(),
                                "Draw Note Saved!", Toast.LENGTH_SHORT);
                        savedText.show();

                    } });

                adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
}