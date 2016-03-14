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
        SeekBar musicSeekBar = (SeekBar) findViewById(R.id.settingsMusicSeekBar);
        SeekBar soundSeekBar = (SeekBar) findViewById(R.id.settingsSoundSeekBar);

        musicSeekBar.setProgress((int) (GestionnaireSons.getMusicVolume() * 10)); //On récupère la valeur du volume et on ajuste la barre de volume en fonction
        soundSeekBar.setProgress((int) (GestionnaireSons.getSoundVolume() * 10)); //On récupère la valeur du volume et on ajuste la barre de volume en fonction

        appuiBoutonRetour(returnButton);
        attendreChangementMusicSeekBar(musicSeekBar);
        attendreChangementSoundSeekBar(soundSeekBar);
    }

    public void appuiBoutonRetour(Button returnButton){
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon("clic");
                finish();
            }
        });
    }

    public void attendreChangementMusicSeekBar(SeekBar musicSeekBar){
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GestionnaireSons.setMusicVolume((float) progress / 10);
                GestionnaireSons.jouerSon("clic");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void attendreChangementSoundSeekBar(SeekBar soundSeekBar){
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GestionnaireSons.setSoundVolume((float) progress / 10);
                GestionnaireSons.jouerSon("clic");
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
