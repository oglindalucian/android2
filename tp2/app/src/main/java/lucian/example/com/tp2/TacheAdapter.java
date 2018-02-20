package lucian.example.com.tp2;

/**
 * Created by lucian on 2018-02-16.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;

import lucian.example.com.tp2.Data.TacheContrat;

public class TacheAdapter extends RecyclerView.Adapter <TacheAdapter.TacheViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructeur utilisant context and the db cursor
     *
     * @param context the calling context/activity
     */
    public TacheAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    //@Override
    public TacheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Pour obtenir le layout d'un élément du RecyclerView
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.element_liste, parent, false);
        return new TacheViewHolder(view);
    }

    // @Override
    public void onBindViewHolder(TacheViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        String nom = mCursor.getString(mCursor.getColumnIndex(TacheContrat.Tache.COLONNE_NOM_TACHE));
        String dateTache = mCursor.getString(mCursor.getColumnIndex(TacheContrat.Tache.COLONNE_DATE_TACHE));
        //String descrTache = mCursor.getString(mCursor.getColumnIndex(TacheContrat.Tache.COLONNE_DESCRIPTION_TACHE)); //a enlever
        holder.nomTacheCheckBox.setText(nom);
        holder.dateTextView.setText(dateTache); //a changer
        long id = mCursor.getLong(mCursor.getColumnIndex(TacheContrat.Tache._ID));
        holder.itemView.setTag(id);
    }


    // @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void echangerCurseur(Cursor newCursor) {
        if(mCursor!=null) mCursor.close();
        mCursor = newCursor;
        if(newCursor!=null)
            this.notifyDataSetChanged();
    }


    /**
     * Classe interne permettant de retenir (hold) la vue à afficher dans un élément de la liste
     * RecyclerView. Ce qui permet de conserver une référence sur les vues
     *  et ne pas avoir à utiliser findViewById à chaque fois
     */
    class TacheViewHolder extends RecyclerView.ViewHolder {

        // Pour afficher le nom de la tache
        CheckBox nomTacheCheckBox;
        // Pour afficher la date de la tache
        TextView dateTextView;


        public TacheViewHolder(View itemView) {
            super(itemView);
            nomTacheCheckBox = (CheckBox) itemView.findViewById(R.id.chk_box);
            dateTextView = (TextView) itemView.findViewById(R.id.text_view_date);
        }
    }
}
