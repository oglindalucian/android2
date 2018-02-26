package lucian.example.com.tp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import lucian.example.com.tp2.Data.TacheContrat;
import lucian.example.com.tp2.Data.TacheDBHelper;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDb;
    private TacheAdapter mAdapter;
    // Unique tag required for the intent extra
    public static final String EXTRA_MESSAGE = "lucian.example.com.tp2.extra.MESSAGE";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Unique tag for the intent reply
    public static final int TEXT_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView tacheRecyclerView;

        // Set local attributes to corresponding views
        tacheRecyclerView = (RecyclerView) this.findViewById(R.id.vue_les_taches);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        tacheRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        TacheDBHelper dbHelper = new TacheDBHelper(this);
        mDb = dbHelper.getWritableDatabase(); //obtenir la base de donnees

        Cursor cursor = obtenirTache();
        mAdapter = new TacheAdapter(this, cursor);
        tacheRecyclerView.setAdapter(mAdapter); //charger les donnees dans le recycler view

  /*     tacheRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                 //  position = (long) Recyclerview.getTag();
                   position = Recyclerview.getChildAdapterPosition(ChildView);

                   Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show(); //
                   boolean b = retirerTache(position);
                   // Toast.makeText(MainActivity.this, String.valueOf(b), Toast.LENGTH_SHORT).show();
                    mAdapter.echangerCurseur(obtenirTache());
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
*/
    //lors du swipe de gauche vers la droite - effacer une tache
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target ) {
                return false;
            }

            public  void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                retirerTache(id);
                mAdapter.echangerCurseur(obtenirTache());
            }
        }).attachToRecyclerView(tacheRecyclerView);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //fonction qui permet d'ouvrir une nouvelle activite qui permet de creer une nouvelle tache
    public void ajouterTache(View view) {
        Intent intent = new Intent(this, NouvelleTache.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    //envoie une requette a la BD pour obtenir les taches de la BD
    private Cursor obtenirTache() {
        return mDb.query(
                TacheContrat.Tache.NOM_TABLE,
                null,
                null,
                null,
                null,
                null,
                TacheContrat.Tache.COLONNE_DATE_TACHE

        );
    }

    //retirer une tache
    private boolean retirerTache(long id) {
        return mDb.delete(TacheContrat.Tache.NOM_TABLE,
                TacheContrat.Tache._ID + "=" + id, null) > 0;
    }

    //ajouter une tache
    private long ajouterNouvelleTache (String nom, String description, String dateTache) {
        ContentValues cv = new ContentValues();
        cv.put(TacheContrat.Tache.COLONNE_NOM_TACHE, nom);
        cv.put(TacheContrat.Tache.COLONNE_DESCRIPTION_TACHE, description);
        cv.put((TacheContrat.Tache.COLONNE_DATE_TACHE).toString(), dateTache); //????  transformer date en string
     //   list.add(cv);
        return mDb.insert(TacheContrat.Tache.NOM_TABLE, null, cv);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Test for the right intent reply
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == RESULT_OK) {
                  String nomTache = data.getStringExtra("EXTRA_NOM");
                  String descriptionTache = data.getStringExtra("EXTRA_DESCRIPTION");
                  String dateTache = data.getStringExtra("EXTRA_DATE");
                  if (nomTache != "" && descriptionTache != "" && dateTache != "") {
                      ajouterNouvelleTache(nomTache, descriptionTache, dateTache);
                      mAdapter.echangerCurseur(obtenirTache());
                  }

            }
        }
    }

    /**
     * Lifecycle callback for start.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    /**
     * Lifecycle callback for restart.
     */
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    /**
     * Lifecycle callback for resume.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    /**
     * Lifecycle callback for pause.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    /**
     * Lifecycle callback for stop.
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    /**
     * Lifecycle callback for destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }




}
