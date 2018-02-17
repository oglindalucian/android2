package lucian.example.com.restopizzaiolo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import lucian.example.com.restopizzaiolo.Data.*;
import lucian.example.com.restopizzaiolo.Data.ReservationContrat;


public class MainActivity extends AppCompatActivity {

    private ReservationAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText nvNomEditText;
    private EditText nvNombreEditText;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    //ItemTouchHelper itemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView reservationRecyclerView;

        // Set local attributes to corresponding views
        reservationRecyclerView = (RecyclerView) this.findViewById(R.id.vue_les_reservations);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        reservationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an adapter for that cursor to display the data
        //mAdapter = new ReservationAdapter (this);

        // Link the adapter to the RecyclerView
        //reservationRecyclerView.setAdapter(mAdapter);

        ReservationDBHelper dbHelper = new ReservationDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        ReservationDataTemp.insererData(mDb);
        Cursor cursor = obtenirReservation();
        mAdapter = new ReservationAdapter(this, cursor);
        reservationRecyclerView.setAdapter(mAdapter);

        nvNomEditText = (EditText)this.findViewById(R.id.nom_edit_text);
        nvNombreEditText = (EditText)this.findViewById(R.id.nombre_edit_text);

        //itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target ) {
                return false;
            }

            public  void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                retirerReservation(id);
                mAdapter.echangerCurseur(obtenirReservation());
            }
        }).attachToRecyclerView(reservationRecyclerView);
    }




    /**
     * Méthode applée quand l'utilisateur clique sur le bouton Ajouter
     *
     * @param view The calling view (button)
     */
    public void ajouterListe(View view) {
        if(nvNombreEditText.getText().length()==0 || nvNomEditText.getText().length()==0) {
            return;
        }
        int nbPersonne = 1;
        try {
            nbPersonne = Integer.parseInt(nvNombreEditText.getText().toString());

        }
        catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Erreur lors de la conversion en entier" + ex.getMessage());
        }
        ajouterNouvelleReservation(nvNomEditText.getText().toString(),nbPersonne);
        mAdapter.echangerCurseur(obtenirReservation());
        nvNombreEditText.clearFocus();
        nvNomEditText.getText().clear();
        nvNombreEditText.getText().clear();
    }

    private Cursor obtenirReservation() {
        return mDb.query(
                ReservationContrat.Reservation.NOM_TABLE,
                null,
                null,
                null,
                null,
                null,
                ReservationContrat.Reservation.COLONNE_HEURE_RESERVATION

        );
    }

    private long ajouterNouvelleReservation (String nom, int nbPersonne) {
        ContentValues cv = new ContentValues();
        cv.put(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION, nom);
        cv.put(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE, nbPersonne);
        return mDb.insert(ReservationContrat.Reservation.NOM_TABLE, null, cv);

    }

    private boolean retirerReservation(long id) {
        return mDb.delete(ReservationContrat.Reservation.NOM_TABLE,
                ReservationContrat.Reservation._ID + "=" + id, null) > 0;
    }

}
