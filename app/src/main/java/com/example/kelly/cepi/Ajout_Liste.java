package com.example.kelly.cepi;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objets.Dossier;
import Objets.Liste;
import Objets.Utilisateur;

/**
 * Created by Kelly on 20/03/2018.
 */

public class Ajout_Liste extends Activity {

    EditText nom_liste =null;
    List<String> mliste = new ArrayList<String>();
    ListView liste = null;
    EditText nouvel_element = null;
    ArrayAdapter<String> adapter = null;
    Button valider = null;
    Intent i_liste = getIntent();
    Utilisateur U1 = (Utilisateur) i_liste.getSerializableExtra("utilisateur");

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_liste);

        valider = findViewById(R.id.ButtonAjouterListe);

        nom_liste = findViewById(R.id.EditTextNomListe);
        //mliste.add("Lait");
        //mliste.add("Oeufs");

        liste = (ListView) findViewById(R.id.ListView);
        adapter = new ArrayAdapter<String>(Ajout_Liste.this,android.R.layout.simple_list_item_multiple_choice,mliste);
        liste.setAdapter(adapter);

        nouvel_element = (EditText) findViewById(R.id.EditTextNouvelElement);
        nouvel_element.setOnKeyListener(Appuye_entree);

        registerForContextMenu(liste);
        liste.setOnLongClickListener(OuvrirMenu);

        if(i_liste.getIntExtra("consultation",0) == 1){
            affichage_consultation();
        }
        else{
            valider.setOnClickListener(ValiderListeListener);
        }
    }

    public void affichage_consultation(){
        int idd = i_liste.getIntExtra("idd",0);
        int idl = i_liste.getIntExtra("idl",0);
        valider.setOnClickListener(ModifierListeListener);
        int i = 0;
        while(i<U1.get_dossiers().size() & U1.get_dossiers().get(i).get_idd() != idd){
            i++;
        }
        Dossier D1 = U1.get_dossiers().get(i);
        i = 0;
        while(i<D1.get_listes().size() & D1.get_listes().get(i).get_idl() != idl){
            i++;
        }
        Liste L1 = D1.get_listes().get(i);
        nom_liste.setText(L1.get_nom_liste());
        //la liste
        //cochés/décochés

    }

    public View.OnClickListener ModifierListeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(Ajout_Liste.this,"Modification",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(Ajout_Liste.this, Page_Principale.class);
            startActivity(i1);
        }
    };

    public View.OnKeyListener Appuye_entree = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                mliste.add(nouvel_element.getText().toString());
                nouvel_element.setText("");
                return true;
            }
            return false;
        }
    };

    View.OnClickListener ValiderListeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent ivalider = new Intent(Ajout_Liste.this, Page_Principale.class);
            startActivity(ivalider);
        }
    };

public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.contextmenu, menu);
    menu.setHeaderTitle("Choose an option");
}

public boolean onContextItemSelected(MenuItem item){
    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    // On récupère la position de l'item concerné
    int item_liste_pos = info.position;
    switch(item.getItemId()){
        case R.id.Modifier:
            Toast.makeText(this,"CA FAIT RIEN POUR L'INSTANT", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.Supprimer:
            mliste.remove(item_liste_pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"Item supprimé", Toast.LENGTH_SHORT).show();
            return true;
    }
    return super.onContextItemSelected(item);
}

public View.OnLongClickListener OuvrirMenu = new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        openContextMenu(view);
        return false;
    }
};
}
