package fr.ratslab.timefixers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class HistoireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoire);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //On lance le premier fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.histoire_container, new FragmentHistoire()).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) return true; //Si l'utilisateur appui sur la touche de retour, ne rien faire
        return false;
    }
}
