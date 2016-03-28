package fr.ratslab.timefixers;

import android.os.Parcel;
import android.os.Parcelable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Créé par Merlet Gregory le 26/03/2016.
 * Copyright RatsLab - 2016
 */

public class QuestionJeu2 implements Parcelable {

    private String question;
    private String reponseV;
    private String reponseF1;
    private String reponseF2;

    public QuestionJeu2(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, null, "question");
        parser.next();
        this.question = parser.getText();
        parser.next();
        parser.require(XmlPullParser.END_TAG, null, "question");
        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "reponsev");
        parser.next();
        this.reponseV = parser.getText();
        parser.next();
        parser.require(XmlPullParser.END_TAG, null, "reponsev");
        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "reponsef1");
        parser.next();
        this.reponseF1 = parser.getText();
        parser.next();
        parser.require(XmlPullParser.END_TAG, null, "reponsef1");
        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "reponsef2");
        parser.next();
        this.reponseF2 = parser.getText();
        parser.next();
        parser.require(XmlPullParser.END_TAG, null, "reponsef2");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, null, "item");

    }

    public String getQuestion(){
        return this.question;
    }

    public String getReponseV(){
        return this.reponseV;
    }

    public String getReponseF1(){
        return this.reponseF1;
    }

    public String getReponseF2(){
        return this.reponseF2;
    }

    @Override
    public String toString(){
        return "QuestionJeu2{" +
                "question='" + this.question + '\'' +
                ", reponseV='" + this.reponseV + '\'' +
                ", reponseF1='" + this.reponseF1 + '\'' +
                ", reponseF2='" + this.reponseF2 + '\'' +
                '}';
    }

    public QuestionJeu2(Parcel in) {
        this.question = in.readString();
        this.reponseV = in.readString();
        this.reponseF1 = in.readString();
        this.reponseF2 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(reponseV);
        dest.writeString(reponseF1);
        dest.writeString(reponseF2);
    }

    public static final Parcelable.Creator<QuestionJeu2> CREATOR = new Parcelable.Creator<QuestionJeu2>()
    {
        @Override
        public QuestionJeu2 createFromParcel(Parcel source)
        {
            return new QuestionJeu2(source);
        }

        @Override
        public QuestionJeu2[] newArray(int size)
        {
            return new QuestionJeu2[size];
        }
    };

    public static Parcelable.Creator<QuestionJeu2> getCreator()
    {
        return CREATOR;
    }
}
