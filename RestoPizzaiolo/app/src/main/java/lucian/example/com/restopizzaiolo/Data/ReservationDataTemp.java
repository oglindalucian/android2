package lucian.example.com.restopizzaiolo.Data;

/**
 * Created by lucian on 2018-02-13.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReservationDataTemp {

    public static void insererData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, "John");
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, 12);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, "Tim");
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, "Jessica");
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, "Larry");
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, "Kim");
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, 45);
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (ReservationContrat.Reservation.NOM_TABLE,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(ReservationContrat.Reservation.NOM_TABLE, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}
