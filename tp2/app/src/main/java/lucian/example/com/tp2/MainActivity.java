package lucian.example.com.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import lucian.example.com.tp2.Data.TacheContrat;
import lucian.example.com.tp2.Data.TacheDBHelper;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDb;
    private TacheAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     //   fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();
          //  }
      //  });

        // final CheckBox checkBox = (CheckBox) findViewById(R.id.chk_box);

        RecyclerView tacheRecyclerView;

        // Set local attributes to corresponding views
        tacheRecyclerView = (RecyclerView) this.findViewById(R.id.vue_les_taches);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        tacheRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TacheDBHelper dbHelper = new TacheDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = obtenirTache();
        mAdapter = new TacheAdapter(this, cursor);
        tacheRecyclerView.setAdapter(mAdapter);


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




    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.CLE_SWITCH,false);
        if(switchPref) {
            // checkBox.setTextSize(getResources().getDimension(R.dimen.grande_police));
        }
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

    public void ajouterTache(View view) {
        Intent intent = new Intent(this, NouvelleTache.class);
        startActivity(intent);
    }

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

   // private long ajouterNouvelleTache (String nom, String dateTache) {
      //  ContentValues cv = new ContentValues();
     //   cv.put(TacheContrat.Tache.COLONNE_NOM_TACHE, nom);
      //  cv.put(String.valueOf(TacheContrat.Tache.COLONNE_DATE_TACHE), dateTache); //????  transformer date en string
     //   return mDb.insert(TacheContrat.Tache.NOM_TABLE, null, cv);

 //   }

    private boolean retirerTache(long id) {
        return mDb.delete(TacheContrat.Tache.NOM_TABLE,
                TacheContrat.Tache._ID + "=" + id, null) > 0;
    }



}
