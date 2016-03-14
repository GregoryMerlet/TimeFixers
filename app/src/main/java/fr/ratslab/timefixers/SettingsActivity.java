package fr.ratslab.timefixers;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button returnButton = (Button) findViewById(R.id.settingsReturnButton);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.settingsVolumeSeekBar);

        volumeSeekBar.setProgress((int)(MenuActivity.VOLUME * 10)); //On récupère la valeur du volume et on ajuste la barre de volume en fonction

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.SOUNDPOOL.play(MenuActivity.SONS.get("clic"), MenuActivity.VOLUME, MenuActivity.VOLUME, 0, 0, 1.0f);
                finish();
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MenuActivity.VOLUME = (float)progress / 10;
                MenuActivity.SOUNDPOOL.play(MenuActivity.SONS.get("clic"), MenuActivity.VOLUME, MenuActivity.VOLUME, 0, 0, 1.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
