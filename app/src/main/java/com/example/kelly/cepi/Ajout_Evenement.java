package com.example.kelly.cepi;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Objets.Dossier;
import Objets.Evenement;
import Objets.Utilisateur;


/**
 * Created by Kelly on 14/03/2018.
 */


public class Ajout_Evenement extends Activity {

    Button definir_date = null;
    Button definir_horaire = null;
    Button valider = null;
    TextView date = null;
    TextView horaire = null;
    EditText nom_evenement = null;
    String nomS;
    int annee;
    int mois;
    int jour;
    int heure;
    int minute;
    Spinner liste_rappels = null;
    int rappel = 0;
    Intent i_evenement = getIntent();
    Utilisateur U1 = (Utilisateur) i_evenement.getSerializableExtra("utilisateur");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_evenement);

        definir_date = findViewById(R.id.ButtonDefinirDate);
        definir_date.setOnClickListener(DefinirDateListener);

        definir_horaire = findViewById(R.id.ButtonDefinirHoraire);
        definir_horaire.setOnClickListener(DefinirHoraireListener);

        valider = findViewById(R.id.ButtonValiderEvenement);


        date = findViewById(R.id.TextViewDate);
        horaire = findViewById(R.id.TextViewHeure);

        nom_evenement = findViewById(R.id.EditTextNomEvenement);

        liste_rappels = (Spinner) findViewById(R.id.ChoixRappel);
        List<String> choix_rappel = new ArrayList<String>();
        choix_rappel.add("Jamais");
        choix_rappel.add("Chaque jour");
        choix_rappel.add("Chaque semaine");
        choix_rappel.add("Chaque mois");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choix_rappel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste_rappels.setAdapter(adapter);
        liste_rappels.setOnItemSelectedListener(ChoixRappelListener);

        if(i_evenement.getIntExtra("consultation",0) == 1){
            affichage_consultation();
        }
        else{
            valider.setOnClickListener(ValiderEvenementListener);
        }
    }

    public void affichage_consultation(){
        int idd = i_evenement.getIntExtra("idd",0);
        int ide = i_evenement.getIntExtra("ide",0);
        valider.setOnClickListener(ModifierEvenementListener);
        int i = 0;
        while(i<U1.get_dossiers().size() & U1.get_dossiers().get(i).get_idd() != idd){
            i++;
        }
        Dossier D1 = U1.get_dossiers().get(i);
        i = 0;
        while(i<D1.get_evenements().size() & D1.get_evenements().get(i).get_ide() != ide){
            i++;
        }
        Evenement E1 = D1.get_evenements().get(i);
        nom_evenement.setText(E1.get_nom_ev());
        int annee = E1.get_date_heure().get(Calendar.YEAR);
        int mois = E1.get_date_heure().get(Calendar.MONTH) ;
        int jour = E1.get_date_heure().get(Calendar.DAY_OF_MONTH);//nom dossier
        date.setText(String.valueOf(jour) + '/' + String.valueOf(mois+1) + '/' + String.valueOf(annee));
        int heure = E1.get_date_heure().get(Calendar.HOUR_OF_DAY);
        int minute = E1.get_date_heure().get(Calendar.MINUTE);
        horaire.setText(String.valueOf(heure) + " : " + String.valueOf(minute));
        //rappel
        //dossier
    }


    public View.OnClickListener ModifierEvenementListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(Ajout_Evenement.this,"Modification",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(Ajout_Evenement.this, Page_Principale.class);
            startActivity(i1);
        }
    };


    public AdapterView.OnItemSelectedListener ChoixRappelListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            rappel = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };



    public View.OnClickListener DefinirDateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment FragmentDate = new DatePickerFragment();
            FragmentDate.show(getFragmentManager(),"DatePicker");
        }
    };

    public View.OnClickListener DefinirHoraireListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment FragmentHeure = new TimePickerFragment();
            FragmentHeure.show(getFragmentManager(),"TimePicker");
        }
    };

    public View.OnClickListener ValiderEvenementListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nomS = nom_evenement.getText().toString();
            /*String date_s = String.valueOf(date);
            String[] parties = date_s.split("/");
            jour = Integer.parseInt(parties[0]);
            mois = Integer.parseInt(parties[1]);
            annee = Integer.parseInt(parties[2]);

            String horaire_s = String.valueOf(horaire);
            String[] parts = horaire_s.split(":");
            heure = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parties[1]);

            Penser à récupérer le choix de rappel qui est dans l'entier "rappel"
             */

            //Enregistrer l'évènement*/
            Toast.makeText(Ajout_Evenement.this,"Enregistré",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(Ajout_Evenement.this, Page_Principale.class);
            startActivity(i1);
        }
    };

}