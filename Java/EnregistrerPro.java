package com.example.projet_rdvgsb;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EnregistrerPro extends AppCompatActivity
{
    // Déclaration des variables membres
    BD bd;
    EditText nom;
    EditText prenom;
    EditText type;
    EditText adresse;
    EditText villeCodep;
    EditText mail;
    EditText tel;
    Spinner typeSpinner;
    int idSelected;
    ArrayList<String> listeTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialisation de l'activité
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer_pro);

        // Initialisation de la base de données et des composants d'interface
        bd = new BD(this);
        nom = findViewById(R.id.EnregeditTextTextNom);
        prenom = findViewById(R.id.EnregeditTextPrenom);
        adresse = findViewById(R.id.EnregeditTextEnregType);
        villeCodep = findViewById(R.id.EnregeditTextEnregVilleCodeP);
        mail = findViewById(R.id.EnregeditTextMail);
        tel = findViewById(R.id.EnregeditTextTel);
        typeSpinner = findViewById(R.id.spinnerEnregPro);

        // Mise à jour des types au démarrage de l'activité
        majTypes();
    }

    // Méthode appelée lorsqu'un bouton est cliqué pour enregistrer un professionnel
    public void enregClic(View view)
    {
        // Appel de la méthode d'enregistrement dans la base de données
        bd.enregPro(nom.getText().toString(), prenom.getText().toString(),
                mail.getText().toString(), tel.getText().toString(), adresse.getText().toString(),
                idSelected, villeCodep.getText().toString());

        // Réinitialisation des champs de saisie
        nom.setText("");
        prenom.setText("");
        adresse.setText("");
        villeCodep.setText("");
        mail.setText("");
        tel.setText("");
    }

    // Méthode pour mettre à jour la liste des types
    public void majTypes()
    {
        try{
            // Création d'une liste de types
            listeTypes = new ArrayList<>();
            listeTypes.add("Pharmacien");
            listeTypes.add("Médecin");
            listeTypes.add("Podologue");

            // Création d'un adaptateur pour le Spinner
            ArrayAdapter<String> aaTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeTypes);
            aaTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Liaison de l'adaptateur au Spinner
            typeSpinner.setAdapter(aaTypes);

            // Gestion de la sélection d'un élément dans le Spinner
            typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    // Mise à jour de l'identifiant sélectionné
                    idSelected = i;
                }
            });
        }
        catch(Exception e)
        {
            // Gestion des exceptions (non spécifiée dans le code)
        }
    }
}
