package lucian.example.com.restopizzaiolo.Data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import lucian.example.com.restopizzaiolo.Data.ReservationContrat.*;

/**
 * Created by lucian on 2018-02-13.
 */

public class ReservationDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "reservation.db";
    private final static int DATABASE_VERSION = 1;

    public ReservationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RESERVATION_TABLE = "CREATE TABLE " +
                Reservation.NOM_TABLE + " (" +
                Reservation._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Reservation.COLONNE_NOM_RESERVATION + " TEXT NOT NULL, " +
                Reservation.COLONNE_NBRE_PERSONNE + " INTEGER NOT NULL, " +
                Reservation.COLONNE_HEURE_RESERVATION +
                " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_RESERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Reservation.NOM_TABLE);
        onCreate(db);
    }
}
