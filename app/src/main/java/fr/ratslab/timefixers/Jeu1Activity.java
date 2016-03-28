package fr.ratslab.timefixers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.HashMap;

public class Jeu1Activity extends AppCompatActivity {

    public static FragmentJeu1 fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu1);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //On lance le premier fragment
        if (savedInstanceState == null) {
            fragment = new FragmentJeu1();
            getSupportFragmentManager().beginTransaction().add(R.id.jeu1_container, fragment).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){ //Si l'utilisateur appui sur la touche de retour, lui demander confirmation avant de retourner au menu
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Retour au menu");

            builder.setMessage("Voulez vous vraiment retourner au menu principal ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fragment.stopTimer();
                    finish();
                }
            });
            builder.setNegativeButton("Non", null);
            builder.show();
            return true;
        }
        return false;
    }
}
