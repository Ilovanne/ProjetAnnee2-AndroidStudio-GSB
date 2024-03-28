package com.example.projet_rdvgsb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Time;

/**
 * Classe qui étend SQLiteOpenHelper pour gérer la base de données.
 */
public class BD extends SQLiteOpenHelper {
    // Définition des noms de la base de données et des tables
    public static final String DATABASE_NAME = "RDVVendeur.db";
    public static final String TABLE_NAME = "professionnels";
    public static final String idP = "id_professionnel";
    public static final String nomP = "nom";
    public static final String prenomP = "prenom";
    public static final String emailP = "email";
    public static final String telP = "tel";
    public static final String adreseP = "adresse";
    public static final String villecodeP = "ville_codep";
    public static final String IDType = "id_type";

    public static final String TABLE_NAME2 = "types";
    public static final String idT = "id_type";
    public static final String nomT = "nom";

    public static final String TABLE_NAME3 = "RDV";
    public static final String idR = "id_rdv";
    public static final String dateRDV = "date_RDV";
    public static final String heuredeDeb = "heure_Deb";
    public static final String heuredeFin = "heure_Fin";
    public static final String motifR = "motif";
    public static final String IDPro = "id_professionnel";

    /**
     * Constructeur de la classe.
     *
     * @param context Le contexte de l'application.
     */
    public BD(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Méthode appelée lors de la création de la base de données.
     *
     * @param db La base de données SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables avec les champs correspondants
        db.execSQL("CREATE table " + TABLE_NAME + "(id_professionnel INTEGER PRIMARY KEY AUTOINCREMENT, nom  TEXT,prenom  TEXT,email  TEXT,tel  TEXT,adresse  TEXT,ville_codep  TEXT,id_type INTEGER NOT NULL,FOREIGN KEY(id_type) REFERENCES types(id_type))");
        db.execSQL("CREATE table " + TABLE_NAME2 + "(id_type INTEGER PRIMARY KEY AUTOINCREMENT,nom  TEXT)");
        db.execSQL("CREATE table " + TABLE_NAME3 + "(id_rdv INTEGER PRIMARY KEY AUTOINCREMENT, date_RDV TEXT, heure_Deb INT, heure_Fin INT,motif TEXT,  id_professionnel INTEGER NOT NULL, FOREIGN KEY(id_professionnel) REFERENCES professionnels(id_professionnel))");
    }

    /**
     * Méthode appelée lors de la mise à niveau de la base de données.
     *
     * @param db         La base de données SQLite.
     * @param oldVersion Ancienne version de la base de données.
     * @param newVersion Nouvelle version de la base de données.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression des tables existantes et recréation
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    /**
     * Méthode pour obtenir toutes les données des professionnels.
     *
     * @return Un objet Cursor contenant les données des professionnels.
     */
    public Cursor getAllDataPro() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return result;
    }

    /**
     * Méthode pour enregistrer un professionnel.
     *
     * @param nom      Le nom du professionnel.
     * @param prenom   Le prénom du professionnel.
     * @param email    L'adresse e-mail du professionnel.
     * @param tel      Le numéro de téléphone du professionnel.
     * @param adresse  L'adresse du professionnel.
     * @param type     L'identifiant du type du professionnel.
     * @param villecode Le code postal de la ville du professionnel.
     */
    public void enregPro(String nom, String prenom, String email, String tel, String adresse, int type, String villecode) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(nomP, nom);
        contentValues.put(prenomP, prenom);
        contentValues.put(emailP, email);
        contentValues.put(telP, tel);
        contentValues.put(adreseP, adresse);
        contentValues.put(IDType, type);
        contentValues.put(villecodeP, villecode);
        bd.insert(TABLE_NAME, null, contentValues);
        bd.close();
    }

    /**
     * Méthode pour enregistrer un rendez-vous.
     *
     * @param date_RDV   La date du rendez-vous.
     * @param heureDeb   L'heure de début du rendez-vous.
     * @param heureFin   L'heure de fin du rendez-vous.
     * @param motifRDV   Le motif du rendez-vous.
     * @param IDProfe    L'identifiant du professionnel associé au rendez-vous.
     */
    public void enregRDV(String date_RDV, int heureDeb, int heureFin, String motifRDV, int IDProfe) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dateRDV, date_RDV);
        contentValues.put(heuredeDeb, heureDeb);
        contentValues.put(heuredeFin, heureFin);
        contentValues.put(motifR, motifRDV);
        contentValues.put(IDPro, IDProfe);
        bd.insert(TABLE_NAME3, null, contentValues);
        bd.close();
    }

    /**
     * Méthode pour obtenir toutes les données des rendez-vous.
     *
     * @return Un objet Cursor contenant les données des rendez-vous.
     */
    public Cursor getAllDataRDV() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor Plan = bd.rawQuery("SELECT * FROM RDV GROUP BY date_RDV ORDER BY date_RDV", null);

        return Plan;
    }

    /**
     * Méthode pour obtenir toutes les données des types.
     *
     * @return Un objet Cursor contenant les noms des types.
     */
    public Cursor getAllTypes() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT nom FROM types", null);
        bd.close();
        return result;
    }


    /**
     * Méthode pour enregistrer des types dans la table 'types'.
     */
    public void enregType() {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(nomT, "Pharmacien");
        bd.insert(TABLE_NAME2, null, contentValues);

        contentValues.put(nomT, "Medecien");
        bd.insert(TABLE_NAME2, null, contentValues);

        contentValues.put(nomT, "Chirugien");
        bd.insert(TABLE_NAME2, null, contentValues);
        bd.close();
    }

    /**
     * Méthode pour sélectionner des professionnels par code postal.
     *
     * @param codeVilleP Le code postal de la ville.
     * @return Un objet Cursor contenant les données des professionnels sélectionnés.
     */
    public Cursor selectionnerPro(String codeVilleP) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ville_codep LIKE '%" + codeVilleP + "%'", null);

        return result;
    }

    /**
     * Méthode pour obtenir des rendez-vous par date.
     *
     * @param date La date des rendez-vous.
     * @return Un objet Cursor contenant les données des rendez-vous pour la date spécifiée.
     */
    public Cursor getRDVByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME3 + " WHERE " + dateRDV + " = ?";
        return db.rawQuery(query, new String[]{date});
    }

    /**
     * Méthode pour rechercher un professionnel à partir de son ID.
     *
     * @param id L'identifiant du professionnel.
     * @return Le nom et le prénom du professionnel s'il est trouvé, sinon null.
     */
    public String recherchProfromId(int id) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT nom, prenom FROM " + TABLE_NAME + " WHERE id_professionnel = + '" + id + "'", null);
        if (result.moveToFirst()) {
            return result.getString(0) + " " + result.getString(1);
        }

        return null;
    }

    /**
     * Méthode pour supprimer toutes les données de la table 'professionnels'.
     */
    public void supprTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME, null);
    }

    /**
     * Méthode pour vérifier une heure dans la table 'RDV'.
     */
    public void verifHeure() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("SELECT heure_Deb FROM " + TABLE_NAME3, null);
    }

}

