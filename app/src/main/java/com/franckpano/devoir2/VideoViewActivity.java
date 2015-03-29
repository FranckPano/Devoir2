package com.franckpano.devoir2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

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

        /*videoview.setMediaController(new MediaController(this));
        videoview.setVideoURI(Uri.parse(uri.getData()));
        videoview.requestFocus();
        videoview.start();*/

        videoview.setVideoPath(uri.getData());
        MediaController mc = new MediaController(this);
        mc.setMediaPlayer(videoview);
        videoview.setMediaController(mc);
        videoview.requestFocus();
    }
}
