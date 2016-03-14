package fr.ratslab.timefixers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
