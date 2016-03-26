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

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GestionnaireSons.initGestionnaireSons(getApplicationContext());
        GestionnaireSons.jouerMusique(GestionnaireSons.Musique.MUSIQUE1, true);

        Button quitButton = (Button) findViewById(R.id.menuQuitButton);
        Button settingsButton = (Button) findViewById(R.id.menuSettingsButton);
        Button playButton = (Button) findViewById(R.id.menuPlayButton);

        appuiBoutonQuitter(quitButton);
        appuiBoutonOptions(settingsButton);
        appuiBoutonJouer(playButton);
    }

    private void appuiBoutonQuitter(Button quitButton){
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                finish();
                System.exit(0);
            }
        });
    }

    private void appuiBoutonOptions(Button settingsButton){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }

    private void appuiBoutonJouer(Button playButton){
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                Intent intent = new Intent(MenuActivity.this, Jeu1Activity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){ //Si l'utilisateur appui sur la touche de retour, lui demander confirmation avant de quitter l'application
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quitter");

            builder.setMessage("Voulez vous vraiment quitter le jeu ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            builder.setNegativeButton("Non", null);
            builder.show();
            return true;
        }
        return false;
    }
}
