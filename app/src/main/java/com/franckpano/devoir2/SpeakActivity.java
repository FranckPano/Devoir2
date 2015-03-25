package com.franckpano.devoir2;

import android.app.Activity;
import android.os.Bundle;
import android.media.AudioRecord;
import android.media.AudioFormat;
import android.media.MediaRecorder;

/**
 * Created by Franck on 23/03/2015.
 */
public class SpeakActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speak_layout);
    }


    public void recordNote() {
        /*int sampleRateInHz = 44100;

        int channelconfig = AudioFormat.CHANNEL_IN_STEREO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelconfig, audioFormat);
        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, channelconfig, audioFormat, bufferSize);

        short[] buffer = new short[bufferSize];
        while(recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {

            // Retourne le nombre de « shorts » lus, parce qu'il peut y en avoir moins que la taille du tableau
            int nombreDeShorts = recorder.read(buffer, 0, bufferSize);
        }*/
        MediaRecorder recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile("test.mp3");

        try {
            recorder.prepare();
        }
        catch(java.io.IOException e){}

        recorder.start();

        recorder.stop();
        recorder.release();
        recorder = null;
        }

    }