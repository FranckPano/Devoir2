package com.franckpano.devoir2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Gabriel on 28/03/2015.
 */
public class VideoViewActivity extends Activity {
    DataText uri;
    VideoView videoview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoview_layout);
        videoview = (VideoView) findViewById(R.id.Video_View);

        final Intent intent = getIntent();
        uri = (DataText)intent.getSerializableExtra(DataViewActivity.DATA);



        /*File source = new File(URI.create(uri.getData()));
        String pathDest = "android.resource://" + getPackageName() + "/" + uri.getName();
        File dest = new File (pathDest);
        copyFile(source, dest);*/

        //String toParse = .substring(0, uri.getData().length()-4);
        //videoview.setVideoURI(Uri.fromFile(dest));
        //videoview.setVideoPath(uri.getData());

        videoview.setVideoURI(Uri.parse(uri.getData()));
        MediaController mc = new MediaController(getApplicationContext());
        mc.setMediaPlayer(videoview);
        videoview.setMediaController(mc);
        //videoview.requestFocus();
        videoview.start();
    }


    /** copie le fichier source dans le fichier resultat
     * retourne vrai si cela réussit
     */
    public static boolean copyFile(File source, File dest){
        try{
            // Declaration et ouverture des flux
            java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);

            try{
                java.io.FileOutputStream destinationFile = null;
                try{
                    destinationFile = new FileOutputStream(dest);
                    // Lecture par segment de 0.5Mo
                    byte buffer[] = new byte[512 * 1024];
                    int nbLecture;
                    while ((nbLecture = sourceFile.read(buffer)) != -1){
                        destinationFile.write(buffer, 0, nbLecture);
                    }
                } finally {
                    destinationFile.close();
                }
            } finally {
                sourceFile.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            return false; // Erreur
        }
        return true; // Résultat OK
    }

}
