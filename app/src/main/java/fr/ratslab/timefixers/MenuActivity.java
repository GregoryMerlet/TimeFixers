package fr.ratslab.timefixers;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Button quitButton = (Button) findViewById(R.id.menuQuitButton);
        Button settingsButton = (Button) findViewById(R.id.menuSettingsButton);

        appuiBoutonQuitter(quitButton);
        appuiBoutonOptions(settingsButton);
    }

    private void appuiBoutonQuitter(Button quitButton){
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon("clic");
                finish();
                System.exit(0);
            }
        });
    }

    private void appuiBoutonOptions(Button settingsButton){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon("clic");
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
}
