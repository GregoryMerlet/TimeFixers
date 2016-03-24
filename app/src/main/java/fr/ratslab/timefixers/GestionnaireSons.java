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

    /**
     * Initialise le gestionaire de sons.
     * @param context   Contexte de l'application
     */
    public static void initGestionnaireSons(Context context){
        CONTEXT = context;
        SOUND_VOLUME = 0.5f;
        MUSIC_VOLUME = 0.5f;
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
     * @param son   Le son à jouer
     */
    public static void jouerSon(Son son){
        SOUNDPOOL.play(son.getId(), SOUND_VOLUME, SOUND_VOLUME, 0, 0, 1.0f);
    }

    /**
     * Joue une musique.
     * @param musique   La musique à jouer
     * @param loop Si la musique doit être joué en boucle
     */
    public static void jouerMusique(Musique musique, boolean loop){
        if(MEDIAPLAYER != null)MEDIAPLAYER.stop();
        MEDIAPLAYER = MediaPlayer.create(CONTEXT, musique.getId());
        MEDIAPLAYER.setVolume(MUSIC_VOLUME, MUSIC_VOLUME);
        MEDIAPLAYER.setLooping(loop);
        MEDIAPLAYER.start();
    }

    /**
     * Arrete la musique.
     */
    public static void arreterMusique(){
        MEDIAPLAYER.stop();
    }

    //enum associant les sons à un nom
    public enum Son {
        TRESBIEN ("tresBien", SOUNDPOOL.load(CONTEXT, R.raw.bien, 1)),
        CLIC ("clic", SOUNDPOOL.load(CONTEXT, R.raw.clic, 1));

        private String name = "";
        private int id = 0;

        //Constructeur
        Son(String name, int id){
            this.name = name;
            this.id = id;
        }

        //Accesseurs
        public String getName(){
            return this.name;
        }

        public int getId(){
            return this.id;
        }

        public String toString(){
            return this.name;
        }
    }

    //enum associant les musiques à un nom
    public enum Musique {
        MUSIQUE1 ("musique1", R.raw.music1),
        MUSIQUE2 ("musique2", R.raw.music2),
        MUSIQUE3 ("musique3", R.raw.music3);

        private String name = "";
        private int id = 0;

        //Constructeur
        Musique(String name, int id){
            this.name = name;
            this.id = id;
        }

        //Accesseurs
        public String getName(){
            return this.name;
        }

        public int getId(){
            return this.id;
        }

        public String toString(){
            return this.name;
        }
    }
}
