package lucian.example.com.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NouvelleTache extends AppCompatActivity {

    EditText editTache;
    EditText editDescription;
    EditText editDate;
    private final static String LOG_TAG = NouvelleTache.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_tache);

        editTache = (EditText) findViewById(R.id.edit_tache) ;
        editDescription = (EditText) findViewById(R.id.edit_description);
        editDate = (EditText) findViewById(R.id.edit_date);
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               showDatePickerDialog(view);
            }
        });
    }



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


        Intent replyIntent = new Intent();
        replyIntent.putExtra("EXTRA_NOM",editTache.getText().toString());
        replyIntent.putExtra("EXTRA_DESCRIPTION",editDescription.getText().toString());
        replyIntent.putExtra("EXTRA_DATE",laDate);

        setResult(RESULT_OK, replyIntent);

        finish();

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
