package fr.ratslab.timefixers;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.util.ArrayList;

public class FragmentHistoire extends Fragment {

    static ArrayList<Integer> IMAGESHISTOIRE = new ArrayList<Integer>();

    private int imageId;
    private int fragmentId;
    private View rootView;
    private CountDownTimer timer;

    public FragmentHistoire() {
        initialiserImages();

        this.fragmentId = 0;
        this.imageId = IMAGESHISTOIRE.get(fragmentId);
        this.timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                final ImageView imageView = (ImageView) rootView.findViewById(R.id.histoireFragmentImg);
                Animation disparitionFondu = AnimationUtils.loadAnimation(FragmentHistoire.this.getActivity(), R.anim.disparition_fondu);
                imageView.startAnimation(disparitionFondu);
                //Pour attendre la fin de l'animation de disparition
                disparitionFondu.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation a) {}
                    public void onAnimationRepeat(Animation a) {}
                    public void onAnimationEnd(Animation a) {
                        fragmentSuivant(imageView);
                    }
                });
            }
        };
    }

    public FragmentHistoire(int previousFragmentId) {
        initialiserImages();

        this.fragmentId = previousFragmentId + 1;
        this.imageId = IMAGESHISTOIRE.get(fragmentId);
        this.timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                final ImageView imageView = (ImageView) rootView.findViewById(R.id.histoireFragmentImg);
                Animation disparitionFondu = AnimationUtils.loadAnimation(FragmentHistoire.this.getActivity(), R.anim.disparition_fondu);
                imageView.startAnimation(disparitionFondu);
                //Pour attendre la fin de l'animation de disparition
                disparitionFondu.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation a) {}
                    public void onAnimationRepeat(Animation a) {}
                    public void onAnimationEnd(Animation a) {
                        fragmentSuivant(imageView);
                    }
                });
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_histoire, container, false);

        Button skipButton = (Button) rootView.findViewById(R.id.histoireFragmentSkipButton);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.histoireFragmentImg);

        timer.start(); //On démarre le timer

        imageView.setImageResource(this.imageId);
        Animation apparitionFondu = AnimationUtils.loadAnimation(FragmentHistoire.this.getActivity(), R.anim.apparition_fondu);
        imageView.startAnimation(apparitionFondu);

        appuiBoutonPasser(skipButton, imageView);
        appuiImage(imageView);

        return rootView;
    }

    public void appuiImage(final ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                fragmentSuivant(imageView);
            }
        });
    }

    public void appuiBoutonPasser(Button skipButton, final ImageView imageView){
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();

                Intent intent = new Intent(FragmentHistoire.this.getActivity(), MenuActivity.class);
                FragmentHistoire.this.getActivity().startActivity(intent);
                FragmentHistoire.this.getActivity().finish();
            }
        });
    }

    private void initialiserImages(){
        if(IMAGESHISTOIRE.size() == 0){
            IMAGESHISTOIRE.add(R.drawable.ratslab);
            IMAGESHISTOIRE.add(R.drawable.histoire_1);
            IMAGESHISTOIRE.add(R.drawable.histoire_2);
            IMAGESHISTOIRE.add(R.drawable.histoire_3);
        }
    }

    private void fragmentSuivant(ImageView imageView){
        timer.cancel();
        imageView.setVisibility(View.INVISIBLE);
        //Si il ne reste plus d'images pour l'histoire on passe au menu, sinon on passe à l'image suivante
        if(FragmentHistoire.this.fragmentId == IMAGESHISTOIRE.size()-1){
            Intent intent = new Intent(FragmentHistoire.this.getActivity(), MenuActivity.class);
            FragmentHistoire.this.getActivity().startActivity(intent);
            FragmentHistoire.this.getActivity().finish();
        } else {
            FragmentHistoire.this.getFragmentManager().beginTransaction().replace(R.id.histoire_container, new FragmentHistoire(FragmentHistoire.this.fragmentId)).addToBackStack(null).commit();
        }
    }
}