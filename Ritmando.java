package com.phdev.br.ritmando;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;

import com.phdev.br.ritmando.RitmandoView.RitmandoThread;
import com.phdev.br.ritmando.bluetooth.Bluetooth;
import com.phdev.br.ritmando.modelos.Tela;

//public class Ritmando extends Activity {
public class Ritmando extends Activity {

    private RitmandoThread thread;
    private RitmandoView ritmandoView;

    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.ritmando_main);
        ritmandoView = (RitmandoView) findViewById(R.id.ritmandoView);
        thread = ritmandoView.getThread();

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Para criação de alguns recursos, é necessaria a autorização de gravação de audio. Não se preocupe, o jogo ainda funcionara caso a autorização seja negada.")
                    .setTitle("Requisito de permissão");

            builder.setPositiveButton("Proximo", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    makeRequest();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                RECORD_REQUEST_CODE);
    }

    @Override
    public void onBackPressed(){
        thread.keyBackPressed();
    }
}
