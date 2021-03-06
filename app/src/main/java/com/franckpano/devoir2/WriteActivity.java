package com.franckpano.devoir2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Franck on 23/03/2015.
 */
public class WriteActivity extends Activity {

    private DataDAO datasource;
    DataText dataInput;

    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_layout);

        final Intent intent = getIntent();
        dataInput = (DataText)intent.getSerializableExtra(DataViewActivity.DATA);
        if(dataInput != null){
            final EditText text = (EditText) findViewById(R.id.texte);
            text.setText(dataInput.getData());
        }
        datasource = new DataDAO(this);
        datasource.open();

        Button speakButton = (Button) findViewById(R.id.speakButton);
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);
            speakButton.setText("Reconnaissance vocale introuvable");
        }
    }

    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition ...");
        startActivityForResult(intent, REQUEST_CODE);
    }
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            final EditText text = (EditText) findViewById(R.id.texte);
            text.setText(text.getText() + " " + matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                enregistrer();
                break;
            case R.id.btnNew:
                final EditText text = (EditText) findViewById(R.id.texte);
                //Alerte de vérification pour nouveau croquis
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Vider");
                newDialog.setMessage("Voulez vous vider la note (Tout sera perdu)?");
                newDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        text.setText("");
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                newDialog.show();
                break;
            case R.id.speakButton:
                startVoiceRecognitionActivity();
                break;
        }
    }

    @Override
    protected void onStop() {
        if(dataInput!=null){
            enregistrer();
        }
        else{
            final EditText text = (EditText) findViewById(R.id.texte);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String NoteFileName = "Text_Note_" + timeStamp;
            Data data = datasource.createData(text.getText().toString(), NoteFileName, MySQLiteHelper.TABLE_TEXTS);
        }
       Toast.makeText(getApplicationContext(),"Text Note Saved!", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    public void enregistrer(){
        final EditText text = (EditText) findViewById(R.id.texte);

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
                    Data data = datasource.createData(text.getText().toString(), et.getText().toString(), MySQLiteHelper.TABLE_TEXTS);
                    Toast.makeText(getApplicationContext(),"Text Note Saved!", Toast.LENGTH_SHORT).show();
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
            dataInput.setData(text.getText().toString());
            datasource.updateElement(dataInput,MySQLiteHelper.TABLE_TEXTS);
            Toast.makeText(getApplicationContext(),"Text Note Saved!", Toast.LENGTH_SHORT).show();
        }
    }
}



