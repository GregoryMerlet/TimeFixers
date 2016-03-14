package fr.ratslab.timefixers;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    static float VOLUME = 0.5f;
    //On créé un SoundPool pour pouvoir jouer des sons et on charge les sons dont on a besoin
    static SoundPool SOUNDPOOL = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    //On créé un HashMap qui va contenir tous les sons à jouer
    static HashMap<String, Integer> SONS = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initialiserSons();

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button quitButton = (Button) findViewById(R.id.menuQuitButton);
        Button settingsButton = (Button) findViewById(R.id.menuSettingsButton);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.SOUNDPOOL.play(MenuActivity.SONS.get("clic"), MenuActivity.VOLUME, MenuActivity.VOLUME, 0, 0, 1.0f);
                finish();
                System.exit(0);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.SOUNDPOOL.play(MenuActivity.SONS.get("clic"), MenuActivity.VOLUME, MenuActivity.VOLUME, 0, 0, 1.0f);
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }

    private void initialiserSons(){
        SONS.put("tresBien", SOUNDPOOL.load(this, R.raw.bien, 1));
        SONS.put("clic", SOUNDPOOL.load(this, R.raw.clic, 1));
    }
}
