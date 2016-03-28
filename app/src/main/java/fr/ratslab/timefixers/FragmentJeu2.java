package fr.ratslab.timefixers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sebastientosello on 28/03/16.
 */
public class FragmentJeu2 extends Fragment {

    static ArrayList<QuestionJeu2> QUESTIONS;
    static ArrayList<String> REPONSES;

    int idImage;

    private View rootView;
    private int questionId;
    private CountDownTimer timer;

    public FragmentJeu2() {
        QUESTIONS = new ArrayList<QuestionJeu2>();
        REPONSES = new ArrayList<String>();

        this.questionId = 0;
        this.timer = new CountDownTimer(30000, 100) {
            public void onTick(long millisUntilFinished) {
                ProgressBar timeBar = (ProgressBar)FragmentJeu2.this.rootView.findViewById(R.id.jeu2TimeProgressBar);
                timeBar.setProgress(timeBar.getProgress()+1);
            }
            public void onFinish() {
                questionSuivante(null);
            }
        };
    }

    public FragmentJeu2(int previousQuestionId) {
        this.questionId = previousQuestionId + 1;
        this.timer = new CountDownTimer(30000, 100) {
            public void onTick(long millisUntilFinished) {
                ProgressBar timeBar = (ProgressBar)FragmentJeu2.this.rootView.findViewById(R.id.jeu2TimeProgressBar);
                timeBar.setProgress(timeBar.getProgress()+1);
            }
            public void onFinish() {
                questionSuivante(null);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.fragment_jeu2, container, false);

        if(QUESTIONS.size() == 0)recupererQuestions();
        else {
            initialiserTextes();
            timer.start(); //On démarre le chronomètre
        }

        Button reponse1 = (Button) this.rootView.findViewById(R.id.jeu2Reponse1);
        Button reponse2 = (Button) this.rootView.findViewById(R.id.jeu2Reponse2);
        Button reponse3 = (Button) this.rootView.findViewById(R.id.jeu2Reponse3);

        ImageView objet = (ImageView) this.rootView.findViewById(R.id.objet);
        objet.setLongClickable(true);
        objet.setClickable(true);

        View.OnTouchListener longListener = new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.print("teteziubfezfeizufeziuhfeizhfiezuhfieuzhiufezhiuezhfiuezhfiu");
                    ImageView obj = (ImageView) v;

                    View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(v);
                    myShadowBuilder.getView().setAlpha(1);

                    ClipData data = ClipData.newPlainText("", "");
                    v.startDrag(data, myShadowBuilder, obj, 0);

                    return true;
                } else {
                    return false;
                }
            }
        };

        objet.setOnTouchListener(longListener);

        appuiBouton(reponse1);
        appuiBouton(reponse2);
        appuiBouton(reponse3);

        return this.rootView;
    }

    public void appuiBouton(final Button reponse){
        reponse.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();
                TextView dropText = (TextView) v;

                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //dropText.setTextColor(Color.GREEN);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        //dropText.setTextColor(Color.RED);
                        break;

                    case DragEvent.ACTION_DROP:
                        GestionnaireSons.jouerSon(GestionnaireSons.Son.CLIC);
                        questionSuivante(reponse.getText().toString());
                        break;
                }

                return true;
            }
        });
    }


    public void questionSuivante(String response){
        timer.cancel();

        REPONSES.add(response);

        //Si il ne reste plus de questions on passe aux résultats, sinon on passe à la question suivante
        if (FragmentJeu2.this.questionId == QUESTIONS.size() - 1) {
            Intent intent = new Intent(FragmentJeu2.this.getActivity(), ResultActivity2.class);
            intent.putParcelableArrayListExtra("questionnaire", QUESTIONS);
            intent.putStringArrayListExtra("reponses", REPONSES);
            FragmentJeu2.this.getActivity().startActivity(intent);
            FragmentJeu2.this.getActivity().finish();
        } else {
            FragmentJeu2 newFragment = new FragmentJeu2(FragmentJeu2.this.questionId);
            Jeu2Activity.fragment = newFragment;
            FragmentJeu2.this.getFragmentManager().beginTransaction().replace(R.id.jeu2_container, newFragment).addToBackStack(null).commit();
        }
    }

    public void initialiserTextes(){
        ImageView objet = (ImageView) FragmentJeu2.this.rootView.findViewById(R.id.objet);
        Button reponse1 = (Button) FragmentJeu2.this.rootView.findViewById(R.id.jeu2Reponse1);
        Button reponse2 = (Button) FragmentJeu2.this.rootView.findViewById(R.id.jeu2Reponse2);
        Button reponse3 = (Button) FragmentJeu2.this.rootView.findViewById(R.id.jeu2Reponse3);
        TextView avance = (TextView) this.rootView.findViewById(R.id.jeu2AvanceTextView);

        avance.setText(getString(R.string.jeu_2_avance_1) + " " + (this.questionId + 1) + " " + getString(R.string.jeu_2_avance_2) + " " + QUESTIONS.size());

        idImage = FragmentJeu2.this.rootView.getResources().getIdentifier(QUESTIONS.get(FragmentJeu2.this.questionId).getQuestion(), "drawable", getActivity().getPackageName());
        //Toast.makeText(getActivity(), QUESTIONS.get(FragmentJeu2.this.questionId).getQuestion() + id,Toast.LENGTH_LONG).show();
        objet.setImageResource(idImage);
        placerReponseAleatoirement(new Button[]{reponse1, reponse2, reponse3}); //On réparti les réponses aléatoirement
    }


    public void placerReponseAleatoirement(Button[] reponses){
        int aleatoire;
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu2.this.questionId).getReponseV());
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu2.this.questionId).getReponseF1());
        do{
            aleatoire = (int)(Math.random() * 3);
        }while (reponses[aleatoire].getText().length() != 0);
        reponses[aleatoire].setText(QUESTIONS.get(FragmentJeu2.this.questionId).getReponseF2());
    }

    private void recupererQuestions(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://si.sebastientosello.com/TimeFixers/jeu2.xml";

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
            QuestionJeu2 question = new QuestionJeu2(parser);
            QUESTIONS.add(question);
        }

        parser.require(XmlPullParser.END_TAG, null, "quizz");
        parser.next();
        parser.require(XmlPullParser.END_DOCUMENT, null, null);
    }

    public void stopTimer(){
        this.timer.cancel();
    }

    private class MyShadowBuilder extends View.DragShadowBuilder
    {
        private Drawable shadow;

        public MyShadowBuilder(View v)
        {
            super(v);
            shadow = getResources().getDrawable(idImage);
        }

        @Override
        public void onDrawShadow(Canvas canvas)
        {
            shadow.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)
        {

            int height, width;
            height = 300;
            width = 300;

            shadow.setBounds(0,0, width, height);

            shadowSize.set(width, height);
            shadowTouchPoint.set(150, 150);
        }

    }
}
