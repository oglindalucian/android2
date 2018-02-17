package lucian.example.com.restopizzaiolo;

/**
 * Created by lucian on 2018-02-13.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import lucian.example.com.restopizzaiolo.Data.ReservationContrat;


public class ReservationAdapter extends RecyclerView.Adapter <ReservationAdapter.ReservationViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructeur utilisant context and the db cursor
     *
     * @param context the calling context/activity
     */
    public ReservationAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    //@Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Pour obtenir le layout d'un élément du RecyclerView
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.element_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

   // @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        String nom = mCursor.getString(mCursor.getColumnIndex(ReservationContrat.Reservation.COLONNE_NOM_RESERVATION));
        int nbrePersonne = mCursor.getInt(mCursor.getColumnIndex(ReservationContrat.Reservation.COLONNE_NBRE_PERSONNE));
        holder.nomTextView.setText(nom);
        holder.nombrePersonneTextView.setText(String.valueOf(nbrePersonne));
        long id = mCursor.getLong(mCursor.getColumnIndex(ReservationContrat.Reservation._ID));
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
    class ReservationViewHolder extends RecyclerView.ViewHolder {

        // Pour afficher le nom de la reservation
        TextView nomTextView;
        // Pour afficher le nombre de place pour la reservation
        TextView nombrePersonneTextView;

        /**
         * Constructeur du ViewHolder danslequel on obtient les références des TextView utilisée
         *
         * @param itemView The View that you inflated in
         *                 {@link ReservationAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public ReservationViewHolder(View itemView) {
            super(itemView);
            nomTextView = (TextView) itemView.findViewById(R.id.nom_text_view);
            nombrePersonneTextView = (TextView) itemView.findViewById(R.id.nombre_personne_text_view);
        }

    }
}
