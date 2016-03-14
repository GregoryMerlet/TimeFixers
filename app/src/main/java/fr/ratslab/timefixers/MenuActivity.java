package fr.ratslab.timefixers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button quitButton = (Button) findViewById(R.id.menuQuitButton);
        Button settingsButton = (Button) findViewById(R.id.menuSettingsButton);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
}
