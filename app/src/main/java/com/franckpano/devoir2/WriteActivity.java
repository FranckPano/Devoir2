package com.franckpano.devoir2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Franck on 23/03/2015.
 */
public class WriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_layout);
        final EditText text = (EditText) findViewById(R.id.texte);

        Button vider = (Button) findViewById(R.id.btn3);
        vider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text.setText("");
            }
        });
    }
}