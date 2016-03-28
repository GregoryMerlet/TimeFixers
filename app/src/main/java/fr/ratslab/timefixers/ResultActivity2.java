package fr.ratslab.timefixers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sebastientosello on 28/03/16.
 */
public class ResultActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //On cache la barre de status pour obtenir un affichage plein écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        ArrayList<QuestionJeu2> questionnaire = i.getParcelableArrayListExtra("questionnaire");
        ArrayList<String> reponses = i.getStringArrayListExtra("reponses");

        TextView score = (TextView) findViewById(R.id.resultScore);
        score.setText(getString(R.string.result_score) + " " + compterBonnesReponses(questionnaire, reponses) + "/" + questionnaire.size());

        //On utilise un adapter pour remplir la ListView
        QuestionnaireAdapter2 adapter = new QuestionnaireAdapter2(this, questionnaire, reponses);
        ListView listView = (ListView)findViewById(R.id.resultListView);
        listView.setAdapter(adapter);

        Button retour = (Button) findViewById(R.id.resultReturnButton);
        appuiBoutonRetour(retour);
    }

    public int compterBonnesReponses(ArrayList<QuestionJeu2> questionnaire, ArrayList<String> reponses){
        int compteur = 0;
        for(int i = 0; i < questionnaire.size(); i++){
            if(questionnaire.get(i).getReponseV().equals(reponses.get(i)))compteur++;//Si la réponse est bonne on incrémente le compteur
        }
        return compteur;
    }

    public void appuiBoutonRetour(Button retour){
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                finish();
            }
        });
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
