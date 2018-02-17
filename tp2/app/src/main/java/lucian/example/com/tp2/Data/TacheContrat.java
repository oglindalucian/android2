package lucian.example.com.tp2.Data;

import android.provider.BaseColumns;

/**
 * Created by lucian on 2018-02-16.
 */

public class TacheContrat {
    private TacheContrat() {}

    public static final class Tache implements BaseColumns {
        public static final String NOM_TABLE = "tache";
        public static final String COLONNE_NOM_TACHE = "nomTache";
        public static final String COLONNE_DESCRIPTION_TACHE = "descriptionTache";
        public static final String COLONNE_DATE_TACHE = "dateTache";
    }
}
