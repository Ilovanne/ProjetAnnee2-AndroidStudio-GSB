package com.example.projet_rdvgsb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Planning extends AppCompatActivity
{
    // Déclaration des variables membres
    EditText Planing;
    BD bd;
    String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialisation de l'activité

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_planning);

        // Liaison de la variable Planing avec le composant d'interface editTextTextMultiLinePlanning


        try{
            Planing = findViewById(R.id.editTextTextMultiLinePlanning);
            bd = new BD(this);
            // Récupération de tous les rendez-vous depuis la base de données
            Cursor data = bd.getAllDataRDV();
            ArrayList<String> listePlanning = new ArrayList<>();
            String planning = "";

            // Parcours du résultat de la requête
            while (data.moveToNext())
            {
                // Construction d'une chaîne de caractères représentant le rendez-vous
                planning += data.getString(1) + " " + data.getString(2) + "h -" + data.getString(3) + "h avec : " + bd.recherchProfromId(data.getInt(5)) + "\n";
            }

            // Affichage du planning dans le composant d'interface
            Planing.setText(planning);
        }
        catch(Exception e)
        {
            // En cas d'erreur, affichage du message d'erreur dans le composant d'interface
            Planing.setText(e.getMessage());
        }
    }
}
