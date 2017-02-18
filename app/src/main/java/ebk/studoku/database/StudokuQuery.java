package ebk.studoku.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by E.Batuhan Kaynak on 18.2.2017.
 */

public class StudokuQuery {
    private static StudokuQuery studokuQuery;

    private SQLiteDatabase db;

    private Cursor scheduleCursor;

    private StudokuQuery(Context context) {
        SQLiteOpenHelper studokuDatabaseHelper = new StudokuDatabaseHelper(context);
        db = studokuDatabaseHelper.getWritableDatabase();
        this.db = db;
    }

    public static StudokuQuery getInstance(Context context){
        if(studokuQuery == null){
            studokuQuery = new StudokuQuery(context);
        }
        return studokuQuery;
    }

    public void init() {
        scheduleCursor = db.query("LECTURELIST", new String[]{"LECTURE", "_id"}, null, null, null,
                null, null);
    }

    public Cursor getScheduleCursor() {
        return scheduleCursor;
    }
}
