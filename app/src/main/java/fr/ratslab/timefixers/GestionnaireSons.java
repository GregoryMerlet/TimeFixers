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

    private static Context CONTEXT;
    private static SoundPool SOUNDPOOL = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    private static MediaPlayer MEDIAPLAYER;
    private static float SOUND_VOLUME;
    private static float MUSIC_VOLUME;
    private static HashMap<String, Integer> SONS = new HashMap<>();
    private static HashMap<String, Integer> MUSIQUES = new HashMap<>();

    public static void initGestionnaireSons(Context context){
        CONTEXT = context;
        SOUND_VOLUME = 0.5f;
        MUSIC_VOLUME = 0.5f;
        initialiserSons();
        initialiserMusiques();

        jouerMusique("musique1", true);
    }

    private static void initialiserSons(){
        SONS.put("tresBien", SOUNDPOOL.load(CONTEXT, R.raw.bien, 1));
        SONS.put("clic", SOUNDPOOL.load(CONTEXT, R.raw.clic, 1));
    }

    private static void initialiserMusiques(){
        MUSIQUES.put("musique1", R.raw.music1);
        MUSIQUES.put("musique2", R.raw.music2);
        MUSIQUES.put("musique3", R.raw.music3);
    }

    public static void setSoundVolume(float valeur){
        SOUND_VOLUME = valeur;
    }

    public static float getSoundVolume(){
        return SOUND_VOLUME;
    }

    public static void setMusicVolume(float valeur){
        MUSIC_VOLUME = valeur;
        MEDIAPLAYER.setVolume(MUSIC_VOLUME, MUSIC_VOLUME);
    }

    public static float getMusicVolume(){
        return MUSIC_VOLUME;
    }

    public static void jouerSon(String name){
        SOUNDPOOL.play(SONS.get(name), SOUND_VOLUME, SOUND_VOLUME, 0, 0, 1.0f);
    }

    public static void jouerMusique(String name, boolean loop){
        if(MEDIAPLAYER != null)MEDIAPLAYER.stop();
        MEDIAPLAYER = MediaPlayer.create(CONTEXT, MUSIQUES.get(name));
        MEDIAPLAYER.setVolume(MUSIC_VOLUME, MUSIC_VOLUME);
        MEDIAPLAYER.setLooping(loop);
        MEDIAPLAYER.start();
    }
}
