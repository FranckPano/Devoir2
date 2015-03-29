package com.franckpano.devoir2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by Franck on 23/03/2015.
 */
public class VideoActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;

    private DataDAO datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

        //Création et ouverture de la base de données, via la DAO
        datasource = new DataDAO(this);
        datasource.open();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
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
                        String uriVideo = data.getData().toString();

                        Data data = datasource.createData(uriVideo, et.getText().toString() ,MySQLiteHelper.TABLE_VIDEOS);
                        Toast savedText = Toast.makeText(getApplicationContext(),
                                "Video Saved!", Toast.LENGTH_SHORT);
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