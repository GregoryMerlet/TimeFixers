package fr.ratslab.timefixers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentJeu1 extends Fragment {

    static ArrayList<QuestionJeu1> QUESTIONS;
    static ArrayList<String> REPONSES;

    private View rootView;
    private int questionId;
    private CountDownTimer timer;

    public FragmentJeu1() {
        QUESTIONS = new ArrayList<QuestionJeu1>();
        REPONSES = new ArrayList<String>();

        this.questionId = 0;
        this.timer = new CountDownTimer(30000, 100) {
            public void onTick(long millisUntilFinished) {
                ProgressBar timeBar = (ProgressBar)FragmentJeu1.this.rootView.findViewById(R.id.jeu1TimeProgressBar);
                timeBar.setProgress(timeBar.getProgress()+1);
            }
            public void onFinish() {
                questionSuivante(null);
            }
        };
    }

    public FragmentJeu1(int previousQuestionId) {
        this.questionId = previousQuestionId + 1;
        this.timer = new CountDownTimer(30000, 100) {
            public void onTick(long millisUntilFinished) {
                ProgressBar timeBar = (ProgressBar)FragmentJeu1.this.rootView.findViewById(R.id.jeu1TimeProgressBar);
                timeBar.setProgress(timeBar.getProgress()+1);
            }
            public void onFinish() {
                questionSuivante(null);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.fragment_jeu1, container, false);

        if(QUESTIONS.size() == 0)recupererQuestions();
        else {
            initialiserTextes();
            timer.start(); //On démarre le chronomètre
        }

        Button reponse1 = (Button) this.rootView.findViewById(R.id.jeu1Reponse1);
        Button reponse2 = (Button) this.rootView.findViewById(R.id.jeu1Reponse2);
        Button reponse3 = (Button) this.rootView.findViewById(R.id.jeu1Reponse3);

        appuiBouton(reponse1);
        appuiBouton(reponse2);
        appuiBouton(reponse3);

        return this.rootView;
    }

    public void appuiBouton(final Button reponse){
        reponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                questionSuivante(reponse.getText().toString());
            }
        });
    }

    public void questionSuivante(String response){
        timer.cancel();

        REPONSES.add(response);

        //Si il ne reste plus de questions on passe aux résultats, sinon on passe à la question suivante
        if (FragmentJeu1.this.questionId == QUESTIONS.size() - 1) {
            Intent intent = new Intent(FragmentJeu1.this.getActivity(), ResultActivity.class);
            intent.putParcelableArrayListExtra("questionnaire", QUESTIONS);
            intent.putStringArrayListExtra("reponses", REPONSES);
            FragmentJeu1.this.getActivity().startActivity(intent);
            FragmentJeu1.this.getActivity().finish();
        } else {
            FragmentJeu1 newFragment = new FragmentJeu1(FragmentJeu1.this.questionId);
            Jeu1Activity.fragment = newFragment;
            FragmentJeu1.this.getFragmentManager().beginTransaction().replace(R.id.jeu1_container, newFragment).addToBackStack(null).commit();
        }
    }

    public void initialiserTextes(){
        TextView question = (TextView) FragmentJeu1.this.rootView.findViewById(R.id.jeu1Question);
        Button reponse1 = (Button) FragmentJeu1.this.rootView.findViewById(R.id.jeu1Reponse1);
        Button reponse2 = (Button) FragmentJeu1.this.rootView.findViewById(R.id.jeu1Reponse2);
        Button reponse3 = (Button) FragmentJeu1.this.rootView.findViewById(R.id.jeu1Reponse3);
        TextView avance = (TextView) this.rootView.findViewById(R.id.jeu1AvanceTextView);

        avance.setText(getString(R.string.jeu_1_avance_1) + " " + (this.questionId + 1) + " " + getString(R.string.jeu_1_avance_2) + " " + QUESTIONS.size());
        question.setText(QUESTIONS.get(FragmentJeu1.this.questionId).getQuestion()); //On place le texte sur la question
        placerReponseAleatoirement(new Button[]{reponse1, reponse2, reponse3}); //On réparti les réponses aléatoirement
    }

    public void placerReponseAleatoirement(Button[] reponses){
        int aleatoire;
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu1.this.questionId).getReponseV());
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu1.this.questionId).getReponseF1());
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu1.this.questionId).getReponseF2());
    }

    private void recupererQuestions(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://si.sebastientosello.com/TimeFixers/jeu1.xml";

        final ProgressDialog progressDialog = ProgressDialog.show(this.getActivity(), "Chargement","Connexion en cours...", true, true, null);
        client.get(this.getActivity(), url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                montrerMessageErreur("Erreur de lecture des données dans failure");
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                //Chargement du fichier XML et construction de la liste de questions
                try {
                    parserReponse(responseBody); //On parse le XML pour le mettre sous forme de questions

                    Collections.shuffle(QUESTIONS); //On mélange les questions

                    initialiserTextes(); //On place les textes

                    timer.start(); //On démarre le chronomètre

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    montrerMessageErreur("Erreur de lecture des données");
                } catch (IOException e) {
                    e.printStackTrace();
                    montrerMessageErreur("Erreur de connexion lors de la lecture des données");
                }
            }


        });
    }

    private void montrerMessageErreur(String m){
        new AlertDialog.Builder(this.getActivity()).setTitle("Erreur").setMessage(m).setNeutralButton("OK", null).show();
    }

    private void parserReponse(String responseBody) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(responseBody));
        parser.require(XmlPullParser.START_DOCUMENT, null, null);
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, null, "quizz");
        int eventType = parser.getEventType();

        while(parser.nextTag()== XmlPullParser.START_TAG) {
            parser.nextTag();
            QuestionJeu1 question = new QuestionJeu1(parser);
            QUESTIONS.add(question);
        }

        parser.require(XmlPullParser.END_TAG, null, "quizz");
        parser.next();
        parser.require(XmlPullParser.END_DOCUMENT, null, null);
    }

    public void stopTimer(){
        this.timer.cancel();
    }
}