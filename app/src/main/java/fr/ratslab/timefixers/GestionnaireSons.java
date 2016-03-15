package fr.ratslab.timefixers;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Créé par Merlet Gregory le 14/03/2016.
 * Copyright RatsLab - 2016
 */

public class GestionnaireSons{

    private static Context CONTEXT; //Le contexte de l'application
    private static SoundPool SOUNDPOOL = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); //Permet de jouer des sons
    private static MediaPlayer MEDIAPLAYER; //Permet de jouer des musiques
    private static float SOUND_VOLUME; //Le volume du son
    private static float MUSIC_VOLUME; //Le volume de la musique
    private static HashMap<String, Integer> SONS = new HashMap<>(); //Stock tous les sons en les associants à un nom
    private static HashMap<String, Integer> MUSIQUES = new HashMap<>(); //Stock toutes les musiques en les associants à un nom


    /**
     * Initialise le gestionaire de sons.
     * @param context   Contexte de l'application
     */
    public static void initGestionnaireSons(Context context){
        CONTEXT = context;
        SOUND_VOLUME = 0.5f;
        MUSIC_VOLUME = 0.5f;
        initialiserSons();
        initialiserMusiques();
    }

    /**
     * Associe chaque son à un nom.
     */
    private static void initialiserSons(){
        SONS.put("tresBien", SOUNDPOOL.load(CONTEXT, R.raw.bien, 1));
        SONS.put("clic", SOUNDPOOL.load(CONTEXT, R.raw.clic, 1));
    }

    /**
     * Associe chaque musique à un nom.
     */
    private static void initialiserMusiques(){
        MUSIQUES.put("musique1", R.raw.music1);
        MUSIQUES.put("musique2", R.raw.music2);
        MUSIQUES.put("musique3", R.raw.music3);
    }

    /**
     * Change le volume des sons.
     * @param valeur   Valeur du nouveau volume
     */
    public static void setSoundVolume(float valeur){
        SOUND_VOLUME = valeur;
    }

    /**
     * Retourne la valeur actuelle du volume.
     * @return la valeur actuelle du volume
     */
    public static float getSoundVolume(){
        return SOUND_VOLUME;
    }

    /**
     * Change le volume de la musique.
     * @param valeur   Valeur du nouveau volume
     */
    public static void setMusicVolume(float valeur){
        MUSIC_VOLUME = valeur;
        MEDIAPLAYER.setVolume(MUSIC_VOLUME, MUSIC_VOLUME);
    }

    /**
     * Retourne la valeur actuelle du volume.
     * @return la valeur actuelle du volume
     */
    public static float getMusicVolume(){
        return MUSIC_VOLUME;
    }

    /**
     * Joue un son.
     * @param name   Le nom associé au son à jouer
     */
    public static void jouerSon(String name){
        SOUNDPOOL.play(SONS.get(name), SOUND_VOLUME, SOUND_VOLUME, 0, 0, 1.0f);
    }

    /**
     * Joue une musique.
     * @param name   Le nom associé à la musique à jouer
     * @param loop Si la musique doit être joué en boucle
     */
    public static void jouerMusique(String name, boolean loop){
        if(MEDIAPLAYER != null)MEDIAPLAYER.stop();
        MEDIAPLAYER = MediaPlayer.create(CONTEXT, MUSIQUES.get(name));
        MEDIAPLAYER.setVolume(MUSIC_VOLUME, MUSIC_VOLUME);
        MEDIAPLAYER.setLooping(loop);
        MEDIAPLAYER.start();
    }
}
