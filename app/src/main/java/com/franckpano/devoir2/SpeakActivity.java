package com.franckpano.devoir2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SpeakActivity extends Activity
{
    private static final String LOG_TAG = "AudioRecordTest";
    private static String fileName = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;

    Button recordButton = null;
    Button playButton = null;

    private DataDAO datasource;

    DataText dataInput;

    private boolean recording, playing;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.speak_layout);
        recording = false;
        playing = false;

        final Intent intent = getIntent();
        dataInput = (DataText)intent.getSerializableExtra(DataViewActivity.DATA);
        if(dataInput == null){
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            fileName += "/audiorecord" + timeStamp +".3gp";
        }
        else{
            fileName = dataInput.getData();
        }

        recordButton = (Button) findViewById(R.id.recordButton);
        playButton = (Button) findViewById(R.id.playButton);

        datasource = new DataDAO(this);
        datasource.open();
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void onRecord(View clickedButton){
            if (!recording) {
                startRecording();
                ((Button)clickedButton).setText("Arret");
            } else {
                stopRecording();
                ((Button)clickedButton).setText("Enregistrer");
            }
            recording = !recording;
    }
    public void onPlay(View clickedButton){
        if (!playing) {
            startPlaying();
            ((Button)clickedButton).setText("Stop");
        } else {
            stopPlaying();
            ((Button)clickedButton).setText("Lire");
        }
        playing = !playing;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void enregistrer(View clickedButton){

        if(dataInput == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            final View alertDialogView = factory.inflate(R.layout.save_layout, null);
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setView(alertDialogView);
            adb.setTitle("Sauvegarde de la note");
            adb.setIcon(android.R.drawable.ic_dialog_alert);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);
                    Data data = datasource.createData(fileName, et.getText().toString(), MySQLiteHelper.TABLE_VOIX);
                    Toast.makeText(getApplicationContext(), "Text Note Saved!", Toast.LENGTH_SHORT).show();
                }
            });

            adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            adb.show();
        }
        else{
            dataInput.setData(fileName);
            datasource.updateElement(dataInput,MySQLiteHelper.TABLE_VOIX);
            Toast.makeText(getApplicationContext(),"Text Note Saved!", Toast.LENGTH_SHORT).show();
        }

    }
}