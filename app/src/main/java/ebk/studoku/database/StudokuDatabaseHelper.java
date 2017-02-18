package ebk.studoku.database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by E.Batuhan Kaynak on 4.7.2016.
 */
public class StudokuDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Srs"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    public StudokuDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            Log.i("in update", "in create");
            db.execSQL("CREATE TABLE SRS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DATE TEXT, "
                    + "LEVEL INTEGER, "
                    + "SRS INTEGER, "
                    + "NOTE TEXT, "
                    + "GRACE INTEGER);");
            db.execSQL("CREATE TABLE SCHEDULE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "MON TEXT, "
                    + "TUE TEXT, "
                    + "WED TEXT, "
                    + "THU TEXT, "
                    + "FRI TEXT, "
                    + "START_TIME TEXT, "
                    + "END_TIME TEXT);");
            for(int i = 0; i < 8; i++){
                db.execSQL("INSERT INTO SCHEDULE DEFAULT VALUES");
            }
            db.execSQL("CREATE TABLE LECTURELIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "LECTURE TEXT);");
        }
        if (oldVersion < 2) {

        }
    }

    //Helper method for AndroidDatabaseManager
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
}
