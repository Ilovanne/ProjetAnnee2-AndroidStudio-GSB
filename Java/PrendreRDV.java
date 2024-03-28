package com.example.projet_rdvgsb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrendreRDV extends AppCompatActivity
{
    // Déclaration des variables membres
    BD bd;
    TextView heureDeb;
    TextView heureFin;
    CalendarView date;
    Spinner nompreP;
    TextView motif;
    String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialisation de l'activité
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre_rdv);

        // Initialisation de la base de données et des composants d'interface
        bd = new BD(this);
        heureDeb = findViewById(R.id.editTextHeureDeb);
        heureFin = findViewById(R.id.editTextHeureFin);
        date = findViewById(R.id.calendarViewDate);
        nompreP = findViewById(R.id.spinnerChoisirType);
        motif = findViewById(R.id.editTextMultiLineMotifRDV);

        // Gestion de la sélection de la date dans le CalendarView
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int annee, int mois, int jours)
            {
                curDate = String.valueOf(jours) + "/" + String.valueOf(mois) + "/" + String.valueOf(annee);
            }
        });

        // Mise à jour des professionnels au démarrage de l'activité
        majPro();
    }

    // Méthode appelée lorsqu'un bouton est cliqué pour enregistrer un RDV
    public void clickEnregRDV(View view)
    {
        // Récupération de tous les rendez-vous
        Cursor data = bd.getAllDataRDV();
        ArrayList<String> listePlanning = new ArrayList<>();
        boolean trouve = false;

        // Parcours des rendez-vous
        while (data.moveToNext())
        {
            // Vérification si l'heure et la date du RDV correspondent à un existant
            if (heureDeb.getText().equals(data.getString(2)) && curDate.equals(data.getString(1)))
            {
                // Affichage d'un message d'erreur
                Toast.makeText(getApplicationContext(), "Erreur horaire", Toast.LENGTH_SHORT).show();
                trouve = true;
            }

            // Si pas de conflit horaire, enregistrement du RDV
            if (!trouve)
            {
                bd.enregRDV(curDate, Integer.parseInt(heureDeb.getText().toString()),
                        Integer.parseInt(heureFin.getText().toString()),
                        motif.getText().toString(),
                        Integer.parseInt(nompreP.getSelectedItem().toString().split(" ")[0]));
            }

            // Réinitialisation des zones de saisie
            heureDeb.setText("");
            heureFin.setText("");
            motif.setText("");
        }
    }

    // Méthode pour mettre à jour la liste des professionnels dans le Spinner
    public void majPro()
    {
        try{
            // Récupération de tous les professionnels
            Cursor data = bd.getAllDataPro();
            ArrayList<String> listePro = new ArrayList<>();

            // Parcours des professionnels et ajout à la liste
            while (data.moveToNext())
            {
                listePro.add(data.getInt(0) + " " + data.getString(1) + " " + data.getString(2));
            }

            // Création de l'adaptateur pour le Spinner
            ArrayAdapter<String> aaPro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listePro);
            aaPro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Liaison de l'adaptateur au Spinner
            nompreP.setAdapter(aaPro);
        }
        catch(Exception e)
        {
            // Gestion des exceptions (non spécifiée dans le code)
        }
    }
}
