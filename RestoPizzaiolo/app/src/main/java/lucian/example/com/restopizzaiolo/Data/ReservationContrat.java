package lucian.example.com.restopizzaiolo.Data;

import android.provider.BaseColumns;

/**
 * Created by lucian on 2018-02-13.
 */

public class ReservationContrat {
    private ReservationContrat() {}

    public static final class Reservation implements BaseColumns {
        public static final String NOM_TABLE = "reservation";
        public static final String COLONNE_NOM_RESERVATION = "nomReservation";
        public static final String COLONNE_NBRE_PERSONNE = "nombrePersonnes";
        public static final String COLONNE_HEURE_RESERVATION = "heureReservation";
    }
}
