package com.example.projet_rdvgsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new BD(this);

        bd.enregType();
    }

    public void enregistrerProClick(View view)
    {
        Intent intentAfficher = new Intent(this, EnregistrerPro.class);
        startActivity(intentAfficher);
    }

    public void prendreRDvClick(View view)
    {
        Intent intentAfficher = new Intent(this, PrendreRDV.class);
        startActivity(intentAfficher);
    }

    public void afficherPlanningClick(View view)
    {
        Intent intentAfficher = new Intent(this, Planning.class);
        startActivity(intentAfficher);
    }

    public void rechercheProClick(View view)
    {
        Intent intentAfficher = new Intent(this, RecherchePro.class);
        startActivity(intentAfficher);
    }
}