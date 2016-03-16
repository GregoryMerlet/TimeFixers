package fr.ratslab.timefixers;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class Fragment_histoire extends Fragment {

    static ArrayList<Integer> IMAGESHISTOIRE = new ArrayList<Integer>();

    private int imageId;
    private int fragmentId;
    private int elapsedTime;

    public Fragment_histoire() {
        initialiserImages();

        this.fragmentId = 0;
        this.imageId = IMAGESHISTOIRE.get(fragmentId);
        this.elapsedTime = 0;
    }

    public Fragment_histoire(int previousFragmentId) {
        initialiserImages();

        this.fragmentId = previousFragmentId + 1;
        this.imageId = IMAGESHISTOIRE.get(fragmentId);
        this.elapsedTime = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_histoire, container, false);

        Chronometer chronometer = (Chronometer) rootView.findViewById(R.id.histoireFragmentChronometer);
        Button skipButton = (Button) rootView.findViewById(R.id.histoireFragmentSkipButton);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.histoireFragmentImg);

        chronometer.start(); //On démarre le chronomètre

        imageView.setImageResource(this.imageId);
        Animation apparitionFondu = AnimationUtils.loadAnimation(Fragment_histoire.this.getActivity(), R.anim.apparition_fondu);
        imageView.startAnimation(apparitionFondu);

        appuiBoutonPasser(skipButton, chronometer, imageView);
        appuiImage(chronometer, imageView);
        verifierTempsChronometre(chronometer, imageView);

        return rootView;
    }

    public void verifierTempsChronometre(Chronometer chronometer, final ImageView imageView){
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(final Chronometer chronometer) {
                if (Fragment_histoire.this.elapsedTime >= 5) {
                    chronometer.stop();

                    Animation disparitionFondu = AnimationUtils.loadAnimation(Fragment_histoire.this.getActivity(), R.anim.disparition_fondu);
                    imageView.startAnimation(disparitionFondu);
                    //Pour attendre la fin de l'animation de disparition
                    disparitionFondu.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationStart(Animation a) {}
                        public void onAnimationRepeat(Animation a) {}
                        public void onAnimationEnd(Animation a) {
                            fragmentSuivant(chronometer, imageView);
                        }
                    });
                } else {
                    Fragment_histoire.this.elapsedTime++;
                }
            }
        });
    }

    public void appuiImage(final Chronometer chronometer, final ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                fragmentSuivant(chronometer, imageView);
            }
        });
    }

    public void appuiBoutonPasser(Button skipButton, final Chronometer chronometer, final ImageView imageView){
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();

                Intent intent = new Intent(Fragment_histoire.this.getActivity(), MenuActivity.class);
                Fragment_histoire.this.getActivity().startActivity(intent);
                Fragment_histoire.this.getActivity().finish();
            }
        });
    }

    private void initialiserImages(){
        if(IMAGESHISTOIRE.size() == 0){
            IMAGESHISTOIRE.add(R.drawable.histoire_1);
            IMAGESHISTOIRE.add(R.drawable.histoire_2);
            IMAGESHISTOIRE.add(R.drawable.histoire_3);
        }
    }

    private void fragmentSuivant(Chronometer chronometer, ImageView imageView){
        imageView.setVisibility(View.INVISIBLE);
        //Si il ne reste plus d'images pour l'histoire on passe au menu, sinon on passe à l'image suivante
        if(Fragment_histoire.this.fragmentId == IMAGESHISTOIRE.size()-1){
            Intent intent = new Intent(Fragment_histoire.this.getActivity(), MenuActivity.class);
            Fragment_histoire.this.getActivity().startActivity(intent);
            Fragment_histoire.this.getActivity().finish();
        } else {
            Fragment_histoire.this.getFragmentManager().beginTransaction().replace(R.id.histoire_container, new Fragment_histoire(Fragment_histoire.this.fragmentId)).addToBackStack(null).commit();
        }
    }
}