package fr.ratslab.timefixers;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by sebastientosello on 28/03/16.
 */
public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button returnButton = (Button) findViewById(R.id.selectionReturnButton);
        ImageView jeu1 = (ImageView) findViewById(R.id.choixJeu1);
        ImageView jeu2 = (ImageView) findViewById(R.id.choixJeu2);

        appuiBoutonRetour(returnButton);
        appuiJeu1(jeu1);
        appuiJeu2(jeu2);
    }

    public void appuiBoutonRetour(Button returnButton){
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                finish();
            }
        });
    }
    public void appuiJeu1(ImageView imageJeu){
        imageJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                Intent intent = new Intent(SelectionActivity.this, Jeu1Activity.class);
                SelectionActivity.this.startActivity(intent);
            }
        });
    }

    public void appuiJeu2(ImageView imageJeu){
        imageJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                Intent intent = new Intent(SelectionActivity.this, Jeu2Activity.class);
                SelectionActivity.this.startActivity(intent);
            }
        });
    }



    public void attendreChangementMusicSeekBar(SeekBar musicSeekBar){
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GestionnaireSons.setMusicVolume((float) progress / 10);
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
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
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
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
