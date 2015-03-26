package com.franckpano.devoir2;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Franck on 26/03/2015.
 */
public class PhotographyActivity extends Activity implements SurfaceHolder.Callback {

    private Camera mCamera = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surface = (SurfaceView) findViewById(R.id.surface_view);
        SurfaceHolder holder = surface.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // On déclare que la classe actuelle gérera les callbacks
        holder.addCallback(this);
    }

    // Se déclenche quand la surface est créée
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Se déclenche quand la surface est détruite

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
    }

    // Se déclenche quand la surface change de dimensions ou de format

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.release();
    }
}