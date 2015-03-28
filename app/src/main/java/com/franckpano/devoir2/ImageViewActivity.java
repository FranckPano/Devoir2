package com.franckpano.devoir2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Franck on 27/03/2015.
 */
public class ImageViewActivity extends Activity {
    Bitmap image;
    byte[] byteArrayInput;
    ImageView imageview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_layout);
        imageview = (ImageView) findViewById(R.id.Image_View);

        final Intent intent = getIntent();
        byteArrayInput = intent.getByteArrayExtra(DataViewActivity.DATA);
        image = BitmapFactory.decodeByteArray(byteArrayInput, 0, byteArrayInput.length);

        imageview.setImageBitmap(image);
    }
}
