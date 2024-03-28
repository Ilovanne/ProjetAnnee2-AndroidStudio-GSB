package com.example.projet_rdvgsb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecherchePro extends AppCompatActivity
{
    // Déclaration des variables membres
    TextView codevilleP;
    BD bd;
    TextView Nom;
    TextView Prenom;
    TextView villecodeP;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialisation de l'activité
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_recherche_pro);

        // Initialisation de la base de données
        bd = new BD(this);

        // Liaison des variables avec les composants d'interface
        codevilleP = findViewById(R.id.editTextRecherProCodeP);
        Nom = findViewById(R.id.textView21AffiNom);
        Prenom = findViewById(R.id.textView22AffiPren);
        villecodeP = findViewById(R.id.textView23AAffiType);

        // Mise à jour des professionnels au démarrage de l'activité
        majPro();
    }

    // Méthode pour mettre à jour l'affichage des professionnels
    public void majPro()
    {
        try
        {
            // Récupération de toutes les données des professionnels
            Cursor donnee = bd.getAllDataPro();
            String prenom = "";
            String nom = "";
            String villecode = "";

            // Parcours du résultat de la requête
            while (donnee.moveToNext())
            {
                nom += donnee.getString(1) + "\n";
                prenom += donnee.getString(2) + "\n";
                villecode += donnee.getString(6) + "\n";
            }

            // Affichage des données dans les TextView correspondants
            Nom.setText(nom);
            Prenom.setText(prenom);
            villecodeP.setText(villecode);
        }
        catch (Exception e)
        {
            // Gestion des exceptions (non spécifiée dans le code)
        }
    }

    // Méthode pour afficher les professionnels en fonction du code ville
    public void Affic(String codeville)
    {
        try
        {
            // Réinitialisation des TextView
            Nom.setText("");
            Prenom.setText("");
            villecodeP.setText("");

            // Récupération des données des professionnels en fonction du code ville
            Cursor donnee = bd.selectionnerPro(codeville);
            String prenom = "";
            String nom = "";
            String villecode = "";

            // Parcours du résultat de la requête
            while (donnee.moveToNext())
            {
                nom += donnee.getString(1) + "\n";
                prenom += donnee.getString(2) + "\n";
                villecode += donnee.getString(6) + "\n";
            }

            // Affichage des données dans les TextView correspondants
            Nom.setText(nom);
            Prenom.setText(prenom);
            villecodeP.setText(villecode);
        }
        catch (Exception e)
        {
            // Gestion des exceptions (non spécifiée dans le code)
        }
    }

    // Méthode appelée lorsqu'un bouton est cliqué
    public void clickBtn(View view)
    {
        // Appel de la méthode Affic avec le code ville entré par l'utilisateur
        Affic(codevilleP.getText().toString());
        // Réinitialisation de la zones de saisie
        codevilleP.setText("");
    }
}
