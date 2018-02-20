package lucian.example.com.tp2.Data;

/**
 * Created by lucian on 2018-02-16.
 */

import lucian.example.com.tp2.Data.TacheContrat.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TacheDBHelper extends SQLiteOpenHelper {

    //private static TacheDBHelper dbInstance; ////

    private final static String DATABASE_NAME = "taches.db";
    private final static int DATABASE_VERSION = 1;

  //  public static synchronized TacheDBHelper getInstance(Context context) { ////

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
     //   if (dbInstance == null) {
           // dbInstance = new TacheDBHelper(context.getApplicationContext());
   //    }
      //  return dbInstance;
   // }

    public TacheDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TACHE_TABLE = "CREATE TABLE " +
                Tache.NOM_TABLE + " (" +
                Tache._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Tache.COLONNE_NOM_TACHE + " TEXT NOT NULL, " +
                Tache.COLONNE_DESCRIPTION_TACHE + " TEXT NOT NULL, " +
                Tache.COLONNE_DATE_TACHE +
                " DATE DEFAULT CURRENT_TIMESTAMP NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_TACHE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tache.NOM_TABLE);
        onCreate(sqLiteDatabase);
        // sqLiteDatabase.execSQL("ALTER TABLE " + Tache.NOM_TABLE + " ADD COLUMN " +
        //  Tache.COLONNE_DESCRIPTION_TACHE + " TEXT NOT NULL ");
    }
}
