package fr.ratslab.timefixers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sebastientosello on 28/03/16.
 */
public class QuestionnaireAdapter2 extends BaseAdapter {

    private ArrayList<QuestionJeu2> questionnaire;
    private ArrayList<String> reponses;

    //Le contexte dans lequel est présent notre adapter
    private Context context;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater layoutInflater;


    public QuestionnaireAdapter2(Context context, ArrayList<QuestionJeu2> questionnaire, ArrayList<String> reponses) {
        this.context = context;
        this.questionnaire = questionnaire;
        this.reponses = reponses;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return questionnaire.size();
    }

    public Object getItem(int position) {
        return questionnaire.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "item_list_view.xml"
            layoutItem = (RelativeLayout) layoutInflater.inflate(R.layout.item_result_2_list_view, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        ImageView image = (ImageView)layoutItem.findViewById(R.id.result2Image);
        TextView goodResponse = (TextView)layoutItem.findViewById(R.id.result2GoodResponse);
        TextView response = (TextView)layoutItem.findViewById(R.id.result2Response);

        //(3) : Renseignement des valeurs
        int idImage = layoutItem.getResources().getIdentifier(this.questionnaire.get(position).getQuestion(), "drawable", context.getPackageName());
        image.setImageResource(idImage);
        goodResponse.setText(this.questionnaire.get(position).getReponseV());
        response.setText((this.reponses.get(position) == null) ? this.context.getString(R.string.result_empty) : this.reponses.get(position));

        //(4) Changement de la couleur du fond de notre item
        if (this.questionnaire.get(position).getReponseV().equals(this.reponses.get(position))) {
            response.setTextColor(Color.GREEN);
        } else {
            response.setTextColor(Color.RED);
        }
        layoutItem.setTag(position);
        //layoutItem.setOnClickListener(new OnClickListener() {
        //
        //    @Override
        //    public void onClick(View v) {
        //        Integer position = (Integer)v.getTag();
        //        sendListener(listeDiplome.get(position), position);
        //    }
        //
        //});
        //On retourne l'item créé.
        return layoutItem;
    }
}

