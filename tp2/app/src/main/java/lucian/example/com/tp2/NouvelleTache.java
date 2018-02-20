package lucian.example.com.tp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lucian.example.com.tp2.Data.TacheContrat;
import lucian.example.com.tp2.Data.TacheDBHelper;

public class NouvelleTache extends AppCompatActivity {

  //  private SQLiteDatabase mDb;
    private TacheAdapter mAdapter; ////
    EditText editTache;
    EditText editDescription;
    EditText editDate;
    public static final String EXTRA_REPLY =
            "lucian.example.com.tp2.extra.REPLY";
    private final static String LOG_TAG = NouvelleTache.class.getSimpleName();
   // Intent intent;
    public ArrayList<ContentValues> list = new ArrayList<ContentValues>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_tache);

        editTache = (EditText) findViewById(R.id.edit_tache) ;
        editDescription = (EditText) findViewById(R.id.edit_description);
        editDate = (EditText) findViewById(R.id.edit_date);

      //  Intent intent = getIntent(); ////

      //  TacheDBHelper dbHelper = new TacheDBHelper(this);
      ////  mDb = dbHelper.getWritableDatabase();

       //// mDb = TacheDBHelper. getInstance(this).getWritableDatabase();////!!!!!!!!!!!!!!!!!!!

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //long startMillis = System.currentTimeMillis();
               // Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
               // builder.appendPath("time");
              //  ContentUris.appendId(builder, startMillis);
              //  intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
               // startActivity(intent);
                showDatePickerDialog(view);
            }
        });

       // Cursor cursor = obtenirTache(); ////
       // mAdapter = new TacheAdapter(this, cursor); ////
    }

   // private long ajouterNouvelleTache (String nom, String description, String dateTache) {
       // ContentValues cv = new ContentValues();
      //  cv.put(TacheContrat.Tache.COLONNE_NOM_TACHE, nom);
     //   cv.put(TacheContrat.Tache.COLONNE_DESCRIPTION_TACHE, description);
   //     cv.put((TacheContrat.Tache.COLONNE_DATE_TACHE).toString(), dateTache); //????  transformer date en string
    //    list.add(cv);
    //   return mDb.insert(TacheContrat.Tache.NOM_TABLE, null, cv);

   // }

    public void ajouterListe(View view) {
        if(editTache.getText().length()==0 || editDescription.getText().length()==0 || editDate.getText().length()==0) {
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String laDate =  dateFormat.format(date);
        try {
            laDate = editDate.getText().toString();
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Erreur lors de la conversion de la date en string" + ex.getMessage());
        }
       /////
        // ajouterNouvelleTache(editTache.getText().toString(),editDescription.getText().toString(),laDate);

       // mAdapter.echangerCurseur(obtenirTache()); ////
       // editTache.clearFocus(); /////
       // editDescription.clearFocus();
      //  editDate.clearFocus();
      //  editTache.getText().clear();
        //editDescription.getText().clear();
       // editDate.getText().clear();

        Intent intent = new Intent(this, MainActivity.class); /////
        Bundle extras = new Bundle();
        extras.putString("EXTRA_NOM",editTache.getText().toString());
        extras.putString("EXTRA_DESCRIPTION",editDescription.getText().toString());
        extras.putString("EXTRA_DATE",laDate);

        intent.putExtras(extras);
        startActivity(intent);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /*
  //  private Cursor obtenirTache() { //////
      //  return mDb.query(
             //   TacheContrat.Tache.NOM_TABLE,
               // null,
              //  null,
             //   null,
             //   null,
                null,
                TacheContrat.Tache.COLONNE_DATE_TACHE

        );
    } */
}
